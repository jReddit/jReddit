package examples;

import com.github.jreddit.action.FlairActions;
import com.github.jreddit.entity.User;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Examples for the FlairActions class
 *
 * @author Ryan Delaney (Vitineth)
 * @since 03/01/2014
 */
public class FlairExample {

    public static void main(String[] args) {
        RestClient restClient = new HttpRestClient();
        restClient.setUserAgent("bot/1.0 by name");

        User user = new User(restClient, Authentication.getUsername(), Authentication.getPassword());
        try {
            user.connect();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        //Create the flair actions controlled
        FlairActions flairActions = new FlairActions(restClient, user);

        System.out.println(flairActions.flair("red", null, "Vitineth", "Test", "myblueprints").getStatusCode());

        //Add the flair 'Blueprinter' to the post 't3_2r86db' in the subreddit 'myblueprints' with the css class 'red'
        System.out.println(flairActions.flair("red", "t3_2r86db", null, "Blueprinter", "myblueprints").getStatusCode());

        //Add the flair 'Blueprinter' to the user 'Tridentac' in the subreddit 'myblueprints' with the css class 'red'
        System.out.println(flairActions.flair("red", null, "Tridentac", "Blueprinter", "myblueprints").getStatusCode());

        //Delete the flair from the user 'Tridentac' in the subreddit 'myblueprints'
        System.out.println(flairActions.deleteFlair("Tridentac", "myblueprints").getStatusCode());

        //Clear all user flair templates in the subreddit 'myblueprints'
        System.out.println(flairActions.clearFlairTemplates("USER_FLAIR", "myblueprints").getStatusCode());

        //Clear all the link flair templates in the subreddit 'myblueprints'
        System.out.println(flairActions.clearFlairTemplates("LINK_FLAIR", "myblueprints").getStatusCode());

        //Set the flair configs as so:
        //allow user and link flairs
        //user flairs on the left
        //link flairs on the right
        //users can assign link flairs
        //users cannot assign user flairs
        System.out.println(flairActions.flairConfig(true, "left", false, "right", true, "myblueprints").getStatusCode());


        //For example. Add the user flair template "BLUE-TAG" with the text "Blue Team" and the css class "blueteam" in the subreddit "/r/somegenericgame" with users not be able to edit the string and print the status code.
        System.out.println(flairActions.flairTemplate("blueteam", "BLUE-TAG", "USER_FLAIR", "Blue Team", false, "somegenericgame").getStatusCode());

        //For example. Give the user /u/somegenericgamer the flair "Blue team" from the flair-template "BLUE-TAG" in the subreddit /r/somegenericgame and print the status code.
        System.out.println(flairActions.selectFlair("BLUE-TAG", null, "somegenericgamer", "Blue team", "somegenericgame").getStatusCode());

        //For example. Enable flair for the currently logged in user in the subreddit /r/somegenericgame and print the status code.
        System.out.println(flairActions.setFlairEnabled(true, "somegenericgame").getStatusCode());
    }


}
