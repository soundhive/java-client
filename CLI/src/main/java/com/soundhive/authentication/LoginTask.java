package com.soundhive.authentication;

import javafx.concurrent.Task;



public class LoginTask extends Task<SessionHandler.LoginStatus> {

    private final String username;
    private final String password;
    private final boolean stayConnected;

    private final SessionHandler session;

    public LoginTask(final String username, final String password, final boolean stayConnected, final SessionHandler session) {
        this.username = username;
        this.password = password;
        this.stayConnected = stayConnected;
        this.session = session;
    }

    protected SessionHandler.LoginStatus call() {
        return this.session.openSession(username, password, stayConnected);
    }
}