package doan.vinh.workit.ui.mainpage.statstab;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import doan.vinh.workit.data.model.StatsModel;
import doan.vinh.workit.databinding.ItemStatsViewBinding;
import doan.vinh.workit.ui.base.BaseViewHolder;

/**
 * Created by Doand on 3/27/2018.
 */

public class StatsTabAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    /*public static final int VIEW_TYPE_NO_STATS = 0;
    public static final int VIEW_TYPE_NORMAL = 1;*/

    ArrayList<StatsModel> mStatsList;

    public StatsTabAdapter(ArrayList<StatsModel> mStatsList)
    {
        this.mStatsList = mStatsList;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemStatsViewBinding itemStatsViewBinding = ItemStatsViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false);
        return new StatsViewHolder(itemStatsViewBinding);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return mStatsList.size();
    }

  /*  @Override
    public int getItemViewType(int position) {
        if (mStatsList != null && !mStatsList.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NO_STATS;
        }
    }*/

    public void refreshStats(ArrayList<StatsModel> mStatsList)
    {

        this.mStatsList.clear();
        this.mStatsList.addAll(mStatsList);
        Log.d("stats adapter ", "list " + mStatsList );
        notifyDataSetChanged();
    }



    public class StatsViewHolder extends BaseViewHolder {

        private StatsItemViewModel mStatsItemViewModel;
        private ItemStatsViewBinding mBinding;

        public StatsViewHolder(ItemStatsViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final StatsModel stats = mStatsList.get(position);
            mStatsItemViewModel = new StatsItemViewModel(stats);
            mBinding.setViewModel(mStatsItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();
        }


    }
}
