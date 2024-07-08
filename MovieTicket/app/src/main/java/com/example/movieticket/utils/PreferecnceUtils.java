package com.example.movieticket.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.example.movieticket.models.Constant;

import java.util.HashMap;
import java.util.Map;

public class PreferecnceUtils {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String AVATAR_KEY = Constant.sp_avatarURL;

    public PreferecnceUtils(Context context) {
        this.sp = context.getSharedPreferences(Constant.sp, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    // Save the changes Asynchronously
    public void apply(HashMap<String, Object>... inputs) {
        try {
            for (HashMap<String, Object> input : inputs) {
                for (Map.Entry<String, Object> entry : input.entrySet()) {

                    String key = entry.getKey();
                    Object value = entry.getValue();

                    switch (getType(value)) {
                        case "String": {
                            editor.putString(key, (String) value);
                            break;
                        }
                        case "Integer": {
                            editor.putInt(key, (int) value);
                            break;
                        }
                        case "Boolean": {
                            editor.putBoolean(key, (boolean) value);
                            break;
                        }
                        case "Float": {
                            editor.putFloat(key, (float) value);
                            break;
                        }
                        case "Long": {
                            editor.putLong(key, (long) value);
                            break;
                        }
                    }
                }
            }

            editor.apply();
        } catch (Exception e) {
            Log.d("Share Preference error", "apply: " + e.getMessage());
        }
    }

    // Save the changes Synchronously
    public void commit(HashMap<String, Object>... inputs) {
        try {
            for (HashMap<String, Object> input : inputs) {
                for (Map.Entry<String, Object> entry : input.entrySet()) {

                    String key = entry.getKey();
                    Object value = entry.getValue();

                    switch (getType(value)) {
                        case "String": {
                            editor.putString(key, (String) value);
                            break;
                        }
                        case "Integer": {
                            editor.putInt(key, (int) value);
                            break;
                        }
                        case "Boolean": {
                            editor.putBoolean(key, (boolean) value);
                            break;
                        }
                        case "Float": {
                            editor.putFloat(key, (float) value);
                            break;
                        }
                        case "Long": {
                            editor.putLong(key, (long) value);
                            break;
                        }
                    }
                }
            }

            editor.commit();
        } catch (Exception e) {
            Log.d("Share Preference error", "commit: " + e.getMessage());
        }

    }

    // apply only for image
    public void apply(String key, byte[] image) {
        try {
            String base64Value = Base64.encodeToString(image, Base64.DEFAULT);
            editor.putString(key, base64Value);
            editor.apply();
        } catch (Exception e) {
            Log.d("Share Preference error", "apply: " + e.getMessage());
        }
        editor.apply();
    }

    // comic only for image
    public void commit(String key, byte[] image) {
        try {
            String base64Value = Base64.encodeToString(image, Base64.DEFAULT);
            editor.putString(key, base64Value);
            editor.apply();
        } catch (Exception e) {
            Log.d("Share Preference error", "apply: " + e.getMessage());
        }
        editor.commit();
    }

    public Object getValue(String key, Object defValue) {
        switch (getType(defValue)) {
            case "String": {
                return sp.getString(key, (String) defValue);
            }
            case "Integer": {
                return sp.getInt(key, (int) defValue);
            }
            case "Boolean": {
                return sp.getBoolean(key, (boolean) defValue);
            }
            case "Float": {
                return sp.getFloat(key, (float) defValue);
            }
            case "Long": {
                return sp.getLong(key, (long) defValue);
            }
        }
        return null;
    }

    // get image only
    public byte[] getValue() {
        String base64 = sp.getString(AVATAR_KEY,"No Base64 String");
        return Base64.decode(base64, Base64.DEFAULT);
    }

    private String getType(Object value) {
        if (value instanceof String) {
            return "String";
        } else if (value instanceof Integer) {
            return "Integer";
        } else if (value instanceof Boolean) {
            return "Boolean";
        } else if (value instanceof Float) {
            return "Float";
        } else if (value instanceof Long) {
            return "Long";
        } else {
            throw new IllegalArgumentException("Unsupported data type for SharedPreferences");
        }
    }
}
