package com.todo.thomas.scrolltask.detail;

import java.util.Calendar;

import com.todo.thomas.scrolltask.edit.AddActivity;
import com.todo.thomas.scrolltask.myprovider.Task;
import com.todo.thomas.scrolltask.myprovider.TaskCpAdapter;
import com.todo.thomas.scrolltask.myprovider.Tasks;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker.OnDateChangedListener;

import com.todo.thomas.scrolltask.R;

public class ModifyActivity extends Activity{

	private EditText titleEdit;
	private RatingBar rating;
	private EditText detailEdit;
	private CheckBox onTop;
	private Button   addbutton;
	
	int tid;
	String name;
	int level;
	int date;
	String detail;
	int top;
	int priority;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify);

        titleEdit   = (EditText)findViewById(R.id.titleContent);
		rating     = (RatingBar)findViewById(R.id.ratingBar);
		detailEdit = (EditText)findViewById(R.id.detailContent);
		onTop      = (CheckBox)findViewById(R.id.checkBox);
		addbutton  = (Button)findViewById(R.id.addBT);

		Intent intent   = getIntent();
		Bundle data = intent.getBundleExtra(DetailActivity.DATA_BUNDLE);
		
		tid         = data.getInt(Tasks.TID, 0);
		name        = data.getString(Tasks.TITLE);
		level       = data.getInt(Tasks.LEVEL, 0);
		detail      = data.getString(Tasks.DETAIL);
		top         = data.getInt(Tasks.TOP, 0);

        titleEdit.setText(name);
	    ////	levelEdit.setText(String.valueOf(level));
		rating.setRating(level);
		
		rating.setOnRatingBarChangeListener(new RatingBarListener());
		
		detailEdit.setText(detail);

		if(top == 1) {
		    onTop.setChecked(true);	
		}
		
		onTop.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean checked) {
				// TODO Auto-generated method stub
				if(checked) {
					top = 1;/////set on top
				} else {
					top = 0;
				}
			}
		}
		);
		
		addbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
                String name   = titleEdit.getText().toString();
                int    level  = (int) rating.getRating();
                String detail = detailEdit.getText().toString();
                int priority = 0;
                
                Intent result = new Intent();
                result.putExtra(Tasks.TID, tid);////actually we should not change ,bug later fixed
                result.putExtra(Tasks.TITLE, name);
                result.putExtra(Tasks.LEVEL, level);
                result.putExtra(Tasks.DETAIL, detail);
                result.putExtra(Tasks.TOP, top);
                result.putExtra(Tasks.PRIORITY, priority);
                ////now we calculate the priority

                updateTask(result);
        	
        		finish();
			}
			
		});
	}

	public void updateTask(Intent data) {
		TaskCpAdapter mCpAdapter = new TaskCpAdapter(this);
		int id = data.getIntExtra(Tasks.TID, -1);
        String title   = data.getStringExtra(Tasks.TITLE);
        int level  = data.getIntExtra(Tasks.LEVEL, 0);
        String detail = data.getStringExtra(Tasks.DETAIL);
        int top = data.getIntExtra(Tasks.TOP, 0);
        int priority = data.getIntExtra(Tasks.PRIORITY, 0);

        Task task = new Task(id, title, level,detail,top, priority);
        mCpAdapter.updateTask(task);
	}
	
	private class RatingBarListener implements RatingBar.OnRatingBarChangeListener{

		@Override
		public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
			// TODO Auto-generated method stub
			//System.out.println("�ȼ���" + rating);
			//System.out.println("���ǣ�" + ratingBar.getNumStars());
			level = (int) rating;
		}
	}

	public void sendResult(Intent result) {
		setResult(Activity.RESULT_OK, result);
		finish();
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

}
