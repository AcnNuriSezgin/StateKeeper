package nurisezgin.com.android.statekeeper.reflection;

/**
 * Created by nuri on 15.07.2018
 */
public final class ReflectionAdapterFactory {

    public static ReflectionAdapter newReflectionAdapter(Object o) {
        return new ReflectionAdapterImp(o);
    }

}
