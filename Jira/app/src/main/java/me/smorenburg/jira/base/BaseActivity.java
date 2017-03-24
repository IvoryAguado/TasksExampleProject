package me.smorenburg.jira.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by MR x on 23/03/2017.
 */

public class BaseActivity extends AppCompatActivity  {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
//        ButterKnife.bind(this);
    }

    protected JiraApplication getJiraApplication() {
        return ((JiraApplication) getApplicationContext());
    }


}
