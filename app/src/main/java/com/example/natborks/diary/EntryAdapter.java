package com.example.natborks.diary;


import android.arch.persistence.room.Database;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.natborks.diary.Database.DiaryEntry;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {

    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";

    private List<DiaryEntry> mDataSet;

    private final EntryAdapterOnClickHandler mClickHandler;

    //private final View.OnClickListener mOnClickListener = new View.OnClickListener();

    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    interface EntryAdapterOnClickHandler{
        void onClick(int entry);
    }

    public EntryAdapter(EntryAdapterOnClickHandler handler){
        mClickHandler = handler;
    }

    public class EntryViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{
        TextView titleView;
        TextView bodyView;
        TextView createdAt;
        ;

        public EntryViewHolder(View view){
            super(view);
           titleView = (TextView) view.findViewById(R.id.tv_entryTitle);
           bodyView = (TextView) view.findViewById(R.id.tv_entryDescription);
           createdAt = (TextView) view.findViewById(R.id.tv_createdAt);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adatperPostion = getAdapterPosition();
                    int entryItem = mDataSet.get(getAdapterPosition()).getId();

                    mClickHandler.onClick(entryItem);
                }
            });
        }



        public void onClick(View v){
            int adatperPostion = getAdapterPosition();
            int entryItem = mDataSet.get(getAdapterPosition()).getId();

            mClickHandler.onClick(entryItem);
        }
    }

    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.entry_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,viewGroup, shouldAttachToParentImmediately);

        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EntryViewHolder holder, int position) {
        DiaryEntry diaryEntry = mDataSet.get(position);
        String title = diaryEntry.getTitle();
        String body = diaryEntry.getBody();
        String createdAt = dateFormat.format(diaryEntry.getCreated_at());

        holder.titleView.setText(title);
        holder.bodyView.setText(body);
        holder.createdAt.setText(createdAt);


    }

    @Override
    public int getItemCount() {
        if (null == mDataSet) return 0;
        return mDataSet.size();
    }

    public List<DiaryEntry> getEntries() {
        return mDataSet;
    }

    public void setEntryData(List<DiaryEntry> entryData) {
        mDataSet = entryData;
        notifyDataSetChanged();
    }
}