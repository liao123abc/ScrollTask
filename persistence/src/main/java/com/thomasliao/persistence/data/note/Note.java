package com.thomasliao.persistence.data.note;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

/**
 * Created by thomasliao on 2017/11/11.
 * an entity is a class persisted in the database (e.g. a row for each object).
 */

@Entity(indexes = {
        @Index(value = "text, date DESC", unique = true)
})
public class Note {

    @Id
    private Long id;

    @NotNull
    private String text;
    private Date date;

    private String comment;

    @Convert(converter = NoteTypeConverter.class, columnType = String.class)
    private NoteType type;

}
