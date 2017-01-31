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

public class ColorsActivity extends AppCompatActivity {

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
        num.add(new Word("Red","Rojo",R.drawable.color_red,R.raw.red));
        num.add(new Word("Black","Nergo",R.drawable.color_black,R.raw.black));
        num.add(new Word("White","Blanco",R.drawable.color_white,R.raw.white));
        num.add(new Word("Yellow","Amarillo",R.drawable.color_mustard_yellow,R.raw.yellow));
        num.add(new Word("Purple","Morado",R.drawable.color_green,R.raw.purple));
        num.add(new Word("Orange","Naranja",R.drawable.color_dusty_yellow,R.raw.orange));
        num.add(new Word("Pink","Roja",R.drawable.color_red,R.raw.pink));
        num.add(new Word("Brown","Maroon",R.drawable.color_brown,R.raw.brown));

        WordAdapter itemsAdapter = new WordAdapter(this, num,R.color.category_colors);

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

                    mediaplayer = MediaPlayer.create(ColorsActivity.this,word.getSound());
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
