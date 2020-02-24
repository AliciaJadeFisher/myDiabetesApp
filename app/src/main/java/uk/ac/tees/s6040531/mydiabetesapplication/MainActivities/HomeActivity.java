package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ForumFragments.ForumFragment;
import uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ForumFragments.ThreadFragment;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class HomeActivity extends AppCompatActivity implements ForumFragment.ThreadFragGo
{
    final Fragment homeFragment = new HomeFragment();
    final Fragment addFragment = new AddFragment();
    final Fragment forumFragment = new ForumFragment();
    final Fragment threadFragment = new ThreadFragment();
    final FragmentManager fragMan = getSupportFragmentManager();

    Fragment activeFragment = homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final BottomNavigationView navView = (BottomNavigationView) findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_home);
        getSupportActionBar().hide();

        fragMan.beginTransaction().add(R.id.home_parent, addFragment,"1").hide(addFragment).commit();
        fragMan.beginTransaction().add(R.id.home_parent, forumFragment,"3").hide(forumFragment).commit();
        fragMan.beginTransaction().add(R.id.home_parent, threadFragment,"3").hide(threadFragment).commit();
        fragMan.beginTransaction().add(R.id.home_parent, homeFragment,"2").commit();


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

    public void goToThreadFragment()
    {
        fragMan.beginTransaction().hide(activeFragment).show(threadFragment).commit();
        activeFragment = threadFragment;
    }

}
