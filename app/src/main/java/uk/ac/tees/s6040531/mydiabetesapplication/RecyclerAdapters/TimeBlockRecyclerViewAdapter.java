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

public class TimeBlockRecyclerViewAdapter extends RecyclerView.Adapter<TimeBlockRecyclerViewAdapter.ViewHolder>
{
    Context context;
    List<TimeBlock> timeBlocks;

    public TimeBlockRecyclerViewAdapter(Context context, List<TimeBlock> tb)
    {
        this.context = context;
        timeBlocks = tb;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvStart, tvEnd, tvRatio;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView)
        {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parent_layout);
            tvStart = (TextView)itemView.findViewById(R.id.tv_start);
            tvEnd = (TextView)itemView.findViewById(R.id.tv_end);
            tvRatio = (TextView)itemView.findViewById(R.id.tv_carb_ratio);

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_time_blocks, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        final Activity activity = (Activity)context;
        final TimeBlock block = timeBlocks.get(position);

        holder.tvStart.setText(String.valueOf(block.getStart()));
        holder.tvEnd.setText(String.valueOf(block.getEnd()));
        holder.tvRatio.setText(String.valueOf(block.getRatio()));
        
    }

    /**
     * Returns the amount of threads
     * @return threadList.size()
     */
    @Override
    public int getItemCount()
    {

        return timeBlocks.size();
    }
}
