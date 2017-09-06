package com.hagai.realitest.utils.retrofit;

import com.hagai.realitest.utils.rss_parser.RssConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by hagay on 9/5/2017.
 */
public class RetrofitClient {
    public static String mBase_url = "http://www.globes.co.il/";
    private static Retrofit mRetrofit = null;

    public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        mRetrofit = null;
        mRetrofit = new Retrofit.Builder()
                .baseUrl(mBase_url)
                .addConverterFactory(RssConverterFactory.create())
                .client(client)
                .build();
        return mRetrofit;
    }
}
