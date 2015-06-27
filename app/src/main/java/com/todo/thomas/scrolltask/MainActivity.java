package com.todo.thomas.scrolltask;


import java.util.LinkedList;

import com.todo.thomas.scrolltask.detail.DetailActivity;
import com.todo.thomas.scrolltask.myprovider.Task;
import com.todo.thomas.scrolltask.myprovider.Tasks;
import com.todo.thomas.scrolltask.myprovider.TaskCpAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

//TODO
//http://javatechig.com/android/app-widgets-example-in-android

public class MainActivity extends Activity {

	//private DBlite dbLite;
    private final static int ADD_TASK_ACTIVITY    = 1;
    private final static int EDIT_TASK_ACTIVITY   = 2;
    private final static int DELETE_TASK_ACTIVITY = 3;
    
    private TaskCpAdapter mCpAdapter;
    private TaskListAdapter mListAdapter;
    private TaskObserver observer;
    private ListView contentList;

    public static final int NOTIFICATION_ID = 1;

    /////get all the tasks to be sorted
    private LinkedList<Task> tasks = null;

    //Log Tag
    protected static final String ACTIVITY_TAG="MainActivity";
	//private Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentList = (ListView)findViewById(R.id.mylistView);

        mCpAdapter = new TaskCpAdapter(this);

        //get all tasks
        tasks = mCpAdapter.getAllTasks();
       // int count = tasks.size();

        mListAdapter = new TaskListAdapter(this);
        contentList.setAdapter(mListAdapter);

        contentList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startDetail(position);
            }
        });

        observer = new TaskObserver(new Handler());
        getContentResolver().registerContentObserver(Tasks.CONTENT_URI, true, observer);

        sendNotification();
    }

    @Override
    public void onDestroy() {
    	super.onDestroy();
    }
    
    public void startDetail(int index) {
		//String actionName = "com.example.scrolltask.intent.action.DetailView";
    	Intent intent = new Intent(this, DetailActivity.class);
		Task task = mCpAdapter.getTaskByPos(index);
		
		intent.putExtra(Tasks.TID, task.getId());
		intent.putExtra(Tasks.TITLE,task.getName());
		intent.putExtra(Tasks.LEVEL, task.getLevel());
		intent.putExtra(Tasks.DETAIL, task.getDetail());
		intent.putExtra(Tasks.TOP, task.getTop());
        intent.putExtra(Tasks.PRIORITY, task.getPriority());

		startActivityForResult(intent, EDIT_TASK_ACTIVITY);
    }
    
    public void startAdd() {
		String actionName = "com.example.scrolltask.intent.action.EditView";
		Intent i = new Intent(actionName);
    	//this.startActivity(i);
		
		startActivityForResult(i, ADD_TASK_ACTIVITY);
    }
    
    public void removeAllTasks() {
    	mCpAdapter.removeAllTasks();
    }
    
    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case ADD_TASK_ACTIVITY: {
                    if(resultCode == Activity.RESULT_OK) {
                            String title   = data.getStringExtra(Tasks.TITLE);
                            int level  = data.getIntExtra(Tasks.LEVEL, 0);
                            String detail = data.getStringExtra(Tasks.DETAIL);
                            int top = data.getIntExtra(Tasks.TOP, 0);
                            int priority = data.getIntExtra(Tasks.PRIORITY, 0);

                            Task task = new Task(-1, title, level, detail, top, priority);
                            mCpAdapter.insertTask(task);
                    }

                    break;
            }

            case EDIT_TASK_ACTIVITY: {
                    if(resultCode == Activity.RESULT_OK) {
                            int action = data.getIntExtra(DetailActivity.EDIT_TASK_ACTION, -1);
                            if(action == DetailActivity.MODIFY_TASK) {
//	                                String name   = data.getStringExtra(Tasks.TASKNAME);
//	                                int level  = data.getIntExtra(Tasks.LEVEL, 0);
//	                                int date   = data.getIntExtra(Tasks.DATE, 0);
//	                                String detail = data.getStringExtra(Tasks.DETAIL);
//	                                int top = data.getIntExtra(Tasks.TOP, 0);
//	                                int priority = data.getIntExtra(Tasks.PRIORITY, 0);
//	
//	                                Task task = new Task(-1, name, level, date,detail,top,priority);
//	                                mCpAdapter.updateTask(task);
                            } else if(action == DetailActivity.DELETE_TASK)     {
                                    int id = data.getIntExtra(Tasks.TID, -1);
                	        		Log.v(ACTIVITY_TAG, "task id = "+id);
                	        		mCpAdapter.removeTask(id);
                            }

                    }

                    break;
                }
            
            case DELETE_TASK_ACTIVITY: {
            	if(resultCode == Activity.RESULT_OK) {
            		
	        		int id = data.getIntExtra(Tasks.TID, -1);
	        		Log.v(ACTIVITY_TAG, "task id = "+id);
	        		mCpAdapter.removeTask(id);
            	}
        		
            	break;
            }
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //this.getActionBar().hide();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_add) {
        	startAdd();
        	return true;
        } else if (id == R.id.action_removeall) {
        	showDeleteDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    
    private class TaskObserver extends ContentObserver {
    	public TaskObserver(Handler handler) {
    		super(handler);
    	}

		@Override
		public void onChange(boolean selfChange) {
			// TODO Auto-generated method stub
			////we have to update the tasks on time
			tasks = mCpAdapter.getAllTasks();
			mListAdapter.notifyDataSetChanged();
		}
    }
    
	public void showDeleteDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure delete all?")
		.setCancelable(false)///back����
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Auto-generated method stub
				removeAllTasks();
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		}).show();
		
		//AlertDialog alert = builder.create();
	}
    
    private class TaskListAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public TaskListAdapter(Context context){
                  inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
               // return mCpAdapter.getTaskCount();
        	return tasks.size();
        }

        @Override
        public Object getItem(int pos) {
             //   return mCpAdapter.getTaskByPos(pos);
        	return tasks.get(pos);
        }

        @Override
        public long getItemId(int pos) {
              //  return mCpAdapter.getTaskByPos(pos).getId();
        	return tasks.get(pos).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        	Task task = (Task)getItem(position);

                if (convertView == null) {
                        convertView = inflater.inflate(R.layout.task_list_view_item, null);
                }
                
                int top = task.getTop();
                if(top == 1) {
                	convertView.setBackgroundColor(Color.YELLOW);
                } else {
                	convertView.setBackgroundColor(Color.WHITE);
                }
                
        		TextView content = (TextView) convertView.findViewById(R.id.title);
        		content.setText(task.getName());

                TextView titleView = (TextView)convertView.findViewById(R.id.detail);
                titleView.setText(task.getDetail());
                
                TextView sortInfo = (TextView)convertView.findViewById(R.id.sortinfo);
                sortInfo.setText("["+
                		","+"priority:"+task.getPriority() + "]");
//
//                TextView abstractView = (TextView)convertView.findViewById(R.id.textview_article_abstract);
//                abstractView.setText("Abstract: " + article.getAbstract());
//
//                TextView urlView = (TextView)convertView.findViewById(R.id.textview_article_url);
//                urlView.setText("URL: " + article.getUrl());

                return convertView;
        }
    }

    /////add notification
    public void sendNotification() {


    }


}
