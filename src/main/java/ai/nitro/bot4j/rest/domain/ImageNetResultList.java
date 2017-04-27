package ai.nitro.bot4j.rest.domain;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Markus on 12.04.2017.
 */
public class ImageNetResultList {
    @SerializedName("results")
    List<ImageNetResult> results;

    public List<ImageNetResult> getResults() {
        return results;
    }

    public void setResults(List<ImageNetResult> results) {
        this.results = results;
    }
}
