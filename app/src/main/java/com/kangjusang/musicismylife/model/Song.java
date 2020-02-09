package com.kangjusang.musicismylife.model;

import android.util.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

public class Song implements SongGeneralizer
{
    private String duration;

    private String image;

    private String file;

    private String singer;

    private String album;

    private String title;

    private String lyrics;

    public String getDuration ()
    {
        return duration;
    }

    public void setDuration (String duration)
    {
        this.duration = duration;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getFile ()
    {
        return file;
    }

    public void setFile (String file)
    {
        this.file = file;
    }

    public String getSinger ()
    {
        return singer;
    }

    public void setSinger (String singer)
    {
        this.singer = singer;
    }

    public String getAlbum ()
    {
        return album;
    }

    public void setAlbum (String album)
    {
        this.album = album;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getLyrics ()
    {
        return lyrics;
    }

    public void setLyrics (String lyrics)
    {
        this.lyrics = lyrics;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [duration = "+duration+", image = "+image+", file = "+file+", singer = "+singer+", album = "+album+", title = "+title+", lyrics = "+lyrics+"]";
    }

    @Override
    public GeneralizedSong generalize() {
        GeneralizedSong result = new GeneralizedSong();
        result.setAlbum(getAlbum());
        result.setDuration(getDuration());
        result.setFile(getFile());
        result.setImage(getImage());
        result.setSinger(getSinger());
        result.setTitle(getTitle());
        String lyrics = getLyrics();
        String[] splitLyrics = lyrics.split("\n");
        if (splitLyrics != null && splitLyrics.length > 0) {
            TreeMap<Long,Integer> longIntegerTreeMap = new TreeMap<>();
            ArrayList<String> stringArrayList = new ArrayList<>();
            for (int i = 0 ; i < splitLyrics.length-1 ; i++) {
                String strFrom = splitLyrics[i];
                String strFromTime = strFrom.substring(0,11);
                String strFromTimeM = strFromTime.substring(1,3);
                String strFromTimeS = strFromTime.substring(4,6);
                String strFromTimeMS = strFromTime.substring(7,10);
                Long from = Long.valueOf(strFromTimeM)*60*1000 + Long.valueOf(strFromTimeS)*1000 + Long.valueOf(strFromTimeMS);
                String strFromLyrics = strFrom.substring(11);
                longIntegerTreeMap.put(from,i);
                stringArrayList.add(strFromLyrics);
            }
            result.setLyricsIndex(longIntegerTreeMap);
            result.setLyrics(stringArrayList);
        }
        return result;
    }
}
