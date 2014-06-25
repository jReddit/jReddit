package examples;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Authentication {
	
	public static String username;
	public static String password;
	
	/**
	 * Location of a file with on the first line the username
	 * and on the second line the password.
	 */
	public static String AUTHENTICATION_FILE_LOCATION = "authentication.txt";
	
	public static String getUsername() {
		
		if (username == null) {
			
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(AUTHENTICATION_FILE_LOCATION));
				String line = null;
				if ((line = reader.readLine()) != null) {
				    username = line;
				}
			} catch (FileNotFoundException e) {
				System.out.println("Please make a authentication.txt file at the root folder of this project with contents: \"username\\npassword\".");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		return username;
		
	}
	
	public static String getPassword() {
		
		if (password == null) {
			
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(AUTHENTICATION_FILE_LOCATION));
				String line = null;
				int i = 0;
				while ((line = reader.readLine()) != null && i <= 1) {
					if (i == 1) {
						password = line;
					}
				    i++;
				}
			} catch (FileNotFoundException e) {
				System.out.println("Please make a authentication.txt file at the root folder of this project with contents: \"username\\npassword\".");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		return password;
		
	}
	
}
