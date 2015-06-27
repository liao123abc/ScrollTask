package com.todo.thomas.scrolltask.myprovider;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

public class TaskProvider extends ContentProvider{
	private static final String LOG_TAG = "dongmin.liao.taskprovider";
	
	private static final String DB_NANE = "Task.db";
	private static final String DB_TABLE = "TaskTable";
	private static final int DB_VERSION = 1;
	
	private static final String DB_CREATE = "create table "+DB_TABLE+"("+
	        Tasks.TID+" integer primary key autoincrement not null,"+
			Tasks.TITLE +" text not null," +
	        Tasks.LEVEL +" integer not null," +
			Tasks.DETAIL +" text not null," +
            Tasks.TOP + " integer not null," +
            Tasks.PRIORITY +" integer not null);";
	
	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(Tasks.AUTHORITY, "item", Tasks.ITEM);
		uriMatcher.addURI(Tasks.AUTHORITY, "item/#", Tasks.ITEM_ID);
		uriMatcher.addURI(Tasks.AUTHORITY, "pos/#", Tasks.ITEM_POS);
	}

	private static final HashMap<String, String> taskProjectionMap;
	static {
		taskProjectionMap = new HashMap<String, String>();
		taskProjectionMap.put(Tasks.TID, Tasks.TID);
		taskProjectionMap.put(Tasks.TITLE, Tasks.TITLE);
		taskProjectionMap.put(Tasks.LEVEL, Tasks.LEVEL);
		taskProjectionMap.put(Tasks.DETAIL, Tasks.DETAIL);
		taskProjectionMap.put(Tasks.TOP, Tasks.TOP);
        taskProjectionMap.put(Tasks.PRIORITY, Tasks.PRIORITY);
	}

	private DBHelper dbHelper = null;
	private ContentResolver resolver = null;
	
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		Context context = getContext();
		resolver = context.getContentResolver();
		dbHelper = new DBHelper(context, DB_NANE, null, DB_VERSION);
		
		Log.i(LOG_TAG, "task provider created!");
		
		return true;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count = 0;
		
		switch(uriMatcher.match(uri)) {
		case Tasks.ITEM: {
			count = db.delete(DB_TABLE, selection, selectionArgs);
			break;
		}
		case Tasks.ITEM_ID: {
			String id = uri.getPathSegments().get(1);
			count = db.delete(DB_TABLE, Tasks.TID + "=" + id
					+ (!TextUtils.isEmpty(selection)? " and (" + selection +')' : ""),
							selectionArgs);
			break;
		}

		default:
			throw new IllegalArgumentException("Error Uri:" + uri);
		}
		
		resolver.notifyChange(uri, null);
		
		return count;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch (uriMatcher.match(uri)) {
		case Tasks.ITEM:
			return Tasks.CONTENT_TYPE;
		case Tasks.ITEM_ID:
		case Tasks.ITEM_POS:
			return Tasks.CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Error Uri: " + uri);	
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		if(uriMatcher.match(uri) != Tasks.ITEM) {
			throw new IllegalArgumentException("Error Uri: " + uri);
		}
		
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		long id = db.insert(DB_TABLE, Tasks.TID, values);
		if(id < 0) {
			throw new SQLiteException("Unable to insert " + values + "for" + uri);
		}
		
		Uri newUri = ContentUris.withAppendedId(uri, id);
		resolver.notifyChange(uri, null);
		
		return newUri;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
        SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
        String limit = null;
        
        switch (uriMatcher.match(uri)) {
        case Tasks.ITEM: {
                sqlBuilder.setTables(DB_TABLE);
                sqlBuilder.setProjectionMap(taskProjectionMap);
                break;
        }
        case Tasks.ITEM_ID: {
                String id = uri.getPathSegments().get(1);
                sqlBuilder.setTables(DB_TABLE);
                sqlBuilder.setProjectionMap(taskProjectionMap);
                sqlBuilder.appendWhere(Tasks.TID + "=" + id);
                break;
        }
        case Tasks.ITEM_POS: {
                String pos = uri.getPathSegments().get(1);
                sqlBuilder.setTables(DB_TABLE);
                sqlBuilder.setProjectionMap(taskProjectionMap);
                limit = pos + ", 1";
                break;
        }
        default:
                throw new IllegalArgumentException("Error Uri: " + uri);
        }

        Cursor cursor = sqlBuilder.query(db,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                TextUtils.isEmpty(sortOrder) ? Tasks.PRIORITY_SORT_ORDER_DESC : sortOrder,
                limit);
        cursor.setNotificationUri(resolver, uri);

        return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count = 0;
		
		switch(uriMatcher.match(uri)) {
		case Tasks.ITEM: {
			count = db.update(DB_TABLE, values, selection, selectionArgs);
			break;
		}
		case Tasks.ITEM_ID: {
			String id = uri.getPathSegments().get(1);
            count = db.update(DB_TABLE, values, Tasks.TID + "=" + id
                    + (!TextUtils.isEmpty(selection) ? " and (" + selection + ')' : ""), selectionArgs);
            break;
		}
		default:
			throw new IllegalArgumentException("Error Uri: " + uri);
		}
		
		resolver.notifyChange(uri, null);
		
		return count;
	}
	
    @Override
    public Bundle call(String method, String request, Bundle args) {
            Log.i(LOG_TAG, "ArticlesProvider.call: " + method);

            if(method.equals(Tasks.METHOD_GET_ITEM_COUNT)) {
                    return getItemCount();
            }

            throw new IllegalArgumentException("Error method call: " + method);
    }
	
    private Bundle getItemCount() {
        Log.i(LOG_TAG, "TasksProvider.getItemCount");

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from " + DB_TABLE, null);

        int count = 0;
        
        if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
        }

        Bundle bundle = new Bundle();
        bundle.putInt(Tasks.KEY_ITEM_COUNT, count);

        cursor.close();
        db.close();

        return bundle;
    }
	
	private static class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(DB_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DEOP TABLE IF EXISTS " + DB_TABLE);
			onCreate(db);
		}
	}

}
