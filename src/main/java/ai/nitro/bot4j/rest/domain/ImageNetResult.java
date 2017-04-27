package ai.nitro.bot4j.rest.domain;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Markus on 12.04.2017.
 */
public class ImageNetResult {

    @SerializedName("labels")
    List<String> labels;

    @SerializedName("score")
    Float score;

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
