package uk.ac.tees.s6040531.mydiabetesapplication.Registration_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * RegistrationActivity
 */
public class RegistrationActivity extends AppCompatActivity
{
    // Variables for layout access
    EditText etEmail, etPassword;
    Button btnReg, btnTerms, btnLogin;

    // Variable for Firebase access
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Grabs the layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Grabs the current firebase auth instance
        auth = FirebaseAuth.getInstance();

        // Initialize widgets
        etEmail = (EditText)findViewById(R.id.et_email);
        etPassword = (EditText)findViewById(R.id.et_password);
        btnReg = (Button) findViewById(R.id.btn_reg);
        btnLogin = (Button)findViewById(R.id.btn_login);
        btnTerms = (Button)findViewById(R.id.btn_login);

        // onClickListener for btnLogin
        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });

        // onClickListener for btnTerms
//        btnTerms.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Intent i = new Intent(RegistrationActivity.this, AboutActivity.class);
//                startActivity(i);
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//            }
//        });

        // onClickListener for btnReg
        btnReg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                //Checks if the inputs are empty
                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(getApplicationContext(),R.string.error_email , Toast.LENGTH_SHORT ).show();
                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(),R.string.error_password, Toast.LENGTH_SHORT).show();
                }
                //Checks that the password is of a suitable length
                if(password.length()< 8)
                {
                    Toast.makeText(getApplicationContext(),R.string.short_pass, Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}
