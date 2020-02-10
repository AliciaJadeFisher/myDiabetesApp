package uk.ac.tees.s6040531.mydiabetesapplication.Registration_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        // onClickListener for btnReg
        btnReg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Loads the RegistrationActivity
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });

        // onClickListener for btnReset
        btnReset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Loads the ResetPasswordActivity
                Intent i = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });

        // onClickListener for btnLogin
        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Grabs the input form the editTexts
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                // Checks if either field is empty and outputs the relevant message
                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(),"Enter email address and password!",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // If validation is passed, logs in the user
                }
            }
        });


    }
}
