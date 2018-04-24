package langmebs.daystart;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import langmebs.daystart.databinding.FragmentTodayBinding;
import langmebs.daystart.db.TaskEntity;
import langmebs.daystart.model.Task;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TodayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TodayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";
    private Task[] mTasks;

    private TaskAdapter mTaskAdapter;
    private FragmentTodayBinding mBinding;
    private TodayTasksViewModel viewModel;

    private TextView mTitle;
    private RecyclerView mTaskList;

    private OnFragmentInteractionListener mListener;

    public TodayFragment() {
        // Required empty public constructor
    }

    public static TodayFragment newInstance(/*Task _tasks[]*/) {    //TODO REMOVE THIS PLEASE
        TodayFragment fragment = new TodayFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);

        //TODO REMOVE THIS WITH THE PARAMETER WHEN IT IS SORTED OUT
        /*
        Parcel p = Parcel.obtain();
        p.writeTypedArray(_tasks, 0);
        args.putParcelableArray(ARG_PARAM1, p.createTypedArray(Task.CREATOR));
        p.recycle();
        fragment.setArguments(args);

        fragment.setTasks(_tasks);
        */
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            //TODO REMOVE THIS WITH THE REST OF THE OLD TASK PASSING
            /*
            Parcel p = Parcel.obtain();
            p.writeTypedArray(getArguments().getParcelableArray(ARG_PARAM1), 0);
            mTasks = p.createTypedArray(Task.CREATOR);
            //mParam2 = getArguments().getString(ARG_PARAM2);
            p.recycle();
            */

        }
        else
        {
            //TODO Error handling
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* TODO Remove old code
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_today, container, false);
        */
        // 3. create adapter and set item data TODO fix to make mTasks work
       /* Task tasks[] = {new Task("Take meds", false),
                new Task("Shower", false),
                new Task("Shave", false),
                new Task("Work until Noon", false),
                new Task("Eat Lunch", false),
                new Task("Start Dinner", false),
                new Task("Do the dishes", false)};
                */
       /* TODO Remove old code when new task database works
        mTaskAdapter = new TaskAdapter(tasks);
        // 1. get a reference to recyclerView
        mTaskList = (RecyclerView) rootView.findViewById(R.id.today_tasks);

        // 2. set layoutManger
        mTaskList.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Add dividers for the list
        DividerItemDecoration bar = new DividerItemDecoration(mTaskList.getContext(), DividerItemDecoration.VERTICAL);
        mTaskList.addItemDecoration(bar);
        // 4. set adapter
        mTaskList.setAdapter(mTaskAdapter);
        // 5. set item animator to default
        mTaskList.setItemAnimator(new DefaultItemAnimator());

        mTitle = (TextView) rootView.findViewById(R.id.task_title);

        return rootView;
        */

        //inflate the data bound view
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false);
        //Set adapter
        mTaskAdapter = new TaskAdapter(mTaskClickCallback);
        mBinding.todayTasks.setAdapter(mTaskAdapter);
        mBinding.addTaskToday.setOnClickListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.viewModel = ViewModelProviders.of(this).get(TodayTasksViewModel.class);

        subscribeUi(viewModel);
    }

    private void subscribeUi(TodayTasksViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getTodayTasks().observe(this, new Observer<List<TaskEntity>>() {
            @Override
            public void onChanged(@Nullable List<TaskEntity> myTasks) {
                // Handle the else case where the data is being loaded
                // Change value to signal displaying loading elements
                if(myTasks != null) {
                    mBinding.setIsLoading(true);
                    mTaskAdapter.setTaskList(myTasks);
                } else {
                    mBinding.setIsLoading(false);
                }
                mBinding.executePendingBindings();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add_task_today:
                if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                    TaskEntity t = new TaskEntity("Work on project", Calendar.getInstance().getTimeInMillis(), false);
                    List<TaskEntity> l = new Vector();
                    l.add(t);
                    viewModel.insertTasks(l);
                }
                break;
        }
    }


    // TODO Delete this if not needed
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    //************************************

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /*public void setTasks(Task _tasks[]) {
        mTasks = _tasks;
    }*/

    private final TaskClickCallback mTaskClickCallback = new TaskClickCallback() {
        @Override
        public void onClick(Task task) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                viewModel.updateCompleted(task.getTaskId(), !(task.isComplete()));
            }
        }
    };

    //TODO Button for adding a new task
    /*
    private final NewTaskCallback mNewTaskCallback = new NewTaskCallback() {
        @Override
        public void onClick() {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                TaskEntity t = new TaskEntity("Work on project", Calendar.getInstance().getTimeInMillis(), false);
                List<TaskEntity> l = new Vector();
                l.add(t);
                viewModel.insertTasks(l);
            }
        }
    };
    */
}
