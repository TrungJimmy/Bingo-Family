package com.arun.rajora.chat.chit.bingo.family.bingofamily.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.arun.rajora.chat.chit.bingo.family.bingofamily.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class SummaryFragment extends Fragment {

	ExpenseAdapter adapter;
	String types[]={"Self","Food","Vehicle","Mobile Recharge","Education","Others","Purchases"};


	List<Entry> entries = new ArrayList<Entry>();
	List<Entry> remEntries = new ArrayList<Entry>();
	float xdata[]={0,1000,7899,2000,1000,0,5000,1000,500,600,200,1300,1414,41,114,214,222,2222};
	float ydata[]={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};
	float maxIncome=40000;

	public SummaryFragment() {
	}

	public static SummaryFragment newInstance() {
		SummaryFragment fragment = new SummaryFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.fragment_summary, container, false);
		LineChart chart = (LineChart) v.findViewById(R.id.chart);

		for(int i=0;i<xdata.length && i<ydata.length;i++){
			if(i>0)
				xdata[i]+=xdata[i-1];
			entries.add(new Entry(ydata[i],xdata[i]));
			remEntries.add(new Entry(ydata[i],maxIncome-xdata[i]));
		}

		((TextView) v.findViewById(R.id.summary_spent)).setText(String.valueOf(xdata[xdata.length-1])+" \u20B9");
		((TextView) v.findViewById(R.id.summary_left)).setText(String.valueOf(maxIncome-xdata[xdata.length-1])+" \u20B9");

		LineDataSet dataSet = new LineDataSet(entries, "Spending");
		LineDataSet remDataSet = new LineDataSet(remEntries, "Remaining");

		dataSet.setDrawCircles(false);
		dataSet.setDrawValues(false);
		remDataSet.setDrawCircles(false);
		remDataSet.setDrawValues(false);

		dataSet.setColor(getResources().getColor(android.R.color.holo_green_dark));
		dataSet.setLineWidth(4);
		remDataSet.setColor(getResources().getColor(android.R.color.holo_red_dark));

		List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
		dataSets.add(dataSet);
		dataSets.add(remDataSet);

		LineData data = new LineData(dataSets);
		chart.setData(data);

		chart.setDrawGridBackground(false);
		chart.getAxisRight().setEnabled(false);
		chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
		chart.getXAxis().setAxisMaximum(31.0f);
		chart.invalidate();

		GridView gridview = (GridView) v.findViewById(R.id.gridview_summary);
		adapter=new ExpenseAdapter(getContext());
		gridview.setAdapter(adapter);

		adapter.addItem("Purchases: 8665\u20B9");
		adapter.addItem("Others: 6682\u20B9");
		adapter.addItem("Education: 7775\u20B9");;

		return v;
	}

	public class ExpenseAdapter extends BaseAdapter {
		private Context mContext;

		final List<String> notifyList = new ArrayList<String>();


		public ExpenseAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return notifyList.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View item;
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (convertView == null) {
				item = inflater.inflate(R.layout.vh_summary,null);
			} else {
				item = convertView;
			}

			((TextView) item.findViewById(R.id.summary_text)).setText(notifyList.get(position));
			return item;
		}

		public void addItem(String str){
			notifyList.add(str);
			notifyDataSetChanged();
		}

	}

}
