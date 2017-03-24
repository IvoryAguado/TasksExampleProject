package me.smorenburg.jira.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.smorenburg.jira.R;
import me.smorenburg.jira.db.core.DatabaseManager;
import me.smorenburg.jira.db.models.base.Skill;
import me.smorenburg.jira.db.models.base.Task;
import me.smorenburg.jira.db.models.base.User;


/**
 * Created by ivor.aguado on 27/08/2015.
 */
public class AddTaskDialog extends AlertDialog.Builder {

    private Context context;
    private Task task = new Task();
    private List<Skill> skillsList;
    private List<User> perfectUserForThisSkill;

    public interface OnCloseTaskDialog {
        void onSuccess();

        void onDelete();

        void onFail();
    }

    private OnCloseTaskDialog onCloseTaskDialog;

    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.textDuration)
    TextView textDuration;
    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.skills)
    Spinner skills;
    @BindView(R.id.users)
    Spinner users;
    @BindView(R.id.timeDurationTask)
    SeekBar duration;

    public AddTaskDialog(final Context context, final Task task) {
        super(context);
        this.context = context;
        this.task = task;
        init();
        if (DatabaseManager.getInstance().getCurrentUser().isAdmin()) {
            setTitle(R.string.edit_task);
            setNeutralButton(R.string.delete_task, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseManager.getInstance().delete(task);
                    if (onCloseTaskDialog != null)
                        onCloseTaskDialog.onDelete();
                }
            });
        } else {
            setTitle(R.string.view_task);
        }
    }


    public AddTaskDialog(final Context context) {
        super(context);
        this.context = context;
        init();
        setTitle(R.string.add_task);
    }

    private void init() {
        final View v = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.add_task_dialog, null);
        setView(v);
        ButterKnife.bind(this, v);

        skillsList = DatabaseManager.getInstance().findAll(Skill.class);
        ArrayAdapter<Skill> skillArrayAdapter = new ArrayAdapter<Skill>(context, android.R.layout.simple_spinner_item, skillsList);
        skillArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        skills.setAdapter(skillArrayAdapter);

        perfectUserForThisSkill = DatabaseManager.getInstance().getPerfectUserForThisSkill((Skill) skills.getSelectedItem());
        ArrayAdapter<User> userArrayAdapter = new ArrayAdapter<User>(context, android.R.layout.simple_spinner_item, perfectUserForThisSkill);
        userArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        users.setAdapter(userArrayAdapter);

        if (task == null)
            task = new Task();
        for (int i = 0; i < skillsList.size(); i++) {
            if (skillsList.get(i).getId() == task.getSkill_id()) {
                skills.setSelection(i);
            }
        }

        if (task.getId() == null)
            thinkSuggestedUserForCurrentSkill();

        if (!DatabaseManager.getInstance().getCurrentUser().isAdmin()) {
            description.setEnabled(false);
            title.setEnabled(false);
            duration.setEnabled(false);
            users.setEnabled(false);
            skills.setEnabled(false);
            description.setFocusable(false);
            title.setFocusable(false);
            duration.setFocusable(false);
            users.setFocusable(false);
            skills.setFocusable(false);
        }

        skills.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                thinkSuggestedUserForCurrentSkill();

                ArrayAdapter<User> userArrayAdapter = new ArrayAdapter<User>(context, android.R.layout.simple_spinner_item, perfectUserForThisSkill);
                userArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                users.setAdapter(userArrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        duration.setMax(23 * 60 * 60 * 1000);
        duration.setProgress(task.getTaskDuration().intValue());

        title.setText(task.getTitle());
        description.setText(task.getDescription());

        textDuration.setText("ET: " + task.getTaskDuration() / 1000 / 60 / 60 + "h");

        duration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textDuration.setText("ET: " + duration.getProgress() / 1000 / 60 / 60 + "h");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textDuration.setText("ET: " + duration.getProgress() / 1000 / 60 / 60 + "h");
            }
        });

        setCancelable(true);

        setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                task.setTitle(title.getText().toString().length() != 0 ? title.getText().toString() : "Default Title");
                task.setDescription(description.getText().toString());
                task.setSkill((Skill) skills.getSelectedItem());
                User selectedItem = (User) users.getSelectedItem();
                task.setUser(selectedItem);
                task.setUser_id(selectedItem.getId());
                Log.d("AddTaskDialog", " -> " + ((User) users.getSelectedItem()).getName());
                task.setTaskDuration((long) duration.getProgress());
                DatabaseManager.getInstance().save(task);
                if (onCloseTaskDialog != null)
                    onCloseTaskDialog.onSuccess();
            }
        });
        if (DatabaseManager.getInstance().getCurrentUser().isAdmin())
            setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (onCloseTaskDialog != null)
                        onCloseTaskDialog.onFail();

                }
            });
        else if (!task.getTaskDone())
            setNeutralButton(R.string.done, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    task.setTaskDone(true);
                    DatabaseManager.getInstance().getDaoSession().update(task);
                    if (onCloseTaskDialog != null)
                        onCloseTaskDialog.onSuccess();
                }
            });
    }

    private void thinkSuggestedUserForCurrentSkill() {
        perfectUserForThisSkill = DatabaseManager.getInstance().getPerfectUserForThisSkill((Skill) skills.getSelectedItem());
        Long lastBestDuration = Long.MAX_VALUE;
        for (User u : perfectUserForThisSkill) {
            if (u.getTasksTotalDuration() < lastBestDuration) {
                lastBestDuration = u.getTasksTotalDuration();
                users.setSelection(perfectUserForThisSkill.indexOf(u));
            }
        }
    }

    public void setOnCloseTaskDialog(OnCloseTaskDialog onCloseTaskDialog) {
        this.onCloseTaskDialog = onCloseTaskDialog;
    }
}
