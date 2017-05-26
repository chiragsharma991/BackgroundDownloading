package com.project.nat.backgrounddownloading;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.CancelReason;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by csuthar on 25/05/17.
 */

public class PostTweetJob extends Job
{
    public  String link;
    public String TAG="TAG";
    public PostTweetJob context=this;
    public static final int PRIORITY = 1;
    public PostTweetJob(String link) {
        // This job requires network connectivity,
        // and should be persisted in case the application exits before job is completed.
        super(new Params(JobConstants.PRIORITY_HIGH).groupBy(link).addTags("ONE").requireNetwork());



        this.link=link;

        Log.e(TAG, "PostTweetJob: ");

    }
    @Override
    public void onAdded() {
        Log.e(TAG, "onAdded: " );
        // Job has been saved to disk.
        // This is a good place to dispatch a UI event to indicate the job will eventually run.
        // In this example, it would be good to update the UI with the newly posted tweet.
    }
    @Override
    public void onRun() throws Throwable {
        Log.e(TAG, "onRun: " );
        // Job logic goes here. In this example, the network call to post to Twitter is done here.
        // All work done here should be synchronous, a job is removed from the queue once
        // onRun() finishes.
        //webservice.postTweet(text);
        int count;

            URL url = new URL(link);
            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
            int lenghtOfFile = conection.getContentLength();

            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            // Output stream to write file
            //OutputStream output = new FileOutputStream("/sdcard/downloadedfile.pdf");
        File file=new File("/sdcard/MyFile");
        file.mkdir();
        File file1=new File("/sdcard/MyFile","download.pdf");
        OutputStream output = new FileOutputStream(file1);
        //OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+"/MeriFile"+"/download.pdf");




        byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {

                total += count;

                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress(""+(int)((total*100)/lenghtOfFile));
//                if((int)((total*100)/lenghtOfFile)>=20){
//                    break;
//                }

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();




    }

    private void publishProgress(String s) {
        Log.e(TAG, "publishProgress: "+s );
    }



    @Override
    protected RetryConstraint shouldReRunOnThrowable(Throwable throwable, int runCount,
                                                     int maxRunCount) {



        Log.e(TAG, "shouldReRunOnThrowable: "+isCancelled());

        // An error occurred in onRun.
        // Return value determines whether this job should retry or cancel. You can further
        // specify a backoff strategy or change the job's priority. You can also apply the
        // delay to the whole group to preserve jobs' running order.
        return RetryConstraint.RETRY;
    }
    @Override
    protected void onCancel(@CancelReason int cancelReason, @Nullable Throwable throwable) {
        Log.e(TAG, "onCancel: "+cancelReason);
        // Job has exceeded retry attempts or shouldReRunOnThrowable() has decided to cancel.
    }






}
