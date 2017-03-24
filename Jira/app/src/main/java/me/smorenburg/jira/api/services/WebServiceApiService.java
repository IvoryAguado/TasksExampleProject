package me.smorenburg.jira.api.services;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import me.smorenburg.jira.api.config.RESTAPIClient;
import me.smorenburg.jira.api.apicore.APIConnectorCallBack;
import me.smorenburg.jira.api.apicore.RestApiCallback;
import me.smorenburg.jira.db.models.base.WebServiceData;
import retrofit2.Call;
import retrofit2.Response;



public class WebServiceApiService {

    private IWebServiceApiService webServiceApiService;


    public WebServiceApiService(Context context) {

        RESTAPIClient restapiClient = new RESTAPIClient(context);
        webServiceApiService = restapiClient.getWebServiceApiService();
        if (webServiceApiService == null)
            try {
                throw new Exception("Not API Instantiated");
            } catch (Exception e) {

            }
    }
    public IWebServiceApiService getWebServiceApiService() {
        return webServiceApiService;
    }
    public void getAll( @NonNull final APIConnectorCallBack<List<WebServiceData>> apiConnectorCallBack) {
        Call<List<WebServiceData>> item1 = webServiceApiService.getAll();
        item1.enqueue(new RestApiCallback<List<WebServiceData>>() {
            @Override
            public void onResponse(Call<List<WebServiceData>> call, Response<List<WebServiceData>> response) {
                apiConnectorCallBack.resultCallBack(response.body());
            }

            @Override
            public void onFailure(Call<List<WebServiceData>> call, Throwable t) {
                apiConnectorCallBack.resultCallBack(null);

            }
        });
    }

    public void webServiceData(String category, String item, @NonNull final APIConnectorCallBack<List<WebServiceData>> apiConnectorCallBack) {
        Call<List<WebServiceData>> item1 = webServiceApiService.findItem(category, item);
        item1.enqueue(new RestApiCallback<List<WebServiceData>>() {
            @Override
            public void onResponse(Call<List<WebServiceData>> call, Response<List<WebServiceData>> response) {
                apiConnectorCallBack.resultCallBack(response.body());
            }

            @Override
            public void onFailure(Call<List<WebServiceData>> call, Throwable t) {
                apiConnectorCallBack.resultCallBack(null);

            }
        });
    }
}
