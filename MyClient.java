import java.io.*;
import java.net.*;
public class MyClient
{
   public static void main(String args[]){
      Socket cliSocket = null;
      OutputStream os;
      DataOutputStream dos;
      InputStream is;
      DataInputStream dis;
	  BufferedReader keyInput;

      String readBuf = null, writeBuf = null;
      String host = "localhost";
//      byte[] data = null;

      try{
         cliSocket = new Socket(host, 5004);
         System.out.println("Connection successful");

         os = cliSocket.getOutputStream();
         dos = new DataOutputStream(os);
         is = cliSocket.getInputStream();
         dis = new DataInputStream(is);

         keyInput = new BufferedReader(new InputStreamReader(System.in));

         System.out.print("Input ID: ");
         writeBuf = keyInput.readLine();

         Message signUpRequest = MessageManager.OwnerSignUpPwRequest();
         signUpRequest.setId(writeBuf);
         signUpRequest.setIdLength((byte) writeBuf.length());

         System.out.print("Input PW: ");
         writeBuf = keyInput.readLine();

         signUpRequest.setPw(writeBuf);
         signUpRequest.setPwLength((byte) writeBuf.length());

         byte[] packet = Packet.makePacket(signUpRequest);

         dos.write(packet);

//         dos.writeByte(signUpRequest.getServiceType());
//         dos.writeByte(signUpRequest.getSubType());
//         dos.writeByte(signUpRequest.getCode());
//         dos.writeInt(signUpRequest.getLength());





//
//		 // =============================================

//
//            subType = 'I';
//            data = writeBuf.getBytes();
//
//            dos.writeChar(subType);
//            dos.writeInt(data.length);
//            dos.write(data);
//            dos.flush();
//
//      // =============================================// =============================================
//
//         System.out.print("Input PW: ");
//         writeBuf = keyInput.readLine();
//
//         subType = 'P';
//         data = writeBuf.getBytes();
//
//         dos.writeChar(subType);
//         dos.writeInt(data.length);
//         dos.write(data);
//         dos.flush();

      }catch(UnknownHostException e){
         System.err.println("Server not found");
      }catch(IOException e){
         System.err.println(e);
      }finally{
         try{
            cliSocket.close();
         }catch(IOException e){
            System.out.println(e);
         }
      }
   }
}