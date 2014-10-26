package com.cornu.masters;

import java.io.Serializable;

import com.cornu.DB.DBHelper;
import com.cornu.tools.ItemValue;

import android.app.Activity;  
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;  
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;  
import android.view.View.OnClickListener;
import android.widget.AdapterView;  
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;  
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;  
import android.widget.TextView;  
import android.widget.Toast;
public class Add extends Activity {  
      
	private static  String[] types=null;
    private static  String[] subjects=null;
    private static  String[] books=null;

   
    private Spinner spn_type;
    private Spinner spn_subject;  
    private Spinner spn_bookname;
    
    private EditText edt_page;
    private EditText edt_ordernum;
    private EditText edt_description;
    
    private Button btn_save;
    private Button btn_cancel;
    
    private ArrayAdapter<String> adp_type;  
    private ArrayAdapter<String> adp_subject;  
    private ArrayAdapter<String> adp_bookname;  

    DBHelper dbopen;
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.add); 
        
        this.findViews();
        
        this.setListeners();
       
        this.init();
        
        this.setOthers();
        
        
         
    }  
    private boolean checkInputData(){
    	String num=edt_ordernum.getText().toString().trim();
		String pages=edt_page.getText().toString().trim();
		if(pages.equals("")||pages==null){
			Toast.makeText(Add.this, "页号不能为空", Toast.LENGTH_LONG).show();
		}
		else if(num.equals("")||num==null){
			
			Toast.makeText(Add.this, "题号不能为空", Toast.LENGTH_LONG).show();
				 
		}
		else{
			return true;
		}
		
		return false;
    	
    }
    private void findViews(){
    	
        spn_type=(Spinner)this.findViewById(R.id.add_spn_type);
        spn_subject=(Spinner)this.findViewById(R.id.add_spn_subject); 
        spn_bookname=(Spinner)this.findViewById(R.id.add_spn_bookname);
      
        edt_description=(EditText)this.findViewById(R.id.add_edt_description);
        edt_ordernum=(EditText)this.findViewById(R.id.add_edt_ordernum);
        edt_page=(EditText)this.findViewById(R.id.add_edt_page);
        
        btn_save=(Button)this.findViewById(R.id.add_btn_save);
        btn_cancel=(Button)this.findViewById(R.id.add_btn_cancel);
       
    }
    private void setListeners(){
    	spn_subject.setOnItemSelectedListener(new OnItemSelectedListener() {

  			
  			public void onItemSelected(AdapterView<?> arg0, View arg1,
  					int arg2, long arg3) {
  				
  				SQLiteDatabase db=(SQLiteDatabase)dbopen.getReadableDatabase();
  				Cursor bookscs=db.query("books", new String[] {"bookname"}, 
  						"subjectname=?",new String[]{subjects[arg2]}, null, null,null);
  				spn_bookname.setEnabled(true);
  				btn_save.setEnabled(true);
  				if(bookscs.getCount()>0){
  					books=new String[bookscs.getCount()];
  					while(bookscs.moveToNext()){
  						books[bookscs.getPosition()]=bookscs.getString(bookscs.getColumnIndex("bookname"));
  					}
  				}else{
  					books=new String[1];
  					books[0]="无书本，请添加";
  					spn_bookname.setEnabled(false);
  					btn_save.setEnabled(false);
  				}
  				ArrayAdapter<String> newadp_book=new ArrayAdapter<String>(Add.this, android.R.layout.simple_spinner_item,books ) ;
  				newadp_book.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
  				spn_bookname.setAdapter(newadp_book);
  				bookscs.close();
  				db.close();
  			}

  			
  			public void onNothingSelected(AdapterView<?> arg0) {
  				// TODO Auto-generated method stub
  				
  			}
  		});
    	btn_save.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String num=edt_ordernum.getText().toString().trim();
	    		String pages=edt_page.getText().toString().trim();
	    		String description=edt_description.getText().toString().trim();
				System.out.println("num="+num);
	    		System.out.println("page="+pages);
				if(description.equals("")||description==null){
					description="无描述";
				}
				if(Add.this.checkInputData()){
				
					SQLiteDatabase db=dbopen.getWritableDatabase();	
					try{
						System.out.println("create itemvalue");
						ItemValue data=new ItemValue();
						data.setBook(books[spn_bookname.getSelectedItemPosition()]);
						data.setDescription(description);
						
						data.setOrdernum(Integer.parseInt(num));
						
						data.setPage(Integer.parseInt(pages));
						
						data.setSubject(subjects[spn_subject.getSelectedItemPosition()]);
						data.setType(types[spn_type.getSelectedItemPosition()]);
						data.setState(ItemValue.NEW);
						ContentValues values=data.getContentValues();
						db.insert("items", "description", values);
						Toast.makeText(Add.this, "已添加", Toast.LENGTH_LONG).show();
						Intent intent=new Intent();
						intent.setClass(Add.this, Index.class);
						Add.this.startActivity(intent);
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						db.close();
					}
				}
			}
			
		});
    	btn_cancel.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(Add.this, Index.class);
				Add.this.startActivity(intent);	
//				finish();
			}
		});
    }
    private void setOthers(){
    	edt_page.setHint("页");
    	edt_ordernum.setHint("题号");
    	edt_description.setHint("描述");
    	//将可选内容与ArrayAdapter连接起来  
    	 
    	adp_type=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,types);  
        adp_subject=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,subjects);  
        adp_bookname=new   ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,books);  
        //设置下拉列表的风格  
        
        adp_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adp_bookname.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        adp_subject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到Spinner中  
        
        spn_type.setAdapter(adp_type);
  
        spn_bookname.setAdapter(adp_bookname);  
       
        spn_subject.setAdapter(adp_subject);
        
        
        //添加事件Spinner事件监听  
      //  m_Spinner.setOnItemSelectedListener(m_SpinnerListener);  
      
        //设置默认值  
        spn_type.setVisibility(View.VISIBLE);
        spn_bookname.setVisibility(View.VISIBLE);  
        spn_subject.setVisibility(View.VISIBLE);
        
      
//        spn_subject.setOnItemClickListener(new OnItemClickListener() {
//
//			
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				String newbooks[];
//				SQLiteDatabase db=(SQLiteDatabase)dbopen.getReadableDatabase();
//				Cursor bookscs=db.query("books", new String[] {"bookname"}, 
//						"subjectname=?",new String[]{subjects[arg2]}, null, null,null);
//				
//				if(bookscs.getCount()>0){
//					newbooks=new String[bookscs.getCount()];
//					while(bookscs.moveToNext()){
//						newbooks[bookscs.getPosition()]=bookscs.getString(bookscs.getColumnIndex("bookname"));
//					}
//				}else{
//					newbooks=new String[1];
//					newbooks[0]="无书本，请添加";
//					spn_bookname.setEnabled(false);
//					btn_save.setEnabled(false);
//				}
//				ArrayAdapter<String> newadp_book=new ArrayAdapter<String>(Add.this, android.R.layout.simple_spinner_dropdown_item,newbooks ) ;
//				spn_subject.setAdapter(newadp_book);
//			}
//		});
//        
    }
    
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "管理");
		menu.add(0, 2, 2, R.string.cancel);
	
		return super.onCreateOptionsMenu(menu);
	}

	
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==1){
			Intent intent=new Intent();
			intent.setClass(Add.this, Manager.class);
			Add.this.startActivity(intent);
			
		}
		if(item.getItemId()==2){
			finish();
		}
		
		return super.onOptionsItemSelected(item);
	}

	private void init(){
		Add.this.setTitle("新建");
		btn_cancel.setText("取消");
    	dbopen=new DBHelper(Add.this,"MastersManager",1);
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
			spn_bookname.setEnabled(false);
			btn_save.setEnabled(false);
		}
		typescs.close();
		//从数据库获取科目列表
		System.out.println("types//"+types.toString());
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
			spn_bookname.setEnabled(false);
			btn_save.setEnabled(false);
		}
		subjectscs.close();
		//从数据库获取书本列表
		
		Cursor bookscs=db.query("books", new String[] {"bookname"}, "subjectname=?",new String[]{subjects[0]}, null, null,null);
		if(bookscs.getCount()>0){
			books=new String[bookscs.getCount()];
			while(bookscs.moveToNext()){
				books[bookscs.getPosition()]=bookscs.getString(bookscs.getColumnIndex("bookname"));
			}
		}else{
			books=new String[1];
			books[0]="无书本，请添加";
			spn_bookname.setEnabled(false);
			btn_save.setEnabled(false);
		}
		bookscs.close();
		db.close();
		
		System.out.println("books//"+books.toString());
    }
}  