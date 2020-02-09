package com.kangjusang.musicismylife.network;

import com.kangjusang.musicismylife.model.SongGeneralizer;

public interface NetworkInterface {
    void getSongByRandom(SongListener songListener);
    interface SongListener {
        void onSuccess(SongGeneralizer song);
        void onFailure(Throwable throwable);
    }
}
