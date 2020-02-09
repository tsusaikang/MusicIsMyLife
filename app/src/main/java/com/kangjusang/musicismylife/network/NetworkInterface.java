package com.kangjusang.musicismylife.network;

import com.kangjusang.musicismylife.model.Song;

public interface NetworkInterface {
    void getSongByRandom(SongListener songListener);
    interface SongListener {
        void onSuccess(Song song);
        void onFailure(Throwable throwable);
    }
}
