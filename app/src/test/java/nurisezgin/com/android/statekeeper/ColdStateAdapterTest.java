package nurisezgin.com.android.statekeeper;

import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Supplier;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import nurisezgin.com.android.statekeeper.reflection.ReflectionAdapterFactory;
import nurisezgin.com.android.statekeeper.storage.StorageAdapter;
import nurisezgin.com.android.statekeeper.testutils.Car;
import nurisezgin.com.android.statekeeper.testutils.Garage;

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

    private static final String CAR_MODEL = "BMW";
    public static final String FIELD_PERSON = "car";

    @Mock
    StorageAdapter mockStorageAdapter;

    private ColdStateAdapter coldStateAdapter;
    private Garage garage;

    @Before
    public void setUp_() {
        garage = new Garage();
        coldStateAdapter = new ColdStateAdapter(
                mockStorageAdapter, ReflectionAdapterFactory.newReflectionAdapter(garage));
    }

    @Test
    public void should_SaveCorrect() {
        garage.setCar(new Car(CAR_MODEL));

        ArgumentCaptor<Supplier> captor = ArgumentCaptor.forClass(Supplier.class);
        coldStateAdapter.save();

        verify(mockStorageAdapter).write(captor.capture());

        Object object = captor.getValue().get();
        ColdStateAdapter.ObjectTable table = (ColdStateAdapter.ObjectTable) object;
        Car car = (Car) table.get(FIELD_PERSON);

        assertThat(car.model, is(equalTo(CAR_MODEL)));
    }
    
    @Test
    public void should_RestoreCorrect() {
        garage.setCar(new Car(""));

        doAnswer(invocation -> {
            Consumer consumer = (Consumer) invocation.getArguments()[0];
            ColdStateAdapter.ObjectTable table = new ColdStateAdapter.ObjectTable();
            table.put(FIELD_PERSON, new Car(CAR_MODEL));

            consumer.accept(table);
            return null;
        }).when(mockStorageAdapter).read(any());

        coldStateAdapter.restore();

        String actual = garage.car.model;

        assertThat(actual, is(equalTo(CAR_MODEL)));
    }

}