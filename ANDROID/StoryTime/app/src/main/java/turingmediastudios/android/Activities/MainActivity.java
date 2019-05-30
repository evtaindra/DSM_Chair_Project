package turingmediastudios.android.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import turingmediastudios.android.Fragments.HomeFragment;
import turingmediastudios.android.Fragments.ProfileFragment;
import turingmediastudios.android.Fragments.WriteFragment;
import turingmediastudios.android.Models.SharedPreferencesManager;
import turingmediastudios.android.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private FragmentManager mFragmentManager;

    private Fragment homeFragment;
    private Fragment writeFragment;
    private Fragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //welcome message
        Toast.makeText(this,
                getResources().getString(R.string.welcome_message) +
                        " " +
                        SharedPreferencesManager.getInstance(this).getLoggedUser().getUser_name(),
                Toast.LENGTH_SHORT).show();

        initLayouts();

    }

    private void initLayouts() {

        //Init fragments
        homeFragment = new HomeFragment();
        writeFragment = new WriteFragment();
        profileFragment = new ProfileFragment();

        //R
        mBottomNavigationView = findViewById(R.id.mainBottomNavigationView);
        mFragmentManager = getSupportFragmentManager();

        //Default fragment to load
        mFragmentManager.beginTransaction().replace(R.id.mainContainer, homeFragment).commit();

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                //Current fragment selected
                Fragment currentFragment = null;

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        currentFragment = homeFragment;
                        break;
                    case R.id.nav_write:
                        currentFragment = writeFragment;
                        break;
                    case R.id.nav_profile:
                        currentFragment = profileFragment;
                        break;
                }

                //Set current fragment on the container
                mFragmentManager.beginTransaction().replace(R.id.mainContainer, currentFragment).commit();

                return true;
            }

        });

    }

}
