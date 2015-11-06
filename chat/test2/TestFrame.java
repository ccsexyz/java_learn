import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class TestFrame extends JFrame
{
    private JPanel contentPane;
    public JTree tree;
    public JButton sendBtn;
    public JButton closeBtn;
    public JTextPane textSendPane;   //发送框
    public JTextPane textRecvPane;   //接收框
    public JTextArea textNoticeArea; //公告

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    TestFrame frame = new TestFrame();
                    frame.setVisible(true);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        //System.out.println("Hello World!");
    }

    public TestFrame()
    {
        setTitle("TestFrame for Chat");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 712, 576);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 153, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JPanel panelRight = new JPanel();
        JPanel panelLeft = new JPanel();

        panelLeft.setBackground(new Color(204, 255, 204));
        GroupLayout gl_contentPane = new GroupLayout(contentPane);

        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                    .addComponent(panelLeft, GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(panelRight, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE))
        );

        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addComponent(panelRight, GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
                .addComponent(panelLeft, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
        );

        JPanel panelTop = new JPanel();
        panelTop.setBackground(new Color(0, 153, 255));

        JPanel panelMid = new JPanel();
        //panelMid.setBackground(new Color(0, 153, 255));

        JPanel panelBottom = new JPanel();
        panelBottom.setBackground(new Color(204, 255, 255));

        GroupLayout gl_panelLeft = new GroupLayout(panelLeft);
        gl_panelLeft.setHorizontalGroup(
            gl_panelLeft.createParallelGroup(Alignment.LEADING)
                .addComponent(panelBottom, GroupLayout.PREFERRED_SIZE, 476, Short.MAX_VALUE)
                .addComponent(panelTop, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                .addComponent(panelMid, GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
        );

        gl_panelLeft.setVerticalGroup(
            gl_panelLeft.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panelLeft.createSequentialGroup()
                    .addComponent(panelTop, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(panelMid, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(panelBottom, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE))
        );

        panelTop.setLayout(null);

        JLabel headImgLabel = new JLabel("");
        headImgLabel.setIcon(new ImageIcon());
        headImgLabel.setBounds(6, 6, 64, 64);
        panelTop.add(headImgLabel);

        JLabel videoLabel = new JLabel("");
        videoLabel.setIcon(new ImageIcon());
        videoLabel.setBounds(82, 16, 54, 54);
        panelTop.add(videoLabel);

        JLabel voiceLabel = new JLabel("");
        voiceLabel.setIcon(new ImageIcon());
        voiceLabel.setBounds(138, 16, 54, 54);
        panelTop.add(voiceLabel);

        JLabel sendFileLabel = new JLabel("");
        sendFileLabel.setIcon(new ImageIcon());
        sendFileLabel.setBounds(193, 16, 54, 54);
        panelTop.add(sendFileLabel);

        panelMid.setLayout(new BorderLayout(0, 0));

        textRecvPane = new JTextPane();
        panelMid.add(textRecvPane);

        textSendPane = new JTextPane();

        sendBtn = new JButton("发送信息");
        sendBtn.setFont(new Font("Monospcaed", Font.PLAIN, 18));

        closeBtn = new JButton("关闭窗口");
        closeBtn.setFont(new Font("Monospcaed", Font.PLAIN, 18));

        JLabel textLabel = new JLabel("");
        textLabel.setIcon(new ImageIcon());

        JLabel faceLabel = new JLabel("");
        faceLabel.setIcon(new ImageIcon());

        JLabel musicLabel = new JLabel("");
        musicLabel.setIcon(new ImageIcon());

        JLabel imgLabel = new JLabel("");
        imgLabel.setIcon(new ImageIcon());

        JLabel screenCutLabel = new JLabel("");
        screenCutLabel.setIcon(new ImageIcon());

        GroupLayout gl_panelBottom = new GroupLayout(panelBottom);
        gl_panelBottom.setHorizontalGroup(
            gl_panelBottom.createParallelGroup(Alignment.TRAILING)
                .addGroup(gl_panelBottom.createSequentialGroup()
                    .addContainerGap(285, Short.MAX_VALUE)
                    .addComponent(closeBtn, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(sendBtn, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                    .addGap(15))
                .addComponent(textSendPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                .addGroup(Alignment.LEADING, gl_panelBottom.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(textLabel)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(faceLabel)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(musicLabel)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(imgLabel)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(screenCutLabel)
                    .addContainerGap(258, Short.MAX_VALUE))
        );
        gl_panelBottom.setVerticalGroup(
            gl_panelBottom.createParallelGroup(Alignment.LEADING)
                .addGroup(Alignment.TRAILING, gl_panelBottom.createSequentialGroup()
                    .addContainerGap(24, Short.MAX_VALUE)
                    .addGroup(gl_panelBottom.createParallelGroup(Alignment.LEADING)
                        .addComponent(textLabel)
                        .addComponent(faceLabel)
                        .addComponent(musicLabel)
                        .addComponent(imgLabel)
                        .addComponent(screenCutLabel))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(textSendPane, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                    .addGap(18)
                    .addGroup(gl_panelBottom.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(sendBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(closeBtn, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap())
        );

        panelBottom.setLayout(gl_panelBottom);
        panelLeft.setLayout(gl_panelLeft);

        JPanel panelNotice = new JPanel();
        panelNotice.setBackground(new Color(0, 153, 255));
        panelNotice.setBorder(new TitledBorder(null, "公告", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, Color.RED));

        JScrollPane scrollPane = new JScrollPane();
        panelNotice.setLayout(new BorderLayout(0, 0));

        textNoticeArea = new JTextArea();
        textNoticeArea.setLineWrap(true);
        textNoticeArea.setForeground(new Color(255, 0, 0));
        textNoticeArea.setText("nothing serious!");
        textNoticeArea.setFont(new Font("Monospcaed", Font.BOLD, 18));
        textNoticeArea.setBackground(new Color(255, 204, 0));
        textNoticeArea.setEnabled(false);

        panelNotice.add(textNoticeArea, BorderLayout.CENTER);
        GroupLayout gl_panelRight = new GroupLayout(panelRight);

        gl_panelRight.setHorizontalGroup(
            gl_panelRight.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panelRight.createSequentialGroup()
                    .addGap(0)
                    .addGroup(gl_panelRight.createParallelGroup(Alignment.LEADING)
                        .addComponent(panelNotice, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE))
                    .addGap(0))
        );

        gl_panelRight.setVerticalGroup(
            gl_panelRight.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panelRight.createSequentialGroup()
                    .addComponent(panelNotice, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE))
        );

        tree = new JTree();
        tree.setRootVisible(false);
        scrollPane.setViewportView(tree);
        panelRight.setLayout(gl_panelRight);
        contentPane.setLayout(gl_contentPane);
    }
}
