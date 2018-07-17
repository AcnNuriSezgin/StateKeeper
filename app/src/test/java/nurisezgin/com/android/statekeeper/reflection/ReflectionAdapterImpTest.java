package nurisezgin.com.android.statekeeper.reflection;

import org.junit.Test;

import nurisezgin.com.android.statekeeper.testutils.Car;
import nurisezgin.com.android.statekeeper.testutils.Suv;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by nuri on 15.07.2018
 */
public class ReflectionAdapterImpTest {

    @Test
    public void should_ManipulateFieldValueCorrect() {
        final String fieldAge = "model";
        final String name = "BMW";
        final String expected = "Mercedes";

        Car car = new Car(name);

        new ReflectionAdapterImp(car)
                .forEachField(
                        field -> {
                            if (field.getName().equals(fieldAge)) {
                                field.trySetValue(expected);
                            }
                        });

        String actual = car.model;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void should_ManipulateSuperClassFieldValueCorrect() {
        final String fieldAge = "model";
        final String name = "BMW";
        final String expectedModel = "Mercedes";

        Suv suv = new Suv(name);

        new ReflectionAdapterImp(suv)
                .forEachField(
                        field -> {
                            if (field.getName().equals(fieldAge)) {
                                field.trySetValue(expectedModel);
                            }
                        });

        String actual = suv.model;

        assertThat(actual, is(equalTo(expectedModel)));
    }

}