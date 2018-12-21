package com.example.misu;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fragment.FindFragment;
import com.fragment.MineFragment;
import com.fragment.MyOrderFragment;
import com.fragment.TouristFragment;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnClickListener {
    private String username = "";
    private RelativeLayout mydeviceLayout, sceneLayout, mineLayout,touristLayout;
    private Fragment find, order, tourist,mine, currentFragment;
    private ImageView findImg, orderImg, touristImg,mineImg;
    private TextView findTv, orderTv,touristTv, mineTv;

    String password, name;
    Dialog dialog;
    CustomProgressDialog dialog1;
    String deviceid;
    String plategid;
    private static final int REQUEST_PERMISSION = 0;
  

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

      
        dialog1 = new CustomProgressDialog(MainActivity.this);
        SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
      
        password = sp.getString("password", "");
        name = sp.getString("name", "");
      
        username = sp.getString("username", "");
     
        Intent i = getIntent();
        deviceid = i.getStringExtra("deviceid");
        plategid = i.getStringExtra("plategid");
        System.out.print("设备信息" + deviceid + plategid);
     
        initUI();
        initTab();
      
    }

    /*
     * 初始化控件
     */
    private void initUI() {
        mydeviceLayout = (RelativeLayout) findViewById(R.id.rl_know);
        sceneLayout = (RelativeLayout) findViewById(R.id.rl_want_know);
        mineLayout = (RelativeLayout) findViewById(R.id.rl_mine);
        touristLayout = (RelativeLayout) findViewById(R.id.rl_tourist);
        
        findImg = (ImageView) findViewById(R.id.iv_know);
        orderImg = (ImageView) findViewById(R.id.iv_i_want_know);
        mineImg = (ImageView) findViewById(R.id.iv_mine);
        touristImg = (ImageView) findViewById(R.id.iv_tourist);
        
        findTv = (TextView) findViewById(R.id.tv_know);
        orderTv = (TextView) findViewById(R.id.tv_i_want_know);
        mineTv = (TextView) findViewById(R.id.tv_mine);
        touristTv = (TextView) findViewById(R.id.tv_tourist);
        
        mydeviceLayout.setOnClickListener(this);
        sceneLayout.setOnClickListener(this);
        mineLayout.setOnClickListener(this);
        touristLayout.setOnClickListener(this);
    }


    private void initTab() {
        if (find == null) {
        	find = new FindFragment();
        }
        if (!find.isAdded()) {

            getSupportFragmentManager().beginTransaction().add(R.id.content_layout, find).commit();
            currentFragment = find;
            findImg.setImageResource(R.drawable.mydevice_blue);
            findTv.setTextColor(getResources().getColor(R.color.bottomtab_press));
            
            orderImg.setImageResource(R.drawable.home_scene_gray);
            orderTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
            
            touristImg.setImageResource(R.drawable.home_scene_gray);
            touristTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
            
            mineImg.setImageResource(R.drawable.personalcenter_gray);
            mineTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_know: // 第一页
                clickTab1Layout();
                break;
            case R.id.rl_want_know: // 第二页
                clickTab2Layout();
                break;
            case R.id.rl_tourist: // 第三页
                clickTab3Layout();
                break;
            case R.id.rl_mine: // 第四页
                clickTab4Layout();
                break;
                
            default:
                break;
        }
    }


    

    /**
     * frament切换
     */

    private void clickTab1Layout() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (find == null) {
        	find = new FindFragment();
        }
//        transaction.replace(R.id.content_layout, mydevicefragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), find);
        findImg.setImageResource(R.drawable.mydevice_blue);
        findTv.setTextColor(getResources().getColor(R.color.bottomtab_press));
        orderImg.setImageResource(R.drawable.home_scene_gray);
        orderTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        touristImg.setImageResource(R.drawable.home_scene_gray);
        touristTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        mineTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        mineImg.setImageResource(R.drawable.personalcenter_gray);
    }

    private void clickTab2Layout() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (order == null) {
        	order = new MyOrderFragment();
        }
        addOrShowFragment(transaction, order);

//        transaction.replace(R.id.content_layout, iWantKnowFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
        findImg.setImageResource(R.drawable.mydevice_gray);
        findTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        orderImg.setImageResource(R.drawable.home_scene_blue);
        orderTv.setTextColor(getResources().getColor(R.color.bottomtab_press));
        touristImg.setImageResource(R.drawable.home_scene_gray);
        touristTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        mineTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        mineImg.setImageResource(R.drawable.personalcenter_gray);
    }

    private void clickTab3Layout() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (tourist == null) {
        	tourist = new TouristFragment();
        }
        addOrShowFragment(transaction, tourist);

//        transaction.replace(R.id.content_layout, iWantKnowFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
        
        findImg.setImageResource(R.drawable.mydevice_gray);
        findTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        orderImg.setImageResource(R.drawable.home_scene_gray);
        orderTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        touristImg.setImageResource(R.drawable.home_scene_blue);
        touristTv.setTextColor(getResources().getColor(R.color.bottomtab_press));
        mineTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        mineImg.setImageResource(R.drawable.personalcenter_gray);
    }
    private void clickTab4Layout() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mine == null) {
        	mine = new MineFragment();
        }
//        transaction.replace(R.id.content_layout, MineFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
        addOrShowFragment(transaction, mine);
        findImg.setImageResource(R.drawable.mydevice_gray);
        findTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        orderImg.setImageResource(R.drawable.home_scene_gray);
        orderTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        touristImg.setImageResource(R.drawable.home_scene_gray);
        touristTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        mineTv.setTextColor(getResources().getColor(R.color.bottomtab_press));
        mineImg.setImageResource(R.drawable.personalcenter_blue);
    }

    private void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {

        if (!fragment.isAdded()) {
            transaction.hide(currentFragment).add(R.id.content_layout, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }
        currentFragment = fragment;

    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;

        }
        return super.dispatchKeyEvent(event);
    }
}
