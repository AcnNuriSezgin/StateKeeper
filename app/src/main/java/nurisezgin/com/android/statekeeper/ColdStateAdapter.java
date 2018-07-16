package nurisezgin.com.android.statekeeper;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import nurisezgin.com.android.statekeeper.annotations.ColdState;
import nurisezgin.com.android.statekeeper.reflection.ConvertedField;
import nurisezgin.com.android.statekeeper.reflection.ReflectionAdapter;
import nurisezgin.com.android.statekeeper.storage.StorageAdapter;

/**
 * Created by nuri on 15.07.2018
 */
public class ColdStateAdapter implements ObjectStateAdapter {

    private ObjectTable table;
    private StorageAdapter storageAdapter;
    private ReflectionAdapter reflectionAdapter;

    public ColdStateAdapter(
            StorageAdapter storageAdapter, ReflectionAdapter reflectionAdapter) {

        this.storageAdapter = storageAdapter;
        this.reflectionAdapter = reflectionAdapter;
        this.table = new ObjectTable();
    }

    @Override
    public void save() {
        reflectionAdapter.forEachField(this::saveToStorage);
        storageAdapter.write(() -> table);
    }

    private void saveToStorage(ConvertedField field) {
        if (field.hasAnnotation(ColdState.class)) {
            final String fieldName = field.getName();
            final Object fieldValue = field.getValue();

            if (fieldValue == null) {
                return;
            }

            if (field.isTypeOfThat(Serializable.class)) {
                table.put(fieldName, (Serializable) fieldValue);
            }
        }
    }

    @Override
    public void restore() {
        storageAdapter.read(o -> table = (ObjectTable) o);
        reflectionAdapter.forEachField(this::restoreFromStorage);
    }

    private void restoreFromStorage(ConvertedField field) {
        if (field.hasAnnotation(ColdState.class)) {
            final String fieldName = field.getName();

            if (field.isTypeOfThat(Serializable.class)) {
                Object value = table.get(fieldName);
                field.trySetValue(value);
            }
        }
    }

    private static class ObjectTable {

        private ConcurrentMap<String, Serializable> table = new ConcurrentHashMap<>();

        private void put(String name, Serializable o) {
            table.put(name, o);
        }

        private Object get(String name) {
            return table.get(name);
        }

        private void clear() {
            table.clear();
        }
    }

}
