package com.thomasliao.persistence.data.todo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by thomasliao on 2017/12/5.
 */
@Entity
public class ToDo {
    @Id
    private Long id;

    @NotNull
    private String text;//内容
    private Date date;//日期

    private String comment;

    @Generated(hash = 1476055854)
    public ToDo(Long id, @NotNull String text, Date date, String comment) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.comment = comment;
    }

    @Generated(hash = 1151496819)
    public ToDo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
