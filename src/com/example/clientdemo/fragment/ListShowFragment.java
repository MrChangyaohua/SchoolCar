package com.example.clientdemo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

import com.example.clientdemo.Model;
import com.example.clientdemo.R;
import com.example.clientdemo.adapter.GridViewAdapter;
import com.example.clientdemo.bean.StationInfo;

public class ListShowFragment extends Fragment {
	private View view;
	private GridView lv_show;
	private GridViewAdapter gridViewAdapter;
	private List<StationInfo> stationInfoList;

	private TextView tv_prompt;
	private TextView tv_first_station;
	private ImageView iv_arrow;
	private TextView tv_next_station;

	private Handler handler;
	private Runnable runnable;

	private int flag = 0;
	private int frontStation = 0;
	private static final int PRETIME = 6000;

	private static final String BOARD_ONE = "�ߵ���";
	private static final String BOARD_TWO = "ѧ�ӳ���";
	private static final String BOARD_THREE = "��¥��";
	private static final String BOARD_FOUR = "УҽԺ";
	private static final String BOARD_FIVE = "��������¥";
	private static final String BOARD_SIX = "������";
	private static final String BOARD_SEVEN = "һ����";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		stationInfoList = new ArrayList<StationInfo>();
		stationInfoList.add(new StationInfo(1, "�ߵ���"));
		stationInfoList.add(new StationInfo(2, "ѧ�ӳ���"));
		stationInfoList.add(new StationInfo(3, "��¥��"));
		stationInfoList.add(new StationInfo(4, "УҽԺ"));
		stationInfoList.add(new StationInfo(5, "��������¥"));
		stationInfoList.add(new StationInfo(6, "������"));
		stationInfoList.add(new StationInfo(7, "һ����"));

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_list, container, false);

		tv_prompt = (TextView) view.findViewById(R.id.tv_prompt);
		tv_first_station = (TextView) view.findViewById(R.id.tv_first_station);
		iv_arrow = (ImageView) view.findViewById(R.id.iv_arrow);
		tv_next_station = (TextView) view.findViewById(R.id.tv_next_station);

		lv_show = (GridView) view.findViewById(R.id.lv_show);
		gridViewAdapter = new GridViewAdapter(getActivity(), stationInfoList);
		lv_show.setAdapter(gridViewAdapter);

		Bmob.initialize(getActivity(), "9f24e0ab1056f46b9b4fd6b7e530124c");

		query();

		this.handler = new Handler();
		this.runnable = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// Ҫ��������

				query();

				handler.postDelayed(this, PRETIME);
			}
		};
		handler.postDelayed(runnable, PRETIME);// ÿ����ִ��һ��runnable.

		return view;
	}

	//���õ�ǰ����������վ��λ��
	private void selectMarker(int i) {
		for (StationInfo stationInfo : stationInfoList) {
			stationInfo.setSelectFlag(0);
		}
		stationInfoList.get(i - 1).setSelectFlag(1);
		gridViewAdapter.notifyDataSetChanged();
	}

	//���ù�����ʻ·�ε���ɫ
	private void setViewLineColor(int i) {

		stationInfoList.get(i - 1).setSelectFlag(2);
		gridViewAdapter.notifyDataSetChanged();
	}

	private void query() {
		BmobQuery<Model> query = new BmobQuery<Model>();
		query.getObject(getActivity(), "3b77fb8d08", new GetListener<Model>() {

			@Override
			public void onSuccess(Model object) {
				// TODO Auto-generated method stub
				System.out.println("�ɹ���ȡ����");
				if (object.getFlag() == 1 && flag == 0) {
					frontStation = object.getFrontStation();
					switch (object.getCount()) {
					case 1: {
						tv_prompt.setText("����" + BOARD_ONE);
						flag = 1;
						selectMarker(1);

						tv_first_station.setText("��ǰվλ��" + BOARD_ONE);
						tv_next_station.setVisibility(View.GONE);
						iv_arrow.setVisibility(View.GONE);

						break;
					}
					case 2: {
						tv_prompt.setText("����" + BOARD_TWO);
						flag = 1;
						selectMarker(2);
						if (frontStation == 1) {
							setViewLineColor(2);
							
							tv_next_station.setVisibility(View.VISIBLE);
							iv_arrow.setVisibility(View.VISIBLE);
							tv_first_station.setText(BOARD_THREE);
							iv_arrow.setImageResource(R.drawable.arrow_small_left);
							tv_next_station.setText(BOARD_TWO);
						} else if (frontStation == 3) {
							setViewLineColor(1);
							
							tv_next_station.setVisibility(View.VISIBLE);
							iv_arrow.setVisibility(View.VISIBLE);
							tv_first_station.setText(BOARD_TWO);
							iv_arrow.setImageResource(R.drawable.arrow_small_right);
							tv_next_station.setText(BOARD_ONE);
						} else {
							tv_first_station.setText("��ǰվλ��" + BOARD_TWO);
							tv_next_station.setVisibility(View.GONE);
							iv_arrow.setVisibility(View.GONE);
						}
						break;
					}
					case 3: {
						tv_prompt.setText("����" + BOARD_THREE);
						flag = 1;
						selectMarker(3);
						if (frontStation == 2) {
							setViewLineColor(3);
							
							tv_next_station.setVisibility(View.VISIBLE);
							iv_arrow.setVisibility(View.VISIBLE);
							tv_first_station.setText(BOARD_FOUR);
							iv_arrow.setImageResource(R.drawable.arrow_small_left);
							tv_next_station.setText(BOARD_THREE);
						} else if (frontStation == 4) {
							setViewLineColor(2);
							
							tv_next_station.setVisibility(View.VISIBLE);
							iv_arrow.setVisibility(View.VISIBLE);
							tv_first_station.setText(BOARD_THREE);
							iv_arrow.setImageResource(R.drawable.arrow_small_right);
							tv_next_station.setText(BOARD_TWO);
						} else {
							tv_first_station.setText("��ǰվλ��" + BOARD_THREE);
							tv_next_station.setVisibility(View.GONE);
							iv_arrow.setVisibility(View.GONE);
						}
						break;
					}
					case 4: {
						tv_prompt.setText("����" + BOARD_FOUR);
						flag = 1;
						selectMarker(4);
						if (frontStation == 3) {
							setViewLineColor(4);
							
							tv_next_station.setVisibility(View.VISIBLE);
							iv_arrow.setVisibility(View.VISIBLE);
							tv_first_station.setText(BOARD_FIVE);
							iv_arrow.setImageResource(R.drawable.arrow_small_left);
							tv_next_station.setText(BOARD_FOUR);
						} else if (frontStation == 5) {
							setViewLineColor(3);
							
							tv_next_station.setVisibility(View.VISIBLE);
							iv_arrow.setVisibility(View.VISIBLE);
							tv_first_station.setText(BOARD_FOUR);
							iv_arrow.setImageResource(R.drawable.arrow_small_right);
							tv_next_station.setText(BOARD_THREE);
						} else {
							tv_first_station.setText("��ǰվλ��" + BOARD_FOUR);
							tv_next_station.setVisibility(View.GONE);
							iv_arrow.setVisibility(View.GONE);
						}
						break;
					}
					case 5: {
						tv_prompt.setText("����" + BOARD_FIVE);
						flag = 1;
						selectMarker(5);
						if (frontStation == 4) {
							setViewLineColor(5);
							
							tv_next_station.setVisibility(View.VISIBLE);
							iv_arrow.setVisibility(View.VISIBLE);
							tv_first_station.setText(BOARD_SIX);
							iv_arrow.setImageResource(R.drawable.arrow_small_left);
							tv_next_station.setText(BOARD_FIVE);
						} else if (frontStation == 6) {
							setViewLineColor(4);
							
							tv_next_station.setVisibility(View.VISIBLE);
							iv_arrow.setVisibility(View.VISIBLE);
							tv_first_station.setText(BOARD_FIVE);
							iv_arrow.setImageResource(R.drawable.arrow_small_right);
							tv_next_station.setText(BOARD_FOUR);
						} else {
							tv_first_station.setText("��ǰվλ��" + BOARD_FOUR);
							tv_next_station.setVisibility(View.GONE);
							iv_arrow.setVisibility(View.GONE);
						}
						break;
					}
					case 6: {
						tv_prompt.setText("����" + BOARD_SIX);
						flag = 1;
						selectMarker(6);
						if (frontStation == 5) {
							setViewLineColor(6);
							
							tv_next_station.setVisibility(View.VISIBLE);
							iv_arrow.setVisibility(View.VISIBLE);
							tv_first_station.setText(BOARD_SEVEN);
							iv_arrow.setImageResource(R.drawable.arrow_small_left);
							tv_next_station.setText(BOARD_SIX);
						} else if (frontStation == 7) {
							setViewLineColor(5);
							
							tv_next_station.setVisibility(View.VISIBLE);
							iv_arrow.setVisibility(View.VISIBLE);
							tv_first_station.setText(BOARD_SIX);
							iv_arrow.setImageResource(R.drawable.arrow_small_right);
							tv_next_station.setText(BOARD_FIVE);
						} else {
							tv_first_station.setText("��ǰվλ��" + BOARD_SIX);
							tv_next_station.setVisibility(View.GONE);
							iv_arrow.setVisibility(View.GONE);
						}
						break;
					}
					case 7: {
						tv_prompt.setText("����" + BOARD_SEVEN);
						flag = 1;
						selectMarker(7);

						tv_first_station.setText("��ǰվλ��" + BOARD_SEVEN);
						tv_next_station.setVisibility(View.GONE);
						iv_arrow.setVisibility(View.GONE);
						break;
					}
					}
				} else if (object.getFlag() == 0 && flag == 1) {

					switch (object.getCount()) {
					case 1: {
						tv_prompt.setText("�뿪" + BOARD_ONE);
						break;
					}
					case 2: {
						tv_prompt.setText("�뿪" + BOARD_TWO);
						flag = 0;
						break;
					}
					case 3: {
						tv_prompt.setText("�뿪" + BOARD_THREE);
						flag = 0;
						break;
					}
					case 4: {
						tv_prompt.setText("�뿪" + BOARD_FOUR);
						flag = 0;
						break;
					}
					case 5: {
						tv_prompt.setText("�뿪" + BOARD_FIVE);
						flag = 0;
						break;
					}
					case 6: {
						tv_prompt.setText("�뿪" + BOARD_SIX);
						flag = 0;
						break;
					}
					case 7: {
						tv_prompt.setText("�뿪" + BOARD_SEVEN);
						flag = 0;
						break;
					}
					}
				}
			}

			@Override
			public void onFailure(int code, String arg0) {
				// TODO Auto-generated method stub
				System.out.println("ʧ�ܻ�ȡ����");
			}

		});

	}
}
