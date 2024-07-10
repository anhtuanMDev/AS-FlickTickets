package com.example.movieticket.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.movieticket.R;
import com.example.movieticket.databinding.ActivityMainBinding;
import com.example.movieticket.interfaces.MainActivityUtils;
import com.example.movieticket.utils.ActivityUtils;
import com.example.movieticket.utils.MainUtils;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MainActivityUtils {

    private static final int HOME_TITLE = R.string.home_title;
    private static final int FAVORITE_TITLE = R.string.favorite;
    private static final int SCHEDULE_TITLE = R.string.schedule;
    private static final int CART_TITLE = R.string.cart;

    private ActivityMainBinding binding;
    private NavController navController;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    boolean isLoggedIn = false;
    private MainUtils utils;
    private ActivityUtils funcUtils;
    private LinearLayout lnrLogout;
    private TextView name, infor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Handle the splash screen transition.
        SplashScreen.installSplashScreen(this);
        getSplashScreen().setOnExitAnimationListener(splashScreenView -> {
            final ObjectAnimator slideUp = ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.TRANSLATION_Y,
                    0f,
                    -splashScreenView.getHeight()
            );
            slideUp.setInterpolator(new AnticipateInterpolator());
            slideUp.setDuration(500L);

            // Call SplashScreenView.remove at the end of your custom animation.
            slideUp.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    splashScreenView.remove();
                }
            });

            slideUp.start();
        });
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup tool bar and change the title
        setSupportActionBar(binding.mainContent.mainContentAppBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.home_title);
        }

        // Setup the listener for drawer
        drawerLayout = binding.drawerLayout;
        utils = new MainUtils(this);
        funcUtils = new ActivityUtils(this);
        lnrLogout = binding.logout;
        View headerView = binding.navView.getHeaderView(0);
        name = headerView.findViewById(R.id.nav_header_name);
        infor = headerView.findViewById(R.id.nav_header_email);
        utils.verifyToken(binding.getRoot(), this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                binding.mainContent.mainContentAppBar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // Setup the listener for bottom navigation
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_content_fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.mainContent.navigation, navController);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Set up the listener for BottomNavigationView
        binding.mainContent.navigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            try {
                switch (id) {
                    case 2131296604:
                        navController.navigate(R.id.nav_home);
                        Objects.requireNonNull(getSupportActionBar()).setTitle(HOME_TITLE);
                        break;
                    case 2131296601:
                        navController.navigate(R.id.nav_fav);
                        Objects.requireNonNull(getSupportActionBar()).setTitle(FAVORITE_TITLE);
                        break;
                    case 2131296606:
                        navController.navigate(R.id.nav_order);
                        Objects.requireNonNull(getSupportActionBar()).setTitle(SCHEDULE_TITLE);
                        break;
                    case 2131296599:
                        navController.navigate(R.id.nav_cart);
                        Objects.requireNonNull(getSupportActionBar()).setTitle(CART_TITLE);
                        break;
                    default:
                        Log.d("MainActivity Error", "Something has happened, the id is " + id);
                        return false; // Return false if no case matches
                }
                return true;
            } catch (Exception e) {
                Log.d("MainActivity Error", Objects.requireNonNull(e.getMessage()));
                return false;
            }
        });

        // Set up the listener for Drawer Navigation View
        binding.navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case 2131296665:
                    // Handle profile item click
                    if (!isLoggedIn) {
                        funcUtils.changeActivity(LoginActivity.class);
                    } else {
                        funcUtils.changeActivity(ProfileActivity.class);
                    }
                    break;
                case 2131296645:
                    // Handle order item click
                    if (!isLoggedIn) {
                        funcUtils.changeActivity(LoginActivity.class);
                    } else {
                        funcUtils.changeActivity(HistoryActivity.class);
                    }
                    break;
                default:
                    Log.d("MainActivity Error", "Unhandled navigation drawer item: " + id);
                    return false; // Return false if no case matches
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Setup the listener for the logout button
        utils.changeDrawerContent(name, infor);
        lnrLogout.setOnClickListener(v->{
            utils.logout();
            drawerLayout.closeDrawer(GravityCompat.START);
            funcUtils.restartActivity(MainActivity.class);
            finish();
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        utils.verifyToken(binding.getRoot(), this);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        if (id == R.id.nav_profile) {
            if (!isLoggedIn) {
                funcUtils.changeActivity(LoginActivity.class);
            } else {
                funcUtils.changeActivity(ProfileActivity.class);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        updateToolbarIcon(menu);
        return true;
    }

    private void updateToolbarIcon(Menu menu) {
        MenuItem userAvatarMenuItem = menu.findItem(R.id.nav_profile);
        if (isLoggedIn) {
            userAvatarMenuItem.setIcon(R.drawable.avatar_base);
        } else {
            userAvatarMenuItem.setIcon(R.drawable.avatar_pre);
        }
    }

    @Override
    public void onVerificationComplete(boolean isSuccess) {
        isLoggedIn = isSuccess;
        invalidateOptionsMenu();
    }
}
