package langmebs.daystart;

import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import langmebs.daystart.databinding.TaskLayoutBinding;
import android.support.v7.widget.CardView;
import langmebs.daystart.db.TaskDatabase;
import langmebs.daystart.db.TaskEntity;
import langmebs.daystart.model.Task;

import android.databinding.DataBindingUtil;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {

        /* TODO Remove old code
        public TextView taskName;
        public ImageButton completed;
        public TaskEntity task;
        public ViewHolder(View taskLayoutView) {
            super(taskLayoutView);
            taskName = (TextView) taskLayoutView.findViewById(R.id.task_name);
            completed = (ImageButton) taskLayoutView.findViewById(R.id.complete_box);
        }
        */
        final TaskLayoutBinding binding;

        public ViewHolder(TaskLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    /* TODO Remove old code after TaskAdapter works
    private TaskEntity[] mTasks;

    public TaskAdapter(TaskDatabase taskDatabase) {
        mTasks = taskDatabase.taskDao().getTasksOfDay(Calendar.getInstance().getTimeInMillis()).;
    }

    //create new views
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //create new view
        View taskLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        //create ViewHolder and return it
        return new ViewHolder(taskLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //get element from todays tasks at this position
        //replace contents of view with that element
        holder.taskName.setText(mTasks[position].getName());
        holder.task = mTasks[position];

        if(mTasks[position].isComplete())
            holder.completed.setImageResource(R.drawable.filled_check);
        else
            holder.completed.setImageResource(R.drawable.empty_check);
    }
    */

    private List<? extends Task> mTasks;

    @Nullable
    private final TaskClickCallback mTaskClickCallback;
    public TaskAdapter(@Nullable TaskClickCallback clickCallback) {
        mTaskClickCallback = clickCallback;
    }

    public void setTaskList(final List<? extends Task> taskList) {
        if(mTasks == null) {
            mTasks = taskList;
            notifyItemRangeInserted(0, taskList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mTasks.size();
                }
                @Override
                public int getNewListSize() {
                    return taskList.size();
                }
                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mTasks.get(oldItemPosition).getTaskId() == taskList.get(newItemPosition).getTaskId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Task newTask = taskList.get(newItemPosition);
                    Task oldTask = mTasks.get(oldItemPosition);
                    return newTask.getTaskId() == oldTask.getTaskId()
                            && Objects.equals(newTask.getName(), oldTask.getName())
                            && newTask.getDay() == oldTask.getDay()
                            && newTask.isComplete() == oldTask.isComplete();
                }
            });
            mTasks = taskList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TaskLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.task_layout, parent,false);
        binding.setCallback(mTaskClickCallback);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setTask(mTasks.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mTasks == null ? 0 : mTasks.size();
    }

}
