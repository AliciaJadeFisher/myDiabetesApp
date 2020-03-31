package uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ForumThread;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ThreadPost;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * PostRecyclerViewAdapter class
 */
public class PostRecyclerViewAdapter  extends RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder>
{
    // RecyclerViewAdapter attributes
    Context context;
    List<ThreadPost> postList;
    ForumThread thread;
    FirebaseAuth auth;

    /**
     * Main constructor
     * @param context
     * @param tempList
     * @param th
     */
    public PostRecyclerViewAdapter(Context context, List<ThreadPost> tempList, ForumThread th)
    {
        this.context = context;
        this.postList = tempList;
        this.thread = th;
        auth = FirebaseAuth.getInstance();
    }

    /**
     * ViewHolder class
     */
    class ViewHolder extends RecyclerView.ViewHolder
    {
        // View layout attributes
        public TextView posterName, postDate, postContent;
        LinearLayout parentLayout;

        /**
         * Main constructor
         * @param itemView
         */
        public ViewHolder(View itemView)
        {
            super(itemView);

            // Initializes the layout and widgets
            parentLayout = itemView.findViewById(R.id.parent_layout);
            posterName = (TextView)itemView.findViewById(R.id.tv_poster);
            postDate = (TextView)itemView.findViewById(R.id.tv_datePosted);
            postContent = (TextView)itemView.findViewById(R.id.tv_postContent);
        }
    }

    /**
     * Creates a new RecyclerView.ViewHolder
     * @param parent
     * @param viewType
     * @return viewHolder
     */
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // Grabs the recycler view layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_posts, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * Updates the contents of the RecyclerView.ViewHolder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        final Activity activity = (Activity)context;

        //Grabs the post at the current position
        final ThreadPost post = postList.get(position);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


        //Sets the TextViews
        holder.posterName.setText(post.getSenderName());
        holder.postDate.setText(dateFormat.format(post.getPostDate()));
        holder.postContent.setText(post.getMessage());

        // Checks if the current user is the post sender
        if(post.getSenderID().equals(auth.getUid()))
        {
            // If they are, changes the post background and poster name to reflect this
            holder.parentLayout.setBackgroundResource(R.drawable.post_background_user);
            holder.posterName.setText("You");
        }
        else
        {
            // If they aren't, sets the secondary background
            holder.parentLayout.setBackgroundResource(R.drawable.post_background_other);
        }
    }

    /**
     * Returns the number of posts
     * @return postList.size()
     */
    @Override
    public int getItemCount()
    {
        return postList.size();
    }
}