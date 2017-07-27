package ai.nitro.bot4j.rest.domain;

import ai.nitro.bot4j.middle.domain.send.payload.ImageSendPayload;

import java.sql.Timestamp;

/*
 * Domain class to summarize the information to send to the CNN Server to receive back a classification for an image.
 */
public class Base64ImageSendPayload extends ImageSendPayload {

    private int messageId;
    private String base64Image;


    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }


    public Base64ImageSendPayload(int messageId, String title, String base64Image) {
        setTitle(new Timestamp(System.currentTimeMillis()).toString() + " " + title);
        setMessageId(messageId);
        setBase64Image(base64Image);
    }

}
