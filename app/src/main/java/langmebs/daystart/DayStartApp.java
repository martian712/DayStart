package langmebs.daystart;

import android.app.Application;

import langmebs.daystart.AppExecutors;
import langmebs.daystart.DataRepository;
import langmebs.daystart.db.TaskDatabase;

/**
 * Created by brandon on 3/8/2018.
 */

public class DayStartApp extends Application {
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public TaskDatabase getDatabase() {
        return TaskDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
