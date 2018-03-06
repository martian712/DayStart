package langmebs.daystart.db;

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

    //Used to get the list of tasks for a given date
    @Query("SELECT * FROM Tasks WHERE date(task_date) = date(:date)")
    List<TaskEntity> getTasksOfDay(String date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(TaskEntity task);

    @Query("UPDATE tasks SET task_completed = :val WHERE task_id = :id")
    void updateCompleted(String id, boolean val);

    //Delete all tasks from the previous day
    @Query("DELETE FROM tasks WHERE date(task_date) < date('now')")
    void deleteYesterdaysTasks();

}
