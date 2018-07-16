package nurisezgin.com.android.statekeeper.testutils;

import nurisezgin.com.android.statekeeper.annotations.ColdState;

public class Garage {

    @ColdState
    public Car car;

    public Garage() { }

    public Garage(Car car) {
        this.car = car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
