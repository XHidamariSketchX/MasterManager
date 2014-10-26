package com.cornu.masters;

import com.cornu.DB.DBHelper;
import com.cornu.tools.ItemValue;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class Index extends Activity {
	Button btn_new,btn_viewbysubject,btn_manager,btn_search;
	ListView lstv_aggregrate;
	
	public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState); 
        this.setContentView(R.layout.index);
        this.findViews();
        this.init();
        this.setListeners();
        
	}
	private void findViews(){
		btn_manager=(Button)findViewById(R.id.btn_manager);
        btn_new=(Button)findViewById(R.id.btn_new);
        btn_search=(Button)findViewById(R.id.btn_search);
        btn_viewbysubject=(Button)findViewById(R.id.btn_viewbysubject);
        lstv_aggregrate=(ListView)findViewById(R.id.lstv_aggregrate);
	}
	private void setListeners(){
		btn_manager.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				Intent intent=new Intent();
				intent.setClass(Index.this,Manager.class);
				
			    Index.this.startActivity(intent);	
			    System.out.println("Manager...Oncreate");
			}
		});
		btn_new.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(Index.this, Add.class);
				Index.this.startActivity(intent);
//				DBHelper dbopen=new DBHelper(Index.this,"QuestionManager",1);
//				SQLiteDatabase db=(SQLiteDatabase)dbopen.getReadableDatabase();
//				Cursor rs=db.query("subject", new String[] {"subjectname"}, null, null, null, null,null);
//				while(rs.moveToNext()){
//					btn_new.setText(rs.getString(rs.getColumnIndex("subjectname")));
//				}
			}
		});
		btn_search.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(Index.this, Search.class);
				Index.this.startActivity(intent);
				
			}
		});
		btn_viewbysubject.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	private void init(){
		System.out.println(".....................");
		DBHelper dbopen=new DBHelper(Index.this,"MastersManager",1);
		SQLiteDatabase db=(SQLiteDatabase)dbopen.getWritableDatabase();
		db.close();
	}
}
