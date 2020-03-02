package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ForumSection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.HomeActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ForumThread;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class CreateThreadActivity extends AppCompatActivity
{
    //Variables used for layout access
    EditText etTitle, etDescription;
    Button btnCreate, btnDelete;

    //Variables used for  thread access
    ForumThread thread;
    String tID;

    //Variable used tro store create/edit status
    String status = "New";

    //Variables used for database access
    FirebaseFirestore threadDbRef, postDbRef;

    private static final String TAG = "CreateThreadActivity";
    private static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_thread);

        //Initialises the widgets
        etTitle = (EditText)findViewById(R.id.et_title);
        etDescription = (EditText)findViewById(R.id.et_description);
        btnCreate = (Button)findViewById(R.id.btn_create_thread);
        btnDelete = (Button)findViewById(R.id.btn_delete_thread);

        threadDbRef = FirebaseFirestore.getInstance();

        btnCreate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String id = "Update me please!";
                String title = etTitle.getText().toString();
                String desc = etDescription.getText().toString();
                String posts = "0";

                ForumThread newThread = new ForumThread(id, title, desc, posts);

                threadDbRef.collection("threads").add(newThread).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {
                        Toast.makeText(CreateThreadActivity.this, "Thread created", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.w(TAG, "========================== Error adding event document =====================", e);
                        Toast.makeText(CreateThreadActivity.this, "Error, details could not be saved", Toast.LENGTH_SHORT).show();
                    }
                });

                // Loads the HomeActivity
                Intent i = new Intent(CreateThreadActivity.this, HomeActivity.class);
                i.putExtra("prev","CreateThread");
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });
    }
}
