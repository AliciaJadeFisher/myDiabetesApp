package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.AuthenticationSection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

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
    User cUser;
    String previous;
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

        // Calls getIncomingIntent()
        getIncomingIntent();

        // Initalizes the pagerAdapter
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), cUser);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setEnabled(false);

        // Initializes the tabLayout
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setEnabled(false);
        tabs.setupWithViewPager(viewPager);
    }

    /**
     * sendDatatoMedical() method
     * @param u
     */
    @Override
    public void sendDatatoMedical(User u)
    {
        // Sends the data to the medical fragment
        String tag = "android:switcher:" + R.id.view_pager + ":" + 1;
        MedicalFragment mf = (MedicalFragment)getSupportFragmentManager().findFragmentByTag(tag);
        mf.dataReceived(u);
    }

    /**
     * sendDataToTime() method
     * @param u
     */
    @Override
    public void sendDataToTime(User u)
    {
        // Sends the data to the time block fragment
        String tag = "android:switcher:" + R.id.view_pager + ":" + 2;
        TimeBlockFragment tf = (TimeBlockFragment) getSupportFragmentManager().findFragmentByTag(tag);
        tf.dataReceived(u);
    }

    /**
     * Retrieves data sent with the intent in the extra field
     */
    public void getIncomingIntent()
    {
        //Checks if the intent has an extra with the reference previous
        if(this.getIntent().hasExtra("previous"))
        {
            //Grabs the data in the extra
            previous =  this.getIntent().getStringExtra("previous");

            if(previous.equals("settings"))
            {
                SharedPreferences myPref = getSharedPreferences(getResources().getString(R.string.pref_key), Context.MODE_PRIVATE);
                Gson gson = new Gson();
                String json = myPref.getString(getResources().getString(R.string.user_key),"");
                cUser = gson.fromJson(json,User.class);
            }
        }
    }

}