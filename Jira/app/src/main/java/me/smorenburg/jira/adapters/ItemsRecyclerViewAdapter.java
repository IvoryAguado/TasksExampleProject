package me.smorenburg.jira.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.smorenburg.jira.R;
import me.smorenburg.jira.db.models.base.WebServiceData;
import me.smorenburg.jira.dialogs.AddTaskDialog;

/**
 * Created by MR x on 24/03/2017.
 */


public class ItemsRecyclerViewAdapter extends RecyclerView.Adapter<ItemsRecyclerViewAdapter.ItemViewHolder> {

    private Context context;
    private List<WebServiceData> taskList;


    public ItemsRecyclerViewAdapter(Context context, List<WebServiceData> _taskList) {
        this.context = context;
        taskList = _taskList;
    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }

    @Override
    public ItemsRecyclerViewAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.item_list_item, null);
        return new ItemsRecyclerViewAdapter.ItemViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(ItemsRecyclerViewAdapter.ItemViewHolder holder, int position) {
        holder.setItem(taskList.get(position), position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View v;

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.timeStamp)
        TextView timeStamp;
        @BindView(R.id.user)
        TextView user;
        @BindView(R.id.list_image)
        ImageView list_image;

        private Context context;
        private WebServiceData task;
        private int pos;
        private AddTaskDialog show;

        public ItemViewHolder(View v, Context context) {
            super(v);
            this.v = v;
            ButterKnife.bind(this, v);

            this.context = context;

        }


        public void setItem(WebServiceData task, int pos) {
            this.task = task;
            this.pos = pos;
            try {
                title.setText(task.getItem() + " - " + task.getCategory());

                list_image.setImageResource(R.drawable.ic_extension_black_24dp);

                timeStamp.setText(task.getId().toString());

                user.setText("Farmer: " + task.getFarmer_id());
                user.append(" - " + task.getFarm_name());
                if (task.getCategory().length() != 0) {
                    description.setVisibility(View.VISIBLE);
                    description.setText(task.getPhone1());
                    description.append(" @ " + task.getLocation_1().getId());
                }
            } catch (Exception e) {
            }
        }
    }
}
