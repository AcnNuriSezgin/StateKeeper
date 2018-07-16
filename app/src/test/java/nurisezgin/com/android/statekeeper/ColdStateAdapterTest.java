package nurisezgin.com.android.statekeeper;

import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Supplier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.Serializable;

import nurisezgin.com.android.statekeeper.annotations.ColdState;
import nurisezgin.com.android.statekeeper.reflection.ReflectionAdapterFactory;
import nurisezgin.com.android.statekeeper.storage.StorageAdapter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

/**
 * Created by nuri on 16.07.2018
 */
@RunWith(MockitoJUnitRunner.class)
public class ColdStateAdapterTest {

    private static final String PERSON_NAME = "John";
    public static final String FIELD_PERSON = "person";

    @Mock
    StorageAdapter mockStorageAdapter;

    private ColdStateAdapter coldStateAdapter;
    private People people;

    @Test
    public void should_SaveCorrect() {
        people = new People(new Person(PERSON_NAME));
        coldStateAdapter = new ColdStateAdapter(
                mockStorageAdapter, ReflectionAdapterFactory.newReflectionAdapter(people));

        ArgumentCaptor<Supplier> captor = ArgumentCaptor.forClass(Supplier.class);
        coldStateAdapter.save();

        verify(mockStorageAdapter).write(captor.capture());

        Object object = captor.getValue().get();
        ColdStateAdapter.ObjectTable table = (ColdStateAdapter.ObjectTable) object;
        Person person = (Person) table.get(FIELD_PERSON);

        assertThat(person.name, is(equalTo(PERSON_NAME)));
    }
    
    @Test
    public void should_RestoreCorrect() {
        people = new People(new Person(""));
        coldStateAdapter = new ColdStateAdapter(
                mockStorageAdapter, ReflectionAdapterFactory.newReflectionAdapter(people));

        doAnswer(invocation -> {
            Consumer consumer = (Consumer) invocation.getArguments()[0];
            ColdStateAdapter.ObjectTable table = new ColdStateAdapter.ObjectTable();
            table.put(FIELD_PERSON, new Person(PERSON_NAME));

            consumer.accept(table);
            return null;
        }).when(mockStorageAdapter).read(any());

        coldStateAdapter.restore();

        String actual = people.person.name;

        assertThat(actual, is(equalTo(PERSON_NAME)));
    }

    public static class People {

        @ColdState
        public Person person;

        public People(Person person) {
            this.person = person;
        }
    }

    public static class Person implements Serializable {

        public String name;

        public Person(String name) {
            this.name = name;
        }
    }


}