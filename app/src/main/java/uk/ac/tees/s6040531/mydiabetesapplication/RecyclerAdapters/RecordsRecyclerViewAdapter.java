package uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeSection.ViewRecordActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.BloodSugarEntry;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class RecordsRecyclerViewAdapter  extends RecyclerView.Adapter<RecordsRecyclerViewAdapter.ViewHolder>
{
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    // RecyclerViewAdapter attributes
    Fragment parent;
    List<BloodSugarEntry> entriesList;

    /**
     * Main construcor
     * @param parent
     * @param tempList
     */
    public RecordsRecyclerViewAdapter(Fragment parent, List<BloodSugarEntry> tempList)
    {
        this.parent = parent;
        this.entriesList = tempList;
    }

    /**
     * ViewHolder class
     */
    class ViewHolder extends RecyclerView.ViewHolder
    {
        // View layout attributes
        public TextView entryDate, entryTime,entryBS, entryIn;
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
            entryDate = (TextView)itemView.findViewById(R.id.tv_viewDate);
            entryTime = (TextView)itemView.findViewById(R.id.tv_viewTime);
            entryBS = (TextView)itemView.findViewById(R.id.tv_viewBs);
            entryIn = (TextView)itemView.findViewById(R.id.tv_viewInsulin);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_records, parent, false);
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
        //Grabs the post at the current position
        final BloodSugarEntry entry = entriesList.get(position);

        //Sets the TextViews
        holder.entryDate.setText(dateFormat.format(entry.getDate()));
        holder.entryTime.setText(entry.getTime());
        holder.entryBS.setText(String.valueOf(entry.getBs()));
        holder.entryIn.setText(String.valueOf(Math.round(entry.getInsulin_t())));

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
                Intent i = new Intent(parent.getActivity(), ViewRecordActivity.class);
                i.putExtra("bs_entry", entry);
                parent.getActivity().startActivity(i);
                parent.getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                parent.getActivity().finish();
            }
        });
    }

    /**
     * Returns the number of entries
     * @return entriesList.size()
     */
    @Override
    public int getItemCount()
    {
        return entriesList.size();
    }
}
