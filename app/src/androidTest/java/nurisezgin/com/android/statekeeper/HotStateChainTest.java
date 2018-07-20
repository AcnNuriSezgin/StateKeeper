package nurisezgin.com.android.statekeeper;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import nurisezgin.com.android.statekeeper.testutils.ListItem;
import nurisezgin.com.android.statekeeper.testutils.ParcelableClass;
import nurisezgin.com.android.statekeeper.testutils.Person;
import nurisezgin.com.android.statekeeper.testutils.SerializableClass;
import nurisezgin.com.android.statekeeper.util.ParcelableList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by nuri on 15.07.2018
 */
@RunWith(AndroidJUnit4.class)
public class HotStateChainTest {

    private Person person;
    private Bundle bundle;
    private HotStateChain hotStateAdapter;
    private StateContext stateContext;

    @Before
    public void setUp_() {
        person = new Person();
        bundle = new Bundle();

        stateContext = StateContext.builder()
                .bundle(bundle)
                .thiz(person)
                .build();
        hotStateAdapter = new HotStateChain();
    }

    @Test
    public void should_SaveBooleanCorrect() {
        final boolean expected = true;

        person.booleanValue = expected;
        hotStateAdapter.save(stateContext);

        boolean actual = bundle.getBoolean("booleanValue");

        assertThat(actual, is(expected));
    }

    @Test
    public void should_RestoreBooleanCorrect() {
        final boolean expected = true;

        bundle.putBoolean("booleanValue", expected);
        hotStateAdapter.restore(stateContext);

        boolean actual = person.booleanValue;

        assertThat(actual, is(expected));
    }

    @Test
    public void should_SaveIntegerCorrect() {
        final int expected = 101;

        person.intValue = expected;
        hotStateAdapter.save(stateContext);

        int actual = bundle.getInt("intValue");

        assertThat(actual, is(expected));
    }

    @Test
    public void should_RestoreIntegerCorrect() {
        final int expected = 101;

        bundle.putInt("intValue", expected);
        hotStateAdapter.restore(stateContext);

        int actual = person.intValue;

        assertThat(actual, is(expected));
    }

    @Test
    public void should_SaveFloatCorrect() {
        final float expected = 10.10f;

        person.floatValue = expected;
        hotStateAdapter.save(stateContext);

        float actual = bundle.getFloat("floatValue");

        assertThat(actual, is(expected));
    }

    @Test
    public void should_RestoreFloatCorrect() {
        final float expected = 10.10f;

        bundle.putFloat("floatValue", expected);
        hotStateAdapter.restore(stateContext);

        float actual = person.floatValue;

        assertThat(actual, is(expected));
    }

    @Test
    public void should_SaveStringCorrect() {
        final String expected = "John";

        person.stringValue = expected;
        hotStateAdapter.save(stateContext);

        String actual = bundle.getString("stringValue");

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void should_RestoreStringCorrect() {
        final String expected = "John";

        bundle.putString("stringValue", expected);
        hotStateAdapter.restore(stateContext);

        String actual = person.stringValue;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void should_SaveSerializableCorrect() {
        final String expected = "John-serialize";

        person.serializableValue = new SerializableClass(expected);
        hotStateAdapter.save(stateContext);

        SerializableClass actual = (SerializableClass)
                bundle.getSerializable("serializableValue");

        assertThat(actual.value, is(equalTo(expected)));
    }

    @Test
    public void should_RestoreSerializableCorrect() {
        final String expected = "John-serialize";

        bundle.putSerializable("serializableValue", new SerializableClass(expected));
        hotStateAdapter.restore(stateContext);

        SerializableClass actual = person.serializableValue;

        assertThat(actual.value, is(equalTo(expected)));
    }

    @Test
    public void should_SaveParcelableCorrect() {
        final String expected = "John-parcel";

        person.parcelableValue = new ParcelableClass(expected);
        hotStateAdapter.save(stateContext);

        ParcelableClass actual = bundle.getParcelable("parcelableValue");

        assertThat(actual.value, is(equalTo(expected)));
    }

    @Test
    public void should_RestoreParcelableCorrect() {
        final String expected = "John-parcel";

        bundle.putParcelable("parcelableValue", new ParcelableClass(expected));
        hotStateAdapter.restore(stateContext);

        ParcelableClass actual = person.parcelableValue;

        assertThat(actual.value, is(equalTo(expected)));
    }

    @Test
    public void should_SaveListCorrect() {
        final String expected = "John-parcel";

        person.listValue = Arrays.asList(new ListItem(expected));
        hotStateAdapter.save(stateContext);

        ListItem actual =
                ((ParcelableList<ListItem>) bundle.getParcelable("listValue"))
                        .getList().get(0);

        assertThat(actual.value, is(equalTo(expected)));
    }

    @Test
    public void should_RestoreListCorrect() {
        final String expected = "John-parcel";

        ParcelableList<ListItem> value =
                new ParcelableList<>(Arrays.asList(new ListItem(expected)));

        bundle.putParcelable("listValue", value);
        hotStateAdapter.restore(stateContext);

        ListItem actual = person.listValue.get(0);

        assertThat(actual.value, is(equalTo(expected)));
    }

}