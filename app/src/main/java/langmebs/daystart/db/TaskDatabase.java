package langmebs.daystart.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import langmebs.daystart.AppExecutors;

@Database(entities = TaskEntity.class, version = 1)
public abstract class TaskDatabase extends RoomDatabase {

    @VisibleForTesting
    public static final String DATABASE_NAME = "Task.db";

    public static TaskDatabase INSTANCE;
    public abstract TaskDao taskDao();
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static TaskDatabase getInstance(Context context, AppExecutors executors) {
        if (INSTANCE == null) {
            synchronized (TaskDatabase.class) {
                if (INSTANCE == null) {
                    //INSTANCE = buildDatabase(context.getApplicationContext(), executors);
                    //INSTANCE.updateDatabaseCreated(context.getApplicationContext());
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }

    public static TaskDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class, DATABASE_NAME)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static TaskDatabase buildDatabase(final Context appContext,
                                             final AppExecutors executors) {
        return Room.databaseBuilder(appContext, TaskDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(() -> {
                            // Generate the data for pre-population
                            TaskDatabase database = TaskDatabase.getInstance(appContext, executors);
                            //TODO REMOVE THIS TEST DATA
                            /*
                            TaskEntity t = new TaskEntity("Work on project", Calendar.getInstance().getTimeInMillis(), false);
                            List<TaskEntity> l = new Vector();
                            l.add(t);
                            insertData(database, l);
                            */
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                }).build();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    private static void insertData(final TaskDatabase db, final List<TaskEntity> tasks) {
        db.runInTransaction(() -> {
            db.taskDao().insertTasks(tasks);
        });
    }
}
