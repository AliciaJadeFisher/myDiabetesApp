package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.AuthenticationSection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

    // Variable for firebase access
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
        btnReg = (Button) findViewById(R.id.btn_register);
        btnLogin = (Button)findViewById(R.id.btn_login);
        btnTerms = (Button)findViewById(R.id.btn_terms);

        // onClickListener for btnLogin
        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnLogin
             * @param v
             */
            @Override
            public void onClick(View v)
            {
                // Loads the LoginActivity
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
            /**
             * onClick() for btnReg
             * @param v
             */
            @Override
            public void onClick(View v)
            {
                // Grabs the user inputs
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                //Checks if the inputs are empty
                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(getApplicationContext(), R.string.error_email , Toast.LENGTH_SHORT ).show();
                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(), R.string.error_password, Toast.LENGTH_SHORT).show();
                }
                //Checks that the password is of a suitable length
                if(password.length()< 8)
                {
                    Toast.makeText(getApplicationContext(), R.string.short_pass, Toast.LENGTH_SHORT).show();
                }

                // Creates a new firebase user
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>()
                {
                    /**
                     * onComplete() method
                     * @param task
                     */
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        // Informs the user that they are creating an account
                        Toast.makeText(RegistrationActivity.this, R.string.create_account,Toast.LENGTH_SHORT).show();

                        // Checks if the task was successful
                        if(!task.isSuccessful())
                        {
                            // Informs the user that the task failed
                            Toast.makeText(RegistrationActivity.this, R.string.create_failed, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            // Informs the user that the task was successful
                            Toast.makeText(RegistrationActivity.this, R.string.create_successs, Toast.LENGTH_SHORT).show();

                            // Loads the AccountSetupActivity
                            Intent i = new Intent(RegistrationActivity.this, AccountSetupActivity.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                        }
                    }
                });
            }
        });
    }

    /**
     * onBackPressed() method
     */
    @Override
    public void onBackPressed()
    {
        // Loads the LoginActivity
        Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
}