package com.ex.controller.auth;

import com.ex.exception.NotLoggedInException;
import com.ex.exception.UserNotAuthorizedException;
import com.ex.model.User;

public class AuthorizationController {

    private Roles authorizedRole;

    public AuthorizationController(Roles roleType) {
        this.authorizedRole = roleType;
    }

    public boolean checkIfAuthorized(User user) {
        try {
            if (user == null) {
                throw new NotLoggedInException();
            } else if (user.getRole().getId() < this.authorizedRole.ordinal()) {
                throw new UserNotAuthorizedException();
            } else {
                return true;
            }
        } catch (NotLoggedInException e) {
            return false;
        } catch (UserNotAuthorizedException e) {
            return false;
        }
    }
}
