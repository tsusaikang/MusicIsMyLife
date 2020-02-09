package com.kangjusang.musicismylife.ui.player;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kangjusang.musicismylife.R;

import java.util.ArrayList;
import java.util.List;

public class LyricsAdapter extends RecyclerView.Adapter<LyricsViewHolder> {

    List<String> mLyrics = new ArrayList<>();
    int mItemViewHeight = 0;

    void updateLyrics(ArrayList<String> lyrics) {
        mLyrics = lyrics;
    }

    @NonNull
    @Override
    public LyricsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.lyrics_row_1, parent, false);
        return new LyricsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LyricsViewHolder holder, int position) {
        holder.tvLyrics.setText(mLyrics.get(position));
        mItemViewHeight = holder.itemView.getHeight();
    }

    @Override
    public int getItemCount() {
        return mLyrics.size();
    }

    public int getItemViewHeight() {
        return mItemViewHeight;
    }
}
