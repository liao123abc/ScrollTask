package com.todo.thomas.scrolltask.edit;

import com.todo.thomas.scrolltask.myprovider.Tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;

import com.todo.thomas.scrolltask.R;

public class AddActivity extends Activity{

    public static final int TOPWEIGHT  = 10;///when ontop should add 10 to priority

	private EditText  titleEdit;
	private RatingBar ratingbar;
	private EditText  detailEdit;
	private CheckBox  onTop;
    private Button    addbutton;

	////this is specially used for level
	int level;
    ////set on top
    int top = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		//ActionBar bar = getActionBar();
		
		Intent intent   = getIntent();

		titleEdit   = (EditText)findViewById(R.id.titleContent);
		ratingbar  = (RatingBar)findViewById(R.id.ratingBar);
		detailEdit = (EditText)findViewById(R.id.detailContent);
		onTop      = (CheckBox)findViewById(R.id.checkBox);
        addbutton  = (Button)findViewById(R.id.addBT);

		ratingbar.setOnRatingBarChangeListener(new RatingBarListener());

		onTop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

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

		addbutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
                String title   = titleEdit.getText().toString();
                String detail = detailEdit.getText().toString();

                //calculate the priority
                int priority = 0;
                priority += level;
                if(top == 1) {
                    priority += TOPWEIGHT;
                }

                Intent result = new Intent();
                result.putExtra(Tasks.TITLE, title);
                result.putExtra(Tasks.LEVEL, level);
                result.putExtra(Tasks.DETAIL, detail);
                result.putExtra(Tasks.TOP, top);
                result.putExtra(Tasks.PRIORITY, priority);

                sendResult(result);
			}

		});
	}
	
	private class RatingBarListener implements RatingBar.OnRatingBarChangeListener{

		@Override
		public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
			// TODO Auto-generated method stub
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
