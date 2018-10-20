package com.example.excalibur.mediaplayer;

import android.app.Fragment;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by excalibur on 2/11/2018.
 */

public class ContentFragment extends Fragment {

    AudioManager audioManager;
    SeekBar volume;
    MediaPlayer mediaPlayer;
    ToggleButton toggleButton;
    Button stop;
    SeekBar time;
    Timer updater;
    AtomicBoolean isTouched = new AtomicBoolean(false);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.content_fragment, container, false);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.skillet);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlaying();
            }
        });

        toggleButton = fragment.findViewById(R.id.switcher);
        stop = fragment.findViewById(R.id.stop);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(toggleButton.isChecked()){
                    play();
                }
                else {
                    pause();
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
            }
        });
        volume = fragment.findViewById(R.id.volume);
        volume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        volume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume.getProgress(), 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        time = fragment.findViewById(R.id.progress);
        time.setMax(mediaPlayer.getDuration() / 1000);
        time.setProgress(0);
        time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(isTouched.get()){
                    mediaPlayer.seekTo(time.getProgress() * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isTouched.set(true);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isTouched.set(false);
            }
        });
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        updater.cancel();
        updater.purge();
    }

    private void stopPlaying(){
        mediaPlayer.stop();
        updater.cancel();
        updater.purge();
        try{
            mediaPlayer.prepare();
            toggleButton.setChecked(false);
            time.setProgress(0);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void pause(){
        mediaPlayer.pause();
        updater.cancel();
        updater.purge();
    }

    private void play(){
        mediaPlayer.start();
        updater = new Timer();
        updater.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!isTouched.get()){
                    time.post(new Runnable() {
                        @Override
                        public void run() {
                            time.setProgress(mediaPlayer.getCurrentPosition() / 1000);
                        }
                    });
                }
            }
        }, 0, 2000);
    }
}
