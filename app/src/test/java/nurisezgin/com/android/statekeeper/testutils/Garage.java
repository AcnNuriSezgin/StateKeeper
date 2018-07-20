package nurisezgin.com.android.statekeeper.testutils;

import nurisezgin.com.android.statekeeper.annotations.ColdState;
import nurisezgin.com.android.statekeeper.annotations.StateIdentifier;

public class Garage {

    @StateIdentifier
    public String identifier;

    @ColdState
    public Car car;

    public Garage(String identifier) {
        this.identifier = identifier;
    }

    public Garage(Car car) {
        this.car = car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
