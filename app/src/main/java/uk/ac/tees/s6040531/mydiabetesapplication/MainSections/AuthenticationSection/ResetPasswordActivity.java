package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.AuthenticationSection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * ResetPasswordActivity
 */
public class ResetPasswordActivity extends AppCompatActivity
{
    // Variables for layout access
    EditText etEmail;
    Button btnReset, btnback;

    // Variable for firebase access
    FirebaseAuth auth;

    /**
     * onCreate() method
     * @param savedInstanceState - instance bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // Initialises the widgets
        etEmail = findViewById(R.id.et_email);
        btnReset = findViewById(R.id.btn_reset);
        btnback = findViewById(R.id.btn_back);

        // Initialises FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // onClickListener for btnReset
        btnReset.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnReset
             * @param v - activity view
             */
            @Override
            public void onClick(View v)
            {
                // Grabs the entered email
                String email = etEmail.getText().toString();

                // Checks if the email is empty
                if(email.isEmpty())
                {
                    // Informs the user that they need to enter an email
                    Toast.makeText(ResetPasswordActivity.this, "Please enter an email address", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Sends the user a reset password email
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        /**
                         * onComplete() method
                         * @param task - contains result of task
                         */
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            // Checks if task was successful
                            if(task.isSuccessful())
                            {
                                // Informs the user that they have been sent an email and returns them to the Login Activity
                                Toast.makeText(ResetPasswordActivity.this, "We have sent you instructions to reset your password.", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                            else
                            {
                                // Informs the user that the email failed
                                Toast.makeText(ResetPasswordActivity.this, "Reset password failed, please check your email and try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        // onClickListener for btnBack
        btnback.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnBack
             * @param v - activity view
             */
            @Override
            public void onClick(View v)
            {
                // Calls onBackPressed() method
                onBackPressed();
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
        Intent i = new Intent(ResetPasswordActivity.this,LoginActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }
}
