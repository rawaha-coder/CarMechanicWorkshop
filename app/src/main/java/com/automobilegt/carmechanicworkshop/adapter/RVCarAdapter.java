package com.automobilegt.carmechanicworkshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.automobilegt.carmechanicworkshop.R;
import com.automobilegt.carmechanicworkshop.interfaces.ListItemClickListener;
import com.automobilegt.carmechanicworkshop.model.Car;

import java.util.List;

public class RVCarAdapter extends RecyclerView.Adapter<RVCarAdapter.ViewHolder> {

    private List<Car> mCarList;
    private ListItemClickListener mListItemClickListener;

    public RVCarAdapter(List<Car> carBrandList, ListItemClickListener listItemClickListener) {
        mCarList = carBrandList;
        mListItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.rv_row_car_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Car car = mCarList.get(position);

        ImageView brandLogo = holder.mCarBrandLogoImageView;
        brandLogo.setImageResource(car.getLogo());

        TextView modelYear = holder.mModelYearTextView;
        modelYear.setText(car.getBrandModelYear());

        ImageView playArrow = holder.mPlayArrowImageView;
        playArrow.setImageResource(R.drawable.ic_play_arrow);
        LinearLayout root = holder.rvRowRoot;
        if (position == (mCarList.size()-1)){
            root.setPadding(0,0,0,100);
        }
    }

    @Override
    public int getItemCount() {
        return mCarList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mCarBrandLogoImageView;
        TextView mModelYearTextView;
        ImageView mPlayArrowImageView;
        LinearLayout rvRowRoot;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCarBrandLogoImageView = itemView.findViewById(R.id.rv_row_car_logo);
            mModelYearTextView = itemView.findViewById(R.id.rv_row_car);
            mPlayArrowImageView = itemView.findViewById(R.id.rv_row_play_arrow);
            rvRowRoot = itemView.findViewById(R.id.rv_row_car_root);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListItemClickListener.onListItemClick(getAdapterPosition());
        }
    }
}
