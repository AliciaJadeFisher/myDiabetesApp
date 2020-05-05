package uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.ForumSection.ViewPostsActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ForumThread;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * ThreadRecyclerViewAdapter
 */
public class ThreadRecyclerViewAdapter extends RecyclerView.Adapter<ThreadRecyclerViewAdapter.ViewHolder>
{
    // RecyclerViewAdapter attributes
    private Fragment parent;
    private List<ForumThread> threadList;

    /**
     * Main constructor
     * @param parent - parent fragment
     * @param tempList - list of threads
     */
    public ThreadRecyclerViewAdapter(Fragment parent, List<ForumThread> tempList)
    {
        this.parent = parent;
        this.threadList = tempList;
    }

    /**
     * ViewHolder class
     */
    class ViewHolder extends RecyclerView.ViewHolder
    {
        // View layout attributes
        TextView threadTitle, threadDesc, threadPosts;
        RelativeLayout parentLayout;

        /**
         * Main constructor
         * @param itemView - view for recycler view
         */
        ViewHolder(View itemView)
        {
            super(itemView);

            // Initializes the layout and widgets
            parentLayout = itemView.findViewById(R.id.parent_layout);
            threadTitle = itemView.findViewById(R.id.tv_viewThreadTitle);
            threadDesc = itemView.findViewById(R.id.tv_viewThreadDesc);
            threadPosts = itemView.findViewById(R.id.tv_viewThreadPosts);
        }
    }

    /**
     * Creates a new RecyclerView.ViewHolder
     * @param parent - parent view group
     * @param viewType - view type
     * @return viewHolder
     */
    @NotNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // Grabs the recycler view layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_threads, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Updates the contents of the RecyclerView.ViewHolder
     * @param holder - view holder
     * @param position - position in list
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
             * @param v - recyclerView view
             */
            @Override
            public void onClick(View v)
            {
                //Passes the user and thread to the CreatePostActivity and loads it up
                Intent i = new Intent(parent.getActivity(), ViewPostsActivity.class);
                i.putExtra("thread", thread);
                Objects.requireNonNull(parent.getActivity()).startActivity(i);
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

    /**
     * Returns the current position
     * @param position - position in list
     * @return position
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
