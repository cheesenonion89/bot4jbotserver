/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.bots;

import ai.nitro.bot4j.bot.impl.BotImpl;
import ai.nitro.bot4j.middle.domain.Participant;
import ai.nitro.bot4j.middle.domain.receive.payload.PostbackReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.TextReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.UrlAttachmentReceivePayload;
import ai.nitro.bot4j.rest.ApiProviderService;
import ai.nitro.bot4j.rest.api.ImageApi;
import ai.nitro.bot4j.rest.domain.Base64ImageReceivePayload;
import ai.nitro.bot4j.rest.domain.Base64ImageSendPayload;
import ai.nitro.bot4j.rest.domain.ImageNetResult;
import ai.nitro.bot4j.rest.domain.ImageNetResultList;
import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;


public class InceptionBotNew extends BotImpl {

    private static final String BUTTON = "button";

    protected final static Logger LOG = LogManager.getLogger(InceptionBotNew.class);

    ImageApi imageApi = ApiProviderService.createService(ImageApi.class);

    @Override
    protected void onReceivePostback(final PostbackReceivePayload postback, final Participant sender) throws Exception {

        LOG.info("ON RECEIVE POSTBACK");
        final Participant recipient = sender;

        final String name = postback.getName();
        final String[] payload = postback.getPayload();

        switch (postback.getName()) {
            case BUTTON:
                final String joinedPayload = StringUtils.join(payload, ", ");
                sendText(joinedPayload, recipient);
                break;
            default:
                LOG.warn("Unknown postback {}", name);
        }
    }

    @Override
    protected void onReceiveText(final TextReceivePayload receiveTextPayload, final Participant sender)
            throws Exception {
        final Participant recipient = sender;
        final String text = receiveTextPayload.getText();

        LOG.info("ON RECEIVE TEXT");
        LOG.info("received {}", text);

        sendText("processing your message...", recipient);

    }

    @Override
    protected void onReceiveAttachment(final UrlAttachmentReceivePayload payload, final Participant sender) {
        final Participant recipient = sender;
        Base64ImageReceivePayload base64ImageReceivePayload = null;
        ImageNetResultList imageNetResultList = null;

        try {
            // Currently not working. call.execute results in an error
            //Call<Base64ImageReceivePayload> call = api.putBase64Image(getBase64ImageSendPayload(0, payload.getTitle(), payload.getUrl()));
            //Response<Base64ImageReceivePayload> response = call.execute();

            //TODO: Current workaround, because direct object parsing does not work
            Call<String> call = imageApi.putBase64ImageString(getBase64ImageSendPayload(0, payload.getTitle(), payload.getUrl()));
            Response<String> response = call.execute();
            LOG.warn(response.body());
            Gson gson = new Gson();
            imageNetResultList = gson.fromJson(response.body(), ImageNetResultList.class);
        } catch (IOException e) {
            LOG.warn(e);
        }

        if (imageNetResultList != null) {
            List<ImageNetResult> imageNetResults = imageNetResultList.getResults();
            ImageNetResult bestResult = imageNetResults.get(0);
            String firstBestLabel = bestResult.getLabels().get(0);
            Float score = bestResult.getScore() * 100;

            String reply = String.format("My neurons tell me that this is a %s. (Probability: %s%s)", firstBestLabel, score, '%');
            sendText(reply, recipient);
        }
        else if (base64ImageReceivePayload != null) {
            final int probIndex = argMax(base64ImageReceivePayload.getProbabilities());
            final float prob = base64ImageReceivePayload.getProbabilities().get(probIndex);
            final String label = base64ImageReceivePayload.getLabels().get(probIndex);
            String reply = String.format("I am %s percent certain, that this is a %s", prob, label);
            sendText(reply, recipient);
        } else {
            sendText("Something went wrong", recipient);
        }



        LOG.info("RECEIVED AN ATTACHMENT");

    }

    private Base64ImageSendPayload getBase64ImageSendPayload(final int messageId, String title, final String url) {
        InputStream inputStream = null;
        Base64ImageSendPayload result = null;

        if (title == null) {
            title = "";
        }

        try {

            inputStream = new URL(url).openStream();
            final String base64Image = Base64.encodeBase64String(IOUtils.toByteArray(inputStream));

            result = new Base64ImageSendPayload(messageId, title, base64Image);

        } catch (final Exception e) {
            LOG.warn(e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (final IOException e) {
                LOG.warn(e);
            }

        }
        return result;
    }

    private int argMax(List<Float> list) {
        int argMax = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > list.get(argMax)) {
                argMax = i;
            }
        }
        return argMax;
    }

}