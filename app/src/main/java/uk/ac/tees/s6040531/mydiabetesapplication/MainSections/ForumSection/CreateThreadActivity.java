package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.ForumSection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ForumThread;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * CreateThreadActivity
 */
public class CreateThreadActivity extends AppCompatActivity
{
    //Variables used for layout access
    EditText etTitle, etDescription;
    Button btnCreate, btnDelete;

    //Variables used for  thread access
    ForumThread thread;
    String tID;

    //Variables used for database access
    FirebaseFirestore threadDbRef;

    /**
     * onCreate() method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Grabs the relevant layout files
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_thread);

        // Initialises the widgets
        etTitle = (EditText)findViewById(R.id.et_title);
        etDescription = (EditText)findViewById(R.id.et_description);
        btnCreate = (Button)findViewById(R.id.btn_create_thread);
        btnDelete = (Button)findViewById(R.id.btn_delete_thread);

        // Initialises the database
        threadDbRef = FirebaseFirestore.getInstance();

        // onClickListener() for btnCreate
        btnCreate.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnCreate
             * @param v
             */
            @Override
            public void onClick(View v)
            {
                // Grabs the new thread details
                String id = "update";
                String title = etTitle.getText().toString();
                String desc = etDescription.getText().toString();
                String posts = "0";

                // Create a new ForumThread object
                ForumThread newThread = new ForumThread(id, title, desc, posts);

                // Saves the thread to the database
                threadDbRef.collection("threads").add(newThread).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {
                        Toast.makeText(CreateThreadActivity.this, "Thread created.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(CreateThreadActivity.this, "Thread failed to create, please try again.", Toast.LENGTH_SHORT).show();
                    }
                });

                // Loads the HomeActivity
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
        // Loads the HomeActivity
        Intent i = new Intent(CreateThreadActivity.this, HomeActivity.class);
        i.putExtra("prev","CreateThread");
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }
}