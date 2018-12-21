package com.fragment;

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
import com.bean.MyOrder;
import com.bean.RoomInfo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.misu.DetailActivity;
import com.example.misu.LoginActivity;
import com.example.misu.MainActivity;
import com.example.misu.MyApplication;
import com.example.misu.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.style.CustomProgressDialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
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

public class MyOrderFragment extends Fragment {
	static MyRoomInfo adapter;
	CustomProgressDialog dialog1;
	List<MyOrder> list;
	ProgressDialog pDialog;
	LocationManagerProxy aMapManager;
	static String str;
	static int position;
	private PullToRefreshGridView mPullRefreshgridView;
	TextView tx_location;
	int down = 2;
	GridView gridView;
	private SharedPreferences sp;
	String username;

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.order, container, false);
		mPullRefreshgridView = (PullToRefreshGridView) view.findViewById(R.id.pull_refresh_grid);
		sp = getActivity().getSharedPreferences("userinfo", getActivity().MODE_PRIVATE);
		dialog1=new CustomProgressDialog(getActivity());
		username = sp.getString("user", "");
		list = new ArrayList<MyOrder>();


		getVolley();
		return view;

	}

	// 上拉加载更多
	/*public void getMore() {

		String url = new Ip().ip + "MinsuManager/frontlogin/getBuy?username=" + username + "&position=" + down;
		System.out.print("url" + url);

		RequestQueue queue = Volley.newRequestQueue(getActivity());
		StringRequest request = new StringRequest(url, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				System.out.println("url" + response);
				try {
					JSONObject obj = new JSONObject(response);
					String status = obj.getString("state");

					if (!status.equals("200")) {
						new Handler(Looper.getMainLooper()).post(new Runnable() {
							@Override
							public void run() {
								// 此处执行UI操作
								Toast.makeText(getActivity(), "获取数据错误", Toast.LENGTH_SHORT).show();
							}
						});
					}
					JSONArray array = obj.getJSONArray("data");
					if (array.length() == 0) {
						Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
						mPullRefreshgridView.onRefreshComplete();
						return;
					}
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj1 = array.getJSONObject(i);
						MyOrder order = new MyOrder();

						String obegintime = obj1.getString("obegintime");
						String oremarks = obj1.getString("oremarks");
						String ototalprice = obj1.getString("ototalprice");
						String uuid = obj1.getString("uuid");
						String roomid = obj1.getString("roomid");
						String id = obj1.getString("id");
						String title = obj1.getString("title");
						String image = obj1.getString("image");

						order.setTitle(title);
						order.setId(id);
						order.setImage(image);
						order.setObegintime(obegintime);
						order.setOremarks(oremarks);
						order.setOtotalprice(ototalprice);
						order.setRoomid(roomid);
						order.setUuid(uuid);
						list.add(order);
					}
					adapter = new MyRoomInfo();
					mPullRefreshgridView.setAdapter(adapter);
					// 适配器刷新
					adapter.notifyDataSetChanged();
					mPullRefreshgridView.onRefreshComplete();
					gridView = mPullRefreshgridView.getRefreshableView();
					gridView.setSelection(position);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(getActivity(), "与服务器连接失败", Toast.LENGTH_SHORT).show();

			}
		});
		queue.add(request);
		down++;
	}*/

	public void getVolley() {
//		MyOrder order = new MyOrder();
//
//		String obegintime = "10:30";
//		String oremarks = "1";
//		String ototalprice = " ";
//		String uuid = " ";
//		String roomid = "1";
//		String id = "1";
//		String title = "1";
//		String image = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545324658555&di=1f94b9cc49bfb2ef81633510b9fd9650&imgtype=0&src=http%3A%2F%2Fs9.sinaimg.cn%2Fmw690%2F005AQSZZzy79Kv5DWMod8%26690";
//
//		order.setTitle(title);
//		order.setId(id);
//		order.setObegintime(obegintime);
//		order.setOremarks(oremarks);
//		order.setOtotalprice(ototalprice);
//		order.setRoomid(roomid);
//		order.setUuid(uuid);
//		list.add(order);


		list = MyApplication.getContext().getOrder();
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
		public View getView(final int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.orderitem, null);
			}

			TextView tx_time = (TextView) convertView.findViewById(R.id.tx_time);
			TextView tx_name = (TextView) convertView.findViewById(R.id.tx_name);
			TextView tx_price = (TextView) convertView.findViewById(R.id.tx_price);
			TextView tx_uuid = (TextView) convertView.findViewById(R.id.tx_uuid);
			TextView tx_state = (TextView) convertView.findViewById(R.id.tx_state);
			final LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.layoutclick);
			ImageView imageView = (ImageView) convertView.findViewById(R.id.bg);

			tx_name.setText(list.get(position).getTitle());
			tx_price.setText(list.get(position).getOtotalprice());
			tx_uuid.setText("订单号: " + list.get(position).getUuid());
			tx_time.setText("提交时间: " + list.get(position).getObegintime());
			if (list.get(position).getOremarks().equals("1")) {
				tx_state.setText("已付款");
			} else {
				tx_state.setText("未付款");
			}
			layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(getActivity(), DetailActivity.class);
					i.putExtra("id", list.get(position).getRoomid());
					startActivity(i);
				}
			});
			layout.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View arg0) {
					// TODO Auto-generated method stub
					 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())

				                .setMessage("确定删除么");
				                builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
				                    public void onClick(final DialogInterface dialog, int which) {
				                    	dialog1.show();
				                    	new Thread(new Runnable() {
											
											@Override
											public void run() {
												 try {
										                String str = "";
//										                BasicHttpParams params1 = new BasicHttpParams();
//										                HttpProtocolParams.setVersion(params1, HttpVersion.HTTP_1_1);
//										                HttpProtocolParams.setContentCharset(params1,
//										                        HTTP.DEFAULT_CONTENT_CHARSET);
//										                HttpProtocolParams.setUseExpectContinue(params1, true);
										//
//										                SSLSocketFactory.getSocketFactory().setHostnameVerifier(
//										                        new AllowAllHostnameVerifier());
										//
//										                SchemeRegistry schReg = new SchemeRegistry();
										//
//										                schReg.register(new Scheme("http", PlainSocketFactory
//										                        .getSocketFactory(), 8098));
										//
//										                ClientConnectionManager connMgr = new ThreadSafeClientConnManager(
//										                        params1, schReg);
										                DefaultHttpClient client = new DefaultHttpClient();
										                String url = new Ip().ip + "MinsuManager/frontlogin/deleteOrder";
										                HttpPost request = new HttpPost(url);
										                List<NameValuePair> params = new ArrayList<NameValuePair>();
										                params.add(new BasicNameValuePair("id", list.get(position).getId()));
										              
										                request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
										                HttpResponse response = client.execute(request);

										                if (response.getStatusLine().getStatusCode() == 200) {
										                    str = EntityUtils.toString(response.getEntity());
										                  

										                    try {
										                        JSONObject object = new JSONObject(str);
										                        String state = object.getString("state");
										                       
										                        if (state.equals("401")) {
										                            new Handler(Looper.getMainLooper()).post(new Runnable() {
										                                @Override
										                                public void run() {
										                                    // 此处执行UI操作
										                                    Toast.makeText(getActivity(), "关键数据不能为空", Toast.LENGTH_SHORT).show();
										                                    dialog1.dismiss();
										                                    dialog.dismiss();
										                                }
										                            });
										                        }
										                        if (state.equals("200")) {
										                            new Handler(Looper.getMainLooper()).post(new Runnable() {
										                                @Override
										                                public void run() {
										                                    // 此处执行UI操作
										                                    Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
										                                    dialog1.dismiss();
										                                    dialog.dismiss();
										                                    getVolley();
										                                }
										                            });
										                        }
										                      
										                    } catch (JSONException e) {
										                        // TODO Auto-generated catch block
										                        e.printStackTrace();
										                    }

										                }
										            } catch (UnsupportedEncodingException e) {
										                e.printStackTrace();
										            } catch (ClientProtocolException e) {
										                e.printStackTrace();
										            } catch (IOException e) {
										                e.printStackTrace();
										            }
											}
										}).start();

				                    }
				                });
				                builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {

				                    public void onClick(DialogInterface dialog, int which) {
				                        dialog.cancel();
				                    }
				                });
				                builder.create().show();
					return false;
				}
			});
					
					
				
		
			Glide.with(getActivity()).load(list.get(position).getImage()).asBitmap().placeholder(R.drawable.ic_launcher)
					.into(imageView);

			return convertView;
		}
	}
}
