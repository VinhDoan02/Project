package doan.vinh.workit.ui.mainpage.statstab;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import javax.inject.Inject;

import doan.vinh.workit.R;
import doan.vinh.workit.data.model.StatsModel;
import doan.vinh.workit.databinding.FragmentStatsTabBinding;
import doan.vinh.workit.ui.base.BaseFragment;

public class StatsTabFragment extends BaseFragment {

    @Inject
    StatsTabAdapter statsTabAdapter;

    FragmentStatsTabBinding fragmentStatsTabBinding;

    @Inject
    StatsTabViewModel statsTabViewModel;

    public static StatsTabFragment newInstance() {
        Bundle args = new Bundle();
        StatsTabFragment fragment = new StatsTabFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initBinding(inflater,container);

        return fragmentStatsTabBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLayout();
    }

    public void initLayout()
    {
        Log.d("stats tab", "initiating layout " + statsTabAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentStatsTabBinding.rvStatsList.setLayoutManager(mLayoutManager);
        fragmentStatsTabBinding.rvStatsList.setItemAnimator(new DefaultItemAnimator());
        fragmentStatsTabBinding.rvStatsList.setAdapter(statsTabAdapter);

        statsTabViewModel.getResponse().observe(this,data -> processData(data));
    }

    public void processData(ArrayList<StatsModel> data)
    {
        statsTabAdapter.refreshStats(data);
    }

    public void initBinding(LayoutInflater inflater,ViewGroup container)
    {
      fragmentStatsTabBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_stats_tab,container,false);
    }

}
