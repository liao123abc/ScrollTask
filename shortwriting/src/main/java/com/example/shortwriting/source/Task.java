package com.example.shortwriting.source;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

import java.util.UUID;

/**
 * Created by thomasliao on 2017/10/16.
 */
@Entity(tableName = "tasks")
public final class Task {
    @PrimaryKey
    @ColumnInfo(name = "entryid")
    private final String mId;

    @Nullable
    @ColumnInfo(name = "title")
    private final String mTitle;

    private final String mDescription;

    private final boolean mCompleted;

    public Task(String title, String description) {
        this(title, description, UUID.randomUUID().toString(), false);
    }

    public Task(String id, String title, String description, boolean completed) {
        this.mId = id;
        this.mTitle = title;
        this.mDescription = description;
        this.mCompleted = completed;
    }
}
