package me.smorenburg.jira.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;

import me.smorenburg.jira.BuildConfig;
import me.smorenburg.jira.api.services.WebServiceApiService;
import me.smorenburg.jira.db.core.DatabaseManager;
import me.smorenburg.jira.dummy.Dummy;
import me.smorenburg.jira.jobs.GetAllAndSyncJob;
import me.smorenburg.jira.utils.EncryptionSuite;
import me.smorenburg.jira.utils.StoreManager;

/**
 * Created by MR x on 22/03/2017.
 */

public class JiraApplication extends MultiDexApplication {


    private WebServiceApiService webService;
    private DatabaseManager databaseManager;
    private StoreManager storeManager;
    private JobManager jobManager;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Dummy d = new Dummy(DatabaseManager.getInstance());

            d.loadAuthorities();
            d.loadDefaultUsers();
            d.loadDefaultTasks();
            d.loadDefaultSkills();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {

        @SuppressLint("HardwareIds")
        String uuid = Settings.Secure.getString(base.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        EncryptionSuite.setSeed(uuid);


        MultiDex.install(base);
        StoreManager.setBaseContext(base);
        DatabaseManager.setDatabaseBaseContext(base);

        super.attachBaseContext(base);
    }

    public WebServiceApiService getWebServiceRestApiClient() {
        if (webService == null)
            webService = new WebServiceApiService(getApplicationContext());
        return webService;
    }

    public DatabaseManager getDatabaseManager() {
        if (databaseManager == null)
            databaseManager = DatabaseManager.getInstance();
        return databaseManager;
    }

    public StoreManager getStoreManager() {
        if (storeManager == null)
            storeManager = StoreManager.getInstance();
        return storeManager;
    }

    public JobManager getJobManager() {
        if (jobManager == null) {
            Configuration configuration = new Configuration.Builder(this)
//                    .customLogger(new CustomLogger() {
//                        private static final String TAG = "JOBS";
//                        @Override
//                        public boolean isDebugEnabled() {
//                            return true;
//                        }
//
//                        @Override
//                        public void d(String text, Object... args) {
//                            Log.d(TAG, String.format(text, args));
//                        }
//
//                        @Override
//                        public void e(Throwable t, String text, Object... args) {
//                            Log.e(TAG, String.format(text, args), t);
//                        }
//
//                        @Override
//                        public void e(String text, Object... args) {
//                            Log.e(TAG, String.format(text, args));
//                        }
//                    })
                    .minConsumerCount(1)//always keep at least one consumer alive
                    .maxConsumerCount(3)//up to 3 consumers at a time
                    .loadFactor(3)//3 jobs per consumer
                    .consumerKeepAlive(120)//wait 2 minute
                    .build();
            jobManager = new JobManager(getApplicationContext());
        }
        return jobManager;
    }
}
