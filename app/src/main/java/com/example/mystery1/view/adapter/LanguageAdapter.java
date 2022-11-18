package com.example.mystery1.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystery1.R;
import com.example.mystery1.databinding.ItemLanguageBinding;
import com.example.mystery1.models.Language;
import com.example.mystery1.view.activity.LanguageActivity;
import com.example.mystery1.view.activity.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {
    private List<Language> languages = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setLanguages(List<Language> languages) {
        this.languages = languages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLanguageBinding binding = ItemLanguageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new LanguageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.txtLanguage.setText(languages.get(position).getTextLanguage());
        holder.binding.txtLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.binding.getRoot().getContext(), SettingsActivity.class);
                intent.putExtra("tag", languages.get(position).getTag());
                intent.putExtra("language", languages.get(position).getTextLanguage());
                holder.binding.getRoot().getContext().startActivity(intent);
                LanguageActivity.finishAct((Activity) holder.binding.getRoot().getContext());
            }
        });
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    public class LanguageViewHolder extends RecyclerView.ViewHolder {
        private ItemLanguageBinding binding;
        public LanguageViewHolder(ItemLanguageBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
