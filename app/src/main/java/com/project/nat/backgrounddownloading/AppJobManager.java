package com.project.nat.backgrounddownloading;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.log.CustomLogger;
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService;
import com.birbit.android.jobqueue.scheduling.GcmJobSchedulerService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.project.nat.backgrounddownloading.services.AppGcmJobService;
import com.project.nat.backgrounddownloading.services.AppJobService;

/**
 * Created by csuthar on 25/05/17.
 */

public class AppJobManager {

    private static JobManager mJobManager;

    public static synchronized JobManager getJobManager() {
        return mJobManager;
    }

    public static synchronized JobManager getJobManager(Context context) {
        if (mJobManager == null) {
            configureJobManager(context);
        }
        return mJobManager;
    }

    private static synchronized void configureJobManager(Context context) {
        if (mJobManager == null) {
            Configuration.Builder builder = new Configuration.Builder(context)
                    .minConsumerCount(1) // always keep at least one consumer alive
                    .maxConsumerCount(3) // up to 3 consumers at a time
                    .loadFactor(3) // 3 jobs per consumer
                    .consumerKeepAlive(120) // wait 2 minute
                    .customLogger(new CustomLogger() {
                        private static final String TAG = "JOBS";
                        @Override
                        public boolean isDebugEnabled() {
                            return true;
                        }

                        @Override
                        public void d(String text, Object... args) {
                            Log.d(TAG, String.format(text, args));
                        }

                        @Override
                        public void e(Throwable t, String text, Object... args) {
                            Log.e(TAG, String.format(text, args), t);
                        }

                        @Override
                        public void e(String text, Object... args) {
                            Log.e(TAG, String.format(text, args));
                        }

                        @Override
                        public void v(String text, Object... args) {

                        }
                    });

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.scheduler(FrameworkJobSchedulerService.createSchedulerFor(context,
                        AppJobService.class), true);
            } else {
                int enableGcm = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
                if (enableGcm == ConnectionResult.SUCCESS) {
                    builder.scheduler(GcmJobSchedulerService.createSchedulerFor(context,
                            AppGcmJobService.class), true);
                }
            }
            mJobManager = new JobManager(builder.build());
        }
    }
}