package nurisezgin.com.android.statekeeper.reflection;

import java.lang.reflect.Field;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by nuri on 15.07.2018
 */
@Builder
public final class ReflectedField {

    @Getter
    private Class<?> type;
    @Getter
    private String name;
    @Getter
    private Object value;
    private Field field;
    private Object owner;

    public void trySetValue(Object value) {
        try {
            field.set(owner, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean hasAnnotation(Class ann) {
        return field.getAnnotation(ann) != null;
    }

    public boolean typeOf(Class<?> type) {
        return this.type.isAssignableFrom(type);
    }

    public boolean isTypeOfThatInterface(Class<?> _interface) {
        Class<?> clazz = type;

        if (clazz == null) {
            return false;
        }

        if (clazz.equals(_interface)) {
            return true;
        }

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
