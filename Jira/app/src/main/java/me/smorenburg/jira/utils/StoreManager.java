package me.smorenburg.jira.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import me.smorenburg.jira.BuildConfig;


/**
 * Created by ivory.aguado on 16/06/2016.
 */
public class StoreManager {

    private final static String TAG = "StoreManager";


    private static final String PREFS_NAME = BuildConfig.APPLICATION_ID;


    private static final String LOGGED_USER = "LOGGED_USER";
    private static final String DEVICE_UID = "DEVICE_UID";
    private static final String DEVICE_NAME = "DEVICE_NAME";
    private static final String DATABASE_KEY = "DATABASE_KEY";
    private static final String SYNCED = "SYNCED";


    private static StoreManager instance;
    private static Context baseContext;
    private final SharedPreferences settings;
    private final SharedPreferences.Editor editor;


    private StoreManager(@NonNull Context context) {
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        instance = this;

    }

    public static void setBaseContext(Context baseContext) {
        StoreManager.baseContext = baseContext;
    }

    public static StoreManager getInstance() {
        if (instance == null) {
            synchronized (StoreManager.class) {
                if (instance == null) {
                    try {
                        instance = new StoreManager(baseContext);
                    } catch (Exception e) {
                        instance = null;
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        }
        return instance;
    }

    public void clearAll() {
        try {
            String uuid = getDeviceName();
            String device_name = getDeviceName();
            String databaseKey = getDatabaseKey();
            editor.clear().commit();
            setDeviceUid(uuid);
            setDeviceName(device_name);
            setDatabaseKey(databaseKey);
        } catch (Exception e) {
        }

    }

    public String getDeviceUid() {
        return EncryptionSuite.decrypt(settings.getString(EncryptionSuite.encrypt(DEVICE_UID), "Unknown"));
    }

    public void setDeviceUid(String device_uid) {
        editor.putString(EncryptionSuite.encrypt(DEVICE_UID), EncryptionSuite.encrypt(device_uid)).apply();
    }

    public String getDeviceName() {
        return EncryptionSuite.decrypt(settings.getString(EncryptionSuite.encrypt(DEVICE_NAME), "Unknown"));
    }

    public void setDeviceName(String deviceName) {
        editor.putString(EncryptionSuite.encrypt(DEVICE_NAME), EncryptionSuite.encrypt(deviceName)).apply();
    }

    public Boolean getSynced() {
         return settings.getBoolean(EncryptionSuite.decrypt(SYNCED), false);
    }

    public void setSynced(Boolean synced) {
         editor.putBoolean(EncryptionSuite.encrypt(SYNCED), synced).apply();
    }

    public String getDatabaseKey() {
        Log.d(TAG, "getDatabaseKey() - Retrieving DB Key...");
        return settings.getString(EncryptionSuite.decrypt(DATABASE_KEY), null);
    }

    public void setDatabaseKey(String databaseKey) {
        Log.d(TAG, "setDatabaseKey() - Saving DB Key... " + databaseKey);
        editor.putString(EncryptionSuite.encrypt(DATABASE_KEY), databaseKey).apply();
    }
    public Long getLoggedUserId() {
         return settings.getLong(EncryptionSuite.decrypt(LOGGED_USER), -1L);
    }

    public void setLoggedUserId(Long loggedUserId) {
         editor.putLong(EncryptionSuite.encrypt(LOGGED_USER), loggedUserId).apply();
    }
}
