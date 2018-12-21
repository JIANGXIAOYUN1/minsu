package com.example.misu;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bean.Conserve;
import com.bean.RoomInfo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fragment.MyOrderFragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import ip.Ip;

public class ConserveActivity extends Activity {

	static MyConserve adapter;
	private SharedPreferences sp;
	String username1;
	GridView grid;
	List<Conserve> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_conserve);
		sp = getSharedPreferences("userinfo", MODE_PRIVATE);
		username1 = sp.getString("user", "");
		grid = (GridView) findViewById(R.id.grid);
		list = new ArrayList<Conserve>();
		getVolley();
	}

	public void getVolley() {
		list = MyApplication.getContext().getConserve();
		adapter = new MyConserve();
		grid.setAdapter(adapter);
	}

	class MyConserve extends BaseAdapter {

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
				convertView = LayoutInflater.from(ConserveActivity.this).inflate(R.layout.conserveitem, null);
			}
			TextView tx_name = (TextView) convertView.findViewById(R.id.tx_name);
		 ImageView conservebg = (ImageView) convertView.findViewById(R.id.conservebg);
		 LinearLayout layout=(LinearLayout) convertView.findViewById(R.id.layout);
			Glide.with(ConserveActivity.this).load(list.get(position).getImage()).asBitmap().placeholder(R.drawable.ic_launcher).into(conservebg);
			tx_name.setText(list.get(position).getTitle());
		

			return convertView;
		}
	}
}
