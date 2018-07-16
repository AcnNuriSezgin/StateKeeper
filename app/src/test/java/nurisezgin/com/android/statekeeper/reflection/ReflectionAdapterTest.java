package nurisezgin.com.android.statekeeper.reflection;

import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;


/**
 * Created by nuri on 16.07.2018
 */
public class ReflectionAdapterTest {

    @Test
    public void should_ListTypeOfSerializableCorrect() {
        final boolean expected = true;

        List list = new ArrayList<>();
        boolean actual = ReflectionAdapter.isTypeOf(list.getClass(), Serializable.class);

        assertThat(actual, is(expected));
    }

    @Test
    public void should_ListNotTypeOfCharSequenceCorrect() {
        final boolean expected = false;

        List list = new ArrayList<>();
        boolean actual = ReflectionAdapter.isTypeOf(list.getClass(), CharSequence.class);

        assertThat(actual, is(expected));
    }

}