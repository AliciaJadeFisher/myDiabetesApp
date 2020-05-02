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

public class ResetPasswordActivity extends AppCompatActivity
{
    EditText etEmail;
    Button btnReset, btnback;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etEmail = (EditText)findViewById(R.id.et_email);
        btnReset = (Button)findViewById(R.id.btn_reset);
        btnback = (Button)findViewById(R.id.btn_back);

        auth = FirebaseAuth.getInstance();

        btnReset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = etEmail.getText().toString();

                if(email.isEmpty())
                {
                    Toast.makeText(ResetPasswordActivity.this, "Please enter an email address", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ResetPasswordActivity.this, "We have sent you instructions to reset your password.", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                            else
                            {
                                Toast.makeText(ResetPasswordActivity.this, "Reset password failed, please check your email and try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Loads the LoginActivity
        Intent i = new Intent(ResetPasswordActivity.this,LoginActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }
}
