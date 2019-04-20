package com.example.hairinferno1.activities;

import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hairinferno1.Interface.Api;
import com.example.hairinferno1.R;
import com.example.hairinferno1.R;

import java.util.PriorityQueue;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    private TextView tvLogin;
    EditText mail,password;
    String TAG="login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fetchId();

        signUpString();
    }

    private void fetchId()
    {

        tvLogin=findViewById(R.id.tv_login);
        mail=findViewById(R.id.et_mail);
        password=findViewById(R.id.et_Password);

    }
    private void signUpString() {

        SpannableString text = new SpannableString("Don't have an account?SIGNUP");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        text.setSpan(clickableSpan, 22, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // TextView textView = (TextView) findViewById(R.id.hello);
        tvLogin.setText(text);
        tvLogin.setMovementMethod(LinkMovementMethod.getInstance());
        tvLogin.setHighlightColor(Color.TRANSPARENT);

    }

    public void login(View view) {
        //Toast.makeText(this, "login", Toast.LENGTH_SHORT).show();
        String regexEmail="^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

        if(mail.getText().toString().matches(regexEmail)) {
            loginAccount();
        }
        else
            Toast.makeText(this,"Please enter proper email",Toast.LENGTH_SHORT).show();

    }

    private void loginAccount()
    {
        String user_email=mail.getText().toString();
        String user_password=password.getText().toString();
        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);


        Call<ResponseBody> call=api.postLogin(user_email,user_password,android_id,"sfghfgfhgfhgf","1");
       // Call<ResponseBody> call=api.postLogin("nitish.dewan@appinventiv.com","1234",android_id,"1","1");


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG,"Response "+response.code());

                if(response.code()==200){

                    Intent intent=new Intent(Login.this,Home.class);
                    startActivity(intent);

                    Toast.makeText(Login.this, "Login successfully", Toast.LENGTH_SHORT).show();
                }
                if(response.code()==101)
                {
                    Toast.makeText(Login.this, "Account Blocked", Toast.LENGTH_SHORT).show();
                }

                if (response.code()==427)
                {
                    Toast.makeText(Login.this, "Account not verified", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG,"Failure "+t.getMessage());


            }
        });


    }
}
