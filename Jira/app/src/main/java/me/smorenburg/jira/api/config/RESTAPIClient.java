package me.smorenburg.jira.api.config;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import me.smorenburg.jira.BuildConfig;
import me.smorenburg.jira.api.apicore.SessionRequestInterceptor;
import me.smorenburg.jira.api.services.IWebServiceApiService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RESTAPIClient {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssz";

    private IWebServiceApiService webServiceApiService;
    private final Gson gson;
    private final Retrofit retrofit;

    public RESTAPIClient(Context context) {

        gson = new GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .create();


        SessionRequestInterceptor sessionRequestInterceptor = new SessionRequestInterceptor(context);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.connectTimeout(60, TimeUnit.SECONDS); // connect timeout
        httpClient.readTimeout(60, TimeUnit.SECONDS);    // socket read timeout
        httpClient.writeTimeout(60, TimeUnit.SECONDS);    // socket write timeout
        httpClient.retryOnConnectionFailure(true);

        // add sessionRequestInterceptor as last interceptor
        httpClient.addInterceptor(sessionRequestInterceptor);  // <-- this is the important line!

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public IWebServiceApiService getWebServiceApiService() {
        if (webServiceApiService == null)
            webServiceApiService = retrofit.create(IWebServiceApiService.class);
        return webServiceApiService;
    }


    public Gson getGson() {
        return gson;
    }
}
