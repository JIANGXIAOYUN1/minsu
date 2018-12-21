package com.fragment;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.misu.ConserveActivity;
import com.example.misu.LoginActivity;
import com.example.misu.R;
import com.example.misu.RegisterActivity;
import com.example.misu.UpdatePwdActivity;
import com.style.CustomProgressDialog;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MineFragment extends Fragment {
  
    CustomProgressDialog dialog1;
    private SharedPreferences sp;
 TextView username;
 String username1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.mine, container, false);
        username= (TextView) view.findViewById(R.id.username);
        sp = getActivity().getSharedPreferences("userinfo", getActivity().MODE_PRIVATE);
		username1 = sp.getString("user", "");
		username.setText(username1);
		 LinearLayout quit = (LinearLayout) view.findViewById(R.id.login_quit);
		 LinearLayout updatePwd = (LinearLayout) view.findViewById(R.id.updatePwd);
		 LinearLayout  conserve = (LinearLayout) view.findViewById(R.id.conserve);
		
		 quit.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())

	                .setMessage("退出");
	                builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                    	SharedPreferences sp = getActivity().getSharedPreferences("userinfo",getActivity().MODE_PRIVATE);
	                      SharedPreferences.Editor editor = sp.edit();
	                      editor.clear();
	                      editor.commit();
	                      Intent intent = new Intent();
	                      intent.setClass(getActivity(), LoginActivity.class);
	                      startActivity(intent);

	                    }
	                });
	                builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {

	                    public void onClick(DialogInterface dialog, int which) {
	                        dialog.cancel();
	                    }
	                });
	                builder.create().show();
	            }
	        });
		 updatePwd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
                intent.setClass(getActivity(), UpdatePwdActivity.class);
                startActivity(intent);
				
			}
		});
		 conserve.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
	                intent.setClass(getActivity(), ConserveActivity.class);
	                startActivity(intent);
					
				}
			});
      return view;
     
    }
//    public void quit(View view){
//    	SharedPreferences sp = getActivity().getSharedPreferences("userinfo",getActivity().MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.clear();
//        editor.commit();
//        Intent intent = new Intent();
//        intent.setClass(getActivity(), LoginActivity.class);
//        startActivity(intent);
//    }
}

