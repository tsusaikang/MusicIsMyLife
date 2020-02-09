package com.kangjusang.musicismylife.ui.player;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kangjusang.musicismylife.model.GeneralizedSong;
import com.kangjusang.musicismylife.model.SongGeneralizer;
import com.kangjusang.musicismylife.network.FLO2020Network;
import com.kangjusang.musicismylife.network.NetworkInterface;

public class PlayerViewModel extends ViewModel {

    private MutableLiveData<GeneralizedSong> mSong;

    public PlayerViewModel() {
        mSong = new MutableLiveData<>();
    }

    public LiveData<GeneralizedSong> getSong() {
        return mSong;
    }

    public void loadSongByRandom() {
        FLO2020Network flo2020Network = new FLO2020Network();
        flo2020Network.getSongByRandom(new NetworkInterface.SongListener() {
            @Override
            public void onSuccess(SongGeneralizer song) {
                GeneralizedSong generalizedSong = song.generalize();
                mSong.setValue(generalizedSong);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("PlayerViewModel",throwable.toString());
            }
        });
    }
}
