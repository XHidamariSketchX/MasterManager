package com.cornu.masters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import com.cornu.tools.ItemValue;
import com.cornu.tools.MyAdapter;
import com.cornu.tools.MyArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ShowList extends Activity {
	ArrayList<ItemValue> itemlist;
	 public void onCreate(Bundle savedInstanceState) {  
		 super.onCreate(savedInstanceState);  
		 
		 Intent intent=this.getIntent();
		 Bundle bundle = intent.getExtras();
		 String subject=intent.getStringExtra("subject");
		 ShowList.this.setTitle(subject);
		
		 itemlist=(ArrayList<ItemValue>)bundle.getSerializable("itemlist");
		 List<String> data=this.getListStringDataFromList(itemlist);
		 ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, 
				 data );
		 ListView listview=new ListView(this);
		 this.setContentView(listview);
		 listview.setAdapter(adapter);
		 listview.setOnItemClickListener(new OnItemClickListener() {

			
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			
			Intent intent=new Intent();
			intent.setClass(ShowList.this, ViewDetial.class);
			intent.putExtra("itemid", itemlist.get(arg2).getId());
			ShowList.this.startActivity(intent);
			}
			 
		});
		 
		 this.setVisible(true);
		 
		

	 }
	 private List<String>  getListStringDataFromList(ArrayList<ItemValue> itemlist ){
		 List<String> data=new ArrayList<String>();
		 for(int i=0;i<itemlist.size();i++){
			 ItemValue item=itemlist.get(i);
			 String inf=item.getBook()+" 中  第"+item.getPage()+" 页 第 "+ item.getOrdernum()+" 题 ";
			 data.add(inf);	 
		 }
		return data;
		 
	 }
	/*
	private class ListViewHolder{
		 TextView txtv;
		 Button btn;
	 }
	 private class ListViewAdapter extends BaseAdapter{
		 private LayoutInflater inflater;
		 ArrayList<ItemValue> itemlist;
		 
		 public ListViewAdapter(Context context,ArrayList<ItemValue> itemlist){
			this.itemlist=itemlist;
			this.inflater=LayoutInflater.from(context);
		}
		
		public int getCount() {
			// TODO Auto-generated method stub
			return itemlist.size();
		}

		
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		
		public View getView(int position, View convertView, ViewGroup parent) {
			ListViewHolder holder = null;
			if (convertView == null) {
                holder=new ListViewHolder(); 
                convertView = inflater.inflate(R.layout.showlist, null);
                holder.btn=(Button)convertView.findViewById(R.id.showlist_btn);
                holder.txtv=(TextView)convertView.findViewById(R.id.showlist_txtv);
                convertView.setTag(holder);
            }else {
                holder = (ListViewHolder)convertView.getTag();
            }
			
            ItemValue item=itemlist.get(position);
            String iteminf=item.getSubject()+item.getBook()+item.getPage()+item.getOrdernum()+item.getDescription();
			holder.txtv.setText(iteminf);    	
			holder.btn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					System.out.println();
				}
				
			});
			return convertView;
		}
		 
	 }	*/
}
