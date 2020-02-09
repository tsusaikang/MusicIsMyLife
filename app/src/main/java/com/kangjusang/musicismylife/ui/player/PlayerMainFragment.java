package com.kangjusang.musicismylife.ui.player;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kangjusang.musicismylife.R;
import com.kangjusang.musicismylife.model.GeneralizedSong;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class PlayerMainFragment extends Fragment {

    private PlayerViewModel playerViewModel;

    SlidingUpPanelLayout mLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_player_main, container, false);

        final TextView tvSongTitle = root.findViewById(R.id.tvSongTitle);
        final TextView tvSongArtist = root.findViewById(R.id.tvSongArtist);
        final ImageView ivSongImage = root.findViewById(R.id.ivSongImage);
        final TextView tvLyrics = root.findViewById(R.id.tvLyrics);

        tvSongTitle.setText("Loading...");
        tvSongArtist.setText("Loading...");
        tvLyrics.setText("Loading...");

        playerViewModel = new ViewModelProvider(this.getActivity()).get(PlayerViewModel.class);
        playerViewModel.getSong().observe(this.getViewLifecycleOwner(), new Observer<GeneralizedSong>() {
            @Override
            public void onChanged(GeneralizedSong song) {
                tvSongTitle.setText(song.getTitle());
                tvSongArtist.setText(song.getSinger());
//                tvLyrics.setText(song.getLyrics());
            }
        });

        playerViewModel.loadSongByRandom();
//        mLayout = root.findViewById(R.id.sliding_layout);

        return root;
    }
}
