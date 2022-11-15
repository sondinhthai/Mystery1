package com.example.mystery1.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mystery1.R;
import com.example.mystery1.control.remote.RequestDocumentsManager;
import com.example.mystery1.control.rest.Callback;
import com.example.mystery1.databinding.ItemBookmarkBinding;
import com.example.mystery1.databinding.ItemDocumentBinding;
import com.example.mystery1.models.Documents;
import com.example.mystery1.view.BottomSheetBookmark;
import com.example.mystery1.view.activity.DetailDocumentActivity;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>{
    private List<Documents> documents;
    private RequestDocumentsManager requestDocumentsManager;

    @SuppressLint("NotifyDataSetChanged")
    public void setDocuments(List<Documents> documents) {
        this.documents = documents;
        notifyDataSetChanged();
    }

    public List<Documents> getDocuments() {
        return documents;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookmarkBinding binding = ItemBookmarkBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BookmarkViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, @SuppressLint("RecyclerView") int position) {
        requestDocumentsManager = new RequestDocumentsManager();
//        BottomSheetBookmark bottomSheetBookmark = new BottomSheetBookmark(holder.binding.getRoot().getContext(), R.style.MaterialDialogSheet);
        holder.binding.titleDocument.setText(documents.get(position).getTitle());
        Glide.with(holder.binding.getRoot().getContext()).load(documents.get(position).getImgUrl()).into(holder.binding.imageDocument);
        holder.binding.itemDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(String.valueOf(R.string.title), documents.get(position).getTitle());
                bundle.putString(String.valueOf(R.string.imgUrl), documents.get(position).getImgUrl());
                bundle.putString(String.valueOf(R.string.content), documents.get(position).getContent());
                DetailDocumentActivity.starter(holder.binding.getRoot().getContext(), bundle);
//                bottomSheetBookmark.dismiss();
            }
        });

        String title = documents.get(position).getTitle();
        String imgUrl = documents.get(position).getImgUrl();
        String content = documents.get(position).getContent();

        Documents document = new Documents(title, content, imgUrl);

        requestDocumentsManager.getAllBookmark(new Callback() {
            @Override
            public void getDocument(List<Documents> list) {
                super.getDocument(list);

                for (Documents documents1 : list) {
                    if (documents1.getTitle().equals(document.getTitle())) {
                        holder.binding.clickBookmark.setChecked(true);
                    }
                }
            }
        });

        holder.binding.clickBookmark.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                if (holder.binding.clickBookmark.isChecked()) {
//                    requestDocumentsManager.saveBookmarks(document);
                } else {
                    documents.remove(position);
                    requestDocumentsManager.deleteBookmarks(document);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }

    public static class BookmarkViewHolder extends RecyclerView.ViewHolder {
        private ItemBookmarkBinding binding;
        public BookmarkViewHolder(ItemBookmarkBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
