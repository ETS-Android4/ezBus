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
    //ListAdapter adapter = new ListAdapter();


    public ItemFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item, container, false);
        mAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, new ArrayList<String>());
        list = view.findViewById(R.id.list_item);
        list.setAdapter(mAdapter);

        return view;
    }

    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.getCount();
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
            title.setText("Ciao");

            return null;
        }

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
        if (mAdapter != null) {
            mAdapter.clear();
            for (Item i : listItem) {
                mAdapter.add(i.getId());
            }
        }
    }

}