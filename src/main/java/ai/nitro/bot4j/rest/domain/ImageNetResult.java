package ai.nitro.bot4j.rest.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Markus on 12.04.2017.
 */
public class ImageNetResult {

    @SerializedName("labels")
    List<String> labels;

    @SerializedName("probabilities")
    List<String> probabilties;

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<String> getProbabilities() {
        return this.probabilties;
    }

    public void setProbabilties(List<String> probabilties) {
        this.probabilties = probabilties;
    }
}
