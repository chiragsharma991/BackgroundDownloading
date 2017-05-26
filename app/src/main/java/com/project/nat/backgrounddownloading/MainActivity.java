package com.project.nat.backgrounddownloading;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.birbit.android.jobqueue.IntCallback;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.TagConstraint;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class MainActivity extends Activity  {

    // button to show progress dialog
    Button btnShowProgress;
    static boolean inter=false;
    private JobManager jobManager;
    private String TAG="TAG";

    // Progress Dialog
    private ProgressDialog pDialog;
    public static ImageView my_image;
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;
    public  Context context=MainActivity.this;

    // File url to download
   // private static String file_url = "http://api.androidhive.info/progressdialog/hive.jpg";
    private static String file_url = "https://sm-dm-prod-public-image.s3.amazonaws.com/mpm/MENS_WEAR_DJ%26C_LOW.pdf";
    private static String file_url2 = "https://sm-dm-prod-public-image.s3.amazonaws.com/mpm/LADIES_WEAR_SPUNK_LOW.pdf";
    private Button btnShowProgress2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // show progress bar button
        btnShowProgress = (Button) findViewById(R.id.btnProgressBar);
        btnShowProgress2 = (Button) findViewById(R.id.btnProgressBar2);
        // Image view to show image after downloading
        my_image = (ImageView) findViewById(R.id.my_image);
        /**
         * Show Progress bar click event
         * */
        btnShowProgress2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // starting new Async Task
              //  new DownloadFileFromURL(context).execute(file_url);
               // Log.e("TAG", "getActiveConsumerCount: "+jobManager.getActiveConsumerCount() );
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        //TODO your background code
                        Log.e("TAG", "count: "+AppJobManager.getJobManager().count() );

                       // AppJobManager.getJobManager().clear();
                        AppJobManager.getJobManager().cancelJobsInBackground(null,TagConstraint.ANY, "ONE");

                    }
                });
                //AppJobManager.getJobManager().addJobInBackground(new PostTweetJob(file_url2));




            }
        });

        btnShowProgress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // starting new Async Task
               // new DownloadFileFromURL(context).execute(file_url);

              //  jobManager.addJobInBackground(new PostTweetJob(file_url));


                AppJobManager.getJobManager().addJobInBackground(new PostTweetJob(file_url));


            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent intent=new Intent(this,TestOne.class);
        startActivity(intent);
        //this.finish();
    }

    public void ClickOn(View view){
       inter=true;
    }


}
