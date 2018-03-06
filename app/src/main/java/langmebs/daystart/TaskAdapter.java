package langmebs.daystart;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView taskName;
        public ViewHolder(View taskLayoutView) {
            super(taskLayoutView);
            taskName = (TextView) taskLayoutView.findViewById(R.id.task_name);
        }
    }

    private Task[] mTasks;

    public TaskAdapter(Task[] tasks) {
        mTasks = tasks;
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
    }

    @Override
    public int getItemCount() {
        return mTasks.length;
    }

}
