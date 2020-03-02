package uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ForumSection.ForumFragments.ForumFragment;
import uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ForumSection.ViewPostsActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ForumThread;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class ThreadRecyclerViewAdapter extends RecyclerView.Adapter<ThreadRecyclerViewAdapter.ViewHolder>
{
    Fragment parent;

    List<ForumThread> threadList;

    User user;

    public ThreadRecyclerViewAdapter(Fragment parent, List<ForumThread> tempList, User u)
    {
        this.parent = parent;
        this.threadList = tempList;
        this.user = u;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView threadTitle, threadDesc, threadPosts;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView)
        {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parent_layout);
            threadTitle = (TextView)itemView.findViewById(R.id.tv_viewThreadTitle);
            threadDesc = (TextView)itemView.findViewById(R.id.tv_viewThreadDesc);
            threadPosts = (TextView)itemView.findViewById(R.id.tv_viewThreadPosts);

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_threads, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * Updates the contents of the RecyclerView.ViewHolder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder,final int position)
    {
        //Grabs the thread at the current position
        final ForumThread thread = threadList.get(position);

        //Sets the TextViews
        holder.threadTitle.setText(thread.getTitle());
        holder.threadDesc.setText(thread.getDesc());
        holder.threadPosts.setText(thread.getPosts());

        //Sets the onClickListener for the parent layout
        holder.parentLayout.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick
             * @param v
             */
            @Override
            public void onClick(View v)
            {
                //Passes the user and thread to the CreatePostActivity and loads it up
                Intent i = new Intent(parent.getActivity(), ViewPostsActivity.class);
                i.putExtra("user", user);
                i.putExtra("thread", thread);
                parent.getActivity().startActivity(i);
                parent.getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                parent.getActivity().finish();
            }
        });
    }

    /**
     * Returns the amount of threads
     * @return threadList.size()
     */
    @Override
    public int getItemCount()
    {

        return threadList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
