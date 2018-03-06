package langmebs.daystart.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.sql.Date;
import java.util.List;


@Dao
public interface TaskDao {

    //Used to get the list of tasks for a given date
    @Query("SELECT * FROM Tasks WHERE task_date = :date")
    List<TaskEntity> getTasksOfDay(Date date);

    //Find a task by ID. Usually for updating an entity
    @Query("")

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(TaskEntity task);

    @Update

}
