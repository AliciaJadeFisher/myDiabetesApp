package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.AuthenticationSection;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.AuthenticationSection.ui.main.BasicFragment;
import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.AuthenticationSection.ui.main.MedicalFragment;
import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.AuthenticationSection.ui.main.SectionsPagerAdapter;
import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.AuthenticationSection.ui.main.TimeBlockFragment;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * AccountSetupActivity
 */
public class AccountSetupActivity extends AppCompatActivity implements BasicFragment.sendDataMedical, MedicalFragment.sendDataTime
{
    /**
     * onCreate() method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Retreives the relevant layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup);

        // Initalizes the pagerAdapter
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setEnabled(false);

        // Initializes the tabLayout
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setEnabled(false);
        tabs.setupWithViewPager(viewPager);
    }

    /**
     * sendDataMedical() method
     * @param u
     */
    @Override
    public void sendDataMedical(User u)
    {
        // Sends the data to the medical fragment
        String tag = "android:switcher:" + R.id.view_pager + ":" + 1;
        MedicalFragment mf = (MedicalFragment)getSupportFragmentManager().findFragmentByTag(tag);
        mf.dataReceived(u);
    }

    /**
     * sendDataTime() method
     * @param u
     */
    @Override
    public void sendDataTime(User u)
    {
        // Sends the data to the time block fragment
        String tag = "android:switcher:" + R.id.view_pager + ":" + 2;
        TimeBlockFragment tf = (TimeBlockFragment) getSupportFragmentManager().findFragmentByTag(tag);
        tf.dataReceived(u);
    }
}