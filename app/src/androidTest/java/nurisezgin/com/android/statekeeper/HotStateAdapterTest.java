package nurisezgin.com.android.statekeeper;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.List;

import nurisezgin.com.android.statekeeper.annotations.HotState;
import nurisezgin.com.android.statekeeper.reflection.ReflectionAdapterFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by nuri on 15.07.2018
 */
@RunWith(AndroidJUnit4.class)
public class HotStateAdapterTest {

    private Person person;
    private Bundle bundle;
    private HotStateAdapter hotStateAdapter;

    @Before
    public void setUp_() {
        person = new Person();

        bundle = new Bundle();
        hotStateAdapter = new HotStateAdapter(
                bundle, ReflectionAdapterFactory.newReflectionAdapter(person));
    }

    @Test
    public void should_SaveBooleanCorrect() {
        final boolean expected = true;

        person.booleanValue = expected;
        hotStateAdapter.save();

        boolean actual = bundle.getBoolean("booleanValue");

        assertThat(actual, is(expected));
    }

    @Test
    public void should_RestoreBooleanCorrect() {
        final boolean expected = true;

        bundle.putBoolean("booleanValue", expected);
        hotStateAdapter.restore();

        boolean actual = person.booleanValue;

        assertThat(actual, is(expected));
    }

    @Test
    public void should_SaveIntegerCorrect() {
        final int expected = 101;

        person.intValue = expected;
        hotStateAdapter.save();

        int actual = bundle.getInt("intValue");

        assertThat(actual, is(expected));
    }

    @Test
    public void should_RestoreIntegerCorrect() {
        final int expected = 101;

        bundle.putInt("intValue", expected);
        hotStateAdapter.restore();

        int actual = person.intValue;

        assertThat(actual, is(expected));
    }

    @Test
    public void should_SaveFloatCorrect() {
        final float expected = 10.10f;

        person.floatValue = expected;
        hotStateAdapter.save();

        float actual = bundle.getFloat("floatValue");

        assertThat(actual, is(expected));
    }

    @Test
    public void should_RestoreFloatCorrect() {
        final float expected = 10.10f;

        bundle.putFloat("floatValue", expected);
        hotStateAdapter.restore();

        float actual = person.floatValue;

        assertThat(actual, is(expected));
    }

    @Test
    public void should_SaveStringCorrect() {
        final String expected = "John";

        person.stringValue = expected;
        hotStateAdapter.save();

        String actual = bundle.getString("stringValue");

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void should_RestoreStringCorrect() {
        final String expected = "John";

        bundle.putString("stringValue", expected);
        hotStateAdapter.restore();

        String actual = person.stringValue;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void should_SaveSerializableCorrect() {
        final String expected = "John-serialize";

        person.serializableValue = new SerializableClass(expected);
        hotStateAdapter.save();

        SerializableClass actual = (SerializableClass)
                bundle.getSerializable("serializableValue");

        assertThat(actual.value, is(equalTo(expected)));
    }

    @Test
    public void should_RestoreSerializableCorrect() {
        final String expected = "John-serialize";

        bundle.putSerializable("serializableValue", new SerializableClass(expected));
        hotStateAdapter.restore();

        SerializableClass actual = person.serializableValue;

        assertThat(actual.value, is(equalTo(expected)));
    }

    @Test
    public void should_SaveParcelableCorrect() {
        final String expected = "John-parcel";

        person.parcelableValue = new ParcelableClass(expected);
        hotStateAdapter.save();

        ParcelableClass actual = bundle.getParcelable("parcelableValue");

        assertThat(actual.value, is(equalTo(expected)));
    }

    @Test
    public void should_RestoreParcelableCorrect() {
        final String expected = "John-parcel";

        bundle.putParcelable("parcelableValue", new ParcelableClass(expected));
        hotStateAdapter.restore();

        ParcelableClass actual = person.parcelableValue;

        assertThat(actual.value, is(equalTo(expected)));
    }

//    @Test
//    public void should_SaveListCorrect() {
//        final String expected = "John-parcel";
//
//        person.listValue = Arrays.asList(new ListItem(expected));
//        hotStateAdapter.save();
//
//        ListItem actual =
//                ((ParcelableList<ListItem>) bundle.getParcelable("listValue"))
//                        .getList().get(0);
//
//        assertThat(actual.value, is(equalTo(expected)));
//    }
//
//    @Test
//    public void should_RestoreListCorrect() {
//        final String expected = "John-parcel";
//
//        ParcelableList<ListItem> value =
//                new ParcelableList<>(Arrays.asList(new ListItem(expected)));
//
//        bundle.putParcelable("listValue", value);
//        hotStateAdapter.restore();
//
//        ListItem actual = person.listValue.get(0);
//
//        assertThat(actual.value, is(equalTo(expected)));
//    }

    public static class Person {

        @HotState
        public boolean booleanValue;

        @HotState
        public int intValue;

        @HotState
        public float floatValue;

        @HotState
        public String stringValue;

        @HotState
        public SerializableClass serializableValue;

        @HotState
        public ParcelableClass parcelableValue;

        @HotState
        public List<ListItem> listValue;

    }

    public static class SerializableClass implements Serializable {

        public String value;

        public SerializableClass(String value) {
            this.value = value;
        }
    }

    public static class ParcelableClass implements Parcelable {

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

        public static Creator<ParcelableClass> CREATOR = new Creator<ParcelableClass>(){
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

    public static class ListItem implements Serializable {

        public String value;

        public ListItem(String value) {
            this.value = value;
        }
    }

}