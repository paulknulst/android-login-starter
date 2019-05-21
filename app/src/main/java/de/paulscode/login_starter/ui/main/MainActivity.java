package de.paulscode.login_starter.ui.main;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import de.paulscode.login_starter.R;
import de.paulscode.login_starter.data.LoginRepository;
import de.paulscode.login_starter.data.model.LoggedInUser;
import de.paulscode.login_starter.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static Context applicationContext;

    private LoginRepository loginRepository;
    private Toast backtoast;

    public static Context getContextOfApplication() {
        return applicationContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applicationContext = getApplicationContext();
        this.loginRepository = LoginRepository.getInstance();
        if (!this.loginRepository.isLoggedIn()) {
            logout();
        }
        this.loginRepository.getLoggedInUser().observe(this, new Observer<LoggedInUser>() {
            @Override
            public void onChanged(@Nullable LoggedInUser user) {
                if (user == null) {
                    logout();
                }
                setResult(Activity.RESULT_OK);
            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (backtoast != null && backtoast.getView().getWindowToken() != null) {
                backtoast.cancel();
                finish();
            } else {
                backtoast = Toast.makeText(this, "Press back to exit", Toast.LENGTH_SHORT);
                backtoast.show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Toast.makeText(getApplicationContext(), R.string.toast_function_not_implemented, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(getApplicationContext(), R.string.toast_function_not_implemented, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(getApplicationContext(), R.string.toast_function_not_implemented, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_tools) {
            Toast.makeText(getApplicationContext(), R.string.toast_function_not_implemented, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
            Snackbar.make(findViewById(R.id.drawer_layout), getString(R.string.toast_function_not_implemented), Snackbar.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Snackbar.make(findViewById(R.id.drawer_layout), getString(R.string.toast_function_not_implemented), Snackbar.LENGTH_SHORT).show();
        } else if (id == R.id.nav_logout) {
            LoginRepository.getInstance().logout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("user", 0);
        SharedPreferences.Editor edit = pref.edit();
        edit.clear().apply();
        Intent loginActivity = new Intent(this, LoginActivity.class);
        loginActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        loginActivity.putExtra("text-to-show", getString(R.string.logout_successfull));
        startActivity(loginActivity);
    }
}
