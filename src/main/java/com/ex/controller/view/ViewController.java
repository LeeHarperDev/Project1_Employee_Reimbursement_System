package com.ex.controller.view;

import io.javalin.http.Context;
import com.ex.model.User;

public interface ViewController {
    boolean isAuthorized(User user);
    void attemptDisplay(Context context);
    void show(Context context);
}
