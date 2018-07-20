package nurisezgin.com.android.statekeeper;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

import nurisezgin.com.android.statekeeper.annotations.HotState;
import nurisezgin.com.android.statekeeper.reflection.ReflectedField;
import nurisezgin.com.android.statekeeper.reflection.ReflectionAdapter;
import nurisezgin.com.android.statekeeper.util.ParcelableList;

/**
 * Created by nuri on 15.07.2018
 */
final class HotStateChain implements StateChain {

    @Override
    public void save(StateContext stateContext) {
        final Bundle bundle = stateContext.getBundle();
        final ReflectionAdapter reflectionAdapter = stateContext.getReflectionAdapter();

        reflectionAdapter
                .forEachField(field -> saveToBundle(bundle, field));
    }

    private void saveToBundle(Bundle bundle, ReflectedField field) {
        if (field.hasAnnotation(HotState.class)) {
            final String fieldName = field.getName();
            final Object fieldValue = field.getValue();

            if (fieldValue == null) {
                return;
            }

            if (field.typeOf(boolean.class) || field.typeOf(Boolean.class)) {
                bundle.putBoolean(fieldName, (Boolean) fieldValue);
            } else if (field.typeOf(int.class) || field.typeOf(Integer.class)) {
                bundle.putInt(fieldName, (Integer) fieldValue);
            } else if (field.typeOf(float.class) || field.typeOf(Float.class)) {
                bundle.putFloat(fieldName, (Float) fieldValue);
            } else if (field.typeOf(String.class)) {
                bundle.putString(fieldName, (String) fieldValue);
            } else if (field.typeOf(List.class) || field.isTypeOfThatInterface(List.class)) {
                ParcelableList list = new ParcelableList((List) fieldValue);
                bundle.putParcelable(fieldName, list);
            } else if (field.isTypeOfThatInterface(Serializable.class)) {
                bundle.putSerializable(fieldName, (Serializable) fieldValue);
            } else if (field.isTypeOfThatInterface(Parcelable.class)) {
                bundle.putParcelable(fieldName, (Parcelable) fieldValue);
            }
        }
    }

    @Override
    public void restore(StateContext stateContext) {
        final Bundle bundle = stateContext.getBundle();
        final ReflectionAdapter reflectionAdapter = stateContext.getReflectionAdapter();

        reflectionAdapter
                .forEachField(field -> restoreFromBundle(bundle, field));
    }

    private void restoreFromBundle(Bundle bundle, ReflectedField field) {
        if (field.hasAnnotation(HotState.class)) {
            final String fieldName = field.getName();

            if (field.typeOf(boolean.class) || field.typeOf(Boolean.class)) {
                boolean value = bundle.getBoolean(fieldName);
                field.trySetValue(value);
            } else if (field.typeOf(int.class) || field.typeOf(Integer.class)) {
                int value = bundle.getInt(fieldName);
                field.trySetValue(value);
            } else if (field.typeOf(float.class) || field.typeOf(Float.class)) {
                float value = bundle.getFloat(fieldName);
                field.trySetValue(value);
            } else if (field.typeOf(String.class)) {
                String value = bundle.getString(fieldName);
                field.trySetValue(value);
            } else if (field.typeOf(List.class) || field.isTypeOfThatInterface(List.class)) {
                Parcelable parcelable = bundle.getParcelable(fieldName);
                if (parcelable != null) {
                    field.trySetValue(((ParcelableList) parcelable).getList());
                }
            } else if (field.isTypeOfThatInterface(Serializable.class)) {
                Serializable value = bundle.getSerializable(fieldName);
                field.trySetValue(value);
            } else if (field.isTypeOfThatInterface(Parcelable.class)) {
                Parcelable value = bundle.getParcelable(fieldName);
                field.trySetValue(value);
            }
        }
    }
}
