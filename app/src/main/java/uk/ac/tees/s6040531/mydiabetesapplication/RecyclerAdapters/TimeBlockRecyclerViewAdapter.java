package uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.TimeBlock;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * TimeBlockRecyclerViewAdapter
 */
public class TimeBlockRecyclerViewAdapter extends RecyclerView.Adapter<TimeBlockRecyclerViewAdapter.ViewHolder>
{
    // RecyclerViewAdapter attributes
    Context context;
    List<TimeBlock> timeBlocks;

    // Main constructor
    public TimeBlockRecyclerViewAdapter(Context context, List<TimeBlock> tb)
    {
        this.context = context;
        timeBlocks = tb;
    }

    /**
     * ViewHolder class
     */
    class ViewHolder extends RecyclerView.ViewHolder
    {
        // View layout attributes
        public TextView tvStart, tvEnd, tvRatio;
        RelativeLayout parentLayout;

        /**
         * Main constructor
         * @param itemView
         */
        public ViewHolder(View itemView)
        {
            super(itemView);

            // Initializes the layout and widgets
            parentLayout = itemView.findViewById(R.id.parent_layout);
            tvStart = (TextView)itemView.findViewById(R.id.tv_viewStart);
            tvEnd = (TextView)itemView.findViewById(R.id.tv_viewEnd);
            tvRatio = (TextView)itemView.findViewById(R.id.tv_viewRatio);

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_time_blocks, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * Updates the contents of the RecyclerView.ViewHolder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        final Activity activity = (Activity)context;

        // Grabs the time block at the current position
        final TimeBlock block = timeBlocks.get(position);

        // Sets the text views
        holder.tvStart.setText(block.getStart());
        holder.tvEnd.setText(block.getEnd());
        holder.tvRatio.setText(block.getRatio());
        
    }

    /**
     * Returns the amount of time blocks
     * @return timeBlocks.size()
     */
    @Override
    public int getItemCount()
    {

        return timeBlocks.size();
    }
}