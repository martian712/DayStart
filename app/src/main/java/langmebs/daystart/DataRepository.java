package langmebs.daystart; /**
 * Created by brandon on 3/8/2018.
 */


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.os.AsyncTask;
import android.provider.ContactsContract;


import java.util.Calendar;
import java.util.List;

import langmebs.daystart.db.TaskDao;
import langmebs.daystart.db.TaskDatabase;
import langmebs.daystart.db.TaskEntity;

/**
 * Repository handling the work with products and comments.
 */
public class DataRepository {

    private static DataRepository sInstance;

    private final TaskDatabase mDatabase;
    private TaskDao mTaskDao;
    private LiveData<List<TaskEntity>> mTasks;
    private MediatorLiveData<List<TaskEntity>> mObservableTasks;
    private MediatorLiveData<List<TaskEntity>> mObservableTodayTasks;
    private int dateLastObserved;

    private DataRepository(final TaskDatabase database) {
        mDatabase = database;
        mTaskDao = database.taskDao();
        mTasks = mTaskDao.loadAllTasks();
        mObservableTasks = new MediatorLiveData<>();
        mObservableTodayTasks = new MediatorLiveData<>();

        mObservableTasks.addSource(mDatabase.taskDao().loadAllTasks(),
                taskEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableTasks.postValue(taskEntities);
                    }
                });
        mObservableTodayTasks.addSource(mDatabase.taskDao().getTasksOfDay(Calendar.getInstance().getTimeInMillis()),
                taskEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableTodayTasks.postValue(taskEntities);
                    }
                });
    }

    public DataRepository(Application application) {
        mDatabase = TaskDatabase.getDatabase(application);
        mTaskDao = mDatabase.taskDao();
        mTasks = mTaskDao.loadAllTasks();
    }

    public static DataRepository getInstance(final TaskDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<TaskEntity>> getTasks() {
        return mTasks;
    }

    public LiveData<List<TaskEntity>> loadTodayTasks() {
//        mDatabase.taskDao().deleteYesterdaysTasks();
        return mObservableTodayTasks;
    }

    public LiveData<TaskEntity> loadTask(final int taskId) {
        return mDatabase.taskDao().loadTask(taskId);
    }

    public void deleteYesterdaysTasks() {
        mDatabase.taskDao().deleteYesterdaysTasks();
    }

    public void updateCompleted(final int taskId, boolean status) {
        mDatabase.runInTransaction(() -> {
            mDatabase.taskDao().updateCompleted(taskId, status);
        });
    }

    public void insertTasks(List<TaskEntity> tasks) {

        mDatabase.runInTransaction(() -> {
            mDatabase.taskDao().insertTasks(tasks);
        });

    }

    public void insert(TaskEntity task) {
        new insertAsyncTask(mTaskDao).execute(task);
    }


    private static class insertAsyncTask extends AsyncTask<TaskEntity, Void, Void> {

        private TaskDao mAsyncTaskDao;

        insertAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TaskEntity... params) {
            mAsyncTaskDao.insertTask(params[0]);
            return null;
        }
    }
}