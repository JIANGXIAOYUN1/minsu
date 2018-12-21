package com.example.misu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import ip.Ip;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
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
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.style.CustomProgressDialog;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegisterActivity extends Activity {
  
    Button  register;
    ImageView fanhui;
    EditText phoneNum, password, confirmpassword,email,phone;
    private static String token = null;
    private static final int ERROR = 1;
    private Handler handler = new Handler();
    private static final int SET = 0;
    private static boolean result = false;
    CustomProgressDialog dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        initViews();
        dialog1 = new CustomProgressDialog(RegisterActivity.this);
    }


    public void initViews() {

     
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);
        password = (EditText) findViewById(R.id.password);
     
        password.setFilters(new InputFilter[]{filter});
        confirmpassword.setFilters(new InputFilter[]{filter});
        confirmpassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (password.getText().toString().equals(confirmpassword.getText().toString())) {
                    register.setEnabled(true);
                    register.setBackgroundResource(R.drawable.loginpage1);
                } else {
                    register.setEnabled(false);
                    register.setBackgroundResource(R.drawable.loginbg2);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
        password.addTextChangedListener(new TextWatcher() {

            @SuppressLint("ResourceAsColor")
			@Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (password.getText().toString().equals(confirmpassword.getText().toString()) ) {
                    register.setEnabled(true);
                    register.setTextColor(R.color.bottomtab_press);
                    register.setBackgroundResource(R.drawable.loginpage1);
                } else {
                    register.setEnabled(false);
                    register.setTextColor(R.color.bottomtab_normal);
                    register.setBackgroundResource(R.drawable.loginbg2);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog1.show();

                    Thread thread = new GetThread();
                    thread.start();
            }
        });
        
      
    }
    private InputFilter filter=new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if(source.equals(" ")||source.toString().contentEquals("\n"))return "";
            else return null;
        }
    };
    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

 

    /**
     *
     *子线程：通过GET方法向服务器发送用户名、密码的信息
     */
    class GetThread extends Thread {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    // 此处执行UI操作
                    Toast.makeText(RegisterActivity.this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
//                                    SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
//                                    // 获得sp的编辑器
//                                    SharedPreferences.Editor ed = sp.edit();
//                                    // 以键值对的显示将用户名和密码保存到sp中
//                                    ed.putString("user", phoneNum.getText().toString());
//                                    ed.commit();
                    Intent intent = new Intent();
                    intent.setClass(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    // finish();
                }
            });
            return;
            /*try {
                String str = "";
//                BasicHttpParams params1 = new BasicHttpParams();
//                HttpProtocolParams.setVersion(params1, HttpVersion.HTTP_1_1);
//                HttpProtocolParams.setContentCharset(params1,
//                        HTTP.DEFAULT_CONTENT_CHARSET);
//                HttpProtocolParams.setUseExpectContinue(params1, true);
//
//                SSLSocketFactory.getSocketFactory().setHostnameVerifier(
//                        new AllowAllHostnameVerifier());
//
//                SchemeRegistry schReg = new SchemeRegistry();
//
//                schReg.register(new Scheme("http", PlainSocketFactory
//                        .getSocketFactory(), 8098));
//
//                ClientConnectionManager connMgr = new ThreadSafeClientConnManager(
//                        params1, schReg);
                DefaultHttpClient client = new DefaultHttpClient();
                String url = new Ip().ip + "MinsuManager/frontlogin/register";
                HttpPost request = new HttpPost(url);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", phoneNum.getText().toString().trim()));
                params.add(new BasicNameValuePair("password", password.getText().toString().trim()));
                params.add(new BasicNameValuePair("phone", phone.getText().toString().trim()));
                params.add(new BasicNameValuePair("uemail", email.getText().toString().trim()));
                request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                HttpResponse response = client.execute(request);
                if (response.getStatusLine().getStatusCode() == 200) {
                    str = EntityUtils.toString(response.getEntity());
                    result = Boolean
                            .parseBoolean(str);
                    Log.v("result", result + "");
                    Log.i("结果", str);

                    try {
                        JSONObject object = new JSONObject(str);
                        String state = object.getString("state");
                        if (state.equals("402")) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    // 此处执行UI操作
                                    Toast.makeText(RegisterActivity.this, "帐号或者密码错误", 2000).show();
                                    dialog1.dismiss();
                                }
                            });
                        }
                        if (state.equals("401")) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    // 此处执行UI操作
                                    Toast.makeText(RegisterActivity.this, "帐号已存在", 2000).show();
                                    dialog1.dismiss();
                                }
                            });
                        }
                        if (state.equals("400")) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    // 此处执行UI操作
                                    Toast.makeText(RegisterActivity.this, "账户不能为空", Toast.LENGTH_SHORT).show();
                                    dialog1.dismiss();
                                }
                            });
                        }
                        if (state.equals("200")) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    // 此处执行UI操作
                                    Toast.makeText(RegisterActivity.this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
//                                    SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
//                                    // 获得sp的编辑器
//                                    SharedPreferences.Editor ed = sp.edit();
//                                    // 以键值对的显示将用户名和密码保存到sp中
//                                    ed.putString("user", phoneNum.getText().toString());
//                                    ed.commit();
                                    Intent intent = new Intent();
                                    intent.setClass(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                   // finish();
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
            }*/
        }
    }

}
