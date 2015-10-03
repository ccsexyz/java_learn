//package threaded;

import java.io.*;
import java.net.*;
import java.util.*;

public class ThreadedEchoServer
{
    public static void main(String[] args) throws IOException
    {
        int i = 1;
        try(ServerSocket s = new ServerSocket(9377))
        {
            while(true)
            {
                Socket incoming = s.accept();
                System.out.println("Spawning " + i);
                Runnable r = new ThreadedEchoHandler(incoming);
                Thread t = new Thread(r);
                t.start();
                i++;
            }
        }
    }
}

class ThreadedEchoHandler implements Runnable
{
    private Socket incoming;

    public ThreadedEchoHandler(Socket i)
    {
        incoming = i;
    }

    public void run()
    {
        try
        {
            try
            {
                InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream();

                Scanner in = new Scanner(inStream);
                PrintWriter out = new PrintWriter(outStream, true); //auto Flush

                out.println("Hello Enter BYE to exit!");

                boolean done = false;
                while(!done && in.hasNextLine())
                {
                    String line = in.nextLine();
                    out.println("Echo: " + line);
                    if(line.trim().equals("BYE")) done = true;
                    //System.out.println(line);
                }
            }
            finally
            {
                incoming.close();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
