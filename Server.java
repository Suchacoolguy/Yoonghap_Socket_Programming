import java.net.*;
import java.io.*;
import java.util.*;

public class Server
{
    public static final int PORT = 5004;

    public static void main(String args[]){
        ServerSocket listenSocket = null;
        Socket commSocket = null;
        ClientsList cliList = new ClientsList();

        try{
            listenSocket = new ServerSocket(PORT);
            System.out.println("Waiting for connection...");

            while(true){
                commSocket = listenSocket.accept();

                System.out.println("Connection received from " + commSocket.getInetAddress().getHostName() + " : " + commSocket.getPort());

                ClientHandler cliHandler = new ClientHandler(commSocket, cliList);
                cliHandler.start();
            }
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

//    public void closeListenSocket()
//    {
//        try {
//            if (listenSocket != null)
//                listenSocket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

class ClientsList
{
    private ArrayList<BufferedWriter> cliList;

    public ClientsList(){
        cliList = new ArrayList<BufferedWriter>();
    }

    public synchronized void addClient(BufferedWriter bw) {
        cliList.add(bw);
    }

    public synchronized void removeClient(BufferedWriter bw) {
        cliList.remove(bw);
    }

    public synchronized void sendDataToAllClients(BufferedWriter fromBw, String msg){
        try{
            for(BufferedWriter bw : cliList){
                if(bw != fromBw){
                    bw.write(msg + "\r\n");
                    bw.flush();
                }
            }
        }catch(IOException e){
            System.err.println(e);
        }
    }
}

class ClientHandler extends Thread{
    private Socket commSocket;
    private ClientsList cliList;
    private String nickname;

    public ClientHandler(Socket commSocket, ClientsList cliList){
        this.commSocket = commSocket;
        this.cliList = cliList;
    }

    public void run(){
        InputStream is;
        OutputStream os;
        BufferedReader br;
        BufferedWriter bw;
        String readBuf;

        try{
            is = commSocket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));

            os = commSocket.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(os));

            doJoin(br, bw);

            while ((readBuf = br.readLine()) != null) {
                if(readBuf.equals("quit")){
                    doQuit(bw);
                    break;
                }

                doSend(bw, readBuf);
            }
            commSocket.close();
        }catch(IOException e){
            System.err.println(e);
        }
    }

    private void doJoin(BufferedReader br, BufferedWriter bw){
        try{
            nickname = br.readLine();

            bw.write("Welcome " + nickname +"\r\n");
            bw.flush();

            String msg = nickname + " entered the room";
            System.out.println(msg);
            cliList.sendDataToAllClients(bw, msg);

            cliList.addClient(bw);
        }catch(IOException e){
            System.err.println(e);
        }
    }

    private void doQuit(BufferedWriter bw){
        String msg = nickname + " left the room";
        System.out.println(msg);
        cliList.sendDataToAllClients(bw, msg);

        cliList.removeClient(bw);
    }

    private void doSend(BufferedWriter bw, String data){
        String msg = nickname + ": " + data;
        cliList.sendDataToAllClients(bw, msg);
    }
}

