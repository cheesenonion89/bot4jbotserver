package ai.nitro.bot4j.rest.domain;

import ai.nitro.bot4j.middle.domain.send.payload.ImageSendPayload;

import java.sql.Timestamp;

/**
 * Created by Markus on 12.04.2017.
 */
public class Base64ImageSendPayload extends ImageSendPayload{

    private int messageId;
    private String base64Image;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public Base64ImageSendPayload(int messageId, String base64Image){
        setTitle(new Timestamp(System.currentTimeMillis()).toString());
        setMessageId(messageId);
        setBase64Image(base64Image);
    }

    public Base64ImageSendPayload(int messageId, String title, String base64Image){
        setTitle(new Timestamp(System.currentTimeMillis()).toString() + " " + title);
        setMessageId(messageId);
        setBase64Image(base64Image);
    }

}
