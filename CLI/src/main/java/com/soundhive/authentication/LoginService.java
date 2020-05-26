package com.soundhive.authentication;

import com.soundhive.response.Response;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import kong.unirest.json.JSONObject;


public class LoginService extends Service<Response<Void>> {

    private final StringProperty usernameProperty;
    private final StringProperty passwordProperty;
    private final BooleanProperty stayConnected;
    private final SessionHandler session;

    public LoginService(final StringProperty usernameProperty, final StringProperty passwordProperty, final BooleanProperty stayConnected, final SessionHandler session) {
        this.usernameProperty = usernameProperty;
        this.passwordProperty = passwordProperty;
        this.stayConnected = stayConnected;
        this.session = session;
    }

    protected Task<Response<Void>> createTask() {
        return new LoginTask(usernameProperty.getValueSafe(),
                passwordProperty.getValueSafe(),
                this.stayConnected.getValue(),
                this.session);
    }
}
