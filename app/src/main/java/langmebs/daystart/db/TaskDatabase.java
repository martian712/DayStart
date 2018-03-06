package langmebs.daystart.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = TaskEntity.class, version = 1)
public abstract class TaskDatabase extends RoomDatabase {

    public static volatile TaskDatabase INSTANCE;
    public abstract TaskDao taskDao();

    public static TaskDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TaskDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class, "Task.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
