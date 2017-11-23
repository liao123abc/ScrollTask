package com.thomasliao.persistence.data.user;

import android.support.annotation.Nullable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by thomasliao on 2017/11/11.
 */

@Entity
public class User {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "USERNAME")
    @Index(unique = true)
    private String name;

    @NotNull
    private int repos;

    @Transient
    private int tempUsageCount; //not persisted

}
