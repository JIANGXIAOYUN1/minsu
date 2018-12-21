package com.example.misu;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.json.JSONException;
import org.json.JSONObject;

import com.style.CustomProgressDialog;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import ip.Ip;

public class LoginActivity extends Activity {

	TextView btn_register;
    EditText username, password;

    private SharedPreferences sp, sp2;
    Button btn_login;
    //    CustomProgressDialog dialog;
    String info;

    private static Handler handler2 = new Handler();
    private static final int SET = 0;
    private static final int ERROR = 1;
    private static boolean result = false;
    private Handler handler = new Handler();
    CustomProgressDialog dialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();
        sp2 = getSharedPreferences("userinfo", MODE_PRIVATE);
        if (sp2.getString("user", "").length() != 0) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        dialog1 = new CustomProgressDialog(LoginActivity.this);
    }

    private void initView() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btn_login = (Button) findViewById(R.id.login);

        btn_register = (TextView) findViewById(R.id.register);
        btn_register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	 dialog1.show();
            	Thread thread = new GetThread();
                thread.start();
            }
        });
        password.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (username.getText().length() != 0 && password.getText().length() != 0) {
                	btn_login.setEnabled(true);

                	btn_login.setBackgroundResource(R.drawable.loginpage1);
                } else {
                	btn_login.setEnabled(false);

                	btn_login.setBackgroundResource(R.drawable.loginbg2);
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
    }

    class GetThread extends Thread {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    // 此处执行UI操作
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
                    // 获得sp的编辑器
                    SharedPreferences.Editor ed = sp.edit();
                    // 以键值对的显示将用户名和密码保存到sp中
                    ed.putString("user", username.getText().toString());
                    ed.commit();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
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
                String url = new Ip().ip + "MinsuManager/frontlogin/login";
                HttpPost request = new HttpPost(url);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username.getText().toString().trim()));
                params.add(new BasicNameValuePair("password", password.getText().toString().trim()));
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
                                    Toast.makeText(LoginActivity.this, "帐号或者密码错误", 50).show();
                                    dialog1.dismiss();
                                }
                            });
                        }
                        if (state.equals("401")) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    // 此处执行UI操作
                                    Toast.makeText(LoginActivity.this, "密码不能为空", 2000).show();
                                    dialog1.dismiss();
                                }
                            });
                        }
                        if (state.equals("400")) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    // 此处执行UI操作
                                    Toast.makeText(LoginActivity.this, "账户不能为空", 50).show();
                                    dialog1.dismiss();
                                }
                            });
                        }
                        if (state.equals("200")) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    // 此处执行UI操作
                                    Toast.makeText(LoginActivity.this, "登录成功", 50).show();
                                    SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
                                    // 获得sp的编辑器
                                    SharedPreferences.Editor ed = sp.edit();
                                    // 以键值对的显示将用户名和密码保存到sp中
                                    ed.putString("user", username.getText().toString());
                                    ed.commit();
                                    Intent intent = new Intent();
                                    intent.setClass(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
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
