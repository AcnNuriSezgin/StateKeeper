package nurisezgin.com.android.statekeeper;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nuri on 15.07.2018
 */
public class ParcelableList<T extends Serializable> implements Parcelable {

    private List<T> list;

    public ParcelableList(List<T> list) {
        if (list == null) {
            this.list = new ArrayList<>();
        }

        this.list = list;
    }

    protected ParcelableList(Parcel in) {
        list = new ArrayList<>();
        in.readList(list, null);
    }

    public List<T> getList() {
        return list;
    }

    public static final Creator<ParcelableList> CREATOR = new Creator<ParcelableList>() {
        @Override
        public ParcelableList createFromParcel(Parcel in) {
            return new ParcelableList(in);
        }

        @Override
        public ParcelableList[] newArray(int size) {
            return new ParcelableList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(list);
    }

}
