package com.kangjusang.musicismylife.model;

import java.util.Arrays;

public class SongFromFLO implements SongGeneralizer
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
        result.setLyrics(Arrays.asList(splitLyrics));
        return result;
    }
}
