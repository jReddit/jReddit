package im.goel.jreddit.MessageTest;
import im.goel.jreddit.user.User;

import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class CaptchaTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		User user = new User("jReddittest", "jReddittest");
		try {
			user.connect();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		JSONObject obj = null;
		try {
			obj = post("", new URL("http://www.reddit.com/api/new_captcha"),
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
			url = new URL("http://www.reddit.com/im.goel.jreddit.captcha/" + iden + ".png");
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
			ImageIO.write(captcha, "png", new File("im.goel.jreddit.captcha.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static JSONObject post(String apiParams, URL url, String cookie)
			throws IOException, ParseException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8");
		connection.setRequestProperty("Content-Length",
				String.valueOf(apiParams.length()));
		connection.setRequestProperty("cookie", "reddit_session=" + cookie);
		connection.setRequestProperty("User-Agent", "Test jReddit");
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(apiParams);
		wr.flush();
		wr.close();

		// Debugging stuff
		// @author Karan Goel
		//		InputStream is = null;
		//		Scanner s = null;
		//		String response = null;
		//		try {
		//			if (connection.getResponseCode() != 200) {
		//				s = new Scanner(connection.getErrorStream());
		//			} else {
		//				is = connection.getInputStream();
		//				s = new Scanner(is);
		//			}
		//			s.useDelimiter("\\Z");
		//			response = s.next();
		//			System.out.println("\n" + response + "\n\n");
		//		} catch (IOException e2) {
		//			e2.printStackTrace();
		//		}
		// Debugging stuff

		JSONParser parser = new JSONParser();
		return (JSONObject) parser.parse(new BufferedReader(new InputStreamReader(
				connection.getInputStream())).readLine());
	}

}
