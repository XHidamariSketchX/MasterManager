package com.cornu.tools;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
public class MyAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	List<Map<String ,Object>> data;
	class ViewHolder{
		
	}
	public MyAdapter(Context context,List<Map<String ,Object>> data){
		this.mInflater=LayoutInflater.from(context);
		this.data=data;
	}
	 
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
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
		
		return null;
	}

}
