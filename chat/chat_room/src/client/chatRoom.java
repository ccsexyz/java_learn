package client;

import com.Bean;
import com.beanType;
import com.clientBean;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by Johnson on 2015/11/7.
 */

class cellRenderer extends JLabel implements ListCellRenderer {
    cellRenderer() {
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        if(value != null) {
            setText(value.toString());
            setIcon(new ImageIcon(System.getProperty("user.dir") + "\\images\\1.jpg"));
        }
        if(isSelected) {
            setBackground(new Color(255, 255, 153));
            setForeground(Color.black);
        } else {
            setBackground(Color.white);
            setForeground(Color.black);
        }
        setEnabled(list.isEnabled());
        setFont(new Font("sdf", Font.ROMAN_BASELINE, 13));
        setOpaque(true);
        return this;
    }
}

class UUListModel extends AbstractListModel {
    private Vector vs;

    public UUListModel(Vector vs) {
        this.vs = vs;
    }

    @Override
    public Object getElementAt(int index) {
        return vs.get(index);
    }

    @Override
    public int getSize(){
        return vs.size();
    }
}

public class chatRoom extends JFrame{
    private static final long serialVersionUID = 6129126482250125466L;

    private static JPanel contentPane;
    private static Socket clientSocket;
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;
    private static String name;
    private static JTextArea textArea;
    private static AbstractListModel listmodel;
    private static JList list;
    private static String filePath;
    private static JLabel lblNewLabel;
    private static JProgressBar progressBar;
    private static Vector onlines;
    private static boolean isSendFile = false;
    private static boolean isReceiveFile = false;

    // 声音
    private static File file, file2;
    private static URL cb, cb2;
    private static AudioClip aau, aau2;

    public chatRoom(String username, Socket client) {
        name = username;
        clientSocket = client;
        onlines = new Vector();

        SwingUtilities.updateComponentTreeUI(this);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch(ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch(InstantiationException e1) {
            e1.printStackTrace();
        } catch(IllegalAccessException e1) {
            e1.printStackTrace();
        } catch(UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }

        setTitle(name);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(200, 100, 688, 510);
        contentPane = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(new ImageIcon(System.getProperty("user.dir") + "\\images\\chat_room1.jpg").getImage(),
                        0, 0, getWidth(), getHeight(), null);
            }
        };
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 10, 410, 300);
        getContentPane().add(scrollPane);

        textArea = new JTextArea();
        textArea.setEnabled(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("sdf", Font.BOLD, 13));
        scrollPane.setViewportView(textArea);

        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(10, 347, 411, 97);
        getContentPane().add(scrollPane2);

        final JTextArea textArea2 = new JTextArea();
        textArea2.setLineWrap(true);
        textArea2.setWrapStyleWord(true);
        scrollPane2.setViewportView(textArea2);

        final JButton closeButton = new JButton("关闭");
        closeButton.setBounds(214, 448, 60, 30);
        getContentPane().add(closeButton);

        JButton sendButton = new JButton("发送");
        sendButton.setBounds(313, 448, 60, 30);
        getRootPane().setDefaultButton(sendButton);
        getContentPane().add(sendButton);

        listmodel = new UUListModel(onlines);
        list = new JList(listmodel);
        list.setCellRenderer(new cellRenderer());
        list.setOpaque(false);
        Border etch = BorderFactory.createEtchedBorder();
        list.setBorder(BorderFactory.createTitledBorder(etch, "<" +
            name + ">" + "在线客户:", TitledBorder.LEADING, TitledBorder.TOP,
                new Font("sdf", Font.BOLD, 20), Color.green));

        JScrollPane scrollPane3 = new JScrollPane(list);
        scrollPane3.setBounds(430, 10, 245, 375);
        scrollPane3.setOpaque(false);
        scrollPane3.getViewport().setOpaque(false);
        getContentPane().add(scrollPane3);

        try {
            oos = new ObjectOutputStream(clientSocket.getOutputStream());

            Bean bean = new Bean();
            bean.setType(beanType.login);
            bean.setName(name);
            bean.setTimer(getTimer());
            System.out.println("try to write object!");
            System.out.print(bean.toString());
            System.out.println();
            oos.writeObject(bean);
            oos.flush();
            System.out.println("successful write object!\n");

            file = new File(System.getProperty("user.dir") + "\\sounds\\ooo.wav");
            cb = file.toURL();
            aau = Applet.newAudioClip(cb);

            file2 = new File(System.getProperty("user.dir") + "\\sounds\\ding.wav");
            cb2 = file2.toURL();
            aau2 = Applet.newAudioClip(cb2);

            new clientInputThread().start();

        } catch(IOException e) {
            System.out.println("nothing!\n");
            e.printStackTrace();
        }

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String info = textArea2.getText();

                Bean clientBean = new Bean();
                clientBean.setType(beanType.message);
                clientBean.setName(name);
                clientBean.setTimer(getTimer());
                clientBean.setInfo(info);

                //textArea.append(getTimer() + ":\r\n" + info + "\r\n");

                sendMessage(clientBean);
                textArea2.setText(null);
                textArea2.requestFocus();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }

    class clientInputThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    ois = new ObjectInputStream(clientSocket.getInputStream());
                    final Bean bean = (Bean)ois.readObject();
                    switch (bean.getType()) {
                        case login: {
                            onlines.clear();
                            HashSet<String> clients = bean.getClients();
                            Iterator<String> it = clients.iterator();
                            while (it.hasNext()) {
                                String str = it.next();
                                if(name.equals(str)) {
                                    onlines.add(str + "(me)");
                                } else {
                                    onlines.add(str);
                                }
                            }

                            listmodel = new UUListModel(onlines);
                            list.setModel(listmodel);
                            aau2.play();
                            textArea.append(bean.getInfo() + "\r\n");
                            //textArea.selectAll();
                            break;
                        }
                        case offline: {
                            break;
                        }
                        case message: {
                            String info = bean.getTimer() + " " + bean.getName()
                                    + ":\r\n";
                            aau.play();
                            textArea.append(info + bean.getInfo() + "\r\n");
                            textArea.selectAll();
                            break;
                        }
                        case trySendFile: {
                            break;
                        }
                        case toRecvFile: {
                            break;
                        }
                        default: {
                            textArea.append(bean.getInfo() + "\r\n");
                            break;
                        }
                    }
                }
            } catch(IOException e) {
                e.printStackTrace();
            } catch(ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if(clientSocket != null) {
                    try {
                        clientSocket.close();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }

                System.exit(0);
            }
        }
    }

    private void sendMessage(Bean clientBean) {
        try {
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.writeObject(clientBean);
            oos.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static String getTimer() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
