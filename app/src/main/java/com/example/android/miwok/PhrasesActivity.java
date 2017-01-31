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

public class PhrasesActivity extends AppCompatActivity {

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
        num.add(new Word("Good Morning","Buenos días",R.raw.good_morning));
        num.add(new Word("Good Afternoon","Buenas tardes",R.raw.good_afternoon));
        num.add(new Word("Good Evening","Buenas noches",R.raw.good_evening));
        num.add(new Word("Hello, my name is Swati","Hola, me llamo Swati",R.raw.my_name));
        num.add(new Word("What is your name?","¿Cómo se llama usted?",R.raw.your_name));
        num.add(new Word("How are you?","¿Cómo está usted?",R.raw.how_r_u));
        num.add(new Word("i am fine","Estoy bien",R.raw.i_m_fine));
        num.add(new Word("Nice to meet you","Mucho gusto",R.raw.nice_meet));
        num.add(new Word("Good bye","Adiós",R.raw.bye));




        WordAdapter itemsAdapter = new WordAdapter(this, num,R.color.category_phrases);

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

                    mediaplayer = MediaPlayer.create(PhrasesActivity.this,word.getSound());
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
