package com.example.hairinferno1.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hairinferno1.Modal.Example;
import com.example.hairinferno1.Interface.Api;
import com.example.hairinferno1.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {

   private TextView tvTerms,tvLogin;
   private Dialog myDialog;
   private Button ok;
   private EditText name,email,password,confirmPassword;
   private SharedPreferences sharedPreferences;
   private CheckBox checkBox;
   String TAG="result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fetchId();
        termsString();
        loginString();
        sharedPreferences=getSharedPreferences("Signup",MODE_PRIVATE);


    }

    private void fetchId()
    {

        tvTerms=findViewById(R.id.tv_terms);
        tvLogin=findViewById(R.id.tv_login);
        ok=findViewById(R.id.btn_ok);
        name=findViewById(R.id.name_signup);
        email=findViewById(R.id.email_signup);
        password=findViewById(R.id.et_Password);
        confirmPassword=findViewById(R.id.et_confirmPassword);
        checkBox=findViewById(R.id.agree_checkbox);
    }
        String regexEmail="^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    private void termsString() {

        String text = "I agree to the <font color='red'>Terms of Service</font> and <font color='red'>Privacy Policy</font>.";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvTerms.setText(Html.fromHtml(text,  Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
        } else {
            tvTerms.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
        }

    }

    private void loginString()
    {

        SpannableString text = new SpannableString("Already have a account?LOGIN");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        text.setSpan(clickableSpan, 23, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvLogin.setText(text);
        tvLogin.setMovementMethod(LinkMovementMethod.getInstance());
        tvLogin.setHighlightColor(Color.TRANSPARENT);

    }

    public void signUp(View view) {

        //Toast.makeText(this, "signup", Toast.LENGTH_SHORT).show();
        String regexEmail="^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

        if(email.getText().toString().length()==0 || password.getText().toString().length()==0 || confirmPassword.getText().toString().length()==0 || name.getText().toString().length()==0 )
            Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show();

        else if(!email.getText().toString().matches(regexEmail))
            Toast.makeText(this,"Please enter correct email",Toast.LENGTH_SHORT).show();
        else if(!password.getText().toString().equals(confirmPassword.getText().toString()))
            Toast.makeText(this,"Password didn't match",Toast.LENGTH_SHORT).show();

        else if(!checkBox.isChecked())
            Toast.makeText(this,"Please agree to our terms and conditions",Toast.LENGTH_SHORT).show();

        else
        {
            postSignup();
        }
        }


//        else  {
//            myDialog = new Dialog(SignUp.this);
//            myDialog.setContentView(R.layout.customdialog);
//            myDialog.setTitle("My Custom Dialog");
////        ok.setEnabled(true);
////
////        ok.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////                Toast.makeText(SignUp.this, "Successfully signed up", Toast.LENGTH_SHORT).show();
////
////            }
////        });
//            myDialog.show();
//
//
//
  //      }

    public void ok(View view) {

        Intent intent=new Intent(SignUp.this,Login.class);
        startActivity(intent);
    }

    public void postSignup()
    {

        String user_name=name.getText().toString();
        String user_email=email.getText().toString();
        String user_password=password.getText().toString();
       // String user_confirmPassword=confirmPassword.getText().toString();

      String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<Example> call=api.postSignup(user_name,user_email,user_password,"1",android_id,"1","dDNsA","ssda","hgghg");
       // Call<Example> call=api.postSignup("dshjghgddsd","vsdfbh23@gmail.com","d87656dasd","1",android_id,"1","dsdfDhjhjNsAA","ssdhjgjgsdfaA","hgsdfghhjksag");
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {


//                    JSONObject jsonObject=new JSONObject(resp);
//                    jsonObject.getJSONObject("RESULT");
//                   String name=jsonObject.getString("name");
//                   String email=jsonObject.getString("email");
//               )    String token=jsonObject.getString("token");

                Log.i("TAG",String.valueOf(response.code()));


                if (response.code()==200)
                { myDialog = new Dialog(SignUp.this);
                  myDialog.setContentView(R.layout.customdialog);
                  myDialog.setTitle("My Custom Dialog");
                  myDialog.show();

                    Example example=response.body();
                    String token=example.getRESULT().getToken();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Token",token);

                }


            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {

                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
