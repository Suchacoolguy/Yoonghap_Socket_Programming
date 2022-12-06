import java.io.*;
import java.net.*;
public class testClient
{
    public static void main(String args[]){
        ServerSocket listenSocket = null;
        Socket commSocket = null;
        InputStream is;
        DataInputStream dis;
        String readBuf;
        char type = '\0';
        int length = 0;
        byte[] data = null;

        try{
            listenSocket = new ServerSocket(5004);
            System.out.println("Server socket created.");

            commSocket = listenSocket.accept();
            System.out.println("Client connected.");

            is = commSocket.getInputStream();
            dis = new DataInputStream(is);

            while(true){
                type = dis.readChar();
                length = dis.readInt();
                System.out.println("Type: " + type + ", Length: " + length);

                if(type == 'S' && length > 0){
                    data = new byte[length];
                    //dis.readFully(data);
                    dis.readFully(data, 0, length);
                }

                readBuf = new String(data);
                System.out.println(readBuf);
                System.out.println();
                if(readBuf.equals("quit")) break;
            }
            commSocket.close();
        }catch(IOException e){
            System.err.println(e);
        }finally{
            if(listenSocket != null){
                try{
                    listenSocket.close();
                }catch(IOException e){
                    System.out.println(e);
                }
            }
        }
    }
}
