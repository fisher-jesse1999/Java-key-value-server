import java.io.*;
import java.net.*;

public class Client {

	/**
	 * perhaps use a thread to collect incoming messages and directly
	 * print them to the main thread's terminal. can pass System.out from the main thread
	 * and the source of server responses. perhaps make it an inner class? idk
	 */
	public static void main(String[] args) {
		
		try {
			
			Socket clientSocket =  new Socket("localhost", 5001);
			DataInputStream socketInput = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream socketOutput = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader terminalInput = new BufferedReader(new InputStreamReader(System.in));

			OutputHandler outputThread = new OutputHandler(socketInput, System.out);
			outputThread.start();

			System.out.println("Starting the input loop");
			
			String message = "";
			while(!message.trim().toLowerCase().equals("exit")) {

				//read in the user's response
				message = terminalInput.readLine();

				//send the user's response to the server
				socketOutput.writeUTF(message);
				socketOutput.flush();
			
			}
			
			socketInput.close();
			socketOutput.close();
			clientSocket.close();
			
		} catch(Exception exception) {
			
			System.out.println("[client]: " + exception);
			
		}
		
	}

}