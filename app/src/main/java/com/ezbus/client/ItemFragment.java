package com.ezbus.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ezbus.R;

import java.util.ArrayList;
import java.util.List;

public class ItemFragment extends Fragment implements View.OnClickListener {

    private View view;
    private ListView list;
    private ArrayAdapter mAdapter;


    public ItemFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item, container, false);
        mAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, new ArrayList<String>());
        list = view.findViewById(R.id.list_item);

        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    void updateItem(List<? extends Item> listItem) {
        this.list.setAdapter(new ListAdapter(listItem));
        /*if (mAdapter != null) {
            mAdapter.clear();
            for (Item i : listItem) {
                mAdapter.add(i.getId());
            }
        }*/
    }

    class ListAdapter extends BaseAdapter {

        private int count;
        private List<? extends Item> listItem;


        ListAdapter(List<? extends Item> listItem) {
            this.listItem = listItem;
            if (listItem != null) this.count = listItem.size();
            else this.count = 0;
        }

        @Override
        public int getCount() {
            return this.count;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_item, null);
            ImageView imgview = view.findViewById(R.id.itemImage);
            TextView title = view.findViewById(R.id.itemTitle);
            title.setText(listItem.get(i).getId());

            return view;
        }

        @Override
        public void notifyDataSetChanged() {
            this.count = listItem.size();
            super.notifyDataSetChanged();
        }

    }

}