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
    public void forEachField(Consumer<ReflectedField> consumer) {
        Class<?> clazz = object.getClass();

        while (clazz != null && clazz != Object.class) {
            Field[] fields = clazz.getFields();

            Stream.of(fields)
                    .map(this::toConvertedField)
                    .forEach(consumer);

            clazz = clazz.getSuperclass();
        }
    }

    private ReflectedField toConvertedField(Field field) {
        Class<?> type = field.getType();
        String name = field.getName();
        Object value = null;
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return ReflectedField.builder()
                .name(name)
                .value(value)
                .type(type)
                .owner(object)
                .field(field)
                .build();
    }

}
