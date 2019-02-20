package com.ezbus.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ezbus.R;

import java.util.List;

public class ItemFragment extends Fragment {

    private ListView list;


    public ItemFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        list = view.findViewById(R.id.list_item);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    void updateItem(List<? extends Item> listItem, int image) {
        ListAdapter adapter = new ListAdapter(listItem, image);
        this.list.setAdapter(adapter);
        this.list.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(getContext(),adapter.getItem(position).getId(),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), ViewItemActivity.class);
            startActivity(intent);
        });
    }

    private class ListAdapter extends BaseAdapter {

        private int count;
        private List<? extends Item> listItem;
        private int image;


        ListAdapter(List<? extends Item> listItem, int image) {
            this.listItem = listItem;
            if (listItem != null) this.count = listItem.size();
            else this.count = 0;
            this.image = image;
        }

        @Override
        public int getCount() {
            return this.count;
        }

        @Override
        public Item getItem(int i) {
            return listItem.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_item, null);
            ImageView itemImage = view.findViewById(R.id.itemImage);
            itemImage.setImageResource(this.image);
            TextView itemTitle = view.findViewById(R.id.itemTitle);
            itemTitle.setText(listItem.get(i).getName());

            return view;
        }

        @Override
        public void notifyDataSetChanged() {
            this.count = listItem.size();
            super.notifyDataSetChanged();
        }

    }

}