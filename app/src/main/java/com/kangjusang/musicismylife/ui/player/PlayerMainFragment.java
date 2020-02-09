package com.kangjusang.musicismylife.ui.player;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Range;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.kangjusang.musicismylife.R;
import com.kangjusang.musicismylife.Utils;
import com.kangjusang.musicismylife.model.GeneralizedSong;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class PlayerMainFragment extends Fragment {

    private PlayerViewModel playerViewModel;

    SlidingUpPanelLayout mLayout;
    MediaPlayer mMediaPlayer;
    Boolean controlWithLyrics = false;

    private Handler myHandler = new Handler();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_player_main, container, false);

        final TextView tvSongTitle = root.findViewById(R.id.tvSongTitle);
        final TextView tvSongArtist = root.findViewById(R.id.tvSongArtist);
        final ImageView ivSongImage = root.findViewById(R.id.ivSongImage);
        final ListView lvLyrics = root.findViewById(R.id.lvLyrics);
        final SeekBar seekBar = root.findViewById(R.id.seekBar);

        final TextView tvPlayTime = root.findViewById(R.id.tvPlayTime);
        final TextView tvDuration = root.findViewById(R.id.tvDuration);

        final ImageButton ibPlayPause = root.findViewById(R.id.ibPlayPause);
        final ImageButton ibFf = root.findViewById(R.id.ibFf);
        final ImageButton ibRew = root.findViewById(R.id.ibRew);

        final ImageButton ibCloseLyricsExpanded = root.findViewById(R.id.ibCloseLyricsExpanded);
        final ToggleButton toggleButton = root.findViewById(R.id.toggleButton);
        final ListView lvLyricsExpanded = root.findViewById(R.id.lvLyricsExpanded);

        tvSongTitle.setText("Loading...");
        tvSongArtist.setText("Loading...");

        playerViewModel = new ViewModelProvider(this.getActivity()).get(PlayerViewModel.class);
        playerViewModel.getSong().observe(this.getViewLifecycleOwner(), new Observer<GeneralizedSong>() {

            boolean isPlaying = false;
            long duration = 0;
            TreeMap<Long,Integer> timeIndexTreeMap;

            @Override
            public void onChanged(GeneralizedSong song) {
                tvSongTitle.setText(song.getTitle());
                tvSongArtist.setText(song.getSinger());
                Glide.with(getActivity()).load(song.getImage()).into(ivSongImage);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.lyrics_row_1, song.getLyrics());
                lvLyrics.setAdapter(arrayAdapter);
                lvLyricsExpanded.setAdapter(arrayAdapter);
                lvLyricsExpanded.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                timeIndexTreeMap = song.getLyricsIndex();
                duration = Integer.valueOf(song.getDuration()) * 1000;
                seekBar.setMax((int)duration);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    int mProgress = 0;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        mProgress = progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        mMediaPlayer.seekTo(mProgress);
                    }
                });
                tvPlayTime.setText(String.format("%02d:%02d",TimeUnit.MILLISECONDS.toMinutes(0),TimeUnit.MILLISECONDS.toSeconds(0)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(0))));
                tvDuration.setText(String.format("%02d:%02d",TimeUnit.MILLISECONDS.toMinutes(duration),TimeUnit.MILLISECONDS.toSeconds(duration)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))));

                if (mMediaPlayer != null) {
                    pausePlay();
                    releasePlayer();
                }
                Uri uri = Uri.parse(song.getFile());
                mMediaPlayer = MediaPlayer.create(getContext(), uri);
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Toast.makeText(getContext().getApplicationContext(), "onCompletion", Toast.LENGTH_SHORT).show();
                        stopPlay();
                    }
                });
                mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
//                        Toast.makeText(getContext().getApplicationContext(), "buffering updated : " + percent, Toast.LENGTH_SHORT).show();
                    }
                });
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        Toast.makeText(getContext().getApplicationContext(), "onPrepared", Toast.LENGTH_SHORT).show();
                    }
                });

                ibPlayPause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        togglePlay();
                    }
                });

                ibFf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ff();
                    }
                });

                ibRew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rew();
                    }
                });

                myHandler.postDelayed(UpdateSongTime, 100);
            }
            private void togglePlay() {
                if (isPlaying)
                    pausePlay();
                else
                    startPlay();
            }
            private void startPlay() {
                ibPlayPause.setImageResource(android.R.drawable.ic_media_pause);
                mMediaPlayer.start();
                isPlaying = true;
                Toast.makeText(getContext().getApplicationContext(), "started", Toast.LENGTH_SHORT).show();
            }
            private void pausePlay() {
                ibPlayPause.setImageResource(android.R.drawable.ic_media_play);
                mMediaPlayer.pause();
                isPlaying = false;
                Toast.makeText(getContext().getApplicationContext(), "paused", Toast.LENGTH_SHORT).show();
            }
            private void stopPlay() {
                ibPlayPause.setImageResource(android.R.drawable.ic_media_play);
                mMediaPlayer.stop();
                isPlaying = false;
                Toast.makeText(getContext().getApplicationContext(), "stopped", Toast.LENGTH_SHORT).show();
                try {
                    mMediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            private void releasePlayer() {
                mMediaPlayer.release();
                mMediaPlayer = null;
            }

            private void ff() {
                mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition() + 5*1000);
            }

            private void rew() {
                mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition() - 5*1000);
            }

            private Runnable UpdateSongTime = new Runnable() {
                public void run() {
                    long startTime = mMediaPlayer.getCurrentPosition();
                    tvPlayTime.setText(String.format("%02d:%02d",TimeUnit.MILLISECONDS.toMinutes(startTime),TimeUnit.MILLISECONDS.toSeconds(startTime)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime))));
                    seekBar.setProgress((int)startTime);
                    myHandler.postDelayed(this, 100);
                    Integer index = Utils.mappedValue(timeIndexTreeMap, startTime);
                    Log.d("UpdateSongTime", "" + startTime + " : " + index);
                    if (index != null) {
                        lvLyrics.setSelection(index);
                        lvLyricsExpanded.setSelection((index - 5 < 0) ? 0 : index - 5);
                        lvLyricsExpanded.setItemChecked(index, true);
                    }
                }
            };
        });
        lvLyrics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
            }
        });
        lvLyricsExpanded.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (controlWithLyrics) {

                } else {
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
            }
        });

        ibCloseLyricsExpanded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                controlWithLyrics = isChecked;
            }
        });

        playerViewModel.loadSongByRandom();

        mLayout = root.findViewById(R.id.sliding_layout);

        return root;
    }
}
