package com.faizurazadri.pustakaku.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAuthor {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<AuthorModel> authorModels;

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<AuthorModel> getAuthorModels() {
        return authorModels;
    }
}
