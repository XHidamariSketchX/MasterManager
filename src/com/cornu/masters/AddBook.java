package com.cornu.masters;

import com.cornu.DB.DBHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class AddBook extends Activity {

	Spinner spn_subject;
	Button btn_save;
	Button btn_cancel;
	EditText edt_bookname;
	ArrayAdapter<String> adp_subject;
	
	String subjects[];
	DBHelper dbopen;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.addbook);
		
		this.findViews();
		this.init();
		this.setListeners();
		this.setOthers();
	}
	private void findViews(){
		btn_cancel=(Button) findViewById(R.id.addbook_btn_cancel);
		btn_save=(Button)findViewById(R.id.addbook_btn_save);
		spn_subject=(Spinner)findViewById(R.id.addbook_spn_subject);
		edt_bookname=(EditText)findViewById(R.id.addbook_edt_bookname);
	}
	private void init(){
		this.setTitle("添加书本");
		dbopen=new DBHelper(AddBook.this,"MastersManager",1);
		SQLiteDatabase db=dbopen.getReadableDatabase();
		Cursor cs=db.query("subjects", new String[]{"subjectname"}, null, null, null, null, null);
		if(cs.getCount()>0){
			subjects=new String[cs.getCount()];
			while(cs.moveToNext()){
				subjects[cs.getPosition()]=cs.getString(cs.getColumnIndex("subjectname"));
			}
		}
		else{
			subjects=new String[1];
			subjects[0]="无科目，请添加";
			btn_save.setEnabled(false);
		}
//		subjects=new String[cs.getCount()];
//		while(cs.moveToNext()){
//			subjects[cs.getPosition()]=cs.getString(cs.getColumnIndex("subjectname"));
//		}
		cs.close();
		db.close();
		
	}
	private void setListeners(){
		btn_save.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {		
				String bookname= edt_bookname.getText().toString().trim();
				if(bookname.equals("")||bookname==null){
					Toast.makeText(AddBook.this, "名不能为空", Toast.LENGTH_LONG).show();
				}else{
					SQLiteDatabase db=dbopen.getWritableDatabase();
					ContentValues values=new ContentValues();
					values.put("bookname",bookname);
					values.put("subjectname", subjects[spn_subject.getSelectedItemPosition()]);
					System.out.println(values.get("subjectname"));
					db.insert("books", "bookname", values);
					db.close();
					Toast.makeText(AddBook.this, "已添加", Toast.LENGTH_LONG).show();
					Intent intent=new Intent();
					intent.setClass(AddBook.this, Manager.class);
					AddBook.this.startActivity(intent);
					
				}
				
			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(AddBook.this, Manager.class);
				AddBook.this.startActivity(intent);
//				finish();
			}
		});
		
	}
	private void setOthers(){
		edt_bookname.setHint("输入名称");
		adp_subject=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, subjects);
		adp_subject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn_subject.setAdapter(adp_subject);
	}
}
