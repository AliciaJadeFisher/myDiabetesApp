package uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ForumFragments.ForumFragment;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ForumThread;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class ThreadRecyclerViewAdapter extends RecyclerView.Adapter<ThreadRecyclerViewAdapter.ViewHolder>
{
    ForumFragment.ThreadFragGo tm;
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
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        final Activity activity = (Activity)parent.getActivity();

        //Grabs the note at the current position
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

                tm.goToThreadFragment(user, threadList.get(position));
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

    /**
     * Interface for ClickListener
     */
    public interface ClickListener
    {
        public void itemClicked(View view ,int position);
    }
}
