package com.example.misu;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.net.ssl.SSLSocketFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
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

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ip.Ip;

public class UpdatePwdActivity extends Activity {
    ImageView fanhui;
    EditText ed_oldpassword, password, confirmpassword;
    Button btn_confirmupdatepassword;
    String login_token, loginpassword;
    private Handler handler = new Handler();
    private static final int SET = 0;
    private static boolean result = false;
    TextView txupdatePwdError;
    private SharedPreferences sp;
    String username1;
    CustomProgressDialog dialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_update_pwd);
        sp = getSharedPreferences("userinfo", MODE_PRIVATE);
		username1 = sp.getString("user", "");
		
        initViews();
        dialog1 = new CustomProgressDialog(UpdatePwdActivity.this);
    }

    private void initViews() {
        // 获取帐号和登陆时的token
      
        // 初始化组件
        password = (EditText) findViewById(R.id.password);
        ed_oldpassword = (EditText) findViewById(R.id.ed_oldpassword);
        btn_confirmupdatepassword = (Button) findViewById(R.id.btn_confirmupdatepassword);
        btn_confirmupdatepassword.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog1.show();
                Thread thread = new GetThread();
                thread.start();
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ed_oldpassword.getText().toString().length() > 0 && password.getText().toString().length() > 0 ) {
                    btn_confirmupdatepassword.setEnabled(true);
                    btn_confirmupdatepassword.setBackgroundResource(R.drawable.loginpage1);
                    //  txupdatePwdError.setVisibility(View.GONE);
                } else {
                    btn_confirmupdatepassword.setEnabled(false);
                    btn_confirmupdatepassword.setBackgroundResource(R.drawable.loginbg2);
                    //  txupdatePwdError.setVisibility(View.VISIBLE);
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
        ed_oldpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ed_oldpassword.getText().toString().length() > 0 && password.getText().toString().length() > 0) {
                    btn_confirmupdatepassword.setEnabled(true);
                    btn_confirmupdatepassword.setBackgroundResource(R.drawable.loginpage1);

                } else {
                    btn_confirmupdatepassword.setEnabled(false);
                    btn_confirmupdatepassword.setBackgroundResource(R.drawable.loginbg2);
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

    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    class GetThread extends Thread {
        @Override
        public void run() {
            try {
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
                String url = new Ip().ip + "MinsuManager/frontlogin/UpdatePwd";
                HttpPost request = new HttpPost(url);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username",username1));
                params.add(new BasicNameValuePair("oldpassword",ed_oldpassword.getText().toString()));
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
                     
                        if (state.equals("201")) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    // 此处执行UI操作
                                    Toast.makeText(UpdatePwdActivity.this, "旧密码错误", Toast.LENGTH_SHORT).show();
                                    dialog1.dismiss();
                                }
                            });
                        }
                        if (state.equals("200")) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    // 此处执行UI操作
                                    Toast.makeText(UpdatePwdActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    dialog1.dismiss();
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
    }
}

