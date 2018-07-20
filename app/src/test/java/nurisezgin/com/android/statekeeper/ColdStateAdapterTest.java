package nurisezgin.com.android.statekeeper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import nurisezgin.com.android.statekeeper.testutils.Car;
import nurisezgin.com.android.statekeeper.testutils.Garage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by nuri on 16.07.2018
 */
public class ColdStateAdapterTest {

    private static final String IDENTIFIER = "::ColdStateAdapterTest#identifier";
    private static final String CAR_MODEL = "BMW";
    private StateContext stateContext;
    private ColdStateChain coldStateAdapter;
    private Garage garage;

    @Before
    public void setUp_() {
        garage = new Garage(IDENTIFIER);
        stateContext = StateContext.builder()
                .bundle(null)
                .cachePath(new File("."))
                .thiz(garage)
                .build();

        coldStateAdapter = new ColdStateChain();
    }

    @After
    public void tearDown_() {
        new File(".", IDENTIFIER).delete();
    }

    @Test
    public void should_SaveToFileCorrect() {
        garage.setCar(new Car(CAR_MODEL));
        coldStateAdapter.save(stateContext);

        boolean isFileExist = new File(".", IDENTIFIER).exists();

        assertThat(isFileExist, is(true));
    }
    
    @Test
    public void should_RestoreFromFileCorrect() {
        garage.setCar(new Car(CAR_MODEL));
        coldStateAdapter.save(stateContext);

        garage.setCar(new Car(""));
        coldStateAdapter.restore(stateContext);

        String actual = garage.car.model;

        assertThat(actual, is(equalTo(CAR_MODEL)));
    }

}