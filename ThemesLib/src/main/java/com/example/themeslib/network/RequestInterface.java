package com.example.themeslib.network;



import com.example.themeslib.model.Datum;
import com.example.themeslib.model.RetroData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RequestInterface {
   @GET("/theme/list")
   Call<RetroData> getData(@Header("Authorization") String authorization, @Header("Content-Type") String contentType, @Query("all") String all);
//http://api.rocksplayer.com/theme/list?all=1(GET)

   @Streaming
   @GET("/images/screens")
   Call<List<Datum>> downloadFileWithDynamicUrlSync(@Url String fileUrl);
}