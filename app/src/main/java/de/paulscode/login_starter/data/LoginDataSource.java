package de.paulscode.login_starter.data;

import java.io.IOException;

import de.paulscode.login_starter.data.model.LoggedInUser;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        if ((username.equals("admin") && password.equals("admin"))
                || (username.equals("ela") && password.equals("elaela"))
                || (username.equals("paul") && password.equals("paul1"))
                || (username.equals("mia") && password.equals("miamaus"))) {
            try {
                LoggedInUser loggedInUser =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                username);
                return new Result.Success<>(loggedInUser);
            } catch (Exception e) {
                return new Result.Error(new IOException("Error logging in", e));
            }
        } else {
            return new Result.Error(new IOException("Error logging in"));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
