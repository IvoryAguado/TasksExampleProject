package me.smorenburg.jira;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.smorenburg.jira.base.BaseActivity;
import me.smorenburg.jira.db.models.base.User;
import me.smorenburg.jira.dialogs.AddTaskDialog;
import me.smorenburg.jira.fragments.TasksFragment;
import me.smorenburg.jira.fragments.WebServiceFragment;

import static android.view.View.GONE;

public class MainActivity extends BaseActivity {

    @BindView(R.id.message)
    TextView mTextMessage;

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    private FragmentManager fragmentManager;
    private AddTaskDialog addTaskDialog;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_tasks:
                    mTextMessage.setText(R.string.tasks);
                    fragmentManager.beginTransaction().replace(R.id.content, TasksFragment.newInstance(getJiraApplication().getDatabaseManager().getCurrentUser())).commit();
                    return true;
                case R.id.navigation_web_service:
                    mTextMessage.setText(R.string.web_service);
                    fragmentManager.beginTransaction().replace(R.id.content, WebServiceFragment.newInstance()).commit();
                    return true;
                case R.id.navigation_logout:
                    getJiraApplication().getStoreManager().clearAll();
                    finish();
                    startActivity(new Intent(MainActivity.this, Jira_Login.class));
                    return true;
            }
            
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        User currentUser = getJiraApplication().getDatabaseManager().getCurrentUser();
        setTitle(currentUser.getName()+" "+currentUser.getLname());
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragmentManager.beginTransaction().replace(R.id.content, TasksFragment.newInstance(getJiraApplication().getDatabaseManager().getCurrentUser())).commit();
        if (getJiraApplication().getDatabaseManager().getCurrentUser().isAdmin())
            floatingActionButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    addTaskDialog = new AddTaskDialog(MainActivity.this);
                    addTaskDialog.setOnCloseTaskDialog(new AddTaskDialog.OnCloseTaskDialog() {
                        @Override
                        public void onSuccess() {
                            fragmentManager.beginTransaction().replace(R.id.content, TasksFragment.newInstance(getJiraApplication().getDatabaseManager().getCurrentUser())).commit();
                        }

                        @Override
                        public void onDelete() {

                        }

                        @Override
                        public void onFail() {

                        }
                    });
                    addTaskDialog.show();

                }
            });
        else {
            floatingActionButton.setVisibility(GONE);
        }
    }
}
