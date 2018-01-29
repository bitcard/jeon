package io.github.ma1uta.matrix.client.model.account;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement
public class AuthenticationData {

    /** AuthType */
    private String type;
    private String session;

    private String user;
    private String password;

    private String medium;
    private String address;

    private String response;

    private String token;
    private String txnId;

    private String uri;

    private ThreePidCred[] threePidCreds;
}