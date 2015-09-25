package com.example.clientdemo.bean;

public class StationInfo {
	private int stationNo;
	private String stationName;
	/**
	 * 是否被选中的标记
	 * 0--未被选中
	 * 1--被选中
	 * 2--view_line的颜色变变绿
	 */
	private int selectFlag = 0;
	
	public StationInfo(int stationNo,String stationName) {
		this.stationNo = stationNo;
		this.stationName = stationName;
	}

	public int getStationNo() {
		return stationNo;
	}

	public void setStationNo(int stationNo) {
		this.stationNo = stationNo;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public int getSelectFlag() {
		return selectFlag;
	}

	public void setSelectFlag(int selectFlag) {
		this.selectFlag = selectFlag;
	}
	

}
