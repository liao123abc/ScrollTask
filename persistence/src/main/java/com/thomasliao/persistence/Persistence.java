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

    public static final boolean ENCRYPTED = false;

    private DaoSession noteDaoSession;
    private DaoSession userDaoSession;

    public Persistence(Context appContext) {
        this.appContext = appContext;

        //初始化的时候建立note session
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(appContext, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        noteDaoSession = new DaoMaster(db).newSession();

        //初始化的时候建立user session
        DaoMaster.DevOpenHelper helper1 = new DaoMaster.DevOpenHelper(appContext, ENCRYPTED ? "users-db-encrypted" : "users-db");
        Database db1 = ENCRYPTED ? helper1.getEncryptedWritableDb("super-secret") : helper1.getWritableDb();
        userDaoSession = new DaoMaster(db1).newSession();
    }

    public DaoSession getNoteDaoSession() {
        return noteDaoSession;
    }

    public DaoSession getUserDaoSession() {
        return userDaoSession;
    }
}
