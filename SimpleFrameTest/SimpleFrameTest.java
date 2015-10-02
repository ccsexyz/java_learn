//package simpleFrame;

import java.awt.*;
import javax.swing.*;

public class SimpleFrameTest
{
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                //SimpleFrame frame = new SimpleFrame();
                JFrame frame = new NotHelloWorldFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //frame.setUndecorated(true);
                frame.setTitle("It was made by ccsexyz");
                frame.setResizable(true);
                frame.setVisible(true);
            }
        });
    }
}

class SimpleFrame extends JFrame
{
    private static final int DEFAULT_WIDTH = 900;
    private static final int DEFAULT_HEIGHT = 600;

    public SimpleFrame()
    {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        setSize(screenWidth / 2, screenHeight / 2);
        setLocationByPlatform(true);

        Image img = new ImageIcon("./1.gif").getImage();
        setIconImage(img);
    }
}

class NotHelloWorldFrame extends JFrame
{
    public NotHelloWorldFrame()
    {
        add(new NotHelloWorldComponent());
        pack();
    }
}

class NotHelloWorldComponent extends JComponent
{
    public static final int MESSAGE_X = 75;
    public static final int MESSAGE_Y = 100;

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public void paintComponent(Graphics g)
    {
        g.drawString("Not a Hello World program!", MESSAGE_X, MESSAGE_Y);
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}
