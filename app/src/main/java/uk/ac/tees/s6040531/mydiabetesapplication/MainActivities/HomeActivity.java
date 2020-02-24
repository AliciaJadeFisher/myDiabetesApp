package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ForumFragments.ForumFragment;
import uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ForumFragments.ThreadFragment;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ForumThread;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class HomeActivity extends AppCompatActivity implements ForumFragment.ThreadFragGo
{
    final Fragment homeFragment = new HomeFragment();
    final Fragment addFragment = new AddFragment();
    final Fragment forumFragment = new ForumFragment();
    final Fragment threadFragment = new ThreadFragment();
    final FragmentManager fragMan = getSupportFragmentManager();

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

        final BottomNavigationView navView = (BottomNavigationView) findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_home);
        getSupportActionBar().hide();

        // Grabs the current firebase auth instance
        auth = null;
        auth = FirebaseAuth.getInstance();

        fragMan.beginTransaction().add(R.id.home_parent, addFragment,"1").hide(addFragment).commit();
        fragMan.beginTransaction().add(R.id.home_parent, forumFragment,"3").hide(forumFragment).commit();
        fragMan.beginTransaction().add(R.id.home_parent, threadFragment,"3").hide(threadFragment).commit();
        fragMan.beginTransaction().add(R.id.home_parent, homeFragment,"2").commit();

        getIncomingIntent();

        if(u == null)
        {
            udbRef.collection("users").whereEqualTo("id", auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
            {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task)
                {
                    if(task.isSuccessful())
                    {
                        DocumentSnapshot doc = task.getResult().getDocuments().get(0);
                        u = (User)doc.getData();

                        Bundle userBundle = new Bundle();
                        userBundle.putSerializable("user",u);

                        addFragment.setArguments(userBundle);
                        homeFragment.setArguments(userBundle);
                        forumFragment.setArguments(userBundle);
                        threadFragment.setArguments(userBundle);
                    }
                }
            });
        }
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
                    activeFragment = forumFragment;
                    return true;
            }
            return false;
        }
    };

    public void goToThreadFragment(User u, ForumThread t)
    {
        fragMan.beginTransaction().hide(activeFragment).show(threadFragment).commit();
        threadFragment;
        activeFragment = threadFragment;
    }

    /**
     * Retrieves data sent with the intent in the extra field
     */
    private void getIncomingIntent()
    {
        //Checks if the intent has an extra with the reference user
        if(this.getIntent().hasExtra("user"))
        {
            //Grabs the data in the extra
            u = (User)this.getIntent().getSerializableExtra("user");

        }
    }

}
