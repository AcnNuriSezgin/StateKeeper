package nurisezgin.com.android.statekeeper.testutils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nuri on 20.07.2018
 */
public class ParcelableClass implements Parcelable {

    public String value;

    public ParcelableClass(String value) {
        this.value = value;
    }

    public ParcelableClass(Parcel in) {
        value = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value);
    }

    public static Creator<ParcelableClass> CREATOR = new Creator<ParcelableClass>() {
        @Override
        public ParcelableClass createFromParcel(Parcel source) {
            return new ParcelableClass(source);
        }

        @Override
        public ParcelableClass[] newArray(int size) {
            return new ParcelableClass[size];
        }
    };
}
