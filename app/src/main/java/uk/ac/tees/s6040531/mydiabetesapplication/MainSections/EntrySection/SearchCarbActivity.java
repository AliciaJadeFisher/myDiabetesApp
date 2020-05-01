package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.EntrySection;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.R;
import uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters.FoodListViewAdapter;

/**
 * SearchCarbActivity
 */
public class SearchCarbActivity extends AppCompatActivity
{
    private BroadcastReceiver bRec;

    // Variables for layout access
    static TextView tvNetCon;
    static SearchView sv;
    static TextView tvDisplay;
    static Button btnSearch;
    static ListView lvResults;

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
        tvNetCon = (TextView)findViewById(R.id.tv_net_con);
        sv = (SearchView)findViewById(R.id.sv_food);
        tvDisplay = (TextView)findViewById(R.id.tv_noRes);
        btnSearch = (Button)findViewById(R.id.btn_search);
        lvResults = (ListView)findViewById(R.id.lv_results);

        // Calls checkCon()
        checkCon();

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

    public void checkCon()
    {
        bRec = new SearchNetworkChecker();
        registerReceiver(bRec, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public static void showSearch(Boolean state)
    {
        if(state)
        {
            tvNetCon.setText("");
            tvNetCon.setVisibility(View.GONE);
            sv.setVisibility(View.VISIBLE);
            tvDisplay.setVisibility(View.VISIBLE);
            btnSearch.setVisibility(View.VISIBLE);
            lvResults.setVisibility(View.VISIBLE);
        }
        else
        {
            sv.setVisibility(View.GONE);
            tvDisplay.setVisibility(View.GONE);
            btnSearch.setVisibility(View.GONE);
            lvResults.setVisibility(View.GONE);
            tvNetCon.setVisibility(View.VISIBLE);
            tvNetCon.setText("Error connecting to network. Please connect to a network and try again.");

        }
    }

    /**
     * getApiResult() method
     * @param s
     */
    public void getApiResult(String s)
    {
        // Create a new request queue
        final RequestQueue queue = Volley.newRequestQueue(this);

        // Replace all spaces with '+'
        String search = s.replaceAll("\\s","+");

        // Create the request url to return JSON data
        final String url = "https://world.openfoodfacts.org/cgi/search.pl?search_terms=" + search +"&search_simple=1&action=process&json=1";

        // Creates an API GET request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    /**
                     * onResponse() method
                     * @param response
                     */
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            // Converts the string response to a JSON object
                            JSONObject object = new JSONObject(response);

                            // Sets the list view adapter and passes in the api response
                            adapter = new FoodListViewAdapter(SearchCarbActivity.this, object.getJSONArray("products"));
                            lvResults.setAdapter(adapter);

                            // Displays the number of relevant results
                            tvDisplay.setText("No. results : " + adapter.getCount());
                        }
                        catch(JSONException e)
                        {
                            // Informs the user that the search failed
                            Toast.makeText(SearchCarbActivity.this, "Search failed, please try again", Toast.LENGTH_SHORT).show();
                        }
                        queue.stop();
                    }
                },
                new Response.ErrorListener()
                {
                    /**
                     * onErrorResponse() method
                     * @param error
                     */
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        // Informs the user that the search failed
                        Toast.makeText(SearchCarbActivity.this, "Search failed, please try again", Toast.LENGTH_SHORT).show();
                        queue.stop();
                    }
                });

        // Adds the request to the queue
        queue.add(stringRequest);
    }

    @Override
    public void onPause()
    {
        super.onPause();

        try
        {
            unregisterReceiver(bRec);
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * onBackPressed() method
     */
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }
}