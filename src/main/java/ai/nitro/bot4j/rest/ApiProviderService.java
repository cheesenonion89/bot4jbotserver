package ai.nitro.bot4j.rest;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Markus on 27.04.2017.
 */
public class ApiProviderService {

    private final static String IMAGE_API_URL = "http://188.64.248.202:42023/";
    private final static String LOCAL_NETWORK_URL = "http://192.168.0.2:5000/";

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(LOCAL_NETWORK_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    public static <S> S createService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
