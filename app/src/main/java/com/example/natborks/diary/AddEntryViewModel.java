package com.example.natborks.diary;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.natborks.diary.Database.AppDatabase;
import com.example.natborks.diary.Database.DiaryEntry;

public class AddEntryViewModel extends ViewModel {
    private LiveData<DiaryEntry> entry;

    public AddEntryViewModel(AppDatabase database, int entryId){
        entry = database.entryDao().loadEntryById(entryId);
    }

    public LiveData<DiaryEntry> getEntry() {
        return entry;
    }
}
