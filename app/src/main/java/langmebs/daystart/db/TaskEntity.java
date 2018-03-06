package langmebs.daystart.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.sql.Date;
import java.util.UUID;

/**
 * Created by brandon on 3/6/2018.
 */

@Entity(tableName = "tasks")
public class TaskEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "task_id")
    private String taskId;

    @ColumnInfo(name = "task_name")
    private String name;

    @ColumnInfo(name = "task_date")
    private String day;

    @ColumnInfo(name = "task_completed")
    private boolean isComplete;

    @Ignore
    //Constructors
    //Standard Constructor that assigns an Id because the user never needs it
    public TaskEntity(String name, String day, boolean isComplete) {
        this.taskId = UUID.randomUUID().toString();
        this.name = name;
        this.day = day;
        this.isComplete = isComplete;
    }

    //Constructor that may be used by the application when giving assigned ids
    public TaskEntity(String taskId, String name, String day, boolean isComplete) {
        this.taskId = taskId;
        this.name = name;
        this.day = day;
        this.isComplete = isComplete;
    }

    //getters and setters
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String _id) {
        taskId = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean val) {
        isComplete = val;
    }

}
