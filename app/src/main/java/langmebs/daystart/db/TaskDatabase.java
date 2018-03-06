package langmebs.daystart.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.RoomDatabase;

public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
}
