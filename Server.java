import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Protocol:
 * set\n<key>\n<value>
 * get\nkey
 * getall\n
 * del\n
 * exit\n
 * @author jaf490
 *
 * can set up initialization and destroy methods and save the key-store to a file
 * can make the server multithreaded to handle multiple clients, but need to synch the storage
 * map.
 * 
 */
public class Server {

	String jsonLocation = "storageData.json";
	
	public static void main(String[] args) {

		try {
			
			ServerSocket serverSocket = new ServerSocket(5001);
			Socket socket = serverSocket.accept();	//establish connection
			
			DataInputStream socketInput = new DataInputStream(socket.getInputStream());
			DataOutputStream socketOutput = new DataOutputStream(socket.getOutputStream()); 

			Map<String, String> storage = setUpStorage();
			
			//Map<String, String> storage = new HashMap<String, String>(); 
			
			System.out.println("connection established. ready for messages.");
			socketOutput.writeUTF("[server]: connection established. ready for messages.");
			
			String message = "";
			socketOutput.writeUTF("[server]: Please enter a command: set, get, getall, del, or exit.");
			message = socketInput.readUTF();
			
			while(!message.trim().toLowerCase().equals("exit")){
				
				if(message.toLowerCase().trim().equals("set")) {
					
					socketOutput.writeUTF("[server]: Please enter the key");
					String key = socketInput.readUTF();;
					
					socketOutput.writeUTF("[server]: Please enter the value");
					String value = socketInput.readUTF();
			
					//check if the key already exists
					if(storage.containsKey(key)) {
						
						storage.put(key, value);
						socketOutput.writeUTF("[server]: Update successful: " + key + " -> " + storage.get(key));
						
					} else {
						
						storage.put(key, value);
						socketOutput.writeUTF("[server]: Insert successful: " + key + " -> " + storage.get(key));
						
					}
					
				} else if(message.toLowerCase().trim().equals("get")) {

					socketOutput.writeUTF("[server]: Please enter the key");
					String key = socketInput.readUTF();
					
					//check if the key exists
					if(storage.containsKey(key)) {
						
						String value = storage.get(key);
						if(value != null) {
							
							socketOutput.writeUTF("[server]: Retrieval successful: " + key + " -> " + storage.get(key));
							
						} else {
							
							socketOutput.writeUTF("[server]: Retrieval failed: value is null");
							
						}
						
					} else {
						
						socketOutput.writeUTF("[server]: Retrieval failed: key does not exist");
						
					}
				} else if(message.toLowerCase().trim().equals("getall")){

					socketOutput.writeUTF("[server]: getall placeholder");

				} else if(message.toLowerCase().trim().equals("del")) {
					
					socketOutput.writeUTF("[server]: deletion placeholder");
					
				} else {
					
					socketOutput.writeUTF("[server]: Error: unrecognized command => " + message.toLowerCase().trim());
					
				}
				
				//read in the next command
				socketOutput.writeUTF("[server]: Please enter a command: set, get, getall, del, or exit");
				message = socketInput.readUTF();
				
			} // end of the while loop
			
			socketOutput.writeUTF("Terminating server...");
			
			socket.close();			//close out the connection to the client
			serverSocket.close();	//close out the socket listener
					
		} catch(Exception exception) {
			
			System.out.println(exception);
			
		}
		
	}

	public static HashMap<String, String> setUpStorage(){

		HashMap<String, String> map = new HashMap<String, String>();
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("E:/sample.json"));



		return map;
	}

}

