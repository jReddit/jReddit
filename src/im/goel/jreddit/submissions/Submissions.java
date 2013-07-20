package im.goel.jreddit.submissions;

import im.goel.jreddit.subreddit.Subreddit;
import im.goel.jreddit.user.User;
import im.goel.jreddit.utils.SortOption;
import im.goel.jreddit.utils.TimeOption;
import im.goel.jreddit.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


/**
 * This class offers some submission utilities.
 *
 * @author <a href="http://www.omrlnr.com">Omer Elnour</a>
 */
public class Submissions {
    /**
     * This function returns a linked list containing the submissions on a given
     * subreddit and page. (in progress)
     *
     * @param redditName The subreddit's name
     * @param type       HOT or NEW and some others to come
     * @param frontpage       TODO this
     * @param user       The user
     * @return The linked list containing submissions
     * @throws IOException    If connection fails
     * @throws ParseException If JSON parsing fails
     */
	public static LinkedList<Submission> getSubmissions(Subreddit sub, SortOption sort, TimeOption time, int limit, User user) throws IOException, ParseException {
    	
		LinkedList<Submission> submissions = new LinkedList<Submission>();
        URL url;
		String after = "";
		int j = 0;
		while (j < limit){
			String afterString = "";
			if (j!=0){
				afterString = "&after="+after;
			}
			try{
				String urlString = "http://www.reddit.com"+sub.getUrl()+"/"+sort.toString().toLowerCase()+".json?t="+time.toString().toLowerCase()+"&limit=1"+afterString;
				
				url = new URL(urlString);
				Object obj = Utils.get("", url, user.getCookie());
		        JSONObject object = (JSONObject) obj;
		        
		        
		        JSONObject data = (JSONObject) object.get("data");
		        after = (String) data.get("after");
		        JSONArray children = (JSONArray) data.get("children");
		        
		        if (!children.isEmpty()){
		        	JSONObject value = (JSONObject) ((JSONObject) children.get(0)).get("data");
		        	submissions.add(new Submission(value, user));
		        	//System.out.println(value.get("title"));
		        }else{
		        	j = limit +1;
		        }
		        
	            //submissions.add(new Submission(user, data.get("id").toString(),
	                    //new URL("http://www.reddit.com" + (data.get("permalink").toString()))));


			}catch (Exception e){
			   e.printStackTrace();
			}
			j++;
		}
		return submissions;
    }
}