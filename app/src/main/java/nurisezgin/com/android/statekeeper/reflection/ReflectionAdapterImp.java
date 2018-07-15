package nurisezgin.com.android.statekeeper.reflection;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;

import java.lang.reflect.Field;

/**
 * Created by nuri on 15.07.2018
 */
public final class ReflectionAdapterImp implements ReflectionAdapter {

    private final Object object;

    ReflectionAdapterImp(Object object) {
        this.object = object;
    }

    @Override
    public boolean isTypeOfThatInterface(Class<?> _interface) {
        Class<?> clazz = object.getClass();

        while (clazz != Object.class) {
            Class<?>[] classes = clazz.getInterfaces();
            for (Class<?> superInterface : classes) {
                if (superInterface.equals(_interface)) {
                    return true;
                }
            }

            clazz = clazz.getSuperclass();
        }

        return false;
    }

    @Override
    public void forEachField(Consumer<ConvertedField> consumer) {
        Class<?> clazz = object.getClass();

        while (clazz != Object.class) {
            Field[] fields = clazz.getFields();

            Stream.of(fields)
                    .map(this::toConvertedField)
                    .forEach(consumer);

            clazz = clazz.getSuperclass();
        }
    }

    private ConvertedField toConvertedField(Field field) {
        Class<?> type = field.getType();
        String name = field.getName();
        Object value = null;
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return ConvertedField.builder()
                .name(name)
                .value(value)
                .type(type)
                .owner(object)
                .field(field)
                .build();
    }

}
