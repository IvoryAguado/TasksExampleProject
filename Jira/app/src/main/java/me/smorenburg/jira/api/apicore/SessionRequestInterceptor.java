package me.smorenburg.jira.api.apicore;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import me.smorenburg.jira.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * You just have to check if a user session is opened and add in this
 * case your credentials to the header. So create a file called SessionRequestInterceptor.java
 * and your class has to implement RequestInterceptor and then you have to implement the intercept method.
 */
public class SessionRequestInterceptor implements Interceptor {

//    private StoreManager storeManager;

    public SessionRequestInterceptor(Context context) {
//        storeManager = new StoreManager(context);
    }


    @Override
    public Response intercept(Chain chain) throws IOException {


        Request originalRequest = chain.request(); //Current Request

        // Request customization: add request headers
        Request.Builder requestBuilder = originalRequest.newBuilder()
                .header("Content-Type", "application/json");

//        String token;
//        if ((token = storeManager.getToken()) != null)
//            requestBuilder.header("Authorization", "Bearer " + token);

        Response response = chain.proceed(requestBuilder.build()); //Get response of the request

        /** DEBUG STUFF */
        if (BuildConfig.BUILD_TYPE == "debug") {
            //I am logging the response body in debug mode. When I do this I consume the response (OKHttp only lets you do this once) so i have re-build a new one using the cached body
            Log.d("API", String.format("Sending request %s with headers %s ", originalRequest.url().toString().replace("\t","").trim(), originalRequest.headers().toString().trim()));

            String bodyString = response.body().string();
            response = response.newBuilder().body(ResponseBody.create(response.body().contentType(), bodyString)).build();
            Log.d("API", String.format("Got response HTTP %s %s  with body %s  with headers %s", response.code(), response.message().trim(), bodyString.trim(), response.headers().toString().replace("\t","").trim()));

        }
        return response;
    }
}
