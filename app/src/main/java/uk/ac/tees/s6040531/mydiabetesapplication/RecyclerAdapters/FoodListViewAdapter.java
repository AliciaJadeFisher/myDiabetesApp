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

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.FoodItem;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class FoodListViewAdapter extends BaseAdapter
{
    Context context;
    LayoutInflater inflater;
    List<FoodItem> producList = new ArrayList<>();

    public FoodListViewAdapter(Context c, JSONArray fl) throws JSONException
    {
        context = c;
        producList = getFoods(fl);
        inflater = LayoutInflater.from(context);
    }

    public class ViewHolder
    {
        TextView tvName, tvCarbs100, tvCarbsServ;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        final Activity activity = (Activity)context;
        final ViewHolder holder;

        if(view == null)
        {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_food_result, null);

            holder.tvName = (TextView)view.findViewById(R.id.tv_product_name);
            holder.tvCarbs100 = (TextView)view.findViewById(R.id.tv_carbs_100);
            holder.tvCarbsServ = (TextView)view.findViewById(R.id.tv_carbs_serving);

            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)view.getTag();
        }

        final FoodItem food = producList.get(position);

        holder.tvName.setText(food.getName());
        holder.tvCarbs100.setText("Carbohydrates per 100g : " + food.getCarbs_100() + "g");
        holder.tvCarbsServ.setText("Carbohydrates per serving (" + food.getServing() + "g) : " + food.getCarbs_serv() + "g");

        return view;
    }

    public List<FoodItem> getFoods(JSONArray products) throws JSONException
    {
        List<FoodItem> foods = new ArrayList<>();

        return foods;
    }
}
