package com.cornu.masters;

import java.util.ArrayList;
import java.util.List;

import com.cornu.DB.DBHelper;
import com.cornu.tools.ItemValue;
import com.cornu.tools.MyArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract.Contacts.Data;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class Search extends Activity {
	Spinner spn_type,spn_subject,spn_book;
	EditText edt_page,edt_ordernum,edt_description;
	Button btn_search,btn_cancel;
	CheckBox chb_solved;
	private static  String[] types=null;
    private static  String[] subjects=null;
    private static  String[] books=null;
    
    private ArrayAdapter<String> adp_type;  
    private ArrayAdapter<String> adp_subject;  
    private ArrayAdapter<String> adp_bookname;  
    
    DBHelper dbopen;
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.search);
		this.finViews();
		this.init();
		this.setOthers();
		this.setListeners();
	}
	private void finViews(){
		spn_book=(Spinner)findViewById(R.id.search_spn_bookname);
		spn_subject=(Spinner)findViewById(R.id.search_spn_subject);
		spn_type=(Spinner)findViewById(R.id.search_spn_type);
		edt_description=(EditText)findViewById(R.id.search_edt_description);
		edt_ordernum=(EditText)findViewById(R.id.search_edt_ordernum);
		edt_page=(EditText)findViewById(R.id.search_edt_page);
		btn_cancel=(Button)findViewById(R.id.search_btn_cancel);
		btn_search=(Button)findViewById(R.id.search_btn_search);
		chb_solved=(CheckBox)findViewById(R.id.search_chb_solved);
	}
	private void init(){
		Search.this.setTitle("查找");
    	dbopen=new DBHelper(Search.this,"MastersManager",1);
		SQLiteDatabase db=(SQLiteDatabase)dbopen.getReadableDatabase();
		//从数据库获取类型列表
		Cursor typescs=db.query("types", new String[] {"typename"}, null, null, null, null,null);
		if(typescs.getCount()>0){
			types=new String[typescs.getCount()];
			while(typescs.moveToNext()){
				types[typescs.getPosition()]=typescs.getString(typescs.getColumnIndex("typename"));
			}
		}else{
			types=new String[1];
			types[0]="无类型，请添加";
			
		}
		typescs.close();
		//从数据库获取科目列表
		
		Cursor subjectscs=db.query("subjects", new String[] {"subjectname"}, null, null, null, null,null);
		if(subjectscs.getCount()>0){
			subjects=new String[subjectscs.getCount()];
			while(subjectscs.moveToNext()){
				subjects[subjectscs.getPosition()]=subjectscs.getString(subjectscs.getColumnIndex("subjectname"));
			}
		}
		else{
			subjects=new String[1];
			subjects[0]="无科目，请添加";
			
		}
		subjectscs.close();
		//从数据库获取书本列表
		
		Cursor bookscs=db.query("books", new String[] {"bookname"}, null, null, null, null,null);
		
		if(bookscs.getCount()>0){
			System.out.println("books count>0");
			books=new String[bookscs.getCount()+1];
			books[0]="选择书名";
			while(bookscs.moveToNext()){
				books[bookscs.getPosition()+1]=bookscs.getString(bookscs.getColumnIndex("bookname"));
			}
		}else{
			books=new String[1];
			books[0]="无书本，请添加";
			
		}
		bookscs.close();
		db.close();
		
    }
	private void setOthers(){
		edt_page.setHint("页");
    	edt_ordernum.setHint("题号");
    	edt_description.setHint("描述");
    	
		adp_type=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
		adp_subject=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjects);
		adp_bookname=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, books);
		
		adp_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    adp_bookname.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
	    adp_subject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
	    spn_type.setAdapter(adp_type);
        spn_book.setAdapter(adp_bookname);  
        spn_subject.setAdapter(adp_subject);
        
        spn_type.setVisibility(View.VISIBLE);
        spn_book.setVisibility(View.VISIBLE);  
        spn_subject.setVisibility(View.VISIBLE);
        
        
        spn_book.setSelection(0);
		
	}
	private void setListeners(){
		btn_search.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				String columns[]={"id","type","subject","book","page","ordernum","description","state"};
				String selection="type=? and subject=?";
				String selectionArgs[];
				
				
				String type=types[spn_type.getSelectedItemPosition()];
				String subject=subjects[spn_subject.getSelectedItemPosition()];
				
				String page=edt_page.getText().toString().trim();
				String num=edt_ordernum.getText().toString().trim();
				String description=edt_description.getText().toString().trim();
				
				System.out.println("type="+type+"subject="+subject+"page="+page+"num="+num+"des="+description);
				System.out.println("book="+books[spn_book.getSelectedItemPosition()]);
				String selectionArgsString=type+"#"+subject;
				
				if(spn_book.getSelectedItemPosition()>0){
					selection+=" and book=?";
					selectionArgsString+=("#"+books[spn_book.getSelectedItemPosition()]);
					
				}
				if(page.matches("^[0-9]*[1-9][0-9]*$")){
					selection+=" and page=?";
					selectionArgsString+=("#"+page);
					System.out.println(page+"matches");
				}
				if(num.matches("^[0-9]*[1-9][0-9]*$")){
					selection+=" and ordernum=?";
					selectionArgsString+=("#"+num);
				}
				if(!description.equals("")){
					selection+=" and description like ?";
					selectionArgsString+=("#%"+description+"%");
				}
				selection+=" and state=?";
				if(chb_solved.isChecked()==true){
					selectionArgsString+=("#"+ItemValue.SOLVED);
				}else{
					selectionArgsString+=("#"+ItemValue.NEW);
				}
				
				
				System.out.println("see all selections"+selection);
				System.out.println("see all args->"+selectionArgsString);
				
				//selection+=",description like";
				selectionArgs=selectionArgsString.split("#");
				
				for(int i=0;i<selectionArgs.length;i++){
					System.out.println("arg"+i+"="+selectionArgs[i]);
				}
				
				ArrayList<ItemValue> itemlist=new ArrayList<ItemValue>();
				ItemValue data;
				SQLiteDatabase db=dbopen.getReadableDatabase();
				Cursor cs=db.query("items", columns, selection,selectionArgs , null, null,null);
				while(cs.moveToNext()){
					data=new ItemValue();
					data.setId(cs.getInt(cs.getColumnIndex("id")));
					data.setType(cs.getString(cs.getColumnIndex("type")));
					data.setSubject(cs.getString(cs.getColumnIndex("subject")));
					data.setBook(cs.getString(cs.getColumnIndex("book")));
					data.setPage(cs.getInt(cs.getColumnIndex("page")));
					data.setOrdernum(cs.getInt(cs.getColumnIndex("ordernum")));
					data.setDescription(cs.getString(cs.getColumnIndex("description")));
					data.setState(cs.getInt(cs.getColumnIndex("state")));
					itemlist.add(data);		
				}
				cs.close();
				db.close();
				Intent intent=new Intent();
				intent.setClass(Search.this, ShowList.class);
				Bundle bundle = new Bundle();
				intent.putExtra("subject", subject);
				
				bundle.putSerializable("itemlist", itemlist);
				intent.putExtras(bundle);
				Search.this.startActivity(intent);
				
			}
		});
		spn_subject.setOnItemSelectedListener(new OnItemSelectedListener() {

  			
  			public void onItemSelected(AdapterView<?> arg0, View arg1,
  					int arg2, long arg3) {
  				
  				SQLiteDatabase db=(SQLiteDatabase)dbopen.getReadableDatabase();
  				Cursor bookscs=db.query("books", new String[] {"bookname"}, 
  						"subjectname=?",new String[]{subjects[arg2]}, null, null,null);
  				
  				if(bookscs.getCount()>0){
  					
  					books=new String[bookscs.getCount()+1];
  					books[0]="选择书名";
  					while(bookscs.moveToNext()){
  						books[bookscs.getPosition()+1]=bookscs.getString(bookscs.getColumnIndex("bookname"));
  					}
  				}else{
  					books=new String[1];
  					books[0]="无书本，请添加";
  					
  				}
  				ArrayAdapter<String> newadp_book=new ArrayAdapter<String>(Search.this, android.R.layout.simple_spinner_item,books ) ;
  				newadp_book.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
  				spn_book.setAdapter(newadp_book);
  				bookscs.close();
  				db.close();
  			}

  			
  			public void onNothingSelected(AdapterView<?> arg0) {
  				// TODO Auto-generated method stub
  				
  			}
  		});
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(Search.this, Index.class);
				Search.this.startActivity(intent);
//				finish();
			}
		});
	}
}
