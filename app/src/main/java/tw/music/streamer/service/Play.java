package tw.music.streamer.service;

import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.app.Service;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;

import androidx.annotation.Nullable;

public class Play extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener, {

    private BroadcastReceiver br;
    private MediaPlayer mp;
    private IntentFilter ief;

    private MediaPlayer.OnPreparedListener onprep;

    @Override
    public void onCreate() {
        super.onCreate();
        initializePlayer();
    }

    @Override
    public int onStartCommand(Intent a, int b, int c) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent a) {
        return null;
    }

    private void initializePlayer() {
        mp = new MediaPlayer();
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context a, Intent b) {
                act = b.getStringExtra("action");
                if (act.equals("seek")) {
                    seekSong(b);
                }
            }
        }
        ief = new IntentFilter("tw.music.streamer.ACTION");
        registerReceiver(br, ief);
        applyMediaListener();
    }

    @Override
    public void onPrepared(MediaPlayer a) {
        //sendDataToActivity(1, "");
    }

    @Override
    public void onBufferingUpdate(MediaPlayer a, int b) {
        //sendDataToActivity(2, String.valueOf((long) _percent));
    }

    private void applyMediaListener() {
        mp.setOnPreparedListener(this);
        mp.setOnBufferingUpdateListener(this);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer _unmp) {
                sendDataToActivity(3, "");
            }
        });
        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer _unmp, int _param1, int _param2) {
                sendDataToActivity(4, String.format("Error(%s%s)", _param1, _param2));
                return true;
            }
        });
    }

    private void playSong(String a) {
        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setDataSource(a);
        
        mp.prepareAsync();
    }

}