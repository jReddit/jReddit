package im.goel.jreddit.captcha;

/**
 * This class corresponds to the reddit's captcha class.
 * @author Karan Goel
 *
 */

import im.goel.jreddit.user.User;
import im.goel.jreddit.utils.Utils;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


public class Captcha {

	/**
	 * Generates and saves a new reddit captcha in the working
	 * directory.
	 * Returns the iden of the generated captcha.
	 * @author Karan Goel
	 */
	public String new_captcha(User user) {
		JSONObject obj = null;
		try {
			obj = Utils.post("", new URL("http://www.reddit.com/api/new_captcha"),
					user.getCookie());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Get the im.goel.jreddit.captcha iden
		String iden = (String) ((JSONArray) ((JSONArray) ((JSONArray) obj.get("jquery")).get(11)).get(3)).get(0);
		System.out.println(iden);
	
		URL url = null;
		try {
			url = new URL("http://www.reddit.com/captcha/" + iden + ".png");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		RenderedImage captcha = null;
		try {
			captcha = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			ImageIO.write(captcha, "png", new File("captcha.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return iden;
	}

}
