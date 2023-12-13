
package com.spotify.oauth2.pojo;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter

@Setter


public class IteamToGetPlayList {
	

    @JsonProperty("href")
    private String href;
    @JsonProperty("items")
    private List<Item> items;
    @JsonProperty("limit")
    private Integer limit;
    @JsonProperty("next")
    private Object next;
    @JsonProperty("offset")
    private Integer offset;
    @JsonProperty("previous")
    private Object previous;
    @JsonProperty("total")
    private Integer total;

 


}
