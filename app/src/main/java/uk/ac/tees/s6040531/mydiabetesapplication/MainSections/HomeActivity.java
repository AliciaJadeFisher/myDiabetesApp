package uk.ac.tees.s6040531.mydiabetesapplication.MainSections;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.ForumSection.ForumFragment;
import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeSection.HomeFragment;
import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.SettingsSection.SettingsFragment;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * HomeActivity
 */
public class HomeActivity extends AppCompatActivity
{
    // Declare the home screen fragments
    final HomeFragment homeFragment = new HomeFragment();
    final ForumFragment forumFragment = new ForumFragment();
    final SettingsFragment settingFragment = new SettingsFragment();
    final FragmentManager fragMan = getSupportFragmentManager();

    // Variable to store the previous activity
    String prev;

    // Variables for navigation
    BottomNavigationView navView;
    Fragment activeFragment = homeFragment;
    
    /**
     * onCreate() method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Sets up the navigation view
        navView = (BottomNavigationView) findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_home);
        getSupportActionBar().hide();

        // Adds all the fragments to the fragments manager and sets the home fragment to be shown
        fragMan.beginTransaction().add(R.id.home_parent, forumFragment,"3").hide(forumFragment).commit();
        fragMan.beginTransaction().add(R.id.home_parent, settingFragment,"1").hide(settingFragment).commit();
        fragMan.beginTransaction().add(R.id.home_parent, homeFragment,"2").commit();

        // Calls the method getIncomingIntent()
        getIncomingIntent();
    }

    /**
     * Sets the navigation item listener
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                // If the home icon is clicked, hides the current fragment and shows thte home fragment
                case R.id.navigation_home:
                    fragMan.beginTransaction().hide(activeFragment).show(homeFragment).commit();
                    activeFragment = homeFragment;
                    return true;
                // If the forum icon is clicked, hides the current fragment and shows the forum fragment
                case R.id.navigation_forum:
                    fragMan.beginTransaction().hide(activeFragment).show(forumFragment).commit();
                    activeFragment = forumFragment;
                    return true;
                // If the settings icon is clicked, hides the current fragment and shows the settings fragment
                case R.id.navigation_settings:
                    fragMan.beginTransaction().hide(activeFragment).show(settingFragment).commit();
                    activeFragment = settingFragment;
                    return true;
            }
            return false;
        }
    };


    /**
     * Retrieves data sent with the intent in the extra field
     */
    public void getIncomingIntent()
    {
        // Checks if the intent has field with the name prev
        if(this.getIntent().hasExtra("prev"))
        {
            //Grabs the data in the extra
            prev = this.getIntent().getStringExtra("prev");

            // If the previous activity was the CreateThreadActivity, it returns the user to the forum activity
            if(prev.equals("CreateThread"))
            {
                navView.setSelectedItemId(R.id.navigation_forum);
            }
        }
    }
}