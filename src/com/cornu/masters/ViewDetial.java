package com.cornu.masters;

import com.cornu.DB.DBHelper;
import com.cornu.tools.ItemValue;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class ViewDetial extends Activity {
	DBHelper dbopen;
	TextView txtv_detail;
	EditText edt_Description;
	CheckBox chb_solved;
	Button btn_modify;
	Button btn_back;
	ItemValue item;
	int id;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.viewdetial);
		this.findViews();
		this.init();
		this.setListeners();
		
		
	}
	private void setListeners(){
		btn_modify.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				String description=edt_Description.getText().toString().trim();
				boolean bol_solved=chb_solved.isChecked();
				int solved;
				if(bol_solved==true){
					solved=ItemValue.SOLVED;
				}
				else{
					solved=ItemValue.NEW;
				}
				SQLiteDatabase db=dbopen.getWritableDatabase();
				ContentValues values=new ContentValues();
				values.put("description", description);
				values.put("state", solved);
				db.update("items", values, "id=?", new String[]{id+""});
				db.close();
				ViewDetial.this.finish();
			}
		});
		btn_back.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				finish();
				
			}
		});
	}
	private void findViews(){
		txtv_detail=(TextView)findViewById(R.id.viewdetial_txtv_detail);
		edt_Description=(EditText)findViewById(R.id.viewdetail_edt_description);
		chb_solved=(CheckBox)findViewById(R.id.viewdetail_chb_solved);
		btn_back=(Button)findViewById(R.id.viewdetail_btn_back);
		btn_modify=(Button)findViewById(R.id.viewdetail_btn_modify);
	}
	private void init(){
		ViewDetial.this.setTitle("ฯ๊ว้");
		dbopen=new DBHelper(ViewDetial.this, "MastersManager", 1);
		SQLiteDatabase db=dbopen.getReadableDatabase();
		id = this.getIntent().getIntExtra("itemid", 0);
		item=new ItemValue();
		String columns[]={"id","type","subject","book","page","ordernum","description","state"};
		
		Cursor cs=db.query("items", columns, "id=?", new String[]{id+""}, null, null, null);
		if(cs.moveToNext()){
			item.setId(cs.getInt(cs.getColumnIndex("id")));
			item.setType(cs.getString(cs.getColumnIndex("type")));
			item.setSubject(cs.getString(cs.getColumnIndex("subject")));
			item.setBook(cs.getString(cs.getColumnIndex("book")));
			item.setPage(cs.getInt(cs.getColumnIndex("page")));
			item.setOrdernum(cs.getInt(cs.getColumnIndex("ordernum")));
			item.setDescription(cs.getString(cs.getColumnIndex("description")));
			item.setState(cs.getInt(cs.getColumnIndex("state")));
		}
		cs.close();
		db.close();
		txtv_detail.setTextSize(20);
		txtv_detail.setText("	"+item.getType()+"->"+item.getSubject()+"->"+item.getBook()+"\n"+"	ตฺ"+item.getPage()+"าณ" +
				"   "+item.getOrdernum()+"ฬโ");
		edt_Description.setText(item.getDescription());
		int state=item.getState();
		if(state==ItemValue.NEW){
			chb_solved.setChecked(false);
		}
		else if(state==ItemValue.SOLVED){
			chb_solved.setChecked(true);
		}
		
	}
}
