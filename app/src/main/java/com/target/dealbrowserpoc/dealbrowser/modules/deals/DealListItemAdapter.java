package com.target.dealbrowserpoc.dealbrowser.modules.deals;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.target.dealbrowserpoc.dealbrowser.R;
import com.target.dealbrowserpoc.dealbrowser.core.models.DealItem;
import com.target.dealbrowserpoc.dealbrowser.modules.dealdetails.DealDetailsActivity;

import java.util.List;

public class DealListItemAdapter extends RecyclerView.Adapter<DealListItemAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<DealItem> dealItems;
    private boolean isListViewType;
    private Context context;
    private Picasso picasso;

    public DealListItemAdapter(Context ctx, List<DealItem> items, boolean isListViewType) {
        super();
        context = ctx;
        inflater = LayoutInflater.from(context);
        dealItems = items;
        this.isListViewType = isListViewType;
        picasso = Picasso.with(context);
        picasso.setLoggingEnabled(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DealListItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(isListViewType ? R.layout.deal_list_item : R.layout.deal_grid_item, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final DealItem dealItem = dealItems.get(position);

        picasso.load(dealItem.getImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.no_image_available)
                .fit()
//                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(holder.productImage);

        holder.title.setText(dealItem.getTitle());
        holder.price.setText(dealItem.getSalePrice());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DealDetailsActivity.class);
                intent.putExtra("dealitem", dealItem);
                context.startActivity(intent);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dealItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImage;
        public TextView title, price;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            productImage = v.findViewById(R.id.deal_list_item_image_view);
            title = v.findViewById(R.id.deal_list_item_title);
            price = v.findViewById(R.id.deal_list_item_price);
        }
    }
}
