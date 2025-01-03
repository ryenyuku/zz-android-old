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

public class Play extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

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
        unregisterReceiver(br);
        if (mp!=null) {
            mp.release();
            mp = null;
        }
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
                } else if (act.equals("pause")) {
                    pauseSong();
                } else if (act.equals("resume")) {
                    resumeSong();
                } else if (act.equals("prev-song")) {
                    playPreviousSong();
                } else if (act.equals("next-song")) {
                    playNextSong();
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

    @Override
    public void onCompletion(MediaPlayer a) {
        //sendDataToActivity(3, "");
    }

    @Override
    public boolean onError(MediaPlayer a, int b, int c) {
        //sendDataToActivity(4, String.format("Error(%s%s)", b, c));
        return true;
    }

    private void applyMediaListener() {
        mp.setOnPreparedListener(this);
        mp.setOnBufferingUpdateListener(this);
        mp.setOnCompletionListener(this);
        mp.setOnErrorListener(this);
    }

    private void playSong(String a) {
        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setDataSource(a);
        applyMediaListener();
        mp.prepareAsync();
        tellActivity("request-play","1");
    }

    private void pauseSong() {
        if (mp==null) return;
        if (mp.isPlaying()) {
            mp.pause();
            tellActivity("request-pause","1");
        }
    }

    private void resumeSong() {
        if (mp==null) return;
        if (mp.isPlaying()) {
            mp.resume();
            tellActivity("request-resume","1");
        }
    }

    private void stopSong() {
        if (mp==null) return;
        if (mp.isPlaying()) {
            mp.stop();
            tellActivity("request-stop","1");
        }
    }

    private void seekSong(Intent a) {
        if (mp==null) return;
        mp.seekTo(a.getIntExtra("seekpos",0));
        tellActivity("request-seek","1");
    }

    private void playPreviousSong() {

    }

    private void playNextSong() {

    }

    private void tellActivity(String a, String b) {
        ita = new Intent("tw.music.streamer.ACTION_UPDATE");
        ita.putExtra("update", a);
        ita.putExtra("data", b);
        sendBroadcast(ita);
    }

}