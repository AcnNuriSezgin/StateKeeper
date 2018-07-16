package nurisezgin.com.android.statekeeper;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

import nurisezgin.com.android.statekeeper.annotations.HotState;
import nurisezgin.com.android.statekeeper.reflection.ConvertedField;
import nurisezgin.com.android.statekeeper.reflection.ReflectionAdapter;

/**
 * Created by nuri on 15.07.2018
 */
class HotStateAdapter implements ObjectStateAdapter {

    private final Bundle bundle;
    private final ReflectionAdapter reflectionAdapter;

    public HotStateAdapter(Bundle bundle, ReflectionAdapter reflectionAdapter) {
        this.bundle = bundle;
        this.reflectionAdapter = reflectionAdapter;
    }

    @Override
    public void save() {
        reflectionAdapter.forEachField(this::saveToBundle);
    }

    private void saveToBundle(ConvertedField field) {
        if (field.hasAnnotation(HotState.class)) {
            final String fieldName = field.getName();
            final Object fieldValue = field.getValue();

            if (fieldValue == null) {
                return;
            }

            if (field.hasType(boolean.class) || field.hasType(Boolean.class)) {
                bundle.putBoolean(fieldName, (Boolean) fieldValue);
            } else if (field.hasType(int.class) || field.hasType(Integer.class)) {
                bundle.putInt(fieldName, (Integer) fieldValue);
            } else if (field.hasType(float.class) || field.hasType(Float.class)) {
                bundle.putFloat(fieldName, (Float) fieldValue);
            } else if (field.hasType(String.class)) {
                bundle.putString(fieldName, (String) fieldValue);
            } else if (field.isTypeOfThat(List.class)) {
                ParcelableList list = new ParcelableList((List) fieldValue);
                bundle.putParcelable(fieldName, list);
            } else if (field.isTypeOfThat(Serializable.class)) {
                bundle.putSerializable(fieldName, (Serializable) fieldValue);
            } else if (field.isTypeOfThat(Parcelable.class)) {
                bundle.putParcelable(fieldName, (Parcelable) fieldValue);
            }
        }
    }

    @Override
    public void restore() {
        reflectionAdapter.forEachField(this::restoreFromBundle);
    }

    private void restoreFromBundle(ConvertedField field) {
        if (field.hasAnnotation(HotState.class)) {
            final String fieldName = field.getName();

            if (field.hasType(boolean.class) || field.hasType(Boolean.class)) {
                boolean value = bundle.getBoolean(fieldName);
                field.trySetValue(value);
            } else if (field.hasType(int.class) || field.hasType(Integer.class)) {
                int value = bundle.getInt(fieldName);
                field.trySetValue(value);
            } else if (field.hasType(float.class) || field.hasType(Float.class)) {
                float value = bundle.getFloat(fieldName);
                field.trySetValue(value);
            } else if (field.hasType(String.class)) {
                String value = bundle.getString(fieldName);
                field.trySetValue(value);
            } else if (field.isTypeOfThat(List.class)) {
                Parcelable parcelable = bundle.getParcelable(fieldName);
                field.trySetValue(((ParcelableList) parcelable).getList());
            } else if (field.isTypeOfThat(Serializable.class)) {
                Serializable value = bundle.getSerializable(fieldName);
                field.trySetValue(value);
            } else if (field.isTypeOfThat(Parcelable.class)) {
                Parcelable value = bundle.getParcelable(fieldName);
                field.trySetValue(value);
            }
        }
    }


}
