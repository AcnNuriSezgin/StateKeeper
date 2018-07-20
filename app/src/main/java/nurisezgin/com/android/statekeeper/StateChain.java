package nurisezgin.com.android.statekeeper;

/**
 * Created by nuri on 15.07.2018
 */
interface StateChain {

    void save(StateContext context);

    void restore(StateContext context);

}
