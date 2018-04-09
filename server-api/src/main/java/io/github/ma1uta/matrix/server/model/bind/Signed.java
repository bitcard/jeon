package io.github.ma1uta.matrix.server.model.bind;

import java.util.Map;

/**
 * Signed part of the invitation request.
 */
public class Signed {

    /**
     * Owner of the 3pid.
     */
    private String mxid;

    /**
     * Token which generated by server.
     */
    private String token;

    /**
     * Signatures by long-term identity server key.
     */
    private Map<String, Map<String, String>> signatures;

    public String getMxid() {
        return mxid;
    }

    public void setMxid(String mxid) {
        this.mxid = mxid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, Map<String, String>> getSignatures() {
        return signatures;
    }

    public void setSignatures(Map<String, Map<String, String>> signatures) {
        this.signatures = signatures;
    }
}