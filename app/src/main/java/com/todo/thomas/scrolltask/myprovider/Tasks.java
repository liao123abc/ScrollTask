package com.todo.thomas.scrolltask.myprovider;

import android.net.Uri;

public class Tasks {

	//description to a table column,Data field
	public static final String TID      = "_id";
	public static final String TITLE    = "name";
	public static final String LEVEL	= "level";
	public static final String DETAIL   = "detail";
	public static final String TOP      = "top";
    public static final String PRIORITY = "priority";
    
	///asc dsc
	public static final String DEFAULT_SORT_ORDER = "_id asc";
	public static final String ID_DSC_SORT_ORDER = "_id desc";
	///we should set it increase
	public static final String PRIORITY_SORT_ORDER_ASC = "priority asc";
	public static final String PRIORITY_SORT_ORDER_DESC = "priority desc";
	
	public static final String METHOD_GET_ITEM_COUNT = "METHOD_GET_ITEM_COUNT";
	public static final String KEY_ITEM_COUNT = "KEY_ITEM_COUNT";
	
	//��Ψһ�ر�ʶ��һ���ض���Content Provider����ˣ��ⲿ������һ��ʹ��Content Provider���ڵ�package��������ʹ������Ψһ�ġ�����ûҪ��
	public static final String AUTHORITY = "com.example.scrolltask";
	
	/*Match Code*/
	public static final int ITEM =1;
	public static final int ITEM_ID = 2;
	public static final int ITEM_POS = 3;
	
	/*MIME*/  
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.example.scrolltask";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.example.scrolltask";
    
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/item");
    public static final Uri CONTENT_POS_URI = Uri.parse("content://" + AUTHORITY + "/pos");
}
