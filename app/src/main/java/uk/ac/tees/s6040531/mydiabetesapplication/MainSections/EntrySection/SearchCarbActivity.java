package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.EntrySection;

import androidx.appcompat.app.AppCompatActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.R;
import uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters.FoodListViewAdapter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * SearchCarbActivity
 */
public class SearchCarbActivity extends AppCompatActivity
{
    // Variables for layout access
    SearchView sv;
    TextView tvDisplay;
    Button btnSearch;
    ListView lvResults;

    // Variable for adapter
    FoodListViewAdapter adapter;

    /**
     * onCreate() method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_carb);

        // Initializes the widgets
        sv = (SearchView)findViewById(R.id.sv_food);
        tvDisplay = (TextView)findViewById(R.id.tv_noRes);
        btnSearch = (Button)findViewById(R.id.btn_search);
        lvResults = (ListView)findViewById(R.id.lv_results);

        // OnClickListner() for btnSearch
        btnSearch.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnSearch
             * @param view
             */
            @Override
            public void onClick(View view)
            {
                // Grabs the user's search
                String search = sv.getQuery().toString();

                // Checks if anything was entered
                if(search.equals(""))
                {
                    // Informs the user that they need to enter something
                    Toast.makeText(SearchCarbActivity.this, "Please enter a search sting", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Calls getApiResult()
                    getApiResult(search);
                }
            }
        });
    }

    /**
     * getApiResult() method
     * @param s
     */
    public void getApiResult(String s)
    {
    }

    /**
     * onBackPressed() method
     */
    @Override
    public void onBackPressed()
    {
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }
}
