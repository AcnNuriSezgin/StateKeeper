package nurisezgin.com.android.statekeeper.reflection;

import com.annimon.stream.function.Consumer;

/**
 * Created by nuri on 15.07.2018
 */
public interface ReflectionAdapter {

    boolean isTypeOfThat(Class<?> _interface);

    void forEachField(Consumer<ConvertedField> consumer);

    static boolean isTypeOf(Class<?> clazz, Class<?> _interface) {
        while (clazz != null && clazz != Object.class) {
            Class<?>[] classes = clazz.getInterfaces();

            if (classes == null || classes.length == 0) {
                return false;
            }

            for (Class<?> superInterface : classes) {
                if (superInterface.equals(_interface)) {
                    return true;
                }
            }

            clazz = clazz.getSuperclass();
        }

        return false;
    }

}
