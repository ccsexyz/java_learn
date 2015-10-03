//package interruptible

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import java.io.*;
import java.nio.channels.*;
import javax.swing.*;

public class InterruptibleSocketTest
{
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                JFrame frame = new interruptibleSocketFrame();
                frame.setTitle("interruptibleSocketTest");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

class interruptibleSocketFrame extends JFrame
{
    public static final int TEXT_ROWS = 20;
    public static final int TEXT_COLUMNS = 20;

    private Scanner in;
    private JButton interruptibleButton;
    private JButton blockingButton;
    private JButton cancelButton;
    private JTextArea messages;
    private TestServer server;
    private Thread connectThread;

    public interruptibleSocketFrame()
    {
        JPanel northPanel = new JPanel();
        add(northPanel, BorderLayout.NORTH);

        messages = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        add(new JScrollPane(messages));

        interruptibleButton = new JButton("Interruptible");
        blockingButton = new JButton("Blocking");
        northPanel.add(interruptibleButton);
        northPanel.add(blockingButton);

        interruptibleButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                interruptibleButton.setEnabled(false);
                blockingButton.setEnabled(false);
                cancelButton.setEnabled(true);
                connectThread = new Thread(new Runnable()
                {
                    public void run()
                    {
                        try
                        {
                            connectInterruptibly();
                        }
                        catch(IOException e)
                        {
                            messages.append("\nInterruptibleSocketTest.connectInterruptibly: " + e);
                        }
                    }
                });
                connectThread.start();
            }
        });

        blockingButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                interruptibleButton.setEnabled(false);
                blockingButton.setEnabled(false);
                cancelButton.setEnabled(true);
                connectThread = new Thread(new Runnable()
                {
                    public void run()
                    {
                        try
                        {
                            connectBlocking();
                        }
                        catch(IOException e)
                        {
                            messages.append("\nInterruptibaleSocketTest.connectBlocking: " + e);
                        }
                    }                    ;
                });
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.setEnabled(false);
        northPanel.add(cancelButton);
        cancelButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                connectThread.interrupt();
                cancelButton.setEnabled(false);
            }
        });

        server = new TestServer();
        new Thread(server).start();
        pack();
    }

    public void connectInterruptibly() throws IOException
    {
        messages.append("Interruptible:\n");
        try(SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 9377)))
        {
            in = new Scanner(channel);
            while(!Thread.currentThread().isInterrupted())
            {
                messages.append("Reading ");
                if(in.hasNextLine())
                {
                    String line = in.nextLine();
                    messages.append(line);
                    messages.append("\n");
                }
            }
        }
        finally
        {
            EventQueue.invokeLater(new Runnable()
            {
                public void run()
                {
                    messages.append("channel closed\n");
                    interruptibleButton.setEnabled(true);
                    blockingButton.setEnabled(true);
                }
            });
        }
    }

    public void connectBlocking() throws IOException
    {
        messages.append("Blocking:\n");
        try(Socket sock = new Socket("localhost", 9377))
        {
            in = new Scanner(sock.getInputStream());
            while(!Thread.currentThread().isInterrupted())
            {
                messages.append("Reading ");
                if(in.hasNextLine())
                {
                    String line = in.nextLine();
                    messages.append(line);
                    messages.append("\n");
                }
            }
        }
        finally
        {
            EventQueue.invokeLater(new Runnable()
            {
                public void run()
                {
                    messages.append("Socket closed!\n");
                    interruptibleButton.setEnabled(true);
                    blockingButton.setEnabled(true);
                }
            });
        }
    }

    class TestServer implements Runnable
    {
        public void run()
        {
            try
            {
                ServerSocket s = new ServerSocket(9377);

                while(true)
                {
                    Socket incoming = s.accept();
                    Runnable r = new TestServerHandler(incoming);
                    Thread t = new Thread(r);
                    t.start();
                }
            }
            catch(IOException e)
            {
                messages.append("\nTestServer.run: " + e);
            }
        }
    }

    class TestServerHandler implements Runnable
    {
        private Socket incoming;
        private int counter;

        public TestServerHandler(Socket i)
        {
            incoming = i;
        }

        public void run()
        {
            try
            {
                try
                {
                    OutputStream outStream = incoming.getOutputStream();
                    PrintWriter out = new PrintWriter(outStream, true);
                    while(counter < 100)
                    {
                        counter++;
                        if(counter <= 10)
                            out.println(counter);

                        Thread.sleep(100);
                    }
                }
                finally
                {
                    incoming.close();
                    messages.append("Closing server\n");
                }
            }
            catch(Exception e)
            {
                messages.append("\nTestServerHandler.run: " + e);
            }
        }
    }
}
