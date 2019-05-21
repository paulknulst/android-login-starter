package de.paulscode.login_starter.data;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import de.paulscode.login_starter.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private MutableLiveData<LoggedInUser> user = new MutableLiveData<>();

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository(new LoginDataSource());
        }
        return instance;
    }

    public LiveData<LoggedInUser> getLoggedInUser() {
        return this.user;
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user.setValue(user);

        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public boolean isLoggedIn() {
        return user.getValue() != null;
    }

    public void logout() {
        user.setValue(null);
        dataSource.logout();
    }

    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }
}
