package com.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bean.RoomInfo;
import com.bean.Tourist;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.misu.DetailActivity;
import com.example.misu.R;
import com.example.misu.TouristDetailActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.style.CustomProgressDialog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import ip.Ip;

public class TouristFragment extends Fragment {
	static MyRoomInfo adapter;
	CustomProgressDialog dialog1;
	List<Tourist> list;
	ProgressDialog pDialog;
	LocationManagerProxy aMapManager;
	static String str;
	static int position;
	private PullToRefreshGridView mPullRefreshgridView;
	TextView tx_location;
	int down = 2;
	GridView gridView;
	Button btn_scan;
	EditText ed_area;
	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.tourist, container, false);
		list = new ArrayList<Tourist>();
		mPullRefreshgridView = (PullToRefreshGridView) view.findViewById(R.id.pull_refresh_grid);
		mPullRefreshgridView.setMode(Mode.DISABLED);//都不允许下拉
		mPullRefreshgridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				 TextView tx_id = (TextView) view.findViewById(R.id.tx_id);
				 Intent i = new Intent(getActivity(), DetailActivity.class);
				 i.putExtra("id", tx_id.getText().toString());
				 startActivity(i);
			}
		});

		Tourist room = new Tourist();
		String id ="1";
		String image = "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E6%9D%AD%E5%B7%9E%E8%A5%BF%E6%B9%96&step_word=&hs=0&pn=1&spn=0&di=183280&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=730849348%2C2537973125&os=4005772174%2C1809899022&simid=2004935712%2C965143680&adpicid=0&lpn=0&ln=1908&fr=&fmq=1545400857986_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=13&oriquery=&objurl=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fa50f4bfbfbedab646d5903fafc36afc379311ee4.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bi7tp7_z%26e3Bv54AzdH3Fri5p5AzdH3Ffi5oAzdH3Fda80amddAzdH3F80n9d9clmaca_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0&islist=&querylist=";
		String title ="杭州西湖";
		String detail = "西湖，位于浙江省杭州市西面，是中国大陆首批国家重点风景名胜区和中国十大风景名胜之一。它是中国大陆主要的观赏性淡水湖泊之一，也是现今《世界遗产名录》中少数几个和中国唯一一个湖泊类文化遗产。";

		room.setImages(image);
		room.setTitle(title);
		room.setDetails(detail);
		room.setId(id);
		list.add(room);

		
		return view;

	}

	class MyRoomInfo extends BaseAdapter {

		@Override
		public int getCount() {

			return list.size();

		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.touristitem, null);
			}
			LinearLayout layout=(LinearLayout) convertView.findViewById(R.id.layoutclick);
			TextView tx_title = (TextView) convertView.findViewById(R.id.tx_title);
			TextView tx_id = (TextView) convertView.findViewById(R.id.tx_id);
			ImageView iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
			tx_title.setText(list.get(position).getTitle());
			tx_id.setText(list.get(position).getId());
			
			Glide.with(getActivity()).load(list.get(position).getImages()).asBitmap().placeholder(R.drawable.ic_launcher)
					.into(iv_icon);
			layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i=new Intent(getActivity(),TouristDetailActivity.class);
					i.putExtra("details", list.get(position).getDetails());
					startActivity(i);
				}
			});
			return convertView;
		}
	}
}
