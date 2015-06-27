package com.todo.thomas.scrolltask.myprovider;

import java.util.Calendar;
import java.util.LinkedList;
import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;

public class TaskCpAdapter {
	private static final String LOG_TAG = "dongmin.liao.scrolltask";
	private ContentResolver resolver = null;
	
	public TaskCpAdapter(Context context) {
		resolver = context.getContentResolver();
	}

	public long insertTask(Task task) {
		ContentValues values = new ContentValues();
		values.put(Tasks.TITLE, task.getName());
		values.put(Tasks.LEVEL, task.getLevel());
		values.put(Tasks.DETAIL, task.getDetail());
		values.put(Tasks.TOP, task.getTop());
        values.put(Tasks.PRIORITY, task.getPriority());
		
		Uri uri = resolver.insert(Tasks.CONTENT_URI, values);
		String itemId = uri.getPathSegments().get(1);
		
		return Integer.valueOf(itemId).longValue();
	}
	
	public boolean updateTask(Task task) {
		Uri uri = ContentUris.withAppendedId(Tasks.CONTENT_URI, task.getId());
		
        ContentValues values = new ContentValues();
		values.put(Tasks.TITLE, task.getName());
		values.put(Tasks.LEVEL, task.getLevel());
		values.put(Tasks.DETAIL, task.getDetail());
		values.put(Tasks.TOP, task.getTop());
        values.put(Tasks.PRIORITY, task.getPriority());

		int count = resolver.update(uri, values, null, null);
	
		return count > 0;
	}
	
	public boolean removeTask(int id) {
		Uri uri = ContentUris.withAppendedId(Tasks.CONTENT_URI, id);
		
		int count = resolver.delete(uri, null, null);
		
		return count > 0;
	}
	
	public LinkedList<Task> getAllTasks() {
		LinkedList<Task> tasks = new LinkedList<Task>();
		
		String[] projection = new String[] {
				Tasks.TID,
				Tasks.TITLE,
				Tasks.LEVEL,
				Tasks.DETAIL,
				Tasks.TOP,
                Tasks.PRIORITY
		};
		
		Cursor cursor = resolver.query(Tasks.CONTENT_URI, projection, null, null, Tasks.PRIORITY_SORT_ORDER_DESC);
		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				String name = cursor.getString(1);
				int level = cursor.getInt(2);
				String detail = cursor.getString(3);
				int top = cursor.getInt(4);
                int priority = cursor.getInt(5);
				
				Task task = new Task( id, name, level, detail, top, priority);
				tasks.add(task);
			} while(cursor.moveToNext());
		}
		
		return tasks;
	}
	
	public void removeAllTasks() {
		String[] projection = new String[] {
				Tasks.TID,
				Tasks.TITLE,
				Tasks.LEVEL,
				Tasks.DETAIL,
				Tasks.TOP,
                Tasks.PRIORITY
		};
		
		//Cursor cursor = resolver.query(Tasks.CONTENT_URI, projection, null, null, Tasks.DEFAULT_SORT_ORDER);
		Cursor cursor = resolver.query(Tasks.CONTENT_URI, projection, null, null, Tasks.ID_DSC_SORT_ORDER);
		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				removeTask(id);
			} while(cursor.moveToNext());
		}
		////return tasks;
	}
	
	@SuppressLint("NewApi") 
	public int getTaskCount() {
        int count = 0;

        try {
        	    Bundle bundle = resolver.acquireContentProviderClient(Tasks.CONTENT_URI).call(Tasks.METHOD_GET_ITEM_COUNT, null, null);
                count = bundle.getInt(Tasks.KEY_ITEM_COUNT, 0);
        } catch(RemoteException e) {
                e.printStackTrace();
        }
        return count;
	}
	
	public Task getTaskById(int id) {
		Uri uri = ContentUris.withAppendedId(Tasks.CONTENT_URI, id);
		
		String[] projection = new String[] {
				Tasks.TID,
				Tasks.TITLE,
				Tasks.LEVEL,
				Tasks.DETAIL,
				Tasks.TOP,
                Tasks.PRIORITY
		};
		
		Cursor cursor = resolver.query(uri, projection, null, null, Tasks.DEFAULT_SORT_ORDER);
		
		if(!cursor.moveToFirst()){
			return null;
		}
		
		String name = cursor.getString(1);
		int level = cursor.getInt(2);
		String detail = cursor.getString(3);
		int top = cursor.getInt(4);
        int priority = cursor.getInt(5);
		
		return new Task( id, name, level, detail, top, priority);
	}
	
    public Task getTaskByPos(int pos) {
        Uri uri = ContentUris.withAppendedId(Tasks.CONTENT_POS_URI, pos);

		String[] projection = new String[] {
				Tasks.TID,
				Tasks.TITLE,
				Tasks.LEVEL,
				Tasks.DETAIL,
				Tasks.TOP,
                Tasks.PRIORITY
		};

        Cursor cursor = resolver.query(uri, projection, null, null, Tasks.PRIORITY_SORT_ORDER_DESC);
        if (!cursor.moveToFirst()) {
                return null;
        }

		int id = cursor.getInt(0);
		String name = cursor.getString(1);
		int level = cursor.getInt(2);
		String detail = cursor.getString(3);
		int top = cursor.getInt(4);
        int priority = cursor.getInt(5);

        return new Task( id, name, level, detail, top, priority);
    }
	
}
