package me.smorenburg.jira.api.services;


import java.util.List;

import me.smorenburg.jira.db.models.base.WebServiceData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


public interface IWebServiceApiService {

    @GET("/resource/hma6-9xbg.json")
    Call<List<WebServiceData>> findItem(
            @Query("category") String category, @Query("item") String item);

    @GET("/resource/hma6-9xbg.json")
    Call<List<WebServiceData>> getAll();


    @GET
    @Streaming
    Call<ResponseBody> downloadFileUrlAsync(@Url String s);
}