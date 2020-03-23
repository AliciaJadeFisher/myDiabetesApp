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

public class PostRecyclerViewAdapter  extends RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder>
{
    Context context;
    List<ThreadPost> postList;
    ForumThread thread;
    FirebaseAuth auth;

    public PostRecyclerViewAdapter(Context context, List<ThreadPost> tempList, ForumThread th)
    {
        this.context = context;
        this.postList = tempList;
        this.thread = th;
        auth = FirebaseAuth.getInstance();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView posterName, postDate, postContent;
        LinearLayout parentLayout;

        public ViewHolder(View itemView)
        {
            super(itemView);

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

        //Grabs the note at the current position
        final ThreadPost post = postList.get(position);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


        //Sets the TextViews
        holder.posterName.setText(post.getSenderName());
        holder.postDate.setText(dateFormat.format(post.getPostDate()));
        holder.postContent.setText(post.getMessage());

        if(post.getSenderID().equals(auth.getUid()))
        {
            holder.parentLayout.setBackgroundResource(R.drawable.post_background_user);
            holder.posterName.setText("You");

        }
        else
        {
            holder.parentLayout.setBackgroundResource(R.drawable.post_background_other);
        }
    }

    /**
     * Returns the amount of threads
     * @return threadList.size()
     */
    @Override
    public int getItemCount()
    {
        return postList.size();
    }
}
