package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ForumSection.ForumFragments.ForumFragment;
import uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.SettingsSection.SettingsFragment;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class HomeActivity extends AppCompatActivity
{
    final HomeFragment homeFragment = new HomeFragment();
    final AddFragment addFragment = new AddFragment();
    final ForumFragment forumFragment = new ForumFragment();
    final SettingsFragment settingFragment = new SettingsFragment();
    final FragmentManager fragMan = getSupportFragmentManager();

    String prev;

    Bundle userBundle = new Bundle();

    BottomNavigationView navView;
    Fragment activeFragment = homeFragment;

    User u;
    // Variable for Firebase access
    FirebaseAuth auth;

    FirebaseFirestore udbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navView = (BottomNavigationView) findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_home);
        getSupportActionBar().hide();

        // Grabs the current firebase auth instance
        auth = null;
        auth = FirebaseAuth.getInstance();
        udbRef = FirebaseFirestore.getInstance();

        getUser();

        fragMan.beginTransaction().add(R.id.home_parent, addFragment,"2").hide(addFragment).commit();
        fragMan.beginTransaction().add(R.id.home_parent, forumFragment,"3").hide(forumFragment).commit();
        fragMan.beginTransaction().add(R.id.home_parent, settingFragment,"4").hide(settingFragment).commit();
        fragMan.beginTransaction().add(R.id.home_parent, homeFragment,"1").commit();

        getIncomingIntent();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId()) {
                case R.id.navigation_add:
                    fragMan.beginTransaction().hide(activeFragment).show(addFragment).commit();
                    activeFragment = addFragment;
                    return true;
                case R.id.navigation_home:
                    fragMan.beginTransaction().hide(activeFragment).show(homeFragment).commit();
                    activeFragment = homeFragment;
                    return true;
                case R.id.navigation_forum:
                    fragMan.beginTransaction().hide(activeFragment).show(forumFragment).commit();
                    forumFragment.setUser(u);
                    activeFragment = forumFragment;
                    return true;
                case R.id.navigation_settings:
                    fragMan.beginTransaction().hide(activeFragment).show(settingFragment).commit();
                    activeFragment = settingFragment;
                    return true;
            }
            return false;
        }
    };

    public void getUser()
    {
        udbRef.collection("users").document(auth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                u = documentSnapshot.toObject(User.class);

                System.out.println("=================================== User : " + u.getName() + " =========================================");

                userBundle.putSerializable("user", u);

                homeFragment.setUser(u);
            }
        });
    }

    public void getIncomingIntent()
    {
        if(this.getIntent().hasExtra("prev"))
        {
            //Grabs the data in the extra
            prev = this.getIntent().getStringExtra("prev");

            if(prev.equals("CreateThread"))
            {
                fragMan.beginTransaction().detach(forumFragment).attach(forumFragment).commit();
                navView.setSelectedItemId(R.id.navigation_forum);
                activeFragment = forumFragment;
            }
        }
    }

}
