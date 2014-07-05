package examples;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.simple.parser.ParseException;

import com.github.jreddit.action.UserActions;
import com.github.jreddit.captcha.Captcha;
import com.github.jreddit.captcha.CaptchaDownloader;
import com.github.jreddit.entity.User;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

public class PostExample {

	public static void main(String[] args) {
		
		// Initialize REST Client
	    RestClient restClient = new HttpRestClient();
	    restClient.setUserAgent("bot/1.0 by name");

		// Connect the user
	    User user = new User(restClient, Authentication.getUsername(), Authentication.getPassword());
		try {
			user.connect();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		// Enable user actions
		UserActions userActions = new UserActions(restClient, user);

		// Captcha requester
		Captcha c = new Captcha(restClient);
		
		// Check if a captcha is needed for this user
		if (c.needsCaptcha(user)) {
			
			// Ask for a new Captcha identification
			String iden = c.newCaptcha(user);
			
			// Show the captcha to you
			showCaptcha(iden);
			
			// Ask for captcha solution
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter the solution to the Captcha (see the window opened just now):");
			String solution = sc.nextLine();
			sc.close();
			
			// Submit the link with captcha
			userActions.submitLink("Funny dog image", "http://www.boredpanda.com/blog/wp-content/uploads/2014/03/funny-cats-dogs-stuck-furniture-1.jpg", "funny", iden, solution);

		} else {
			
			// Submit the link without captcha
			userActions.submitLink("Funny dog image", "http://www.boredpanda.com/blog/wp-content/uploads/2014/03/funny-cats-dogs-stuck-furniture-1.jpg", "funny", "", "");
		
		}
	
	}
	
	/**
	 * Show the captcha to the user.
	 * @param iden Captcha identifier (used for retrieving captcha)
	 */
	public static void showCaptcha(String iden) {
		
		// Create the frame
		JFrame frame = new JFrame("Captcha");

		// Download captcha
		CaptchaDownloader cd = new CaptchaDownloader();
		Image image;
		
		try {
			
			// Add the captcha image to the frame
			image = (Image) cd.getCaptchaImage(iden);
			JPanel mainPanel = new JPanel(new BorderLayout());
			mainPanel.add(new JLabel(new ImageIcon(image)));
			frame.setSize(200, 200);
			frame.add(mainPanel);
			frame.setVisible(true);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
