package com.example.themeslib;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themeslib.model.Datum;
import com.example.themeslib.model.RetroData;
import com.example.themeslib.network.RequestInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoadThemesFromApi extends AppCompatActivity implements OnButtonClickListener {

    public static final String Authorization = "S^e#r7#&01)b8r*(#%^@T";
    public static final String  contentType ="application/json";
    private boolean isDownload;
    private Button downloadBtn;
    private String name;
    private String url;
    private ProgressBar progress;
    boolean isDownloaded = false;
    public final int  REQUEST_CODE_FOR_PERMISSIONS = 123;

    RecyclerView recyclerView;

    private void allStationData(Context context) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.rocksplayer.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<RetroData> call = requestInterface.getData(Authorization, contentType, "1");
        call.enqueue(new Callback<RetroData>() {



            @Override
            public void onResponse(Call<RetroData> call, Response<RetroData> response) {
                RetroData model = response.body();
                if (model != null) {
                    generateDataList((model.getData()), null);
                } else {
                    Toast.makeText(context, "No data to show", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RetroData> call, Throwable t) {

            }

            private void generateDataList(List<Datum> data, List<Datum> themeList) {
                CustomAdapter adapter;
                recyclerView = findViewById(R.id.show_theme_data_recycler_view);

                List<Datum> mList = new ArrayList<>();
                for (Datum item : themeList) {
                    if (item.getImgUrl() != null && !item.getImgUrl().equals("null")) {
                        mList.add(item);
                    }
                }
                adapter = new CustomAdapter(context, data, LoadThemesFromApi.this::onDownloadBtnClicked);

                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 3);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

            }

        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDownloadBtnClicked(String str1, String str2, Button downloadBtn, ProgressBar progress, boolean download_progress) {
            this.url = url;
            this.name = name;
            this.downloadBtn = downloadBtn;
            this.isDownload = isDownloaded;
            this.progress = progress;
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
            {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    //beginDownload();
                }
                else
                {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_FOR_PERMISSIONS);
                }
            }
            else
            {
                //beginDownload();//download without permission
            }
    }

}