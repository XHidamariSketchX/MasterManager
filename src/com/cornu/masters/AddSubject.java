package com.cornu.masters;

import com.cornu.DB.DBHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddSubject extends Activity {
	EditText edt_subjectname;
	Button btn_save;
	Button btn_cancel;
	DBHelper dbopen;
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.addsubject);
		this.findViews();
		this.init();
		this.setListeners();
		this.setOthers();
	}
	private void findViews(){
		edt_subjectname=(EditText)findViewById(R.id.addsubject_edt_subject);
		btn_save=(Button)findViewById(R.id.addsubject_btn_save);
		btn_cancel=(Button)findViewById(R.id.addsubject_btn_cancel);
	}
	private void init(){
		AddSubject.this.setTitle("添加科目");
		dbopen=new DBHelper(this, "MastersManager", 1);
	}
	private void setListeners(){
		btn_save.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				String subjectname=edt_subjectname.getText().toString().trim();
				if(subjectname.equals("")||subjectname==null){
					Toast.makeText(AddSubject.this, "名不能为空", Toast.LENGTH_LONG).show();
				}else{
					SQLiteDatabase db=dbopen.getWritableDatabase();
					ContentValues values=new ContentValues();
					values.put("subjectname",subjectname );
					db.insert("subjects", "subjectname", values);
					db.close();
					Toast.makeText(AddSubject.this, "已添加", Toast.LENGTH_LONG).show();
					Intent intent=new Intent();
					intent.setClass(AddSubject.this, Manager.class);
					AddSubject.this.startActivity(intent);
				}
				
			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(AddSubject.this, Manager.class);
				AddSubject.this.startActivity(intent);
//				finish();
			}
		});
	}
	private void setOthers(){
		edt_subjectname.setHint("输入科目");
	}
}
