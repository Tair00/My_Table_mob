package ru.mvlikhachev.mytablepr.Domain;

import com.google.gson.annotations.SerializedName;

public class LoginResult {
    @SerializedName("access_token")
    private String token;
    private String name;

    private String password;

    public String getName() {
        return name;
    }
    public String getToken() {
        return token;
    }

    public String getPassword() {
        return password;
    }
}
