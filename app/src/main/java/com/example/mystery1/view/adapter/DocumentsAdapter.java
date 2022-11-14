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
import com.example.mystery1.databinding.ItemDocumentBinding;
import com.example.mystery1.models.Documents;
import com.example.mystery1.view.activity.DetailDocumentActivity;

import java.util.ArrayList;
import java.util.List;

public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.DocumentsViewHolder> {
    private List<Documents> documents;

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
    public DocumentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDocumentBinding binding = ItemDocumentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DocumentsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.titleDocument.setText(documents.get(position).getTitle());
        Glide.with(holder.binding.getRoot().getContext()).load(documents.get(position).getImgUrl()).into(holder.binding.imageDocument);
        holder.binding.itemDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(String.valueOf(R.string.title), documents.get(position).getTitle());
                bundle.putString(String.valueOf(R.string.imgUrl), documents.get(position).getImgUrl());
                bundle.putString(String.valueOf(R.string.content), documents.get(position).getContent());
                DetailDocumentActivity.starter((Activity) holder.binding.getRoot().getContext(), bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }

    public static class DocumentsViewHolder extends RecyclerView.ViewHolder {
        private ItemDocumentBinding binding;
        public DocumentsViewHolder(ItemDocumentBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
