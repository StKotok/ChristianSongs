package co.neatapps.allchristsongs.android.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.ParameterizedType;

public class Prefs {

    public static final ValueWrapper<Integer> TEXT_SIZE = new ValueWrapper<Integer>("TEXT_SIZE", 24) {
    };

    public static final ValueWrapper<String> DIGESTS_SELECTED = new ValueWrapper<String>("DIGESTS_SELECTED", "") {
    };

    public static abstract class ValueWrapper<T> {
        private final T defaultValue;
        private final String name;
        private final Class type;

        public ValueWrapper(String name) {
            this(name, null);
        }

        public ValueWrapper(String name, T defaultValue) {
            this.defaultValue = defaultValue;
            this.name = name;
            this.type = getType();
        }

        public T getValue(Context ctx) {
            SharedPreferences sharedPreferences = getSharedPreferences(ctx);
            if (!sharedPreferences.contains(name)) {
                return defaultValue;
            }
            Object result = defaultValue;
            if (Integer.class.isAssignableFrom(type)) {
                result = sharedPreferences.getInt(name, (Integer) defaultValue);
            } else if (String.class.isAssignableFrom(type)) {
                result = sharedPreferences.getString(name, (String) defaultValue);
            } else if (Long.class.isAssignableFrom(type)) {
                result = sharedPreferences.getLong(name, (Long) defaultValue);
            } else if (Boolean.class.isAssignableFrom(type)) {
                result = sharedPreferences.getBoolean(name, (Boolean) defaultValue);
            } else if (Float.class.isAssignableFrom(type)) {
                result = sharedPreferences.getFloat(name, (Float) defaultValue);
            }
            return (T) result;
        }

        public void remove(Context ctx) {
            SharedPreferences.Editor edit = getSharedPreferences(ctx).edit();
            edit.remove(name);
            edit.apply();
        }

        public boolean exists(Context ctx) {
            return getSharedPreferences(ctx).contains(name);
        }

        public void setValue(Context ctx, T value) {
            SharedPreferences.Editor edit = getSharedPreferences(ctx).edit();
            if (Integer.class.isAssignableFrom(type)) {
                edit.putInt(name, (Integer) value);
            } else if (String.class.isAssignableFrom(type)) {
                edit.putString(name, (String) value);
            } else if (Long.class.isAssignableFrom(type)) {
                edit.putLong(name, (Long) value);
            } else if (Boolean.class.isAssignableFrom(type)) {
                edit.putBoolean(name, (Boolean) value);
            } else if (Float.class.isAssignableFrom(type)) {
                edit.putFloat(name, (Float) value);
            }
            edit.apply();
        }

        private SharedPreferences getSharedPreferences(Context context) {
            return context.getSharedPreferences("APP", Context.MODE_PRIVATE);
        }

        private Class<?> getType() {
            return (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
    }

}
