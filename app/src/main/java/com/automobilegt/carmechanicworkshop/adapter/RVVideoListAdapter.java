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
import com.automobilegt.carmechanicworkshop.interfaces.ListItemClickListener;
import com.automobilegt.carmechanicworkshop.model.RepairVideo;

import java.util.ArrayList;

public class RVVideoListAdapter extends RecyclerView.Adapter<RVVideoListAdapter.ViewHolder> {
    private ListItemClickListener mListItemClickListener;
    private ArrayList<RepairVideo> mCarVideoList;
    private int mLogoResId;

    public RVVideoListAdapter(ListItemClickListener listItemClickListener, ArrayList<RepairVideo> carModelList, int logoResId) {
        mCarVideoList = carModelList;
        mLogoResId = logoResId;
        mListItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.video_list_row_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RepairVideo videoModel = mCarVideoList.get(position);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mCarBrandLogoImageView;
        TextView mVideoTitleTextView;
        ImageView mPlayArrowImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCarBrandLogoImageView = itemView.findViewById(R.id.adapter_car_brand_logo);
            mVideoTitleTextView = itemView.findViewById(R.id.adapter_car_video_name);
            mPlayArrowImageView = itemView.findViewById(R.id.adapter_play_arrow_model);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListItemClickListener.onListItemClick(getAdapterPosition());
        }
    }
}
