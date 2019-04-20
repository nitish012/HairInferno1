package com.example.hairinferno1.fragments;

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
import com.example.hairinferno1.R;
import com.example.hairinferno1.Modal.RESULTHOME;
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
    String resp;
    RESULTHOME resulthome;
    private static final String TAG = "Homefragment";
    int post_id;

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
        //getLike();
        context = getActivity();
        //setData();
        return view;

    }

    private void setId() {
        recycler=view.findViewById(R.id.recycler);
        recyclerContent=view.findViewById(R.id.recycler_content);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

      //  list = new ArrayList<DataList>();


     /*   setData();
        setContentData();*/



    }

    private void callNewsApi() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHeader())
                .build();
        Api api = retrofit.create(Api.class);

        Call<ResponseBody> call = api.getData("1", "1","1");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG, "onResponse: " + response.body());

                resulthomes=new ArrayList<>();
                list=new ArrayList<>();
                String result = null;
                try {
                    result = response.body().string();
                    JSONObject jsonObject = new JSONObject(result);
                    //  Log.i(TAG, "onResponse: "+jsonObject.getJSONArray("RESULT").length());
                    //
                    JSONArray jsonArray = jsonObject.getJSONArray("RESULT");
                    Log.i("LENGTH", String.valueOf(jsonArray.length()));
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String name = jsonObject1.getString("name");
                        String user_image = jsonObject1.getString("user_image");
                        int is_like = jsonObject1.getInt("is_like");
                        String avg_rating = jsonObject1.getString("avg_rating");
                        int like_count = jsonObject1.getInt("like_count");
                        String like_plus_comment = jsonObject1.getString("like_plus_comment");
                        String user_rating = jsonObject1.getString("user_rating");
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
                if (t instanceof IOException) {
                    // Toast.makeText(MainActivity.this, "Internet Issue", Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(ImageLoadActivity.this, "Some Big Issue", Toast.LENGTH_SHORT).show();
                }

                // swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private OkHttpClient getHeader() {

  //      sharedPreferences=getSharedPreferences("login",Context.MODE_PRIVATE);
//        email= sharedPreferences.getString("emailkey","3");
        sharedPreferences=getActivity().getSharedPreferences("Signup", Context.MODE_PRIVATE);
        final String token =sharedPreferences.getString("token","2");
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



/*    private OkHttpClient getHeader() {

//        sharedPreferences=getSharedPreferences("login",Context.MODE_PRIVATE);
//        email= sharedPreferences.getString("emailkey","3");
        sharedPreferences=getActivity().getSharedPreferences("signup",Context.MODE_PRIVATE);
        final String token =sharedPreferences.getString("token","2");
        Log.i("token", token);
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
//                                .addHeader("Authorization", "Basic cGl4YWxpdmU6REFGODdEU0ZEU0ZEU0E5OEZTQURLSkUzMjRLSkwzMkhGRDdGRFNGQjI0MzQzSjQ5RFNG")
                                .addHeader("Authorization", "Bearer eyJ0eXAiOiJqd3QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPRGc9Iiwic3ViIjoiSGFpciBJbmZlcm5vIiwiYXVkIjoiYzJSbVlYTmtaZz09IiwianRpIjoiWVhOa1ptRnpaZz09IiwiZXhwIjoiMjAxOS0wNC0yNiAxMjoyMDo0OCIsIm5iZiI6IjIwMTktMDQtMTggMDk6NTA6NDgifQ.0OKXjJhFiwykytV4uWIYFlaokMdzofV_dU2R3wg9xJQ")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();
    }*/



   /* private void setData() {


        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), HORIZONTAL, true));

//        for (int i = 0; i < 100; i++) {
//            list.add(new DataList("java", ""));
//        }

        list=new ArrayList<RESULTHOME>();
       // list=(ArrayList)resp;
        list.add(resulthome.getLikeCount());
        list.add(resulthome.getUserImage());
        list.add(resulthome.getName());
        list.add(resulthome.getIsLike());

        recycler.setAdapter(new ViewAdaptor(list,context));



//        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.Base_Url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        Api api = retrofit.create(Api.class);
//
//        Call<List<ContactsContract.Contacts.Data>> call = api.getPost();
//        call.enqueue(new Callback<List<Data>>() {
//            @Override
//            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
//
//                Log.i("tag", "onResponse: ");
//                list = (ArrayList) response.body();
//                recycler.setLayoutManager(new LinearLayoutManager(Homefragment.this));
//                recycler.setAdapter(new ViewAdaptor(list, context));
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Data>> call, Throwable t) {
//
//            }
//        });
    }*/




/*    private void setContentData()
    {




    }*/
}
