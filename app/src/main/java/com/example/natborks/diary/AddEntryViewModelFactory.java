package com.example.natborks.diary;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.natborks.diary.Database.AppDatabase;

public class AddEntryViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private final AppDatabase mDb;
    private final int mEntryid;

    public AddEntryViewModelFactory(AppDatabase database, int entryId){
        mDb = database;
        mEntryid = entryId;
    }

    public <T extends ViewModel> T create(Class<T> modelClass){

        return (T) new AddEntryViewModel(mDb, mEntryid);
    }
}
