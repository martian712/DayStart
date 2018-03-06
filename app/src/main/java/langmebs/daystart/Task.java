package langmebs.daystart;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by brandon on 2/27/2018.
 */

public class Task implements Parcelable {

    private String name;
    public Task() {

    }

    public Task(String _name) {
        name = _name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
    }

    public static final Parcelable.Creator<Task> CREATOR
            = new Parcelable.Creator<Task>() {
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    private Task(Parcel in) {
        name = in.readString();
    }
}
