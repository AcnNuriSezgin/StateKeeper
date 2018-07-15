package nurisezgin.com.android.statekeeper.reflection;

import java.lang.reflect.Field;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by nuri on 15.07.2018
 */
@Builder
public class ConvertedField {

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

}
