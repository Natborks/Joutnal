package com.example.natborks.diary;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.example.natborks.diary.Database.AppDatabase;
import com.example.natborks.diary.Database.AppExecutors;
import com.example.natborks.diary.Database.DiaryEntry;

import java.util.List;

import static android.widget.GridLayout.VERTICAL;
import static com.example.natborks.diary.CreateEntry.EXTRA_TASK_ID;


public class MainActivity extends AppCompatActivity
        implements EntryAdapter.EntryAdapterOnClickHandler{
    private static final int NUM_LIST_ITEMS = 50;
    private EntryAdapter mAdapter;
    private RecyclerView mEntryList;
    private Toast mToast;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEntryList = (RecyclerView) findViewById(R.id.rv_entries);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mEntryList.setLayoutManager(linearLayoutManager);

        mEntryList.setHasFixedSize(true);

        mAdapter = new EntryAdapter(this);

        mEntryList.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mEntryList.addItemDecoration(decoration);

        /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |
                ItemTouchHelper.RIGHT){
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target){
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir){
             //delete entry with new runnable
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<DiaryEntry> entries = mAdapter.getEntries();
                        mDb.entryDao().deleteEntry(entries.get(position));
                    }
                });
            }
        }).attachToRecyclerView(mEntryList);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(MainActivity.this, CreateEntry.class);
                startActivity(intent);
            }
        });

        mDb = AppDatabase.getsInstance(getApplicationContext());

        setUpViewModel();
    }

    @Override
    public void onClick(int entryItemId){
        Intent intent = new Intent(MainActivity.this, CreateEntry.class);
        intent.putExtra(EXTRA_TASK_ID,entryItemId);
        startActivity(intent);
    }

    private void setUpViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getEntries().observe(this, new Observer<List<DiaryEntry>>() {
            @Override
            public void onChanged(@Nullable List<DiaryEntry> diaryEntries) {
                mAdapter.setEntryData(diaryEntries);
            }
        });
    }


}
