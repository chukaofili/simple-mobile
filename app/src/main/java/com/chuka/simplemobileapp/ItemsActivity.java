package com.chuka.simplemobileapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chuka.simplemobileapp.data.ItemResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemsActivity extends AppCompatActivity {

    private static final String TAG = ItemsActivity.class.getSimpleName();

    public  static final String EXTRA_ITEMS = "extra_items";

    @BindView(R.id.item_list) RecyclerView itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        ButterKnife.bind(this);

        ItemResponse items = (ItemResponse) getIntent().getSerializableExtra(EXTRA_ITEMS);

        itemList.setLayoutManager(new LinearLayoutManager(this));
        itemList.setItemAnimator(new DefaultItemAnimator());
        itemList.setAdapter(new ItemAdapter(items));
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

        List<ItemResponse.Item> items = new ArrayList<>();

        public ItemAdapter(ItemResponse items) {
            this.items = items.getItems();
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            holder.title.setText(items.get(position).getTitle());
            holder.desc.setText(items.get(position).getDescription());
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {

            public View itemView;

            @BindView(R.id.title) TextView title;
            @BindView(R.id.desc) TextView desc;

            public ItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                this.itemView = itemView;
            }
        }
    }
}
