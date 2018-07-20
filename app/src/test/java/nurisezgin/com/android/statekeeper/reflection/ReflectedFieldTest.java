package nurisezgin.com.android.statekeeper.reflection;

import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by nuri on 17.07.2018
 */
public class ReflectedFieldTest {

    @Test
    public void should_ListTypeOfListCorrect() {
        final boolean expected = true;

        ReflectedField field = ReflectedField.builder()
                .type(List.class)
                .build();

        boolean actual = field.isTypeOfThatInterface(List.class);

        assertThat(actual, is(expected));
    }

    @Test
    public void should_ListTypeOfSerializableCorrect() {
        final boolean expected = true;

        ReflectedField field = ReflectedField.builder()
                .type(List.class)
                .build();

        boolean actual = field.isTypeOfThatInterface(Collection.class);

        assertThat(actual, is(expected));
    }

    @Test
    public void should_ListNotTypeOfCharSequenceCorrect() {
        final boolean expected = false;

        ReflectedField field = ReflectedField.builder()
                .type(List.class)
                .build();

        boolean actual = field.isTypeOfThatInterface(CharSequence.class);

        assertThat(actual, is(expected));
    }

}