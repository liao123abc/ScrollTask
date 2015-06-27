package com.todo.thomas.scrolltask.detail;


import com.todo.thomas.scrolltask.myprovider.Task;
import com.todo.thomas.scrolltask.myprovider.TaskCpAdapter;
import com.todo.thomas.scrolltask.myprovider.Tasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.todo.thomas.scrolltask.R;

public class DetailActivity extends Activity{

	private TextView titleTv;
	private RatingBar ratingbar;
	private CheckBox checkbox;
	private TextView detailTv;
	private Switch   switchFinish;

	//private TaskCpAdapter mCpAdapter = null;
	private Intent intent= null;
	
	PopupWindow mPop = null;
	
	protected static final String ACTIVITY_TAG="DetailActivity";
	protected static final String DATA_BUNDLE ="Modify_data";
	
	public final static String EDIT_TASK_ACTION = "EDIT_TASK_ACTION";
	
	public final static int MODIFY_TASK = 1;
	public final static int DELETE_TASK = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//mCpAdapter = new TaskCpAdapter(this);
		
		intent = getIntent();
		String title = intent.getStringExtra(Tasks.TITLE);
		int level = intent.getIntExtra(Tasks.LEVEL, 0);
		String detail = intent.getStringExtra(Tasks.DETAIL);
		int top = intent.getIntExtra(Tasks.TOP, 0);
		
		setContentView(R.layout.activity_detail);

        titleTv = (TextView)findViewById(R.id.titleContent);
        titleTv.setText(title);
		
		ratingbar = (RatingBar)findViewById(R.id.ratingBar);
		ratingbar.setRating(level);
		//ratingbar.setClickable(false);
		//ratingbar.setActivated(false);
		
		detailTv = (TextView)findViewById(R.id.detailContent);
		detailTv.setText(detail);

        checkbox = (CheckBox)findViewById(R.id.checkBox);

        switchFinish = (Switch)findViewById(R.id.switchfinish);
        switchFinish.setChecked(true);

        switchFinish.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton view, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked) {
					/////reset the state to undone
					switchFinish.setChecked(true);
				} else {
					showDoneDialog();
				}
			}
			
		}
		);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        //this.getActionBar().hide();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        if (id == R.id.action_detail_delete) {
        	showDeleteDialog();
        	//finish();
            return true;
        } else if (id == R.id.action_detail_add) {
        	startAdd();
        	return true;
        } else if (id == R.id.action_detail_modify) {
        	startModify();
        	return true;
        }
        
        ////others
        return super.onOptionsItemSelected(item);
    }
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	public void startAdd() {
		
	}
	
	public void startModify() {
        int id          = intent.getIntExtra(Tasks.TID, 0);
		String name     = intent.getStringExtra(Tasks.TITLE);
		int level       = intent.getIntExtra(Tasks.LEVEL, 0);
		String detail   = intent.getStringExtra(Tasks.DETAIL);
		int top         = intent.getIntExtra(Tasks.TOP, -1);
    	
    	Bundle data = new Bundle();
    	data.putInt(Tasks.TID, id);
    	data.putString(Tasks.TITLE, name);
    	data.putString(Tasks.DETAIL, detail);
    	data.putInt(Tasks.LEVEL, level);
    	data.putInt(Tasks.TOP, top);
    	
    	Intent modify = new Intent(this, ModifyActivity.class);
    	modify.putExtra(DATA_BUNDLE, data);
    	
		startActivityForResult(modify, MODIFY_TASK);
		
		finish();
	}

//    private static View getRootView(Activity context)  
//    {  
//        return ((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);  
//    }  
    
	public void startDelete() {

		Intent result = new Intent();
		result.putExtra(Tasks.TID, intent.getIntExtra(Tasks.TID, 0));///id
		result.putExtra(EDIT_TASK_ACTION, DELETE_TASK);
		
		setResult(Activity.RESULT_OK, result);
		finish();
	}

	public void showDeleteDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure want to delete?")
		.setCancelable(false)///back����
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Auto-generated method stub
				startDelete();
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		}).show();
	}
	
	public void showDoneDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Did you really accomplish it?")
		.setCancelable(false)///back����
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Auto-generated method stub
				startDelete();
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Auto-generated method stub
				/////reset the state to undone
				switchFinish.setChecked(true);
				dialog.cancel();
			}
		}).show();
	}
}
