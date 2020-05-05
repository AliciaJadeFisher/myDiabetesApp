package uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.TimeBlock;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * TimeBlockRecyclerViewAdapter
 */
public class TimeBlockRecyclerViewAdapter extends RecyclerView.Adapter<TimeBlockRecyclerViewAdapter.ViewHolder>
{
    // RecyclerViewAdapter attributes
    private Context context;
    private List<TimeBlock> timeBlocks;

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
        TextView tvStart, tvEnd, tvRatio;
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
            tvStart = itemView.findViewById(R.id.tv_viewStart);
            tvEnd = itemView.findViewById(R.id.tv_viewEnd);
            tvRatio = itemView.findViewById(R.id.tv_viewRatio);

        }
    }

    /**
     * Creates a new RecyclerView.ViewHolder
     * @param parent - view group parent
     * @param viewType - view type
     * @return viewHolder
     */
    @NotNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // Grabs the recycler view layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_time_blocks, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Updates the contents of the RecyclerView.ViewHolder
     * @param holder - view holder
     * @param position - position in list
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
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