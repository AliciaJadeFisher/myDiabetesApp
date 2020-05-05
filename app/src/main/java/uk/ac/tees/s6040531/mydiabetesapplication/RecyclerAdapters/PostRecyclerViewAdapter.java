package uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ForumThread;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ThreadPost;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * PostRecyclerViewAdapter class
 */
public class PostRecyclerViewAdapter  extends RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder>
{
    // RecyclerViewAdapter attributes
    private Context context;
    private List<ThreadPost> postList;
    private ForumThread thread;
    private FirebaseAuth auth;

    /**
     * Main constructor
     * @param context - context called from
     * @param tempList - list of posts
     * @param th - thread for posts
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
        TextView posterName, postDate, postContent;
        LinearLayout parentLayout;

        /**
         * Main constructor
         * @param itemView - view for recyclerView
         */
        ViewHolder(View itemView)
        {
            super(itemView);

            // Initializes the layout and widgets
            parentLayout = itemView.findViewById(R.id.parent_layout);
            posterName = itemView.findViewById(R.id.tv_poster);
            postDate = itemView.findViewById(R.id.tv_datePosted);
            postContent = itemView.findViewById(R.id.tv_postContent);
        }
    }

    /**
     * Creates a new RecyclerView.ViewHolder
     * @param parent - parent view group
     * @param viewType - stores type of view
     * @return viewHolder
     */
    @NotNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // Grabs the recycler view layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_posts, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Updates the contents of the RecyclerView.ViewHolder
     * @param holder - view holder
     * @param position - position in list
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        //Grabs the post at the current position
        final ThreadPost post = postList.get(position);
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

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