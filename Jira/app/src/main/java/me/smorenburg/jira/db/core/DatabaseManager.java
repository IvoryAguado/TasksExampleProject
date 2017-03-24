package me.smorenburg.jira.db.core;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import me.smorenburg.jira.BuildConfig;
import me.smorenburg.jira.R;
import me.smorenburg.jira.db.models.base.DaoMaster;
import me.smorenburg.jira.db.models.base.DaoSession;
import me.smorenburg.jira.db.models.base.Location_1;
import me.smorenburg.jira.db.models.base.Skill;
import me.smorenburg.jira.db.models.base.User;
import me.smorenburg.jira.db.models.base.User_Skills;
import me.smorenburg.jira.db.models.base.WebServiceData;
import me.smorenburg.jira.db.models.base.WebServiceDataDao;
import me.smorenburg.jira.utils.StoreManager;


public class DatabaseManager implements DbConnect {

    private static DatabaseManager instance;

    private static Context context;
    private DaoSession daoSession;

    private DatabaseManager() throws Exception {
        if (context == null)
            throw new Exception("Initialize Database before use, call setDatabaseBaseContext(Context base)");

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, context.getString(R.string.app_name) + "_" + BuildConfig.BUILD_TYPE + ".db");
        Database db = (BuildConfig.BUILD_TYPE == "release") ? helper.getEncryptedWritableDb(new SecretsDatabaseKeyHolder().getKey()) : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        Log.d(getClass().getSimpleName(), "Database Singleton debug: " + BuildConfig.BUILD_TYPE + " " + BuildConfig.DEBUG);

    }

    public static void setDatabaseBaseContext(Context context) {
        DatabaseManager.context = context;
        Log.d("DatabaseManager", "Setting Database Context");
        DatabaseManager.getInstance();
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    try {
                        instance = new DatabaseManager();
                    } catch (Exception e) {
                        instance = null;
                        Log.e("DatabaseManager", e.getMessage());
                    }
                }
            }
        }
        return instance;
    }


    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void dropAll() {
        for (AbstractDao abstractDao : daoSession.getAllDaos())
            abstractDao.deleteAll();
    }

    public void drop(Class clazz) {
        daoSession.deleteAll(clazz);
    }

    public void delete(DbBaseModel object) {
        daoSession.delete(object);
    }

    public List<String> getItemCategories() {
        Set<String> categories = new HashSet<>();
        List<WebServiceData> webServiceDatas = daoSession.getWebServiceDataDao().loadAll();
        for (WebServiceData wb : webServiceDatas) {
            categories.add(wb.getCategory());
        }
        return new LinkedList<>(categories);
    }

    public List<String> getItemsStringsForThisCategory(String category) {
        Set<String> strings = new HashSet<>();
        List<WebServiceData> webServiceDatas = daoSession.getWebServiceDataDao().loadAll();
        for (WebServiceData wb : webServiceDatas) {
            if (category.equalsIgnoreCase(wb.getCategory()))
                strings.add(wb.getItem());
        }
        return new LinkedList<>(strings);
    }

    public List<WebServiceData> getItemsForThisName(String itemName) {
        return daoSession.getWebServiceDataDao().queryBuilder().where(WebServiceDataDao.Properties.Item.eq(itemName)).build().list();
    }

    @Override
    public synchronized Long save(DbBaseModel object) {

        Long _id;

        try {
            Log.d(getClass().getSimpleName(), "Save: " + object.toString());
            _id = daoSession.insertOrReplace(object);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Save Error: " + e.getMessage());
            return null;
        }


        if (object instanceof User) {

            User cast = User.class.cast(object);

        }


        if (object instanceof WebServiceData) {

            WebServiceData cast = WebServiceData.class.cast(object);
            Location_1 location_1 = cast.getLocation_1Raw();
            if (location_1 != null) {
                cast.setLocation_1Id(save(location_1));
                location_1.setWebServiceDataId(_id);
                daoSession.update(location_1);
            }
        }

        return _id;
    }

    public <T> T find(Class<T> clazz, Object key) {
        return daoSession.load(clazz, key);
    }

    public <T> List<T> findAll(Class<T> clazz) {
        return daoSession.loadAll(clazz);
    }

    @Override
    public User getCurrentUser() {
        try {
            return daoSession.getUserDao().load(StoreManager.getInstance().getLoggedUserId());
        } catch (NullPointerException e) {
            return null;
        }
    }


    public List<User> getPerfectUserForThisSkill(Skill skill) {
        List<User_Skills> users = findAll(User_Skills.class);
        List<User> usersCl = new LinkedList<>();
        for (User_Skills user : users) {
            if (skill.getId() == user.getSkill_id()) {
                usersCl.add(user.getUser());
            }
        }
        return usersCl;
    }


    private static class SecretsDatabaseKeyGenerator {

        public static final int DEFAULT_KEY_LENGTH = 256;

        public static String generateKey() {
            try {
                SecretKey secretKey = internalGenerateKey();
                return Base64.encodeToString(secretKey.getEncoded(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException ignored) {
            }
            return null;
        }

        private static SecretKey internalGenerateKey() throws NoSuchAlgorithmException {
            SecureRandom random = new SecureRandom();
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(DEFAULT_KEY_LENGTH, random);
            return keyGenerator.generateKey();
        }
    }

    private class SecretsDatabaseKeyHolder {

        private static final String LOG_TAG = "KeyHolder";
        private final StoreManager storeManager;


        public SecretsDatabaseKeyHolder() {
            storeManager = StoreManager.getInstance();
        }

        public String getKey() {
            String key = storeManager.getDatabaseKey();
            if (key == null) {
                key = SecretsDatabaseKeyGenerator.generateKey();
                storeManager.setDatabaseKey(key);
            }
            if (BuildConfig.DEBUG) {
                Log.d(LOG_TAG, "DB key: " + key);
            }
            return key;
        }
    }
}
