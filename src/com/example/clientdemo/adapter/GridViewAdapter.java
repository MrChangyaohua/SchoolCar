package com.example.clientdemo.adapter;

import java.util.List;
import java.util.zip.Inflater;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clientdemo.R;
import com.example.clientdemo.bean.StationInfo;

public class GridViewAdapter extends BaseAdapter {
	private Context context;
	private List<StationInfo> stationInfoList;
	public GridViewAdapter(Context context,List<StationInfo> stationInfoList) {
		this.context = context;
		this.stationInfoList = stationInfoList;
	}

	@Override
	public int getCount() {
		return stationInfoList.size();
	}
 
	@Override
	public Object getItem(int position) {
		return stationInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		StationInfo stationInfo = stationInfoList.get(position);
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview, null);
			viewHolder.tv_station_no = (TextView) convertView.findViewById(R.id.tv_station_no);
			viewHolder.tv_station_name = (TextView) convertView.findViewById(R.id.tv_station_name);
			viewHolder.view_line = convertView.findViewById(R.id.view_line);
			viewHolder.iv_marker = (ImageView)convertView.findViewById(R.id.iv_marker);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_station_no.setText("" + stationInfo.getStationNo());
		viewHolder.tv_station_name.setText(stationInfo.getStationName());
		if (position ==  stationInfoList.size()-1) {
			viewHolder.view_line.setVisibility(View.GONE);
		}
		if (stationInfo.getSelectFlag() == 0) {
			viewHolder.iv_marker.setVisibility(View.INVISIBLE);
			viewHolder.tv_station_no.setTextColor(Color.parseColor("#000000"));
			viewHolder.tv_station_name.setTextColor(Color.parseColor("#000000"));
			viewHolder.view_line.setBackgroundColor(Color.parseColor("#0000FF"));
		} else if (stationInfo.getSelectFlag() == 1) {
			viewHolder.iv_marker.setVisibility(View.VISIBLE);
			viewHolder.tv_station_no.setTextColor(Color.parseColor("#00FF00"));
			viewHolder.tv_station_name.setTextColor(Color.parseColor("#00FF00"));
			viewHolder.view_line.setBackgroundColor(Color.parseColor("#0000FF"));
		}
		if (stationInfo.getSelectFlag() == 2) {
			viewHolder.view_line.setBackgroundColor(Color.parseColor("#00FF00"));
		}
		return convertView;
	}
	
	class ViewHolder{
		private TextView tv_station_no;
		private TextView tv_station_name;
		private View view_line;
		private ImageView iv_marker;
	}

}
