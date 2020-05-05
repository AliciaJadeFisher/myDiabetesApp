package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.EntrySection;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import uk.ac.tees.s6040531.mydiabetesapplication.R;
import uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters.FoodListViewAdapter;

/**
 * SearchCarbActivity
 */
public class SearchCarbActivity extends AppCompatActivity
{
    // Variable for broadcast receiver
    private BroadcastReceiver bRec;

    // Variables for layout access
    @SuppressLint("StaticFieldLeak")
    static TextView tvNetCon;
    @SuppressLint("StaticFieldLeak")
    static SearchView sv;
    @SuppressLint("StaticFieldLeak")
    static TextView tvDisplay;
    @SuppressLint("StaticFieldLeak")
    static Button btnSearch;
    @SuppressLint("StaticFieldLeak")
    static ListView lvResults;
    @SuppressLint("StaticFieldLeak")
    static ProgressBar pbSearch;

    // Variable for adapter
    FoodListViewAdapter adapter;

    /**
     * onCreate() method
     * @param savedInstanceState - instance bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_carb);

        // Initializes the widgets
        tvNetCon = findViewById(R.id.tv_net_con);
        sv = findViewById(R.id.sv_food);
        tvDisplay = findViewById(R.id.tv_noRes);
        btnSearch = findViewById(R.id.btn_search);
        lvResults = findViewById(R.id.lv_results);
        pbSearch = findViewById(R.id.pb_search);

        // Calls checkCon()
        checkCon();

        // OnClickListener() for btnSearch
        btnSearch.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnSearch
             * @param view - activity view
             */
            @Override
            public void onClick(View view)
            {

                // Shows the progress bar
                pbSearch.setVisibility(View.VISIBLE);

                // Hides the keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                Objects.requireNonNull(imm).hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(),0);

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
     * checkCon() method
     */
    public void checkCon()
    {
        // Creates and registers a new broadcast receiver
        bRec = new SearchNetworkChecker();
        registerReceiver(bRec, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     * showSearch() method
     * @param state - network connection
     */
    @SuppressLint("SetTextI18n")
    public static void showSearch(Boolean state)
    {
        // Checks if there is a network connection
        if(state)
        {
            // Hides the network TextView
            tvNetCon.setText("");
            tvNetCon.setVisibility(View.GONE);

            // Shows the widgets
            sv.setVisibility(View.VISIBLE);
            tvDisplay.setVisibility(View.VISIBLE);
            btnSearch.setVisibility(View.VISIBLE);
            lvResults.setVisibility(View.VISIBLE);
        }
        else
        {
            // Hides the widgets
            sv.setVisibility(View.GONE);
            tvDisplay.setVisibility(View.GONE);
            btnSearch.setVisibility(View.GONE);
            lvResults.setVisibility(View.GONE);
            tvNetCon.setVisibility(View.VISIBLE);

            // Shows the network TextView
            tvNetCon.setText("Error connecting to network. Please connect to a network and try again.");

        }
    }

    /**
     * getApiResult() method
     * @param s - search string
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
                     * @param response - api response
                     */
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            // Converts the string response to a JSON object
                            JSONObject object = new JSONObject(response);

                            pbSearch.setVisibility(View.GONE);
                            // Sets the list view adapter and passes in the api response
                            adapter = new FoodListViewAdapter(SearchCarbActivity.this, object.getJSONArray("products"));
                            lvResults.setAdapter(adapter);

                            // TextView display
                            String display ="No. results : " + adapter.getCount() ;

                            // Displays the number of relevant results
                            tvDisplay.setText(display);
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
                     * @param error - error
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

    /**
     * onPause() method
     */
    @Override
    public void onPause()
    {
        super.onPause();

        // Unregisters broadcast receiver
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