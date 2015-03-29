package Shared;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Handles all the conversion for the Chess program
 * @author zacharilius
 *
 */
public class Conversion {
	/**
	 * Converts from a Message object to a JSON String
	 * @param message A message object
	 * @return The JSON String representation of the inputed message
	 */
	public static String messageToJSON(Message message){
		ObjectMapper mapper = new ObjectMapper();
        String messageJSON = "";
        
        // Convert from Java Object to JSON String
        try {
			messageJSON = mapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return messageJSON;
	}
	/**
	 * Converts from a JSON String to a Message object
	 * @param JSON A JSON String
	 * @return The message representation of the inputed JSON String
	 */
	public static Message jsonToMessage(String JSON){
		ObjectMapper mapper = new ObjectMapper();
        Message messageObject = new Message();
        try {
			messageObject = mapper.readValue(JSON, Message.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return messageObject;
	}
	
}
