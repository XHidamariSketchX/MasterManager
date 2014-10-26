package com.cornu.masters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Manager extends Activity {
	Button btn_book;
	Button btn_subject;
	Button btn_type;
	Button btn_cancel;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.manager);
		this.findViews();
		this.init();
		this.setListeners();
	}
	private void init(){
		Manager.this.setTitle("№ЬАн");
	}
	private void findViews(){
		btn_book=(Button)findViewById(R.id.manager_btn_book);
		btn_subject=(Button)findViewById(R.id.manager_btn_subject);
		btn_type=(Button)findViewById(R.id.manager_btn_type);
		btn_cancel=(Button)findViewById(R.id.manager_btn_cancel);
	}
	private void setListeners(){
		btn_book.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(Manager.this, AddBook.class);
				Manager.this.startActivity(intent);	
			}
		});
		btn_subject.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(Manager.this, AddSubject.class);
				Manager.this.startActivity(intent);	
			}
		});
		btn_type.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(Manager.this, AddType.class);
				Manager.this.startActivity(intent);	
			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(Manager.this, Index.class);
				Manager.this.startActivity(intent);
//				finish();
			}
		});
	}
}
