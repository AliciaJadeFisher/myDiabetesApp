package uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.FoodItem;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * FoodListViewAdapter class
 */
public class FoodListViewAdapter extends BaseAdapter
{
    // ListViewAdapter attributes
    private Context context;
    private LayoutInflater inflater;
    private List<FoodItem> productList;

    /**
     * Main constructor
     * @param c - context called from
     * @param fl - list of food products
     * @throws JSONException - deals with JSON exceptions
     */
    public FoodListViewAdapter(Context c, JSONArray fl) throws JSONException
    {
        context = c;
        productList = getFoods(fl);
        inflater = LayoutInflater.from(context);
    }

    /**
     * ViewHolder class
     */
    public class ViewHolder
    {
        // View layout attributes
        TextView tvName, tvCarbs100, tvCarbsServ;
    }

    /**
     * getView() method
     * @param position - list position
     * @param view - view for listView
     * @param parent - parent view group
     * @return view
     */
    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        // Declares holder
        final ViewHolder holder;

        // Checks if the view is null or not
        if(view == null)
        {
            // Grabs the relevant view
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_food_result, null);

            // Initializes the layout widgets
            holder.tvName = view.findViewById(R.id.tv_product_name);
            holder.tvCarbs100 = view.findViewById(R.id.tv_carbs_100);
            holder.tvCarbsServ = view.findViewById(R.id.tv_carbs_serving);

            // Attaches the holder
            view.setTag(holder);
        }
        else
        {
            // Sets the holder
            holder = (ViewHolder)view.getTag();
        }

        final FoodItem food = productList.get(position);

        // Sets the textView texts for each list item
        holder.tvName.setText(food.getName());
        String c100_display = "Carbohydrates per 100g : " + food.getCarbs_100() + "g";
        String cServ_display = "Carbohydrates per serving (" + food.getServing() + "g) : " + food.getCarbs_serv() + "g";
        holder.tvCarbs100.setText(c100_display);
        holder.tvCarbsServ.setText(cServ_display);

        return view;
    }

    /**
     * getFoods() method
     * @param products - list of products
     * @return foods
     * @throws JSONException - deals with JSON exceptions
     */
    private List<FoodItem> getFoods(JSONArray products) throws JSONException
    {
        // New list
        List<FoodItem> foods = new ArrayList<>();

        // Loops through the JSONArray
        for(int i = 0; i < products.length(); i++)
        {
            // Grabs the current JSONObject
            JSONObject product = products.getJSONObject(i);

            // Gets the product name
            String n;
            try
            {
                n = product.getString("product_name");
            }
            catch(JSONException e)
            {
                n="n/a";
            }

            // Gets the product details
            int s;
            double c,cs;
            try
            {
                s = product.getInt("serving_quantity");
            }
            catch(JSONException e)
            {
                s = 0;
            }

            try
            {
                c = product.getJSONObject("nutriments").getDouble("carbohydrates_100g");
            }
            catch(JSONException e)
            {
                c = 0;
            }
            try
            {
                cs = product.getJSONObject("nutriments").getDouble("carbohydrates_serving");
            }
            catch(JSONException e)
            {
                cs = 0;
            }

            // Creates a new FoodItem with the product details and adds it to the list,
            // if all information is present
            if(!(n.equals("n/a") || s == 0 || c == 0 || cs == 0))
            {
                FoodItem newFood = new FoodItem(n, s, c, cs);
                foods.add(newFood);
            }

        }
        return foods;
    }

    /**
     * getCount() method
     * @return productList.getSize()
     */
    @Override
    public int getCount()
    {
        return productList.size();
    }

    /**
     * getItem() method
     * @param position - position in list
     * @return productList.get(position)
     */
    @Override
    public Object getItem(int position)
    {
        return productList.get(position);
    }

    /**
     * getItemId() method
     * @param position - position in list
     * @return position
     */
    @Override
    public long getItemId(int position)
    {
        return position;
    }
}