package com.example.hairinferno1.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hairinferno1.Interface.Api;
import com.example.hairinferno1.R;
import com.example.hairinferno1.Modal.RESULTHOME;
import com.example.hairinferno1.activities.Login;
import com.example.hairinferno1.adapter.ContentAdapter;
import com.example.hairinferno1.adapter.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;


public class Homefragment extends Fragment {

    RecyclerView recycler,recyclerContent;
    private Context context;
    private  View view;
    SharedPreferences sharedPreferences;
    List<RESULTHOME> resulthomes,list;
    RESULTHOME resulthome;
    private static final String TAG = "Homefragment";
    int post_id;
    private ProgressDialog mProgressDialog;

    public static Homefragment newInstance() {
        Homefragment fragment = new Homefragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view= inflater.inflate(R.layout.fragment_homefragment,container,false);
        setId();
        callNewsApi();
        context = getActivity();
        return view;
    }

    private void setId() {
        recycler=view.findViewById(R.id.recycler);
        recyclerContent=view.findViewById(R.id.recycler_content);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void callNewsApi() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHeader())
                .build();
        Api api = retrofit.create(Api.class);
        progressBar();
        Call<ResponseBody> call = api.getData("1", "1","1");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG, "onResponse: " + response.body());

                if(mProgressDialog.isShowing())
                {
                    mProgressDialog.dismiss();
                }
                resulthomes=new ArrayList<>();
                list=new ArrayList<>();
                String result = null;
                try {
                    result = response.body().string();
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("RESULT");
                    Log.i("LENGTH", String.valueOf(jsonArray.length()));
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String name = jsonObject1.getString("name");
                        String user_image = jsonObject1.getString("user_image");
                        int is_like = jsonObject1.getInt("is_like");
                        String avg_rating = jsonObject1.getString("avg_rating");
                        int like_count = jsonObject1.getInt("like_count");
                        post_id=jsonObject1.getInt("post_id");

                        resulthome = new RESULTHOME();
                        resulthome.setLikeCount(like_count);
                        resulthome.setUserImage(user_image);
                        resulthome.setName(name);
                        resulthome.setIsLike(is_like);
                        resulthome.setPostId(post_id);
                        resulthomes.add(resulthome);

                        list.add(resulthome);

                        Log.i(TAG, "onResponse:1 ");
                    }
                    recyclerContent.setLayoutManager(new LinearLayoutManager(getContext()));
                    ContentAdapter contentAdapter = new ContentAdapter(getActivity(),resulthomes);
                    recyclerContent.setAdapter(contentAdapter);

                    recycler.setLayoutManager(new LinearLayoutManager(getContext(), HORIZONTAL,true));
                    ViewAdaptor viewAdaptor=new ViewAdaptor(list, getActivity());
                    recycler.setAdapter(viewAdaptor);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
    private OkHttpClient getHeader() {

        sharedPreferences=getActivity().getSharedPreferences("Signup", Context.MODE_PRIVATE);
        final String token =sharedPreferences.getString("Token","2");
        Log.i("token", token);
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
//                                .addHeader("Authorization", "Basic cGl4YWxpdmU6REFGODdEU0ZEU0ZEU0E5OEZTQURLSkUzMjRLSkwzMkhGRDdGRFNGQjI0MzQzSjQ5RFNG")
                                .addHeader("Authorization", "Bearer "+"eyJ0eXAiOiJqd3QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPRGc9Iiwic3ViIjoiSGFpciBJbmZlcm5vIiwiYXVkIjoiYzJSbVlYTmtaZz09IiwianRpIjoiWVhOa1ptRnpaZz09IiwiZXhwIjoiMjAxOS0wNC0yNiAxMjoyMDo0OCIsIm5iZiI6IjIwMTktMDQtMTggMDk6NTA6NDgifQ.0OKXjJhFiwykytV4uWIYFlaokMdzofV_dU2R3wg9xJQ")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();
    }

    private void progressBar()
    {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setMax(20);
        mProgressDialog.show();
    }

}
