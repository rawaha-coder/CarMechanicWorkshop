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
import com.automobilegt.carmechanicworkshop.model.CarVideoModel;

import java.util.ArrayList;

public class VideoListRecyViewAdapter extends RecyclerView.Adapter<VideoListRecyViewAdapter.ViewHolder> {

    private ArrayList<CarVideoModel> mCarVideoList;
    private int mLogoResId;

    public VideoListRecyViewAdapter(ArrayList<CarVideoModel> carModelList, int logoResId) {
        mCarVideoList = carModelList;
        mLogoResId = logoResId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View carModelRowAdapter = layoutInflater.inflate(R.layout.video_list_row_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(carModelRowAdapter);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CarVideoModel videoModel = mCarVideoList.get(position);

        ImageView brandLogo = holder.mCarBrandLogoImageView;
        brandLogo.setImageResource(mLogoResId);

        TextView videoTitle = holder.mVideoTitleTextView;
        videoTitle.setText(videoModel.getVideoTitle());

        ImageView playArrow = holder.mPlayArrowImageView;
        playArrow.setImageResource(R.drawable.ic_play_arrow);
    }

    @Override
    public int getItemCount() {
        return mCarVideoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mCarBrandLogoImageView;
        TextView mVideoTitleTextView;
        ImageView mPlayArrowImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCarBrandLogoImageView = itemView.findViewById(R.id.adapter_car_brand_logo);
            mVideoTitleTextView = itemView.findViewById(R.id.adapter_car_video_name);
            mPlayArrowImageView = itemView.findViewById(R.id.adapter_play_arrow_model);
        }
    }

}
