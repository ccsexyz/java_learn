package client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Johnson on 2015/11/7.
 */
public class Login extends JFrame{
    private JPanel contentPane;
    private JTextField textField;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Login() {
        setTitle("chat room");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(350, 250, 450, 300);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textField = new JTextField();
        textField.setBounds(128, 153, 104, 21);
        textField.setOpaque(false);
        contentPane.add(textField);
        textField.setColumns(10);

        final JButton btnNewButton = new JButton();
        btnNewButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\images\\login.jpg"));
        btnNewButton.setBounds(246, 227, 50, 25);
        getRootPane().setDefaultButton(btnNewButton);
        contentPane.add(btnNewButton);

        final JLabel newLabel = new JLabel();
        newLabel.setBounds(60, 220, 151, 21);
        newLabel.setForeground(Color.red);
        getContentPane().add(newLabel);

        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField.getText();
                if(username.length() == 0) return;

                try {
                    Socket client = new Socket("localhost", 9377);

                    btnNewButton.setEnabled(false);
                    chatRoom frame = new chatRoom(username, client);
                    frame.setVisible(true);
                    setVisible(false);
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                    errorTip("连接错误,请重试!");
                } catch (IOException e1) {
                    e1.printStackTrace();
                    errorTip("连接错误,请重试!");
                }
            }
        });


    }

    protected void errorTip(String str) {
        JOptionPane.showMessageDialog(contentPane, str, "Error Message",
                JOptionPane.ERROR_MESSAGE);
        textField.setText("");
        textField.requestFocus();
    }
}
