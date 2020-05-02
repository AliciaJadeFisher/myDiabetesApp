package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.AuthenticationSection;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * LoginActivity
 */
public class LoginActivity extends AppCompatActivity
{
    // Variables for layout access
    EditText etEmail, etPassword;
    Button btnReg, btnLogin, btnReset;

    // Variable for firebase access
    FirebaseAuth auth;

    /**
     * onCreate() method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Grabs the layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Grabs the current firebase auth instance
        auth = null;
        auth = FirebaseAuth.getInstance();

         // Checks if a user is currently logged in
//        if(auth.getCurrentUser() != null)
//        {
//            // Loads the HomeActivity
//            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
//            startActivity(i);
//            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
//            finish();
//        }

        // Initialize widgets
        etEmail = (EditText)findViewById(R.id.et_email);
        etPassword = (EditText)findViewById(R.id.et_password);
        btnReg = (Button)findViewById(R.id.btn_reg);
        btnLogin = (Button)findViewById(R.id.btn_login);
        btnReset = (Button)findViewById(R.id.btn_reset_password);

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
            /**
             * onClick for btnReset()
             * @param v
             */
            @Override
            public void onClick(View v)
            {
                // Loads the ResetPasswordActivity
                Intent i = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

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
                // Grabs the input form the editTexts
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                // Checks if either field is empty and outputs the relevant message
                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(), R.string.error_email_password,Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(getApplicationContext(), R.string.error_email, Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(), R.string.error_password, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // If validation is passed, logs in the user
                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if(!task.isSuccessful())
                            {
                                // Error signing in
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                // Loads the HomeActivity
                                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(i);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                finish();
                            }
                        }
                    });
                }
            }
        });

    }
}
