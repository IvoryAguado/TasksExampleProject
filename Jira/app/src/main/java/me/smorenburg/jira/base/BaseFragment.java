package me.smorenburg.jira.base;


import android.support.v4.app.Fragment;

/**
 * Created by MR x on 23/03/2017.
 */

public class BaseFragment extends Fragment {

    protected JiraApplication getJiraApplication() {
        return ((JiraApplication) getActivity().getApplicationContext());
    }


}
