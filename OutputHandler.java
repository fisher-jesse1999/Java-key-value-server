import java.io.*;

public class OutputHandler extends Thread {

    DataInputStream serverResponseStream;
    PrintStream terminalStream;

    public OutputHandler(DataInputStream serverResponse, PrintStream terminal){
        serverResponseStream = serverResponse;
        //terminalStream = terminal;
        terminalStream = System.out;
    }

    public void run(){

        try{

            terminalStream.println("output handler running");

            while(true){
                terminalStream.println(serverResponseStream.readUTF());
            }

        } catch(Exception e){

            terminalStream.println(e);
            terminalStream.println("Thread terminating...");

        }

    }

}
