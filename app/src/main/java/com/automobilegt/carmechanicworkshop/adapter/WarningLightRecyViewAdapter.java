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
import com.automobilegt.carmechanicworkshop.model.WarningLight;

import java.util.List;

public class WarningLightRecyViewAdapter extends RecyclerView.Adapter<WarningLightRecyViewAdapter.ViewHolder> {

    private List<WarningLight> mWarningLightList;

    public WarningLightRecyViewAdapter(List<WarningLight> warningLightList) {
        mWarningLightList = warningLightList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View warningLightRowAdapter = layoutInflater.inflate(R.layout.warning_light_row_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(warningLightRowAdapter);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // Get the warninglight data model based on position
        WarningLight warningLight = mWarningLightList.get(position);

        ImageView warningImage = holder.mWarningImageView;
        warningImage.setImageResource(warningLight.getSymboleResId());

        TextView symbolTitle = holder.mTitleTextView;
        symbolTitle.setText(warningLight.getSymbolTitle());

        ImageView playArrow = holder.mPlayArrowImageView;
        playArrow.setImageResource(R.drawable.play_arrow_black);

    }

    @Override
    public int getItemCount() {
        return mWarningLightList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mWarningImageView;
        TextView mTitleTextView;
        ImageView mPlayArrowImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mWarningImageView = itemView.findViewById(R.id.adapter_warning_light_icon);
            mTitleTextView = itemView.findViewById(R.id.adapter_warning_light_title);
            mPlayArrowImageView = itemView.findViewById(R.id.adapter_play_arrow_image_view);

        }
    }
}
