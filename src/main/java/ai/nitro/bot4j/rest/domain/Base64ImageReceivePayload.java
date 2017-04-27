package ai.nitro.bot4j.rest.domain;

import ai.nitro.bot4j.middle.domain.receive.payload.PostbackReceivePayload;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Markus on 12.04.2017.
 */
public class Base64ImageReceivePayload {

    @SerializedName("title")
    public String title;

    @SerializedName("labels")
    public List<String> labels;

    @SerializedName("probabilities")
    public List<Float> probabilities;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<Float> getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(List<Float> probabilities) {
        this.probabilities = probabilities;
    }

    @Override
    public String toString(){
        return title + "\n" +
                labels.toString() + "\n" +
                probabilities.toString();
    }
}
