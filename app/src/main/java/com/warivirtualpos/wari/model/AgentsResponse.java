package com.warivirtualpos.wari.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hilary on 12/17/17.
 */

public class AgentsResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("responseBody")
    private List<Agent> responseBody;

    public AgentsResponse() {
    }

    public AgentsResponse(String status, List<Agent> responseBody) {
        this.status = status;
        this.responseBody = responseBody;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Agent> getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(List<Agent> responseBody) {
        this.responseBody = responseBody;
    }
}
