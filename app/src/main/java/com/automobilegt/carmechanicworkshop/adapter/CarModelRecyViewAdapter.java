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
import com.automobilegt.carmechanicworkshop.model.CarModel;

import java.util.ArrayList;

public class CarModelRecyViewAdapter extends RecyclerView.Adapter<CarModelRecyViewAdapter.ViewHolder> {

    private ArrayList<CarModel> mCarModelList;

    public CarModelRecyViewAdapter(ArrayList<CarModel> carModelList) {
        mCarModelList = carModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View carModelRowAdapter = layoutInflater.inflate(R.layout.car_model_row_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(carModelRowAdapter);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CarModel carModel = mCarModelList.get(position);

        ImageView brandLogo = holder.mCarBrandLogoImageView;
        brandLogo.setImageResource(carModel.getCarModelLogo());

        TextView brandName = holder.mModelNameTextView;
        brandName.setText(carModel.getCarModelName());

        ImageView playArrow = holder.mPlayArrowImageView;
        playArrow.setImageResource(R.drawable.play_arrow_black);
    }

    @Override
    public int getItemCount() {
        return mCarModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mCarBrandLogoImageView;
        TextView mModelNameTextView;
        ImageView mPlayArrowImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCarBrandLogoImageView = itemView.findViewById(R.id.adapter_car_model_logo);
            mModelNameTextView = itemView.findViewById(R.id.adapter_car_model_name);
            mPlayArrowImageView = itemView.findViewById(R.id.adapter_play_arrow_model);
        }
    }
}
