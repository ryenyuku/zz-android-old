package tw.music.streamer.service;

import android.os.IBinder;
import android.app.Service;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

public class ZryteZenePlay extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    private BroadcastReceiver br;
    private MediaPlayer mp;
    private IntentFilter ief;
    private SharedPreferences sp;
    private String lm, act, csp;
    private Intent ita;

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
        sp = getSharedPreferences("teamdata", Activity.MODE_PRIVATE);
        lm = sp.getString("fvsAsc", "");
        mp = new MediaPlayer();
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context a, Intent b) {
                act = b.getStringExtra("action");
                if (act.equals("seek")) {
                    seekSong(b);
                } else if (act.equals("play")) {
                    playSong(b);
                } else if (act.equals("pause")) {
                    pauseSong();
                } else if (act.equals("resume")) {
                    resumeSong();
                } else if (act.equals("stop")) {
                    stopSong();
                } else if (act.equals("update-sp")) {
                    updateSP(b);
                } else if (act.equals("restart-song")) {
                    restartSong();
                } else if (act.equals("reset")) {
                    resetMedia();
                }
            }
        };
        ief = new IntentFilter("tw.music.streamer.ACTION");
        registerReceiver(br, ief);
        applyMediaListener();
    }

    @Override
    public void onPrepared(MediaPlayer a) {
        tellActivity("on-prepared","1");
    }

    @Override
    public void onBufferingUpdate(MediaPlayer a, int b) {
        tellActivity("on-bufferupdate",b);
    }

    @Override
    public void onCompletion(MediaPlayer a) {
        tellActivity("on-completion","1");
    }

    @Override
    public boolean onError(MediaPlayer a, int b, int c) {
        tellActivity("on-error", String.format("Error(%s%s)", b, c));
        return true;
    }

    private void applyMediaListener() {
        mp.setOnPreparedListener(this);
        mp.setOnBufferingUpdateListener(this);
        mp.setOnCompletionListener(this);
        mp.setOnErrorListener(this);
    }

    private void playSong(Intent a) {
        csp = a.getStringExtra("req-data");
        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        applyMediaListener();
        try {
            mp.setDataSource(csp);
            mp.prepareAsync();
            tellActivity("request-play");
        } catch (Exception e) {
            tellActivity("on-error", e.toString());
        }
    }

    private void pauseSong() {
        if (mp==null) return;
        if (mp.isPlaying()) {
            mp.pause();
            tellActivity("request-pause");
        }
    }

    private void resumeSong() {
        if (mp==null) return;
        if (mp.isPlaying()) {
            mp.start();
            tellActivity("request-resume");
        }
    }

    private void stopSong() {
        if (mp==null) return;
        if (mp.isPlaying()) {
            mp.stop();
            tellActivity("request-stop");
        }
    }

    private void seekSong(Intent a) {
        if (mp==null) return;
        if (mp.isPlaying()) {
            int b = a.getIntExtra("req-data",0);
            mp.seekTo(b);
            tellActivity("request-seek", b);
        }
    }

    private void restartSong() {
        if (mp==null) return;
        if (mp.isPlaying()) mp.stop();
        mp.seekTo(0);
        mp.start();
        tellActivity("request-restart");
    }

    private void resetMedia() {
        mp = new MediaPlayer();
        tellActivity("request-reset");
    }

    private void tellActivity(String a) {
        ita = new Intent("tw.music.streamer.STATUS_UPDATE");
        ita.putExtra("update", a);
        sendBroadcast(ita);
    }

    private void tellActivity(String a, String b) {
        ita = new Intent("tw.music.streamer.STATUS_UPDATE");
        ita.putExtra("update", a);
        ita.putExtra("data", b);
        sendBroadcast(ita);
    }

    private void tellActivity(String a, int b) {
        ita = new Intent("tw.music.streamer.STATUS_UPDATE");
        ita.putExtra("update", a);
        ita.putExtra("data", b);
        sendBroadcast(ita);
    }

    private void updateSP(Intent a) {
        String b = a.getStringExtra("req-data");
        if (b.equals("loop")) {
            lm = sp.getString("fvsAsc", "");
        }
    }

}