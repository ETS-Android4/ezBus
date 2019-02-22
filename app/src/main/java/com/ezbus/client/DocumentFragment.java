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

import com.ezbus.R;

import java.util.List;

public class DocumentFragment extends Fragment {

    private ListView list;


    public DocumentFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_document, container, false);
        list = view.findViewById(R.id.list_document);

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

    void updateDocuments(List<? extends Document> documents) {
        ListAdapter adapter = new ListAdapter(documents);
        this.list.setAdapter(adapter);
        this.list.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getContext(), ViewDocumentActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Document", adapter.getItem(position));
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private class ListAdapter extends BaseAdapter {

        private int count;
        private List<? extends Document> listDocuments;


        ListAdapter(List<? extends Document> documents) {
            this.listDocuments = documents;
            if (documents != null) this.count = documents.size();
            else this.count = 0;
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
            documentItem.setImageResource(listDocuments.get(i).getImage());
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