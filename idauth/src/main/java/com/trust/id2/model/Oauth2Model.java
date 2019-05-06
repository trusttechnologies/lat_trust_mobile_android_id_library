package com.trust.id2.model;

public class Oauth2Model {
    private String scopes;
    private String acrValueLogin;
    private String acrValuePassword;
    private String redirectUri;
    private String baseUrl;
    private String clientId;
    private String clientSecret;

    public Oauth2Model(String scopes, String acrValueLogin, String acrValuePassword, String redirectUri, String baseUrl, String clientId, String clientSecret) {
        this.scopes = scopes;
        this.acrValueLogin = acrValueLogin;
        this.acrValuePassword = acrValuePassword;
        this.redirectUri = redirectUri;
        this.baseUrl = baseUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public String getAcrValueLogin() {
        return acrValueLogin;
    }

    public void setAcrValueLogin(String acrValueLogin) {
        this.acrValueLogin = acrValueLogin;
    }

    public String getAcrValuePassword() {
        return acrValuePassword;
    }

    public void setAcrValuePassword(String acrValuePassword) {
        this.acrValuePassword = acrValuePassword;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
