package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ForumSection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

import uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.HomeActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ForumThread;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ThreadPost;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class CreatePostActivity extends AppCompatActivity
{
    //Variables used for layout access
    EditText postContent;
    Button btnCreate, btnDelete;

    //Variables used for project, thread and post access
    ForumThread thread;
    ThreadPost post;
    String pID;

    //Variables used for database access
    FirebaseFirestore threadDbRef, postDbRef;

    FirebaseAuth auth;
    User u;

    private static final String TAG = "CreatePostActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        //Initialises the widgets
        postContent = (EditText)findViewById(R.id.et_post);
        btnCreate = (Button) findViewById(R.id.btn_create);
        btnDelete = (Button)findViewById(R.id.btn_delete);

        getIncomingIntent();

        //Retrieves the current user
        auth = FirebaseAuth.getInstance();

        threadDbRef = FirebaseFirestore.getInstance();
        postDbRef = FirebaseFirestore.getInstance();

        btnCreate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Increments the number of posts and saves this to the database
                int posts = Integer.parseInt(thread.getPosts());
                posts ++;
                thread.setPosts(Integer.toString(posts));
                threadDbRef.collection("threads").document(thread.getThreadID()).update("posts",Integer.toString(posts))
                .addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.w(TAG, "Error updating document", e);
                    }
                });

                //Retrieves the user's input
                String p = postContent.getText().toString();

                //Retrieves the new id and the current date, and creates a new ThreadPost
                Date created = new Date();
                post = new ThreadPost(thread.getThreadID(),"update",u.getId(), u.getName(), p, created);

                postDbRef.collection("thread_posts").add(post).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {
                        Toast.makeText(CreatePostActivity.this, "Thread created", Toast.LENGTH_SHORT).show();
                    }
                })
                        .addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Log.w(TAG, "========================== Error adding event document =====================", e);
                                Toast.makeText(CreatePostActivity.this, "Error, details could not be saved", Toast.LENGTH_SHORT).show();
                            }
                        });

                // Loads the HomeActivity
                Intent i = new Intent(CreatePostActivity.this, ViewPostsActivity.class);
                i.putExtra("user", u);
                i.putExtra("thread", thread);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();

            }
        });

    }

    /**
     * Retrieves data sent with the intent in the extra field
     */
    private void getIncomingIntent()
    {
        //Checks if the intent has an extra with the reference thread
        if (this.getIntent().hasExtra("thread")) {
            //Grabs the data in the extra
            thread = (ForumThread) this.getIntent().getSerializableExtra("thread");
        }

        //Checks if the intent has an extra with the reference user
        if (this.getIntent().hasExtra("user")) {
            //Grabs the data in the extra
            u = (User) this.getIntent().getSerializableExtra("user");
        }
    }
}
