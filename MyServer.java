import java.io.*;
import java.net.*;
public class MyServer {
   ServerSocket listenSocket = null;
   Socket commSocket = null;
   InputStream is;
   DataInputStream dis;
   OutputStream os;
   DataOutputStream dos;
   String readBuf;
   byte serviceType = '\0';
   byte subType = '\0';
   byte code = '\0';
   byte idLength = 0;
   byte pwLength = 0;

   byte[] data = null;
   User user = new User();
   Message message;

   public Message receiveMessage() {
      try {
         listenSocket = new ServerSocket(5004);
         System.out.println("Server socket created.");

         commSocket = listenSocket.accept();
         System.out.println("Client connected.");

         is = commSocket.getInputStream();
         dis = new DataInputStream(is);
         os = commSocket.getOutputStream();
         dos = new DataOutputStream(os);

         subType = dis.readByte();
         code = dis.readByte();
         idLength = dis.readByte();
         pwLength = dis.readByte();
         short totalLen = dis.readShort();
         data = new byte[idLength];
         dis.readFully(data, 0, idLength);
         String id = new String(data);

         data = new byte[pwLength];
         dis.readFully(data, 0, pwLength);
         String pw = new String(data);

         System.out.println("Sub Type: " + subType);
         System.out.println("Code: " + code);
         System.out.println("ID: " + id);
         System.out.println("PW: " + pw);


//
//         message = new OwnerMessage();
//         message.setServiceType(serviceType);
//         message.setSubType(subType);
//         message.setCode(code);
//         message.setLength(length);
//
//         if (length > 0)
//         {
//            data = new byte[length];
//            dis.readFully(data, 0, length);
//         }

//         readBuf = new String(data);

         // ============================================================

//
//         while (true) {
//            serviceType = dis.readChar();
//            length = dis.readInt();
//            System.out.println("Type: " + serviceType + ", Length: " + length);
//
//            if (serviceType == 'I' && length > 0) {
//               data = new byte[length];
//               //dis.readFully(data);
//               dis.readFully(data, 0, length);
//               user.setId(new String(data));
//            }
//
//            if (serviceType == 'P' && length > 0) {
//               data = new byte[length];
//               //dis.readFully(data);
//               dis.readFully(data, 0, length);
//               user.setPw(new String(data));
//            }
//
//            readBuf = new String(data);
//            System.out.println(readBuf);
//            System.out.println();
//            if (serviceType == 'P') break;
//         }
         commSocket.close();
      } catch (IOException e) {
         System.err.println(e);
      } finally {
         if (listenSocket != null) {
            try {
               listenSocket.close();
            } catch (IOException e) {
               System.out.println(e);
            }
         }
      }
      return message;
   }
}

class Reader extends Thread
{
   private Socket commSocket;

   Reader(Socket commSocket){
      this.commSocket = commSocket;
   }

   public void run(){
      InputStream is;
      BufferedReader br;
      String readBuf;

      try{
         is = commSocket.getInputStream();
         br = new BufferedReader(new InputStreamReader(is));

         while((readBuf = br.readLine()) != null ){
            System.out.println("Client: " + readBuf);
            System.out.println();
         }
      }catch(IOException e){
         System.err.println(e);
      }
   }
}

class Sender extends Thread
{
   private Socket commSocket;

   Sender(Socket commSocket){
      this.commSocket = commSocket;
   }

   public void run(){
      OutputStream os;
      BufferedReader keyInput;
      BufferedWriter bw;
      String writeBuf;

      try{
         os = commSocket.getOutputStream();
         bw = new BufferedWriter(new OutputStreamWriter(os));

         keyInput = new BufferedReader(new InputStreamReader(System.in));

         while(true){
            writeBuf = keyInput.readLine();
            bw.write(writeBuf+"\r\n");
            bw.flush();

            if(writeBuf.equals("quit")) break;
         }
      }catch(IOException e){
         System.err.println(e);
      }
   }
}