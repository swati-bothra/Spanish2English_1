package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RemoteController;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {


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

//        String[] num = new String[10];
//        num[0]="One";
//        num[1]="Two";
//        num[2]="Three";
//        num[3]="Four";
//        num[4]="Five";
//        num[5]="Six";
//        num[6]="Seven";
//        num[7]="Eight";
//        num[8]="Nine";
//        num[9]="Ten";

//
//        ArrayList<String> num = new ArrayList<String>();
//        num.add("One");
//        num.add("Two");
//        num.add("Three");
//        num.add("Four");
//        num.add("Five");
//        num.add("Six");
//        num.add("Seven");
//        num.add("Eight");
//        num.add("Nine");
//        num.add("Ten");



        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);



        final ArrayList<Word> num = new ArrayList<Word>();
        num.add(new Word("One","Uno",R.drawable.number_one,R.raw.one));
        num.add(new Word("Two","Dos",R.drawable.number_two,R.raw.two));
        num.add(new Word("Three","Tres",R.drawable.number_three,R.raw.three));
        num.add(new Word("Four","Cuatro",R.drawable.number_four,R.raw.four));
        num.add(new Word("Five","Cinco",R.drawable.number_five,R.raw.five));
        num.add(new Word("Six","Seis",R.drawable.number_six,R.raw.six));
        num.add(new Word("Seven","Siete",R.drawable.number_seven,R.raw.seven));
        num.add(new Word("Eight","Ocho",R.drawable.number_eight,R.raw.eight));
        num.add(new Word("Nine","Nueve",R.drawable.number_nine,R.raw.nine));
        num.add(new Word("Ten","Diez",R.drawable.number_ten,R.raw.ten));

//        Log.v("NumbersActivity","Word at index 3 : "+ num.get(3));
//        LinearLayout rootView = (LinearLayout)findViewById(R.id.rootView);
//        TextView wordView = new TextView(this);
//        wordView.setText(num.get(0));
//        rootView.addView(wordView);

//        int index = 0;
//        while (index<num.size()){
//            TextView wordView = new TextView(this);
//            wordView.setText(num.get(index));
//            rootView.addView(wordView);
//            index++;
//        }

//        for (int index = 0; index<num.size(); index++){
//            TextView wordView = new TextView(this);
//            wordView.setText(num.get(index));
//            rootView.addView(wordView);
//        }
        WordAdapter itemsAdapter = new WordAdapter(this, num, R.color.category_numbers);

        ListView listView = (ListView)findViewById(R.id.list);
//        GridView listView = (GridView) findViewById(R.id.list);

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

                    mediaplayer = MediaPlayer.create(NumbersActivity.this,word.getSound());
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
