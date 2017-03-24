package me.smorenburg.jira.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.smorenburg.jira.R;
import me.smorenburg.jira.adapters.ItemsRecyclerViewAdapter;
import me.smorenburg.jira.base.BaseFragment;
import me.smorenburg.jira.db.core.DatabaseManager;
import me.smorenburg.jira.events.NewDataFetched;

public class WebServiceFragment extends BaseFragment {


    @BindView(R.id.recyclerViewItems)
    RecyclerView recyclerViewItems;
    @BindView(R.id.spinnerItem)
    Spinner spinnerItems;
    @BindView(R.id.category)
    Spinner spinnerCategory;
    private List<String> itemCategories;
    private List<String> itemList;

    public WebServiceFragment() {
        // Required empty public constructor
    }

    public static WebServiceFragment newInstance() {
        WebServiceFragment fragment = new WebServiceFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NewDataFetched event) {
        itemCategories = DatabaseManager.getInstance().getItemCategories();
        ArrayAdapter<String> spinnnerCategoryList = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, itemCategories);
        spinnnerCategoryList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(spinnnerCategoryList);

        think();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_web, container, false);
        ButterKnife.bind(this, v);

        itemCategories = DatabaseManager.getInstance().getItemCategories();
        ArrayAdapter<String> spinnnerCategoryList = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, itemCategories);
        spinnnerCategoryList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(spinnnerCategoryList);

        if (spinnerCategory.getSelectedItem() != null) {
            itemList = DatabaseManager.getInstance().getItemsStringsForThisCategory(spinnerCategory.getSelectedItem().toString());
            ArrayAdapter<String> spinnerItemsList = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, itemList);
            spinnnerCategoryList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerItems.setAdapter(spinnerItemsList);
        }
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                think();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                recyclerViewItems.setAdapter(new ItemsRecyclerViewAdapter(getActivity(), getJiraApplication().getDatabaseManager().getItemsForThisName(spinnerItems.getSelectedItem().toString())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        recyclerViewItems.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewItems.setAdapter(new ItemsRecyclerViewAdapter(getActivity(), getJiraApplication().getDatabaseManager().getItemsForThisName(spinnerItems.getSelectedItem().toString())));
        return v;
    }

    private void think() {
        itemList = DatabaseManager.getInstance().getItemsStringsForThisCategory(spinnerCategory.getSelectedItem().toString());
        ArrayAdapter<String> spinnerItemsList = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, itemList);
        spinnerItemsList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItems.setAdapter(spinnerItemsList);
        if (spinnerItems.getSelectedItem() != null)
            recyclerViewItems.setAdapter(new ItemsRecyclerViewAdapter(getActivity(), getJiraApplication().getDatabaseManager().getItemsForThisName(spinnerItems.getSelectedItem().toString())));
    }

}
