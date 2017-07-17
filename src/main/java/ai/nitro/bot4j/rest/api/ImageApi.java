package ai.nitro.bot4j.rest.api;

import ai.nitro.bot4j.rest.domain.Base64ImageReceivePayload;
import ai.nitro.bot4j.rest.domain.Base64ImageSendPayload;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by Markus on 11.04.2017.
 */
public interface ImageApi {

    @POST("classify")
    Call<Base64ImageReceivePayload> putBase64Image(@Body Base64ImageSendPayload base64ImageSendPayload);

    @POST("classify/{bot_id}")
    Call<String> putBase64ImageString(
            @Path("bot_id") String botId,
            @Body Base64ImageSendPayload base64Image
    );
}
