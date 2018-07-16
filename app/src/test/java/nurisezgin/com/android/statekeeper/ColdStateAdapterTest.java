package nurisezgin.com.android.statekeeper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.Serializable;

import nurisezgin.com.android.statekeeper.annotations.ColdState;
import nurisezgin.com.android.statekeeper.storage.StorageAdapter;

/**
 * Created by nuri on 16.07.2018
 */
@RunWith(MockitoJUnitRunner.class)
public class ColdStateAdapterTest {

    @Mock
    StorageAdapter storageAdapter;

    @Test
    public void should_SaveCorrect() {

    }
    
    @Test
    public void should_RestoreCorrect() {

    }

    private static class People {

        @ColdState
        public Person person;

        public People(Person person) {
            this.person = person;
        }
    }

    private static class Person implements Serializable {

        public String name;

        public Person(String name) {
            this.name = name;
        }
    }


}