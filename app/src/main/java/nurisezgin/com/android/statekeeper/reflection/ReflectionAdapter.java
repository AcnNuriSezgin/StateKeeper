package nurisezgin.com.android.statekeeper.reflection;

import com.annimon.stream.function.Consumer;

/**
 * Created by nuri on 15.07.2018
 */
public interface ReflectionAdapter {

    void forEachField(Consumer<ReflectedField> consumer);

}
