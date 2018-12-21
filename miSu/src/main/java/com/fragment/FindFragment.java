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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.misu.DetailActivity;
import com.example.misu.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
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
import android.widget.TextView;
import android.widget.Toast;
import ip.Ip;

public class FindFragment extends Fragment {
	static MyRoomInfo adapter;
	CustomProgressDialog dialog1;
	List<RoomInfo> list;
	ProgressDialog pDialog;
	LocationManagerProxy aMapManager;
	static String str;
	static int position;
	private PullToRefreshGridView mPullRefreshgridView;
	TextView tx_location;
	int down = 2;
	GridView gridView;


	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.find, container, false);
		mPullRefreshgridView = (PullToRefreshGridView) view.findViewById(R.id.pull_refresh_grid);
		list = new ArrayList<RoomInfo>();

		getQuery();

		// 定位
		//initLocation();
		return view;

	}

	// 获取查询到的数据
	public void getQuery() {
		RoomInfo room = new RoomInfo();
		String image = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545324658555&di=1f94b9cc49bfb2ef81633510b9fd9650&imgtype=0&src=http%3A%2F%2Fs9.sinaimg.cn%2Fmw690%2F005AQSZZzy79Kv5DWMod8%26690";
		String name = "杭州西湖畔300米地铁300米欧式大床房";
        String price = "1500/每晚";
		String id = "1";
		room.setId(id);
		room.setImage(image);
		room.setPrice(price);
		room.setName(name);
		list.add(room);

		RoomInfo room1 = new RoomInfo();
		String image1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545324658555&di=b93c721f6444d25fe155823a1b655680&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F7af40ad162d9f2d3d4d389d5a2ec8a136227ccce.jpg";
		String name1 = "西湖音乐喷泉湖滨银泰温馨大床房";
        String price1 = "1000/每晚";
		String id1 = "2";
		room1.setId(id1);
		room1.setImage(image1);
		room1.setPrice(price1);
		room1.setName(name1);
		list.add(room1);

		RoomInfo room2 = new RoomInfo();
		String image2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545324658529&di=c74d81afbfb37b931c738e25af04cdf4&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F7c1ed21b0ef41bd57bd129cd5ada81cb39db3d0d.jpg";
		String name2 = "西湖林隐寺西溪北欧两居";
        String price2 = "800/每晚";
		String id2 = "3";
		room2.setId(id2);
		room2.setImage(image2);
		room2.setPrice(price2);
		room2.setName(name2);
		list.add(room2);

		RoomInfo room3 = new RoomInfo();
		String image3 = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=777294833,3487317045&fm=27&gp=0.jpg";
		String name3 = "购物中心ins风一居室";
        String price3 = "1200/每晚";
		String id3 = "4";
		room3.setId(id3);
		room3.setImage(image3);
		room3.setPrice(price3);
		room3.setName(name3);
		list.add(room3);

		RoomInfo room4 = new RoomInfo();
		String image4 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545324658531&di=11c559e2aedc3501372cafa9cf0fd5ea&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F86d6277f9e2f0708b1ec6b3ae224b899a901f21b.jpg";
		String name4 = "【武林广场】近西湖/市中心高端公寓 ";
        String price4 = "2000/每晚";
		String id4 = "5";
		room4.setId(id4);
		room4.setImage(image4);
		room4.setPrice(price4);
		room4.setName(name4);
		list.add(room4);

		RoomInfo room5 = new RoomInfo();
		String image5 = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1085040100,3343526315&fm=27&gp=0.jpg";
		String name5 = "河坊街 南宋御街【温馨亲子双床房】 ";
        String price5 = "600/每晚";
		String id5 = "6";
		room5.setId(id5);
		room5.setImage(image5);
		room5.setPrice(price5);
		room5.setName(name5);
		list.add(room5);

		adapter = new MyRoomInfo();
		mPullRefreshgridView.setAdapter(adapter);
		// 适配器刷新
		adapter.notifyDataSetChanged();
		mPullRefreshgridView.onRefreshComplete();

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
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.deviceview, null);
			}
			final TextView tx_name = (TextView) convertView.findViewById(R.id.tx_name);
			final TextView tx_id = (TextView) convertView.findViewById(R.id.tx_id);
			final TextView tx_price = (TextView) convertView.findViewById(R.id.tx_price);
			final FrameLayout layout = (FrameLayout) convertView.findViewById(R.id.frame_bg);
			tx_name.setText(list.get(position).getName());
			tx_price.setText(list.get(position).getPrice());
			tx_id.setText(list.get(position).getId());
			layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(getActivity(), DetailActivity.class);
					i.putExtra("id", tx_id.getText().toString());
					startActivity(i);
				}
			});
			Glide.with(getActivity()).load(list.get(position).getImage()).asBitmap().placeholder(R.drawable.ic_launcher)
					.into(new SimpleTarget<Bitmap>() {
						@Override
						public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
							Drawable drawable = new BitmapDrawable(bitmap);
							layout.setBackgroundDrawable(drawable);
						}
					});

			return convertView;
		}
	}
}
