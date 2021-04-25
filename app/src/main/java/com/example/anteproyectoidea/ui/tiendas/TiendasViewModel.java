package com.example.anteproyectoidea.ui.tiendas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TiendasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TiendasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}