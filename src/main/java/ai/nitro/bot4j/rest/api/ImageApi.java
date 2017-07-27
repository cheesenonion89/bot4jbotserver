package ai.nitro.bot4j.rest.api;

import ai.nitro.bot4j.rest.domain.Base64ImageSendPayload;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/*
 * HTTP enpoint to send images to the CNN Server
 */
public interface ImageApi {

    @POST("classify/{bot_id}")
    Call<String> postBase64Image(
            @Path("bot_id") String botId,
            @Body Base64ImageSendPayload base64Image
    );
}
