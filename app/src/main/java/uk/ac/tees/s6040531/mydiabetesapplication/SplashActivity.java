package uk.ac.tees.s6040531.mydiabetesapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.AuthenticationSection.LoginActivity;

/**
 * SplashActivity
 */
public class SplashActivity extends AppCompatActivity
{
    /**
     * onCreate() method
     * @param savedInstanceState - instance bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Create new handler to deal with the delay and transitions
        // Delay for screen
        int SPLASH_TIME_OUT = 3000;
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                // Create new intent to go to the LoginActivity
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);

                // Change the animation to a custom animation
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
