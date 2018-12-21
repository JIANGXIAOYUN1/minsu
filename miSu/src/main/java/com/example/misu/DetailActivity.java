package com.example.misu;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
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
import com.bean.MyOrder;
import com.bean.RoomInfo;
import com.bumptech.glide.Glide;
import com.example.misu.RegisterActivity.GetThread;
import com.style.CustomProgressDialog;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ip.Ip;

public class DetailActivity extends Activity {
	String id;
	String price;
	String name;
	TextView tvHotelName, tv_address, tvHotelPrice, tvHotelType, tvHotelSize, iv_logo;
	Button btnHotelOrd;
	ImageView imagein;
	Button conserve;
	CustomProgressDialog dialog1;
	private SharedPreferences sp;
	String username;
	MyOrder order = new MyOrder();
	Conserve myConserve = new Conserve();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detail);
		sp = getSharedPreferences("userinfo", MODE_PRIVATE);
		username = sp.getString("user", "");
		dialog1 = new CustomProgressDialog(DetailActivity.this);
		tvHotelName = (TextView) findViewById(R.id.tvHotelName);
		tv_address = (TextView) findViewById(R.id.address);
		btnHotelOrd=(Button) findViewById(R.id.btnHotelOrd);
		tvHotelPrice = (TextView) findViewById(R.id.tvHotelPrice);
		tvHotelType = (TextView) findViewById(R.id.tvHotelType);
		tvHotelSize = (TextView) findViewById(R.id.tvHotelSize);
		imagein = (ImageView) findViewById(R.id.image);
		conserve = (Button) findViewById(R.id.conserve);
		Intent i = getIntent();
		id = i.getStringExtra("id");
		price = i.getStringExtra("price");
		name = i.getStringExtra("name");
		tvHotelName.setText(id + "");
		conserve.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog1.show();

				Thread thread = new GetThread();
				thread.start();
			}
		});
		btnHotelOrd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog1.show();

				Thread thread = new BuyThread();
				thread.start();
			}
		});
		String size = "";
		String type = "";
		String address = "";
		String price = "";
		String name = "";
		if (id.equals("1")){
			order.setObegintime("10:30");
			order.setOremarks("1");
			order.setId("1");
			order.setImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545324658555&di=1f94b9cc49bfb2ef81633510b9fd9650&imgtype=0&src=http%3A%2F%2Fs9.sinaimg.cn%2Fmw690%2F005AQSZZzy79Kv5DWMod8%26690");
			order.setOtotalprice("1500/ÿ��");
			order.setRoomid("1");
			order.setTitle("���������������");
			order.setUuid("1");

			myConserve.setId("1");
			myConserve.setImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545324658555&di=1f94b9cc49bfb2ef81633510b9fd9650&imgtype=0&src=http%3A%2F%2Fs9.sinaimg.cn%2Fmw690%2F005AQSZZzy79Kv5DWMod8%26690");
			myConserve.setRoomid("1");
			myConserve.setTitle("���������������");

			size = "75ƽ��";
			type = "���׳���";
			String image = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545324658555&di=1f94b9cc49bfb2ef81633510b9fd9650&imgtype=0&src=http%3A%2F%2Fs9.sinaimg.cn%2Fmw690%2F005AQSZZzy79Kv5DWMod8%26690";
			address = "���������������";
			price = "1500/ÿ��";
			name = "����������300�׵���300��ŷʽ�󴲷�";
			Glide.with(DetailActivity.this).load(image).asBitmap().placeholder(R.drawable.ic_launcher)
					.into(imagein);
		}
		else if(id.equals("2")){
			order.setObegintime("10:30");
			order.setOremarks("2");
			order.setId("2");
			order.setImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545324658555&di=b93c721f6444d25fe155823a1b655680&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F7af40ad162d9f2d3d4d389d5a2ec8a136227ccce.jpg");
			order.setOtotalprice("1000/ÿ��");
			order.setRoomid("2");
			order.setTitle("����������Ȫ������̩��ܰ�󴲷�");
			order.setUuid("2");

			myConserve.setId("2");
			myConserve.setImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545324658555&di=b93c721f6444d25fe155823a1b655680&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F7af40ad162d9f2d3d4d389d5a2ec8a136227ccce.jpg");
			myConserve.setRoomid("2");
			myConserve.setTitle("����������Ȫ������̩��ܰ�󴲷�");

			size = "60ƽ��";
			type = "���׳���";
			String image = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545324658555&di=b93c721f6444d25fe155823a1b655680&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F7af40ad162d9f2d3d4d389d5a2ec8a136227ccce.jpg";
			address = "�������ŵ���վ";
			price = "1000/ÿ��";
			name = "����������Ȫ������̩��ܰ�󴲷�";
			Glide.with(DetailActivity.this).load(image).asBitmap().placeholder(R.drawable.ic_launcher)
					.into(imagein);
		}
		else if(id.equals("3")){
			order.setObegintime("10:30");
			order.setOremarks("3");
			order.setId("3");
			order.setImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545324658529&di=c74d81afbfb37b931c738e25af04cdf4&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F7c1ed21b0ef41bd57bd129cd5ada81cb39db3d0d.jpg");
			order.setOtotalprice("800/ÿ��");
			order.setRoomid("3");
			order.setTitle("������������Ϫ��ŷ����");
			order.setUuid("3");

			myConserve.setId("3");
			myConserve.setImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545324658529&di=c74d81afbfb37b931c738e25af04cdf4&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F7c1ed21b0ef41bd57bd129cd5ada81cb39db3d0d.jpg");
			myConserve.setRoomid("3");
			myConserve.setTitle("������������Ϫ��ŷ����");

			size = "55ƽ��";
			type = "���׳���";
			String image = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545324658529&di=c74d81afbfb37b931c738e25af04cdf4&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F7c1ed21b0ef41bd57bd129cd5ada81cb39db3d0d.jpg";
			address = "�������ŵ���վ";
			price = "800/ÿ��";
			name = "������������Ϫ��ŷ����";
			Glide.with(DetailActivity.this).load(image).asBitmap().placeholder(R.drawable.ic_launcher)
					.into(imagein);
		}
		else if(id.equals("4")){
			order.setObegintime("10:30");
			order.setOremarks("4");
			order.setId("4");
			order.setImage("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=777294833,3487317045&fm=27&gp=0.jpg");
			order.setOtotalprice("1200/ÿ��");
			order.setRoomid("4");
			order.setTitle("��������ins��һ����");
			order.setUuid("4");

			myConserve.setId("4");
			myConserve.setImage("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=777294833,3487317045&fm=27&gp=0.jpg");
			myConserve.setRoomid("4");
			myConserve.setTitle("��������ins��һ����");

			size = "100ƽ��";
			type = "���׳���";
			String image = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=777294833,3487317045&fm=27&gp=0.jpg";
			address = "���������������";
			price = "1200/ÿ��";
			name = "��������ins��һ����";
			Glide.with(DetailActivity.this).load(image).asBitmap().placeholder(R.drawable.ic_launcher)
					.into(imagein);
		}
		else if(id.equals("5")){
			order.setObegintime("10:30");
			order.setOremarks("5");
			order.setId("5");
			order.setImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545324658531&di=11c559e2aedc3501372cafa9cf0fd5ea&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F86d6277f9e2f0708b1ec6b3ae224b899a901f21b.jpg");
			order.setOtotalprice("2000/ÿ��");
			order.setRoomid("5");
			order.setTitle("�����ֹ㳡��������/�����ĸ߶˹�Ԣ");
			order.setUuid("5");

			myConserve.setId("5");
			myConserve.setImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545324658531&di=11c559e2aedc3501372cafa9cf0fd5ea&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F86d6277f9e2f0708b1ec6b3ae224b899a901f21b.jpg");
			myConserve.setRoomid("5");
			myConserve.setTitle("�����ֹ㳡��������/�����ĸ߶˹�Ԣ");

			size = "75ƽ��";
			type = "���׳���";
			String image = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545324658531&di=11c559e2aedc3501372cafa9cf0fd5ea&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F86d6277f9e2f0708b1ec6b3ae224b899a901f21b.jpg";
			address = "���������������";
			price = "2000/ÿ��";
			name = "�����ֹ㳡��������/�����ĸ߶˹�Ԣ";
			Glide.with(DetailActivity.this).load(image).asBitmap().placeholder(R.drawable.ic_launcher)
					.into(imagein);
		}
		else if(id.equals("6")){
			order.setObegintime("10:30");
			order.setOremarks("6");
			order.setId("6");
			order.setImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1085040100,3343526315&fm=27&gp=0.jpg");
			order.setOtotalprice("600/ÿ��");
			order.setRoomid("6");
			order.setTitle("�ӷ��� �������֡���ܰ����˫������");
			order.setUuid("6");

			myConserve.setId("6");
			myConserve.setImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1085040100,3343526315&fm=27&gp=0.jpg");
			myConserve.setRoomid("6");
			myConserve.setTitle("�ӷ��� �������֡���ܰ����˫������");


			size = "45ƽ��";
			type = "���׳���";
			String image = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1085040100,3343526315&fm=27&gp=0.jpg";
			address = "���������������";
			price = "600/ÿ��";
			name = "�ӷ��� �������֡���ܰ����˫������";
			Glide.with(DetailActivity.this).load(image).asBitmap().placeholder(R.drawable.ic_launcher)
					.into(imagein);
		}
		tvHotelSize.setText("�������:  " + size);
		tvHotelName.setText("��������:  " + name);
		tvHotelPrice.setText("�۸�"+ price);
		tv_address.setText("���ݵ�ַ:  " + address);
		tvHotelType.setText("��������:  " + type);
	}

	class GetThread extends Thread {
		@Override
		public void run() {
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					// �˴�ִ��UI����
					Toast.makeText(DetailActivity.this, "�ղسɹ�", Toast.LENGTH_SHORT).show();
					MyApplication.getContext().addConserve(myConserve);
					dialog1.dismiss();
				}
			});

		}
	}
	//�������
	class BuyThread extends Thread {
		@Override
		public void run() {
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					// �˴�ִ��UI����
					Toast.makeText(DetailActivity.this, "�µ��ɹ�", Toast.LENGTH_SHORT).show();
					MyApplication.getContext().addOrder(order);
					dialog1.dismiss();
				}
			});

		}
	}
}
