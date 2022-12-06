import java.io.IOException;
import java.net.ServerSocket;
import java.sql.ClientInfoStatus;

public class Server_Main {
    public static void main(String[] args) {

        MyServer server = new MyServer();
        server.receiveMessage();
//        final int PORT = 5000;
//        MyServer server = new MyServer();
//
//        try {
//            server.listenSocket = new ServerSocket(PORT);
//            System.out.println("Wating for connection...");
//
//            server.commSocket = server.listenSocket.accept();
//            System.err.println();
//            System.err.println("Connection successful");
//
//            Reader rd = new Reader(server.commSocket);
//            rd.start();
//
//            Sender sd = new Sender(server.commSocket);
//            sd.start();
//        } catch (IOException e)
//        {
//            System.err.println(e);
//        } finally {
//            if (server.listenSocket != null) {
//                try {
//                    server.listenSocket.close();
//                } catch (IOException e) {
//                    System.out.println(e);
//                }
//            }
//        }

//        Message returnedMessage = server.receiveMessage();
//        System.out.println("Service Type: " + returnedMessage.getServiceType());
//        System.out.println("Sub Type: " + returnedMessage.getSubType());
//        System.out.println("Code: " + returnedMessage.getCode());
//        System.out.println("Length: " + returnedMessage.getLength());
    }
}
