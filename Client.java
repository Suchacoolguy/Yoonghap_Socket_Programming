import java.io.*;
import java.net.*;

public class Client
{
    public static final String HOST = "localhost";
    public static final int PORT = 5004;

    public static void main(String args[]){
        Socket cliSocket = null;

        try{
            cliSocket = new Socket(HOST, PORT);
            System.out.println("Connection successful");

            ReadData rd = new ReadData(cliSocket);
            rd.start();

            SendData sd = new SendData(cliSocket);
            sd.start();
        }catch(IOException e){
            System.err.println(e);
        }
    }
}

class ReadData extends Thread
{
    private Socket cliSocket;

    ReadData(Socket cliSocket){
        this.cliSocket = cliSocket;
    }

    public void run(){
        InputStream is;
        BufferedReader br;
        String readBuf;

        try{
            is = cliSocket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));

            while((readBuf = br.readLine()) != null ){
                System.out.println(readBuf);
                System.out.println();
            }
        }catch(IOException e){
            System.err.println(e);
        }
    }
}

class SendData extends Thread
{
    private Socket cliSocket;

    SendData(Socket cliSocket){
        this.cliSocket = cliSocket;
    }

    public void run(){
        OutputStream os;
        BufferedReader keyInput;
        BufferedWriter bw;
        String writeBuf;

        try{
            os = cliSocket.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(os));

            keyInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your nickname: ");
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
