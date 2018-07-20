package nurisezgin.com.android.statekeeper;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import nurisezgin.com.android.statekeeper.annotations.ColdState;
import nurisezgin.com.android.statekeeper.annotations.StateIdentifier;
import nurisezgin.com.android.statekeeper.reflection.ReflectedField;
import nurisezgin.com.android.statekeeper.reflection.ReflectionAdapter;
import nurisezgin.com.android.statekeeper.storage.StorageAdapter;
import nurisezgin.com.android.statekeeper.storage.StorageAdapterFactory;

/**
 * Created by nuri on 15.07.2018
 */
final class ColdStateChain implements StateChain {

    private ObjectTable tableHolder = new ObjectTable();

    @Override
    public void save(StateContext stateContext) {
        final ReflectionAdapter reflectionAdapter = stateContext.getReflectionAdapter();

        reflectionAdapter.forEachField(this::saveToTable);
        saveToStorage(stateContext);
        wipeObjectTable();
    }

    private void saveToTable(ReflectedField field) {
        if (field.hasAnnotation(ColdState.class)) {
            final String fieldName = field.getName();
            final Object fieldValue = field.getValue();

            if (fieldValue == null) {
                return;
            }

            if (field.isTypeOfThatInterface(Serializable.class)
                    || field.isTypeOfThatInterface(List.class)) {
                tableHolder.put(fieldName, (Serializable) fieldValue);
            }
        }
    }

    private void saveToStorage(StateContext stateContext) {
        StorageAdapter adapter = getStorageAdapter(stateContext);

        adapter.delete();
        adapter.write(() -> tableHolder);
    }

    @Override
    public void restore(StateContext stateContext) {
        final ReflectionAdapter reflectionAdapter = stateContext.getReflectionAdapter();

        StorageAdapter storageAdapter = getStorageAdapter(stateContext);

        restoreFromStorage(storageAdapter);
        reflectionAdapter.forEachField(this::restoreFromTable);
        deleteTemporary(storageAdapter);
    }

    private void restoreFromStorage(StorageAdapter adapter) {
        adapter.read(o -> tableHolder = (ObjectTable) o);
    }

    private void restoreFromTable(ReflectedField field) {
        if (field.hasAnnotation(ColdState.class)) {
            final String fieldName = field.getName();

            if (tableHolder.has(fieldName)) {
                Object value = tableHolder.get(fieldName);
                field.trySetValue(value);
            }
        }
    }

    private void deleteTemporary(StorageAdapter storageAdapter) {
        storageAdapter.delete();
        wipeObjectTable();
    }

    private void wipeObjectTable() {
        tableHolder.clear();
    }

    private StorageAdapter getStorageAdapter(StateContext stateContext) {
        String uniqueIdentifier = findIdentifier(stateContext.getReflectionAdapter())
                .getValue();
        return StorageAdapterFactory.newStorageAdapter(
                stateContext.getCachePath(), uniqueIdentifier);
    }

    public final UniqueIdentifierHolder findIdentifier(ReflectionAdapter reflectionAdapter) {
        UniqueIdentifierHolder holder = new UniqueIdentifierHolder();

        reflectionAdapter.forEachField(field -> {
            if (field.hasAnnotation(StateIdentifier.class)
                    && field.getValue() != null) {
                holder.setValue(field.getValue());
            }
        });

        return holder;
    }

    public static class UniqueIdentifierHolder {

        private Object value;

        public <T> T getValue() {
            return (T) value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

    static class ObjectTable implements Serializable {

        private ConcurrentMap<String, Serializable> table = new ConcurrentHashMap<>();

        void put(String name, Serializable o) {
            if (o != null) {
                table.put(name, o);
            }
        }

        Object get(String name) {
            return table.get(name);
        }

        boolean has(String name) {
            return table.containsKey(name);
        }

        void clear() {
            table.clear();
        }
    }

}
