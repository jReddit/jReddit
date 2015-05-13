package jreddit.github.com.jredditsampleproject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.entity.User;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.SubmissionSort;
import com.github.jreddit.utils.restclient.PoliteHttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class JRedditSample extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_j_reddit_sample);
        List<Submission> submissionList = null;

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletionService<List<Submission>> completionService = new ExecutorCompletionService<List<Submission>>(executorService);

        completionService.submit(new Callable<List<Submission>>() {
            @Override
            public List<Submission> call() throws Exception {
                RestClient restClient = new PoliteHttpRestClient();
                restClient.setUserAgent("bot/1.0 by name");

                // Connect the user
                // change USER and PASSWORD with your own credentials
                User user = new User(restClient, "USER", "PASSWORD");
                try {
                    user.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Handle to Submissions, which offers the basic API submission functionality
                Submissions subms = new Submissions(restClient, user);

                // Retrieve submissions of a submission
                return subms.ofSubreddit("programming", SubmissionSort.TOP, -1, 100, null, null, true);
            }
        });

        try {
            final Future<List<Submission>> completedFuture = completionService.take();
            submissionList = completedFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("hello");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_j_reddit_sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
