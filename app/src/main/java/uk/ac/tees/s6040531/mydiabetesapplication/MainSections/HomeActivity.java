package uk.ac.tees.s6040531.mydiabetesapplication.MainSections;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Objects;

import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.ForumSection.ForumFragment;
import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeSection.HomeFragment;
import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.SettingsSection.SettingsFragment;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
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

    // Variable for progress bar
    ProgressBar pbHome;

    // Variables for user and firebase access
    User user;
    FirebaseAuth auth;
    FirebaseFirestore udbRef;

    // Variable to store the previous activity
    String prev;

    // Variables for navigation
    BottomNavigationView navView;
    Fragment activeFragment = homeFragment;

    /**
     * onCreate() method
     * @param savedInstanceState - instance bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialises progress bar and shows it
        pbHome = findViewById(R.id.pb_home);
        pbHome.setVisibility(View.VISIBLE);

        // Sets up the navigation view
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_home);

        // Call getUser()
        getUser();
    }

    /**
     * Sets the navigation item listener
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        /**
         * onNavigationItemSelected() method
         * @param item - menu item selected
         * @return selected item fragment
         */
        @Override public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            // Returns the relevant fragment
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
                    pbHome.setVisibility(View.GONE);
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
     * getUser() method
     */
    public void getUser()
    {
        // Initialises firebase variables
        auth = FirebaseAuth.getInstance();
        udbRef = FirebaseFirestore.getInstance();

        // Grabs the user from the database
        udbRef.collection("users").document(Objects.requireNonNull(auth.getUid())).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            /**
             * onSuccess() method
             * @param documentSnapshot - database document
             */
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                // Saves the user
                user = documentSnapshot.toObject(User.class);

                // Cache's the user's data
                SharedPreferences myPref = getSharedPreferences(getResources().getString(R.string.pref_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor prefEd = myPref.edit();
                Gson gson = new Gson();
                String json = gson.toJson(user);
                prefEd.putString(getResources().getString(R.string.user_key),json);
                prefEd.apply();

                // Calls setUpFrags()
                setUpFrags();
            }
        });
    }

    /**
     * setUpFrags() method
     */
    public void setUpFrags()
    {
        // Adds all the fragments to the fragments manager
        fragMan.beginTransaction().add(R.id.home_parent, forumFragment,"3").hide(forumFragment).commit();
        fragMan.beginTransaction().add(R.id.home_parent, settingFragment,"1").hide(settingFragment).commit();

        // Hides the progress bar
        pbHome.setVisibility(View.GONE);

        // Sets the home fragment to be shown
        fragMan.beginTransaction().add(R.id.home_parent, homeFragment,"2").commit();
    }
}