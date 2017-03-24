package me.smorenburg.jira.jobs;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.atomic.AtomicInteger;

import me.smorenburg.jira.api.services.WebServiceApiService;
import me.smorenburg.jira.db.core.DbConnect;
import me.smorenburg.jira.db.models.base.Location_1;
import me.smorenburg.jira.db.models.base.WebServiceData;
import me.smorenburg.jira.events.NewDataFetched;
import me.smorenburg.jira.utils.StoreManager;

/**
 * Created by MR x on 24/03/2017.
 */

public class GetAllAndSyncJob extends Job {

    private static final AtomicInteger jobCounter = new AtomicInteger(0);
    private final int id;

    private WebServiceApiService webServiceApiService;
    private transient DbConnect databaseManager;

    public GetAllAndSyncJob(WebServiceApiService webServiceApiService, DbConnect databaseManager) {
        // This job requires network connectivity,
        super(new Params(1).requireNetwork().groupBy("get_all"));

        this.webServiceApiService = webServiceApiService;
        this.databaseManager = databaseManager;
        id = jobCounter.incrementAndGet();

    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        if (id != jobCounter.get()) {
            //looks like other fetch jobs has been added after me. no reason to keep fetching
            //many times, cancel me, let the other one fetch tweets.
            return;
        }
        databaseManager.drop(WebServiceData.class);
        databaseManager.drop(Location_1.class);
        for (WebServiceData next : webServiceApiService.getWebServiceApiService().getAll().execute().body()) {
            databaseManager.save(next);
        }

        StoreManager.getInstance().setSynced(true);

        EventBus.getDefault().post(new NewDataFetched());
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
