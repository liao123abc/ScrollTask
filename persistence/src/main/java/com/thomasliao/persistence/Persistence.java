package com.thomasliao.persistence;

import android.content.Context;

import com.thomasliao.persistence.data.DaoMaster;
import com.thomasliao.persistence.data.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by thomasliao on 2017/11/11.
 */

public class Persistence {

    private Context appContext;

    public static final String ENCRYPTED_DB_NAME = "short-writing-encrypted.db";
    public static final String DB_NAME = "short-writing.db";

    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    ToDoManager toDoManager;

    public Persistence(Context appContext) {
        this.appContext = appContext;

        //初始化的时候建立note session
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(appContext, ENCRYPTED ? ENCRYPTED_DB_NAME : DB_NAME);
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }
}
