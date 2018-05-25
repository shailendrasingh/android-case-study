package com.target.dealbrowserpoc.dealbrowser.modules.deals;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.target.dealbrowserpoc.dealbrowser.R;
import com.target.dealbrowserpoc.dealbrowser.core.dataprovider.TargetRepository;
import com.target.dealbrowserpoc.dealbrowser.core.models.DealItem;
import com.target.dealbrowserpoc.dealbrowser.ui.BoundaryItemDecoration;
import com.target.dealbrowserpoc.dealbrowser.ui.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DealListFragment extends Fragment {
    private View rootView;
    private Activity activity;
    private EmptyRecyclerView rvDeals;
    private DealListItemAdapter adapter;
    private TextView tvEmptyView;
    private ArrayList<DealItem> dealItemList;
    private boolean isListViewType = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuViewType:
                isListViewType = !isListViewType;
                item.setIcon(isListViewType ? R.drawable.ic_viewtype_list : R.drawable.ic_viewtype_grid);
                refreshView(dealItemList);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.change_view_type, menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_deal_list, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        initViews();
        new PullTask().execute();
    }

    private void initViews() {
        tvEmptyView = rootView.findViewById(R.id.tvNoDeal);
        rvDeals = rootView.findViewById(R.id.rvDeals);
        rvDeals.setHasFixedSize(true);
        rvDeals.setEmptyView(tvEmptyView);
    }

    private void refreshView(List<DealItem> dealItemList) {
        BoundaryItemDecoration itemDecoration = new BoundaryItemDecoration(activity, R.color.gray_color, 2);
        rvDeals.setLayoutManager(isListViewType ? new LinearLayoutManager(activity) : new GridLayoutManager(activity, 2));
        adapter = new DealListItemAdapter(activity, dealItemList, isListViewType);
        rvDeals.addItemDecoration(itemDecoration);
        rvDeals.setAdapter(adapter);
    }

    private class PullTask extends AsyncTask<Void, Void, List<DealItem>> {
        @Override
        protected List<DealItem> doInBackground(Void... voids) {
            TargetRepository targetRepository = new TargetRepository(activity);
            return targetRepository.getDeals();
        }

        @Override
        protected void onPostExecute(List<DealItem> result) {
            super.onPostExecute(result);
            dealItemList = (ArrayList<DealItem>) result;
            refreshView(dealItemList);
        }
    }

}