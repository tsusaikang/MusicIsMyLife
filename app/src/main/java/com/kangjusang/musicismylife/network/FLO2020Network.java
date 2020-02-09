package com.kangjusang.musicismylife.network;

import android.net.Uri;

import com.kangjusang.musicismylife.model.SongFromFLO;
import com.kangjusang.musicismylife.model.SongGeneralizer;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class FLO2020Network implements NetworkInterface {

    MusicService musicService;

    public FLO2020Network() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Uri.Builder().scheme("https").authority("grepp-programmers-challenges.s3.ap-northeast-2.amazonaws.com").build().toString())
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        musicService = retrofit.create(MusicService.class);
    }

    @Override
    public void getSongByRandom(final SongListener songListener) {
        musicService.getSongByRandom().enqueue(new Callback<SongFromFLO>() {
            @Override
            public void onResponse(Call<SongFromFLO> call, Response<SongFromFLO> response) {
                songListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SongFromFLO> call, Throwable t) {
                songListener.onFailure(t);
            }
        });
    }

    private interface MusicService {
        @GET("2020-flo/song.json")
        Call<SongFromFLO> getSongByRandom();
    }
}
