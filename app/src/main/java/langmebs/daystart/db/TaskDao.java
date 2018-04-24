package langmebs.daystart.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.sql.Date;
import java.util.List;


@Dao
public interface TaskDao {

    //The two queries below are for the DataRepository
    @Query("SELECT * FROM Tasks")
    LiveData<List<TaskEntity>> loadAllTasks();

    @Query("Select * FROM Tasks WHERE task_id = :taskId")
    LiveData<TaskEntity> loadTask(int taskId);

    //Used to get the list of tasks for a given date
    @Query("SELECT * FROM Tasks WHERE date(task_date,'unixepoch','localtime') = date(:date,'unixepoch','localtime')")
    LiveData<List<TaskEntity>> getTasksOfDay(long date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTasks(List<TaskEntity> tasks);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(TaskEntity task);

    @Query("UPDATE tasks SET task_completed = :val WHERE task_id = :id")
    void updateCompleted(int id, boolean val);

    //Delete all tasks from the previous day
    @Query("DELETE FROM tasks WHERE date(task_date, 'unixepoch','localtime') < date('now')")
    void deleteYesterdaysTasks();

}
