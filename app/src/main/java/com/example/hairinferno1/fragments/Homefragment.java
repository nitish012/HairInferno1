package com.example.hairinferno1.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.hairinferno1.Interface.Api;
import com.example.hairinferno1.Modal.ExampleHome;
import com.example.hairinferno1.R;
import com.example.hairinferno1.Modal.RESULTHOME;
import com.example.hairinferno1.adapter.ContentAdapter;
import com.example.hairinferno1.adapter.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

public class Homefragment extends Fragment {

    RecyclerView recycler,recyclerContent;

    private  View view;
    SharedPreferences sharedPreferences;
    List<RESULTHOME> resulthomes,list;
    private static final String TAG = "Homefragment";
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
        callApi();
        Context context = getActivity();
        return view;
    }

    private void setId() {
        recycler=view.findViewById(R.id.recycler);
        recyclerContent=view.findViewById(R.id.recycler_content);
    }


//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }

    /* hitting api of feching details on fragment */
    private void callApi() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHeader())
                .build();
        Api api = retrofit.create(Api.class);
        progressBar();
        Call<ExampleHome> call = api.getData("1", "1","1");

        call.enqueue(new Callback<ExampleHome>() {
            @Override
            public void onResponse(Call<ExampleHome> call, Response<ExampleHome> response) {

                if(mProgressDialog.isShowing())
                {
                    mProgressDialog.dismiss();
                }
                 resulthomes = new ArrayList<>();
                list=new ArrayList<>();
                resulthomes.addAll( response.body().getRESULT());

                Log.i(TAG, "onResponse:1 ");

                recyclerContent.setLayoutManager(new LinearLayoutManager(getContext()));
                ContentAdapter contentAdapter = new ContentAdapter(getActivity(),resulthomes);
                recyclerContent.setAdapter(contentAdapter);

                recycler.setLayoutManager(new LinearLayoutManager(getContext(), HORIZONTAL,true));
                ViewAdaptor viewAdaptor=new ViewAdaptor(resulthomes, getActivity());
                recycler.setAdapter(viewAdaptor);
            }

            @Override
            public void onFailure(Call<ExampleHome> call, Throwable t) {
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
                              .addHeader("Authorization", "Bearer "+"eyJ0eXAiOiJqd3QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPRGc9Iiwic3ViIjoiSGFpciBJbmZlcm5vIiwiYXVkIjoiYzJSbVlYTmtaZz09IiwianRpIjoiWVhOa1ptRnpaZz09IiwiZXhwIjoiMjAxOS0wNC0yNiAxMjoyMDo0OCIsIm5iZiI6IjIwMTktMDQtMTggMDk6NTA6NDgifQ.0OKXjJhFiwykytV4uWIYFlaokMdzofV_dU2R3wg9xJQ")
   //                            .addHeader("Authorization","Bearer "+token)
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
