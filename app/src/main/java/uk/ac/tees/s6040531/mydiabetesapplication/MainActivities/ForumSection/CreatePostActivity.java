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

/**
 * CreatePostActivity
 */
public class CreatePostActivity extends AppCompatActivity
{
    //Variables used for layout access
    EditText postContent;
    Button btnCreate, btnDelete;

    //Variables used for thread and post access
    ForumThread thread;
    ThreadPost post;
    String pID;

    //Variables used for Firebase access
    FirebaseFirestore threadDbRef, postDbRef;
    FirebaseAuth auth;
    User u;

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
                // Retrieves the user's input
                String p = postContent.getText().toString();

                // Retrieves the new id and the current date, and creates a new ThreadPost
                Date created = new Date();
                post = new ThreadPost(thread.getThreadID(),"update",u.getId(), u.getName(), p, created);

                // Saves the post to the database
                postDbRef.collection("thread_posts").add(post).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {
                        // Increments the number of posts
                        int posts = Integer.parseInt(thread.getPosts());
                        posts ++;
                        thread.setPosts(Integer.toString(posts));

                        // Updates the thread in the database, to reflect the post number change
                        threadDbRef.collection("threads").document(thread.getThreadID()).update("posts",Integer.toString(posts))
                                .addOnSuccessListener(new OnSuccessListener<Void>()
                                {
                                    @Override
                                    public void onSuccess(Void aVoid)
                                    {
                                        Toast.makeText(CreatePostActivity.this,"Post created." ,Toast.LENGTH_SHORT);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(CreatePostActivity.this, "Post failed, please try again.",Toast.LENGTH_SHORT);
                    }
                });

                // Loads the ViewPostsActivity
                onBackPressed();
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

    /**
     * onBackPressed() method
     */
    @Override
    public void onBackPressed()
    {
        // Loads the ViewPostsActivity
        Intent i = new Intent(CreatePostActivity.this, ViewPostsActivity.class);
        i.putExtra("thread", thread);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }
}