package langmebs.daystart;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TodayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TodayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";
    private Task[] mTasks;
    private TaskAdapter mTaskAdapter;
    private TextView mTitle;
    private RecyclerView mTaskList;

    private OnFragmentInteractionListener mListener;

    public TodayFragment() {
        // Required empty public constructor
    }

    public static TodayFragment newInstance(Task _tasks[]) {
        TodayFragment fragment = new TodayFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);

        Parcel p = Parcel.obtain();
        p.writeTypedArray(_tasks, 0);
        args.putParcelableArray(ARG_PARAM1, p.createTypedArray(Task.CREATOR));
        p.recycle();
        fragment.setArguments(args);

        //fragment.setTasks(_tasks);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            Parcel p = Parcel.obtain();
            p.writeTypedArray(getArguments().getParcelableArray(ARG_PARAM1), 0);
            mTasks = p.createTypedArray(Task.CREATOR);
            //mParam2 = getArguments().getString(ARG_PARAM2);
            p.recycle();

        }
        else
        {
            //TODO Error handling
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_today, container, false);

        // 3. create adapter and set item data TODO fix to make mTasks work
        Task tasks[] = {new Task("Take meds"),
                new Task("Shower"),
                new Task("Shave"),
                new Task("Work until Noon"),
                new Task("Eat Lunch"),
                new Task("Start Dinner"),
                new Task("Do the dishes")};
        mTaskAdapter = new TaskAdapter(tasks);
        // 1. get a reference to recyclerView
        mTaskList = (RecyclerView) rootView.findViewById(R.id.today_tasks);

        // 2. set layoutManger
        mTaskList.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 4. set adapter
        mTaskList.setAdapter(mTaskAdapter);
        // 5. set item animator to default
        mTaskList.setItemAnimator(new DefaultItemAnimator());

        mTitle = (TextView) rootView.findViewById(R.id.task_title);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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
}
