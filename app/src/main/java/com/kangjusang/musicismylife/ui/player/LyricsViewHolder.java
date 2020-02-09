package com.kangjusang.musicismylife.ui.player;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LyricsViewHolder extends RecyclerView.ViewHolder {

    TextView tvLyrics;

    public LyricsViewHolder(@NonNull View itemView) {
        super(itemView);

        tvLyrics = itemView.findViewById(android.R.id.text1);
    }
}
