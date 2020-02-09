package com.kangjusang.musicismylife.ui.player;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.kangjusang.musicismylife.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class PlayerMainFragment extends Fragment {

    SlidingUpPanelLayout mLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_player_main, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        textView.setText("Now Playing...");

        mLayout = root.findViewById(R.id.sliding_layout);

        return root;
    }
}
