package com.example.mystery1.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mystery1.R;
import com.example.mystery1.control.remote.RequestDocumentsManager;
import com.example.mystery1.control.rest.Callback;
import com.example.mystery1.databinding.FragmentDocumentsBinding;
import com.example.mystery1.models.Documents;
import com.example.mystery1.view.adapter.DocumentsAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocumentsFragment extends Fragment {
    private FragmentDocumentsBinding binding;

    private DocumentsAdapter documentsAdapter;
    private RequestDocumentsManager requestDocumentsManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_documents, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.revDocument.setLayoutManager(layoutManager);
        documentsAdapter = new DocumentsAdapter();
        setListDocuments();

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefresh.setRefreshing(false);
                setListDocuments();
            }
        });

        binding.revDocument.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int currentFirstVisible = layoutManager.findLastVisibleItemPosition();
                if (currentFirstVisible < documentsAdapter.getDocuments().size() - 1) {
                    binding.toTheTop.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    binding.toTheTop.setVisibility(View.VISIBLE);
                    binding.toTheTop.setOnClickListener(view -> {
                        binding.toTheTop.setVisibility(View.INVISIBLE);
                        binding.revDocument.scrollToPosition(0);
                    });
                } else if (dy == 0) {
                    binding.toTheTop.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void setListDocuments() {
        requestDocumentsManager = new RequestDocumentsManager();
        requestDocumentsManager.getAllDocument(new Callback() {
            @Override
            public void getDocument(List<Documents> list) {
                super.getDocument(list);
                Collections.shuffle(list);
                documentsAdapter.setDocuments(list);
                binding.revDocument.setAdapter(documentsAdapter);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}