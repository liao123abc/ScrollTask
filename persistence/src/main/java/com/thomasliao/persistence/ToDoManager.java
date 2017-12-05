package com.thomasliao.persistence;

import com.thomasliao.persistence.data.DaoSession;

/**
 * Created by thomasliao on 2017/12/5.
 */

public class ToDoManager {

    private DaoSession daoSession;

    public ToDoManager(DaoSession daoSession) {
        this.daoSession = daoSession;
    }
}
