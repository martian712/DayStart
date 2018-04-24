package langmebs.daystart;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import langmebs.daystart.db.TaskEntity;

/**
 * Created by brandon on 3/8/2018.
 */

public class TodayTasksViewModel extends AndroidViewModel {

    private MediatorLiveData<List<TaskEntity>> mObservableTasks;
    private DataRepository mRepo;
    private LiveData<List<TaskEntity>> mAllTasks;

    public TodayTasksViewModel(@NonNull Application application) {
        super(application);

        mRepo = new DataRepository(application);
        mAllTasks = mRepo.getTasks();

        mObservableTasks = new MediatorLiveData<>();

        //By default until we load values:
        mObservableTasks.setValue(null);

        // Here is the magic of app executors. We ask our application
        // layer to interact with the database on behalf of the viewmodel
        // as opposed to a traditional solution that involves AsyncTask
        // objects and an instance of the database held by the viewmodel
        // (and by each instance of this viewmodel)
        LiveData<List<TaskEntity>> tasks = ((DayStartApp) application).getRepository().loadTodayTasks();

        //Observe changes
        mObservableTasks.addSource(tasks, mObservableTasks::setValue);
    }

    public LiveData<List<TaskEntity>> getTodayTasks() {
        return mObservableTasks;
    }

    public void deleteYesterdaysTasks() {
        // Once again, asking the application to access the database
        // using app executors.
        ((DayStartApp) this.getApplication()).getRepository().deleteYesterdaysTasks();
    }

    public void updateCompleted(final int taskId, boolean completed) {
        ((DayStartApp) this.getApplication()).getRepository().updateCompleted(taskId, completed);
    }

    public void insertTasks(List<TaskEntity> tasks) {
        ((DayStartApp) this.getApplication()).getRepository().insertTasks(tasks);
    }

    public void insert(TaskEntity task) { mRepo.insert(task); }

}
