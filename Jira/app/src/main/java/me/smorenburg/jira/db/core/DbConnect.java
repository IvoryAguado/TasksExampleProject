package me.smorenburg.jira.db.core;


import java.util.List;

import me.smorenburg.jira.db.models.base.DaoSession;
import me.smorenburg.jira.db.models.base.User;


/**
 * Created by ivorsmorenburgaguado on 18/08/16.
 */
public interface DbConnect {

    DaoSession getDaoSession();

    void dropAll();

    void drop(Class clazz);

    void delete(DbBaseModel object);

   Long  save(DbBaseModel object);

    <T> T find(Class<T> clazz, Object key);

    <T> List<T> findAll(Class<T> clazz);

    User getCurrentUser();
}