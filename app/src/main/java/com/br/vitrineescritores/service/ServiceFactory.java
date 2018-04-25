package com.br.vitrineescritores.service;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rlima on 24/04/18.
 */

public class ServiceFactory {
    public static final String TAG = ServiceFactory.class.getCanonicalName().toUpperCase();

    private static ServiceFactory instance;
    private final String baseUrl = "https://bibliapp.herokuapp.com/api/";
    private Retrofit retrofit;

    public static synchronized ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    public ServiceFactory() {
        setRetrofit(createService());
    }

    public Retrofit createService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        return retrofit;
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.readTimeout(30, TimeUnit.SECONDS);
        okHttpClient.interceptors().add(new LogInterceptor());
        return okHttpClient.build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    class LogInterceptor implements okhttp3.Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Log.d(TAG, String.format("Enviando requisição para o serviço. Url = %s, conexão = %s, cabeçalho= %s", request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Log.d(TAG, String.format("Resposta recebida da url %s em %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }
}
