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

public class DocumentFragment extends Fragment {

    private ListView list;


    public DocumentFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_document, container, false);
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

    void updateDocuments(List<? extends Document> documents, int image) {
        ListAdapter adapter = new ListAdapter(documents, image);
        this.list.setAdapter(adapter);
        this.list.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(getContext(),adapter.getItem(position).getId(),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), ViewDocumentActivity.class);
            startActivity(intent);
        });
    }

    private class ListAdapter extends BaseAdapter {

        private int count;
        private List<? extends Document> listDocuments;
        private int image;


        ListAdapter(List<? extends Document> documents, int image) {
            this.listDocuments = documents;
            if (documents != null) this.count = documents.size();
            else this.count = 0;
            this.image = image;
        }

        @Override
        public int getCount() {
            return this.count;
        }

        @Override
        public Document getItem(int i) {
            return listDocuments.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_item, null);
            ImageView documentItem = view.findViewById(R.id.itemImage);
            documentItem.setImageResource(this.image);
            TextView documentTitle = view.findViewById(R.id.itemTitle);
            documentTitle.setText(listDocuments.get(i).getName());

            return view;
        }

        @Override
        public void notifyDataSetChanged() {
            this.count = listDocuments.size();
            super.notifyDataSetChanged();
        }

    }

}