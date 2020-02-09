package com.kangjusang.musicismylife.ui.player;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kangjusang.musicismylife.R;
import com.kangjusang.musicismylife.model.GeneralizedSong;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.lang.reflect.Array;

public class PlayerMainFragment extends Fragment {

    private PlayerViewModel playerViewModel;

    SlidingUpPanelLayout mLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_player_main, container, false);

        final TextView tvSongTitle = root.findViewById(R.id.tvSongTitle);
        final TextView tvSongArtist = root.findViewById(R.id.tvSongArtist);
        final ImageView ivSongImage = root.findViewById(R.id.ivSongImage);
        final ListView lvLyrics = root.findViewById(R.id.lvLyrics);

        tvSongTitle.setText("Loading...");
        tvSongArtist.setText("Loading...");

        playerViewModel = new ViewModelProvider(this.getActivity()).get(PlayerViewModel.class);
        playerViewModel.getSong().observe(this.getViewLifecycleOwner(), new Observer<GeneralizedSong>() {
            @Override
            public void onChanged(GeneralizedSong song) {
                tvSongTitle.setText(song.getTitle());
                tvSongArtist.setText(song.getSinger());
                Glide.with(getActivity()).load(song.getImage()).into(ivSongImage);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.lyrics_row_1, song.getLyrics());
                lvLyrics.setAdapter(arrayAdapter);
            }
        });

        playerViewModel.loadSongByRandom();

        return root;
    }
}
