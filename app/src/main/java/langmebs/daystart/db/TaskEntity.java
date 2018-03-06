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

@Entity
public class TaskEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "task_id")
    private String taskId;

    @ColumnInfo(name = "task_name")
    private String name;

    @ColumnInfo(name = "task_date")
    private Date day;

    @ColumnInfo(name = "task_completed")
    private boolean isComplete;

    @Ignore
    //Constructors
    //Standard Constructor that assigns an Id because the user never needs it
    public TaskEntity(String _name, Date _day, boolean _complete) {
        taskId = UUID.randomUUID().toString();
        name = _name;
        day = _day;
        isComplete = _complete;
    }

    //Constructor that may be used by the application when giving assigned ids
    //TODO Do I need this?
    public TaskEntity(String _id, String _name, Date _day, boolean _complete) {
        taskId = _id;
        name = _name;
        day = _day;
        isComplete = _complete;
    }

    //getters and setters
    public String getId() {
        return taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean val) {
        isComplete = val;
    }

}
