package com.example.android.miwok;

import java.security.PublicKey;

/**
 * Created by Owner on 1/23/2017.
 */
public class Word {

    private String mEnglish;
    private String mMiwok;
    private int mImageId=default_value;
    private static final int default_value= -1;
    private int mSoundId;

    public Word(String english ,String miwok,int sound){
        mEnglish=english;
        mMiwok=miwok;
        mSoundId=sound;
    }
    public Word(String english ,String miwok,int imageid,int soundid){
        mEnglish=english;
        mMiwok=miwok;
        mImageId=imageid;
        mSoundId=soundid;
    }

    public String getEnglishAns(){
        return mEnglish;
    }
    public String getMiwokAns(){
        return mMiwok;
    }
    public int getImageId(){
        return mImageId;
    }
    public int getSound(){
        return mSoundId;
    }

    public boolean hasImage(){
        return mImageId != default_value;
    }



}
