package com.example.natborks.diary;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.natborks.diary.Database.AppDatabase;
import com.example.natborks.diary.Database.DiaryEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel{

    private LiveData<List<DiaryEntry>> entries;

    public MainViewModel(@NonNull Application application) {
        super(application);

        AppDatabase database = AppDatabase.getsInstance(this.getApplication());
        entries = database.entryDao().loadAllEntries();
    }

    public LiveData<List<DiaryEntry>> getEntries() {
        return entries;
    }
}
