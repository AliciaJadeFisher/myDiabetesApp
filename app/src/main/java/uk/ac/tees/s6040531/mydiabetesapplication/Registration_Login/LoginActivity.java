package uk.ac.tees.s6040531.mydiabetesapplication.Registration_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import uk.ac.tees.s6040531.mydiabetesapplication.HomeActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * LoginActivity
 */
public class LoginActivity extends AppCompatActivity
{
    // Variables for layout access
    EditText etEmail, etPassword;
    Button btnReg, btnLogin, btnReset;

    // Variable for Firebase access
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Grab the layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Grabs the current firebase auth instance
        auth = null;
        auth = FirebaseAuth.getInstance();

        // Checks if a current user exists
        if(auth.getCurrentUser() != null)
        {
            // Loads the HomeActivity
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            finish();
        }

        // Initialize widgets
        etEmail = (EditText)findViewById(R.id.et_email);
        etPassword = (EditText)findViewById(R.id.et_password);
        btnReg = (Button)findViewById(R.id.btn_reg);
        btnLogin = (Button)findViewById(R.id.btn_login);
        btnReset = (Button)findViewById(R.id.btn_reset_password);


    }
}
