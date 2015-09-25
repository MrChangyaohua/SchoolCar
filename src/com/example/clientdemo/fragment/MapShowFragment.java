package com.example.clientdemo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.example.clientdemo.Model;
import com.example.clientdemo.R;

public class MapShowFragment extends Fragment {
	private AMap aMap;
	private MapView mapView;
	private Marker mGPSMarker;
	private UiSettings  uiSettings;
	private ArrayList<BitmapDescriptor> giflist;
	private Polyline polyline;
	
	private Marker marker1;
	private Marker marker2;
	private Marker marker3;
	private Marker marker4;
	private Marker marker5;
	private Marker marker6;
	private Marker marker7;
	
	private Handler handler;
	private Runnable runnable;
	
	private View view;
	
	private static final LatLng ONE = new LatLng(38.0143004487611845,112.45582768843197);
	private static final LatLng TEMP1 = new LatLng(38.01425552781914,112.4545531489705);
	private static final LatLng TWO = new LatLng(38.01556027982925,112.4544496388025);
	private static final LatLng THREE = new LatLng(38.01539498774411,112.45000964587852);
	private static final LatLng TEMP2 = new LatLng(38.016943886093784,112.44990964587852);
	private static final LatLng FOUR = new LatLng(38.01682156955341,112.44621322359004);
	private static final LatLng FIVE = new LatLng(38.01518249093445,112.44621322359004);
	private static final LatLng TEMP3 = new LatLng(38.01303480789175,112.4457897234504);
	private static final LatLng SIX = new LatLng(38.01262826009574,112.44356875082703);
	private static final LatLng TEMP4 = new LatLng(38.009768664695216,112.44379796955457);
	private static final LatLng TEMP5 = new LatLng(38.00971031039748,112.44269299210835);
	private static final LatLng SEVEN = new LatLng(38.0086102273198,112.44289429105383);
	
	private static final String BOARD_ONE = "始发站:七道门";
	private static final String BOARD_TWO = "第二站:学子超市";
	private static final String BOARD_THREE = "第三站:主楼东";
	private static final String BOARD_FOUR = "第四站:校医院";
	private static final String BOARD_FIVE = "第五站:生活服务大楼";
	private static final String BOARD_SIX = "第六站:二道门";
	private static final String BOARD_SEVEN = "终点站:一道门";
	
	private static final int PRETIME = 6000;
	
	private static final int RADIUS = 50;
	
	private int flag;
	private int frontStation;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_map, container, false);
		
		this.mapView = (MapView) view.findViewById(R.id.map);
		this.mapView.onCreate(savedInstanceState);// 此方法必须重写
		
		init();
		
		Bmob.initialize(getActivity(), "9f24e0ab1056f46b9b4fd6b7e530124c");
		
		query();
		
		this.handler=new Handler();  
		this.runnable=new Runnable() {  
		    @Override  
		    public void run() {  
		        // TODO Auto-generated method stub  
		        //要做的事情  
		    	
		    	query();
		    	
		        handler.postDelayed(this, PRETIME);  
		    } 
		};
		handler.postDelayed(runnable, PRETIME);//每两秒执行一次runnable. 
		
		
		
		return view;
	}

	/**
	 * 初始化地图
	 */
	private void init() {
		
		if (aMap == null) {
			aMap = mapView.getMap();
			
			//更改可视区域
			//设置当期地图显示为中北大学
            aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(38.01492392538442,112.44981391049477), 16, 10, 0)));
          
            //设置定点位置的标记
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.bus));
            mGPSMarker = aMap.addMarker(markerOptions);
            
            PolylineOptions polylineOptions = new PolylineOptions();
			polylineOptions.add(ONE,TEMP1,TWO,THREE,TEMP2,FOUR,FIVE,TEMP3,SIX,TEMP4,TEMP5,SEVEN);
			polylineOptions.color(Color.GREEN);
			polylineOptions.setDottedLine(true);
			polylineOptions.width(10);   
	        aMap.addPolyline(polylineOptions);
	        
	        //用于显示公交正在行驶的路段
	        PolylineOptions polylineTemp = new PolylineOptions();
	        polylineTemp.color(Color.rgb(77,180,237));
	        polylineTemp.width(10);   
	        this.polyline = aMap.addPolyline(polylineTemp);
            
          //设置定点位置为中心的圆圈代表地理围栏
	        CircleOptions circleOptions1 = new CircleOptions();
	        circleOptions1.center(ONE).radius(RADIUS)
	        .fillColor(Color.argb(100, 224, 171, 10))
	        . strokeColor(Color.argb(0, 0, 0, 0));
	        aMap.addCircle(circleOptions1);
	        
	        CircleOptions circleOptions2 = new CircleOptions();
	        circleOptions2.center(TWO).radius(RADIUS)
	               .fillColor(Color.argb(100, 224, 171, 10))
	                . strokeColor(Color.argb(0, 0, 0, 0));
	        aMap.addCircle(circleOptions2);
	        
	        CircleOptions circleOptions3 = new CircleOptions();
	        circleOptions3.center(THREE).radius(RADIUS)
	        .fillColor(Color.argb(100, 224, 171, 10))
	                . strokeColor(Color.argb(0, 0, 0, 0));
	        aMap.addCircle(circleOptions3);
	        
	        
	        CircleOptions circleOptions4 = new CircleOptions();
	        circleOptions4.center(FOUR).radius(RADIUS)
	        .fillColor(Color.argb(100, 224, 171, 10))
	        . strokeColor(Color.argb(0, 0, 0, 0));
	        aMap.addCircle(circleOptions4);
	        
	        CircleOptions circleOptions5 = new CircleOptions();
	        circleOptions5.center(FIVE).radius(RADIUS)
	        .fillColor(Color.argb(100, 224, 171, 10))
	        . strokeColor(Color.argb(0, 0, 0, 0));
	        aMap.addCircle(circleOptions5);
	        
	        CircleOptions circleOptions6 = new CircleOptions();
	        circleOptions6.center(SIX).radius(RADIUS)
	        .fillColor(Color.argb(100, 224, 171, 10))
	        . strokeColor(Color.argb(0, 0, 0, 0));
	        aMap.addCircle(circleOptions6);
	        
	        CircleOptions circleOptions7 = new CircleOptions();
	        circleOptions7.center(SEVEN).radius(RADIUS)
	        .fillColor(Color.argb(100, 224, 171, 10))
	        . strokeColor(Color.argb(0, 0, 0, 0));
	        aMap.addCircle(circleOptions7);
	        
	        giflist = new ArrayList<BitmapDescriptor>();
			giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point1));
			giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point2));
			giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point3));
			giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point4));
			giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point5));
			giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point6));
	        
	        uiSettings = aMap.getUiSettings() ;
	        uiSettings.setCompassEnabled(true);
	        
	        setMarker();
	        
	        
		}
	}
	
	private void setMarker() {
		// TODO Auto-generated method stub
        
		marker1 = aMap.addMarker(new MarkerOptions()
		.title(BOARD_ONE)
		.position(ONE)
		.icon(BitmapDescriptorFactory
				.fromResource(R.drawable.start)));
		
		marker2 = aMap.addMarker(new MarkerOptions()
		.title(BOARD_TWO)
		.position(TWO)
		.icon(BitmapDescriptorFactory
				.fromResource(R.drawable.point3)));
		
		marker3 = aMap.addMarker(new MarkerOptions()
			.title(BOARD_THREE)
			.position(THREE)
			.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.point3)));
	        
	    marker4 = aMap.addMarker(new MarkerOptions()
	        .title(BOARD_FOUR)
	        .position(FOUR)
	        .icon(BitmapDescriptorFactory
	        		.fromResource(R.drawable.point3)));
	    
	    marker5 = aMap.addMarker(new MarkerOptions()
	    .title(BOARD_FIVE)
	    .position(FIVE)
	    .icon(BitmapDescriptorFactory
	    		.fromResource(R.drawable.point3)));
	    
	    marker6 = aMap.addMarker(new MarkerOptions()
	    .title(BOARD_SIX)
	    .position(SIX)
	    .icon(BitmapDescriptorFactory
	    		.fromResource(R.drawable.point3)));
	    
	    marker7 = aMap.addMarker(new MarkerOptions()
	    .title(BOARD_SEVEN)
	    .position(SEVEN)
	    .icon(BitmapDescriptorFactory
	    		.fromResource(R.drawable.end)));
		
	}
	
	private void selectMarker(int i){
		switch(i){
		 case 1:{
			 marker1.setIcons(giflist);
			 marker1.setPeriod(50);
			 break;
		 }
		 case 2:{
			 marker2.setIcons(giflist);
			 marker2.setPeriod(50);
			 break;
		 }
		 case 3:{
			 marker3.setIcons(giflist);
			 marker3.setPeriod(50);
			 break;
		 }
		 case 4:{
			 marker4.setIcons(giflist);
			 marker4.setPeriod(50);
			 break;
		 }
		 case 5:{
			 marker5.setIcons(giflist);
			 marker5.setPeriod(50);
			 break;
		 }
		 case 6:{
			 marker6.setIcons(giflist);
			 marker6.setPeriod(50);
			 break;
		 }
		 case 7:{
			 marker7.setIcons(giflist);
			 marker7.setPeriod(50);
			 break;
		 }
	 }
	}
	
	private void resetMarker(){
		marker1.remove();
		marker2.remove();
		marker3.remove();
		marker4.remove();
		marker5.remove();
		marker6.remove();
		marker7.remove();
	 
		setMarker();
	}

	public void query(){
		BmobQuery<Model> query = new BmobQuery<Model>();
		query.getObject(getActivity(), "3b77fb8d08", new GetListener<Model>() {

		    @Override
		    public void onSuccess(Model object) {
		        // TODO Auto-generated method stub
		    	 System.out.println("成功获取数据");
		    	 updateLocation(object.getLocation());
		    	 if(object.getFlag() == 1 && flag == 0){
		    		 resetMarker();
		    		 frontStation = object.getFrontStation();
		    		 List<LatLng> pointlist = new ArrayList<LatLng>();
		    		 switch(object.getCount()){
			    		 case 1:{
			    			 Toast.makeText(getActivity(), "进入" + BOARD_ONE, Toast.LENGTH_SHORT).show();
			    			 flag = 1;
			    			 selectMarker(1);
			    			 break;
			    		 }
			    		 case 2:{
			    			 Toast.makeText(getActivity(), "进入" + BOARD_TWO, Toast.LENGTH_LONG).show();
			    			 flag = 1;
			    			 if(frontStation == 1){
			    				 pointlist.add(TWO);
			    				 pointlist.add(THREE);
			    				 MapShowFragment.this.polyline.setPoints(pointlist);
			    				 selectMarker(3);
			    			 }else if(frontStation == 3){
			    				 pointlist.add(ONE);
			    				 pointlist.add(TEMP1);
			    				 pointlist.add(TWO);
			    				 selectMarker(1);
			    				 MapShowFragment.this.polyline.setPoints(pointlist);
			    			 }else{
			    				 selectMarker(2);
			    			 }
			    			 break;
			    		 }
			    		 case 3:{
			    			 Toast.makeText(getActivity(), "进入" + BOARD_THREE, Toast.LENGTH_LONG).show();
			    			 flag = 1;
			    			 if(frontStation == 2){
			    				 pointlist.add(THREE);
			    				 pointlist.add(TEMP2);
			    				 pointlist.add(FOUR);
			    				 MapShowFragment.this.polyline.setPoints(pointlist);
			    				 selectMarker(4);
			    			 }else if(frontStation == 4){
			    				 pointlist.add(TWO);
			    				 pointlist.add(THREE);
			    				 MapShowFragment.this.polyline.setPoints(pointlist);
			    				 selectMarker(2);
			    			 }else{
			    				 selectMarker(3);
			    			 }
			    			 break;
			    		 }
			    		 case 4:{
			    			 Toast.makeText(getActivity(), "进入" + BOARD_FOUR, Toast.LENGTH_SHORT).show();
			    			 flag = 1;
			    			 if(frontStation == 3){
			    				 pointlist.add(FOUR);
			    				 pointlist.add(FIVE);
			    				 MapShowFragment.this.polyline.setPoints(pointlist);
			    				 selectMarker(5);
			    			 }else if(frontStation == 5){
			    				 pointlist.add(FOUR);
			    				 pointlist.add(TEMP2);
			    				 pointlist.add(THREE);
			    				 MapShowFragment.this.polyline.setPoints(pointlist);
			    				 selectMarker(3);
			    			 }else{
			    				 selectMarker(4);
			    			 }
			    			 break;
			    		 }
			    		 case 5:{
			    			 Toast.makeText(getActivity(), "进入" + BOARD_FIVE, Toast.LENGTH_LONG).show();
			    			 flag = 1;
			    			 if(frontStation == 4){
			    				 pointlist.add(FIVE);
			    				 pointlist.add(TEMP3);
			    				 pointlist.add(SIX);
			    				 MapShowFragment.this.polyline.setPoints(pointlist);
			    				 selectMarker(6);
			    			 }else if(frontStation == 6){
			    				 pointlist.add(FIVE);
			    				 pointlist.add(FOUR);
			    				 MapShowFragment.this.polyline.setPoints(pointlist);
			    				 selectMarker(4);
			    			 }else{
			    				 selectMarker(5);
			    			 }
			    			 break;
			    		 }
			    		 case 6:{
			    			 Toast.makeText(getActivity(), "进入" + BOARD_SIX, Toast.LENGTH_LONG).show();
			    			 flag = 1;
			    			 if(frontStation == 5){
			    				 pointlist.add(SIX);
			    				 pointlist.add(TEMP4);
			    				 pointlist.add(TEMP5);
			    				 pointlist.add(SEVEN);
			    				 MapShowFragment.this.polyline.setPoints(pointlist);
			    				 selectMarker(7);
			    			 }else if(frontStation == 7){
			    				 pointlist.add(SIX);
			    				 pointlist.add(FIVE);
			    				 MapShowFragment.this.polyline.setPoints(pointlist);
			    				 selectMarker(5);
			    			 }else{
			    				 selectMarker(6);
			    			 }
			    			 break;
			    		 }
			    		 case 7:{
			    			 Toast.makeText(getActivity(), "进入" + BOARD_SEVEN, Toast.LENGTH_LONG).show();
			    			 flag = 1;
			    			 selectMarker(7);
			    			 break;
			    		 }
		    		 }
		    	 }else if(object.getFlag() == 0 && flag == 1){
		    		 
		    		 switch(object.getCount()){
		    		 case 1:{
		    			 Toast.makeText(getActivity(), "离开" + BOARD_ONE, Toast.LENGTH_LONG).show();
		    			 flag = 0;
		    			 break;
		    		 }
		    		 case 2:{
		    			 Toast.makeText(getActivity(), "离开" + BOARD_TWO, Toast.LENGTH_LONG).show();
		    			 flag = 0;
		    			 break;
		    		 }
		    		 case 3:{
		    			 Toast.makeText(getActivity(), "离开" + BOARD_THREE, Toast.LENGTH_LONG).show();
		    			 flag = 0;
		    			 break;
		    		 }
		    		 case 4:{
		    			 Toast.makeText(getActivity(), "离开" + BOARD_FOUR, Toast.LENGTH_LONG).show();
		    			 flag = 0;
		    			 break;
		    		 }
		    		 case 5:{
		    			 Toast.makeText(getActivity(), "离开" + BOARD_FIVE, Toast.LENGTH_LONG).show();
		    			 flag = 0;
		    			 break;
		    		 }
		    		 case 6:{
		    			 Toast.makeText(getActivity(), "离开" + BOARD_SIX, Toast.LENGTH_LONG).show();
		    			 flag = 0;
		    			 break;
		    		 }
		    		 case 7:{
		    			 Toast.makeText(getActivity(), "离开" + BOARD_SEVEN, Toast.LENGTH_LONG).show();
		    			 flag = 0;
		    			 break;
		    		 }
	    		 }		 
		     }
		   }

		    @Override
		    public void onFailure(int code, String arg0) {
		        // TODO Auto-generated method stub
		    	System.out.println("失败获取数据");
		    }

		});
	}   
	
	/*
     * 根据新的经纬度更新GPS位置和设置地图中心
     */
    private void updateLocation(LatLng location) {
        if (mGPSMarker != null) {
            mGPSMarker.setPosition(location);
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(location)) ;
        }
 
    }
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MapShowFragment.this.mapView.onResume();
		handler.postDelayed(runnable, PRETIME);//每两秒执行一次runnable. 
		
		flag = 0;
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MapShowFragment.this.mapView.onPause();
		handler.removeCallbacks(runnable);
//		MainActivity.this.mapView.onDestroy();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MapShowFragment.this.mapView.onDestroy();
		handler.removeCallbacks(runnable);
	}

}

	


