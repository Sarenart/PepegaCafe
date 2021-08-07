package com.cyberburyatenterprise.pepegacafe.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cyberburyatenterprise.pepegacafe.R;
import com.cyberburyatenterprise.pepegacafe.databinding.MealListItemBinding;
import com.cyberburyatenterprise.pepegacafe.model.models.Meal;

import java.util.ArrayList;

public class MealRecyclerViewAdapter extends RecyclerView.Adapter<MealRecyclerViewAdapter.MealViewHolder> {

    private OnItemClickListener onItemClickListener;
    private ArrayList<Meal> mealArrayList = new ArrayList<>();

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MealListItemBinding mealListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.meal_list_item,
                parent, false
        );
        return new MealViewHolder(mealListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull  MealRecyclerViewAdapter.MealViewHolder holder, int position) {
        Meal meal = mealArrayList.get(position);
        holder.mealListItemBinding.setMeal(meal);
    }

    @Override
    public int getItemCount() {
        return mealArrayList.size();
    }


    class MealViewHolder extends RecyclerView.ViewHolder{

        MealListItemBinding mealListItemBinding;

        public MealViewHolder(@NonNull MealListItemBinding mealListItemBinding) {
            super(mealListItemBinding.getRoot());

            this.mealListItemBinding = mealListItemBinding;

            mealListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if(onItemClickListener != null & position !=RecyclerView.NO_POSITION)
                        onItemClickListener.onItemClick(mealArrayList.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener{

        void onItemClick(Meal characterInfo);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setMealArrayList(ArrayList<Meal> mealArrayList) {
        clearMealArrayList();
        this.mealArrayList = mealArrayList;
        notifyDataSetChanged();
    }

    public void clearMealArrayList(){
        this.mealArrayList.clear();
    }
}
