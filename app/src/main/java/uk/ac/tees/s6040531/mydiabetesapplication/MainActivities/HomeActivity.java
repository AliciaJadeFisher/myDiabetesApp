package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ui.home.HomeFragment;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class HomeActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final BottomNavigationView navView = (BottomNavigationView) findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.navigation_home);
        getSupportActionBar().hide();

        BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener()
        {
                    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item)
                    {
                        switch (item.getItemId()) {
                            case R.id.navigation_add:
                                navView.setSelectedItemId(R.id.navigation_add);
                                return true;
                            case R.id.navigation_home:
                                navView.setSelectedItemId(R.id.navigation_home);
                                return true;
                            case R.id.navigation_forum:
                                navView.setSelectedItemId(R.id.navigation_forum);
                                return true;
                        }
                        return false;
                    }
                };

    }

}
