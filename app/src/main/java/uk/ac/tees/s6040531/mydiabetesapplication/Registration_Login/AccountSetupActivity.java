package uk.ac.tees.s6040531.mydiabetesapplication.Registration_Login;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;
import uk.ac.tees.s6040531.mydiabetesapplication.Registration_Login.ui.main.BasicFragment;
import uk.ac.tees.s6040531.mydiabetesapplication.Registration_Login.ui.main.MedicalFragment;
import uk.ac.tees.s6040531.mydiabetesapplication.Registration_Login.ui.main.SectionsPagerAdapter;
import uk.ac.tees.s6040531.mydiabetesapplication.Registration_Login.ui.main.TimeBlockFragment;

public class AccountSetupActivity extends AppCompatActivity implements BasicFragment.sendDataMedical, MedicalFragment.sendDataTime
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setEnabled(false);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setEnabled(false);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void sendDataMedical(User u)
    {
        String tag = "android:switcher:" + R.id.view_pager + ":" + 1;
        MedicalFragment mf = (MedicalFragment)getSupportFragmentManager().findFragmentByTag(tag);
        mf.dataReceived(u);
    }

    @Override
    public void sendDataTime(User u)
    {
        String tag = "android:switcher:" + R.id.view_pager + ":" + 2;
        TimeBlockFragment tf = (TimeBlockFragment) getSupportFragmentManager().findFragmentByTag(tag);
        tf.dataReceived(u);
    }

}