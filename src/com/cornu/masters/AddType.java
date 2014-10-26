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

public class AddType extends Activity {
	Button btn_save;
	Button btn_cancel;
	EditText edt_type;
	DBHelper dbopen;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.addtype);
		this.findViews();
		this.init();
		this.setListeners();
		this.setOthers();
	}
	private void findViews(){
		edt_type=(EditText)findViewById(R.id.addtype_edt_type);
		btn_save=(Button)findViewById(R.id.addtype_btn_save);
		btn_cancel=(Button)findViewById(R.id.addtype_btn_cancel);
	}
	private void init(){
		AddType.this.setTitle("添加类型");
		dbopen=new DBHelper(this, "MastersManager", 1);
	}
	private void setListeners(){
		btn_save.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				String type=edt_type.getText().toString().trim();
				if(type.equals("")||type==null){
					Toast.makeText(AddType.this, "名不能为空", Toast.LENGTH_LONG).show();
				}else{
					SQLiteDatabase db=dbopen.getWritableDatabase();
					ContentValues values=new ContentValues();
					values.put("typename", type);
					db.insert("types", "typename", values);
					db.close();
					Toast.makeText(AddType.this, "已添加", Toast.LENGTH_LONG).show();
					Intent intent=new Intent();
					intent.setClass(AddType.this, Manager.class);
					AddType.this.startActivity(intent);
				}
				
			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(AddType.this, Manager.class);
				AddType.this.startActivity(intent);
//				finish();
			}
		});
	}
	private void setOthers(){
		edt_type.setHint("输入类型");
	}
}
