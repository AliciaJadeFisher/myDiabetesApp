package uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters;

import android.app.Activity;
import android.content.Context;
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

import androidx.recyclerview.widget.RecyclerView;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.BloodSugarEntry;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class RecordsRecyclerViewAdapter  extends RecyclerView.Adapter<RecordsRecyclerViewAdapter.ViewHolder>
{
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    // RecyclerViewAdapter attributes
    Context context;
    List<BloodSugarEntry> entriesList;
    String filter;
    Date today;
    Date week;
    Date month;

    /**
     * Main constructor
     * @param context
     * @param tempList
     * @param f
     */
    public RecordsRecyclerViewAdapter(Context context, List<BloodSugarEntry> tempList, String f, Date t, Date w, Date m)
    {
        this.context = context;
        this.filter = f;
        this.today = t;
        this.week = w;
        this.month = m;
        this.entriesList = filterList(tempList);
    }

    /**
     * ViewHolder class
     */
    class ViewHolder extends RecyclerView.ViewHolder
    {
        // View layout attributes
        public TextView entryDate, entryBS, entryIn;
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
        final Activity activity = (Activity)context;

        //Grabs the post at the current position
        final BloodSugarEntry entry = entriesList.get(position);

        //Sets the TextViews
        holder.entryDate.setText(dateFormat.format(entry.getDate()));
        holder.entryBS.setText(Double.toString(entry.getBs()));
        holder.entryIn.setText(Double.toString(entry.getInsulin_t()));
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

    public List<BloodSugarEntry> filterList(List<BloodSugarEntry> list)
    {
        List<BloodSugarEntry> filteredList = new ArrayList<>();

        if(filter.equals("All"))
        {
            filteredList = list;
        }
        else if(filter.equals("Today"))
        {
            for(BloodSugarEntry e : list)
            {
                if(dateFormat.format(e.getDate()) == dateFormat.format(today))
                {
                    filteredList.add(e);
                }
            }
        }
        else if(filter.equals("Week"))
        {
            for(BloodSugarEntry e : list)
            {
                if(e.getDate().after(week))
                {
                    filteredList.add(e);
                }
            }
        }
        else if(filter.equals("Month"))
        {
            for(BloodSugarEntry e : list)
            {
                if(e.getDate().after(month))
                {
                    filteredList.add(e);
                }
            }
        }

        return filteredList;
    }
}
