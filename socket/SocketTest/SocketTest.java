//pockage socket;

import java.io.*;
import java.net.*;
import java.util.*;

public class SocketTest
{
    public static void main(String[] args) throws IOException
    {
        if(args.length != 2) {
            System.out.println("usage: ./SocketTest domain port!");
            System.exit(1);
        }
        try(Socket s = new Socket(args[0], Integer.parseInt(args[1])))
        {
            InputStream inStream = s.getInputStream();
            Scanner in = new Scanner(inStream);

            while(in.hasNextLine())
            {
                String line = in.nextLine();
                System.out.println(line);
            }
        }
    }
}
