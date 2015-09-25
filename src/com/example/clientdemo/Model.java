package com.example.clientdemo;

import com.amap.api.maps.model.LatLng;

import cn.bmob.v3.BmobObject;

public class Model extends BmobObject{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private LatLng location;
	private int flag;
	private int count;
	private int frontStation;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LatLng getLocation() {
		return location;
	}
	public void setLocation(LatLng location) {
		this.location = location;
	}    
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getFrontStation() {
		return frontStation;
	}
	public void setFrontStation(int frontStation) {
		this.frontStation = frontStation;
	}    

}
