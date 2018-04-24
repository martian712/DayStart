package langmebs.daystart.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by brandon on 2/27/2018.
 */

public interface Task {

    int getTaskId();
    String getName();
    long getDay();
    boolean isComplete();

}
