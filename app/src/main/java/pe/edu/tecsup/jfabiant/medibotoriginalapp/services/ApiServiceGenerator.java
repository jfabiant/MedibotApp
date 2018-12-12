package pe.edu.tecsup.jfabiant.medibotoriginalapp.services;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public final class ApiServiceGenerator {

    private static Retrofit retrofit;

    private static final String TAG = ApiServiceGenerator.class.getSimpleName();

    private ApiServiceGenerator() {
    }

    public static <S> S createService(final Context context, Class<S> serviceClass) {
        if(retrofit == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS);

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    try {
                        Request originalRequest = chain.request();

                        SharedPreferences preferences = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
                        String token = preferences.getString("token", "");

                        Log.d("Loaded token:  ", token);

                        if (token == null) {
                            // Firsttime assuming login
                            return chain.proceed(originalRequest);
                        }

                        Request modifiedRequest = originalRequest.newBuilder()
                                .header("Authorization", "")
                                .build();


                        Log.d("Token passing: ", modifiedRequest.toString());

                        Response response = chain.proceed(modifiedRequest);

                        if (response.code() == 401) {

                            Log.d(TAG, "Response " + response.code() + ": token_expired, refreshing token...");

                        } else {
                            Log.d(TAG, "Response " + response.code() + ": OK");
                        }
                        return response;

                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                        throw e;
                    }
                }
            });

            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiService.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()).build();

        }
        return retrofit.create(serviceClass);
    }

}