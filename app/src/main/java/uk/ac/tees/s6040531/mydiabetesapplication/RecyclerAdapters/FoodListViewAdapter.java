package uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters;

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
    Context context;
    LayoutInflater inflater;
    List<FoodItem> productList = new ArrayList<>();

    /**
     * Main constructor
     * @param c
     * @param fl
     * @throws JSONException
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
     * @param position
     * @param view
     * @param parent
     * @return view
     */
    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        final Activity activity = (Activity)context;
        final ViewHolder holder;

        // Checks if the view is null or not
        if(view == null)
        {
            // Grabs the relevant view
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_food_result, null);

            // Initializes the layout widgets
            holder.tvName = (TextView)view.findViewById(R.id.tv_product_name);
            holder.tvCarbs100 = (TextView)view.findViewById(R.id.tv_carbs_100);
            holder.tvCarbsServ = (TextView)view.findViewById(R.id.tv_carbs_serving);

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
        holder.tvCarbs100.setText("Carbohydrates per 100g : " + food.getCarbs_100() + "g");
        holder.tvCarbsServ.setText("Carbohydrates per serving (" + food.getServing() + "g) : " + food.getCarbs_serv() + "g");

        return view;
    }

    /**
     * getFoods() method
     * @param products
     * @return foods
     * @throws JSONException
     */
    public List<FoodItem> getFoods(JSONArray products) throws JSONException
    {
        List<FoodItem> foods = new ArrayList<>();

        // Loops through the JSONArray
        for(int i = 0; i < products.length(); i++)
        {
            // Grabs the current JSONObject
            JSONObject product = products.getJSONObject(i);

            String n;
            System.out.println("=========== Product : " + product);
            // Gets the product name
            try
            {
                n = product.getString("product_name");
                System.out.println("=============== Product Name : " + n + "===============");
            }
            catch(JSONException e)
            {
                n="n/a";
            }


            int s;
            double c,cs;

            // Gets the product details
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
     * @param position
     * @return productList.get(position)
     */
    @Override
    public Object getItem(int position)
    {
        return productList.get(position);
    }

    /**
     * getItemId() method
     * @param position
     * @return position
     */
    @Override
    public long getItemId(int position)
    {
        return position;
    }
}