package com.automobilegt.carmechanicworkshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.automobilegt.carmechanicworkshop.R;
import com.automobilegt.carmechanicworkshop.model.CarBrand;

import java.util.ArrayList;

public class CarBrandRecyViewAdapter extends RecyclerView.Adapter<CarBrandRecyViewAdapter.ViewHolder> {

    private ArrayList<CarBrand> mCarBrandList;

    public CarBrandRecyViewAdapter(ArrayList<CarBrand> carBrand) {
        mCarBrandList = carBrand;
    }

    @NonNull
    @Override
    public CarBrandRecyViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View carBrandRowAdapter = layoutInflater.inflate(R.layout.car_brand_row_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(carBrandRowAdapter);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarBrandRecyViewAdapter.ViewHolder holder, final int position) {

        CarBrand carMaker = mCarBrandList.get(position);


        ImageView brandLogo = holder.mCarBrandLogoImageView;
        brandLogo.setImageResource(carMaker.getCarBrandLogo());

        TextView brandName = holder.mBrandNameTextView;
        brandName.setText(carMaker.getCarBrandName());

        ImageView playArrow = holder.mPlayArrowImageView;
        playArrow.setImageResource(R.drawable.play_arrow_black);

    }

    @Override
    public int getItemCount() {
        return mCarBrandList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mCarBrandLogoImageView;
        TextView mBrandNameTextView;
        ImageView mPlayArrowImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCarBrandLogoImageView = itemView.findViewById(R.id.adapter_car_brand_logo);
            mBrandNameTextView = itemView.findViewById(R.id.adapter_car_brand_name);
            mPlayArrowImageView = itemView.findViewById(R.id.adapter_play_arrow_image);
        }
    }
}
