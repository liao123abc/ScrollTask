package com.thomasliao.persistence.data.note;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by thomasliao on 2017/11/11.
 */

public class NoteTypeConverter implements PropertyConverter<NoteType, String> {
    @Override
    public NoteType convertToEntityProperty(String databaseValue) {
        return NoteType.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(NoteType entityProperty) {
        return entityProperty.name();
    }
}
