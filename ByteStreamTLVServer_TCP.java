import java.io.*;
import java.net.*;
public class ByteStreamTLVServer_TCP
{
   public static void main(String args[]){
      ServerSocket listenSocket = null;
      Socket commSocket = null;
      InputStream is;
      DataInputStream dis;
      OutputStream os;
      DataOutputStream dos;
      String readBuf;
      char type = '\0';
      int idLength = 0;
      byte[] data = null;

      try{
		 listenSocket = new ServerSocket(5004);
         System.out.println("Server socket created.");

         commSocket = listenSocket.accept();
         System.out.println("Client connected.");

         is = commSocket.getInputStream();
         dis = new DataInputStream(is);
         os = commSocket.getOutputStream();
         dos = new DataOutputStream(os);

            type = dis.readChar();
            idLength = dis.readInt();
            System.out.println("Type: " + type + ", Length: " + idLength);

            if(type == 'S' && idLength > 0){
               data = new byte[idLength];
               //dis.readFully(data);
               dis.readFully(data, 0, idLength);
            }

            readBuf = new String(data);
            System.out.println(readBuf);
            System.out.println();

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