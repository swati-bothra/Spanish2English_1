package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    private MediaPlayer mediaplayer;
    private MediaPlayer.OnCompletionListener mCompletelistener=  new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    private AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener mAudioFocusChange = new AudioManager.OnAudioFocusChangeListener(){
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaplayer.pause();
                mediaplayer.seekTo(0);
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_GAIN){
                mediaplayer.start();
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> num = new ArrayList<Word>();
        num.add(new Word("Father","el padre",R.drawable.family_grandfather,R.raw.father));
        num.add(new Word("Mother","la madre",R.drawable.family_grandmother,R.raw.mother));
        num.add(new Word("Brother","el hermano",R.drawable.family_older_brother,R.raw.brother));
        num.add(new Word("Sister","la hermana",R.drawable.family_older_sister,R.raw.sister));
        num.add(new Word("Son","el hijo",R.drawable.family_younger_brother,R.raw.husband));
        num.add(new Word("Daughter","la hija",R.drawable.family_younger_sister,R.raw.daughter));
        num.add(new Word("Husband","el marido",R.drawable.family_father,R.raw.husband));
        num.add(new Word("Wife","la mujer",R.drawable.family_mother,R.raw.wife));


        WordAdapter itemsAdapter = new WordAdapter(this, num,R.color.category_family);

        ListView listView = (ListView)findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(NumbersActivity.this,"Play",Toast.LENGTH_SHORT).show();
                Word word = num.get(position);

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mAudioFocusChange,
                        AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
//                    mAudioManager.registerMediaButtonEventReceiver(RemoteControl);

                    mediaplayer = MediaPlayer.create(FamilyActivity.this,word.getSound());
                    mediaplayer.start();
                    mediaplayer.setOnCompletionListener(mCompletelistener);
                }
            }
        });

    }
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
    private void releaseMediaPlayer() {
        if (mediaplayer != null) {
            mediaplayer.release();
            mediaplayer = null;
            mAudioManager.abandonAudioFocus(mAudioFocusChange);
        }
    }
}
