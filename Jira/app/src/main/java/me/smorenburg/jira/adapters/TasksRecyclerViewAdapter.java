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
import butterknife.OnClick;
import me.smorenburg.jira.R;
import me.smorenburg.jira.db.models.base.Task;
import me.smorenburg.jira.dialogs.AddTaskDialog;

public class TasksRecyclerViewAdapter extends RecyclerView.Adapter<TasksRecyclerViewAdapter.TaskViewHolder> {

    private Context context;
    private List<Task> taskList;


    public TasksRecyclerViewAdapter(Context context, List<Task> _taskList) {
        this.context = context;
        taskList = _taskList;
    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.item_list_item, null);
        return new TaskViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.setTask(taskList.get(position), position);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

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
        private Task task;
        private int pos;
        private AddTaskDialog show;

        public TaskViewHolder(View v, Context context) {
            super(v);
            this.v = v;
            ButterKnife.bind(this, v);

            this.context = context;

        }

        @OnClick()
        public void submit(View view) {
            show = new AddTaskDialog(context, task);
            show.setOnCloseTaskDialog(new AddTaskDialog.OnCloseTaskDialog() {
                @Override
                public void onSuccess() {
                    notifyDataSetChanged();
                }

                @Override
                public void onDelete() {
                    taskList.remove(task);
                    notifyItemRemoved(pos);
                }

                @Override
                public void onFail() {

                }
            });
            show.show();
        }

        public void setTask(Task task, int pos) {
            this.task = task;
            this.pos = pos;
            try {
                title.setText(task.getTitle() + " - " + task.getSkill().getTittle());

                user.setText(task.getUser().getName() + " " + task.getUser().getLname() + " (" + task.getUser().getUsername() + ") Done: "+task.getTaskDone());

                timeStamp.setText("ET: " + task.getTaskDuration() / 1000 / 60 / 60 + "h");
                if (task.getDescription().length() != 0) {
                    description.setVisibility(View.VISIBLE);
                    description.setText(task.getDescription());
                }
                if (task.getTaskDone())
                    v.setBackgroundResource(R.color.colorPrimarySuper);
            } catch (Exception e) {
            }
        }
    }
}
