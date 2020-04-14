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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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
        // Create a new request queue
        final RequestQueue queue = Volley.newRequestQueue(this);

        // Replace all spaces with '+'
        String search = s.replaceAll("\\s","+");

        // Create the request url to return JSON data
        final String url = "https://world.openfoodsfacts.org/cgi.search.p1?search_terms=" + search +"&search_simple=1&action=process&json=1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("========== Search url : " + url);
                        System.out.println("========== API Respon : " + response);

                        try {
                            JSONObject object = new JSONObject(response);

                            adapter = new FoodListViewAdapter(SearchCarbActivity.this, object.getJSONArray("products"));
                            lvResults.setAdapter(adapter);

                            tvDisplay.setText("No. results : " + adapter.getCount());
                        } catch (JSONException e) {
                            Toast.makeText(SearchCarbActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        queue.stop();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(SearchCarbActivity.this, "API response : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        queue.stop();
                    }
                });

        queue.add(stringRequest);
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
