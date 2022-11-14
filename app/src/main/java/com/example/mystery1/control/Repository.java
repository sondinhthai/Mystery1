package com.example.mystery1.control;

import com.example.mystery1.control.remote.RequestDocumentsManager;
import com.example.mystery1.control.rest.Callback;
import com.example.mystery1.models.Documents;

import java.util.List;

public class Repository {
    private RequestDocumentsManager requestDocumentsManager;

    public Repository (){
        requestDocumentsManager = new RequestDocumentsManager();
    }
}
