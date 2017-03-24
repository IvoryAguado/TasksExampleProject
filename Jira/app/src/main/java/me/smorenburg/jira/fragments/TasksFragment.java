package me.smorenburg.jira.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.smorenburg.jira.R;
import me.smorenburg.jira.adapters.TasksRecyclerViewAdapter;
import me.smorenburg.jira.base.BaseFragment;
import me.smorenburg.jira.db.core.DatabaseManager;
import me.smorenburg.jira.db.models.base.Task;
import me.smorenburg.jira.db.models.base.User;

public class TasksFragment extends BaseFragment {

    private static final String ARG_USER = "argsUser";

    private User user;

    @BindView(R.id.recyClerViewTasks)
    RecyclerView recyClerViewTasks;


    public TasksFragment() {
        // Required empty public constructor
    }

    public static TasksFragment newInstance(User user) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable(ARG_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_tasks, container, false);
        ButterKnife.bind(this, inflate);

        recyClerViewTasks.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (user.isAdmin())
            recyClerViewTasks.setAdapter(new TasksRecyclerViewAdapter(getActivity(), DatabaseManager.getInstance().findAll(Task.class)));
        else
            recyClerViewTasks.setAdapter(new TasksRecyclerViewAdapter(getActivity(), user.getTasks()));

        return inflate;
    }


}
