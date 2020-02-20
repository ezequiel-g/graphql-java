package com.howtographql.hackernews;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a filter for searching for links
 */
public class LinkFilter {

    private String descriptionContains;
    private String urlContains;

    @JsonProperty("description_contains")
    public String getDescriptionContains() {
        return descriptionContains;
    }

    public void setDescriptionContains(final String descriptionContains) {
        this.descriptionContains = descriptionContains;
    }

    @JsonProperty("url_contains")
    public String getUrlContains() {
        return urlContains;
    }

    public void setUrlContains(final String urlContains) {
        this.urlContains = urlContains;
    }
}
