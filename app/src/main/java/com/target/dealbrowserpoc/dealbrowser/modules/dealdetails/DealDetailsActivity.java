package com.target.dealbrowserpoc.dealbrowser.modules.dealdetails;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.target.dealbrowserpoc.dealbrowser.R;
import com.target.dealbrowserpoc.dealbrowser.core.models.DealItem;

/**
 * Created by CropIn-Shailendra on 5/25/2018.
 */

public class DealDetailsActivity extends Activity {

    ImageView ivProduct;
    TextView tvSalePrice, tvPrice, tvTitle, tvDescription;
    DealItem dealItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_details);
        getExtras();
        setTitle();
        initViews();
        renderViews();
    }

    private void setTitle(){
        setTitle("Details");
    }
        private void initViews() {
        ivProduct = findViewById(R.id.ivProduct);
        tvSalePrice = findViewById(R.id.tvSalePrice);
        tvPrice = findViewById(R.id.tvPrice);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
    }

    private void getExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            dealItem = (DealItem) extras.getSerializable("dealitem");
        }
    }

    private void renderViews() {
        if (dealItem != null) {
            Picasso.with(this).load(dealItem.getImage())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.no_image_available)
                    .fit()
                    .into(ivProduct);

            tvSalePrice.setText(dealItem.getSalePrice());
//            tvPrice.setText(dealItem.getPrice());

            tvTitle.setText(dealItem.getTitle());
            tvDescription.setText(dealItem.getDescription());
            strikeThroughRegularPrice();
        }
    }

    private void strikeThroughRegularPrice() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("Reg:");
        ssb.append(" ");

        // Create a span that will strikethrough the text
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();

        // Add the secondWord and apply the strikethrough span to only the second word
        ssb.append(dealItem.getPrice());
        ssb.setSpan(
                strikethroughSpan,
                ssb.length() - dealItem.getPrice().length(),
                ssb.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

// Set the TextView text and denote that it is Editable
// since it's a SpannableStringBuilder
        tvPrice.setText(ssb, TextView.BufferType.EDITABLE);
    }
}
