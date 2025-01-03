package tw.music.streamer.service;

import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.content.BroadcastReceiver;

import androidx.annotation.Nullable;

public class Play extends Service {

    private BroadcastReceiver br;

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
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context a, Intent b) {
                act = b.getStringExtra("action");
                if (act.equals("seek")) {
                    seekSong(b);
                }
            }
        }
        IntentFilter ief = new IntentFilter("tw.music.streamer.ACTION");
        registerReceiver(br, ief);
    }

    private void _play(final String _key) { //line 2703
        _CoreProgressLoading(true);
        tmservice._resetMp();
        final double _position = currentlyChild.indexOf(_key);
        tmservice._playSongFromURL(currentlyMap.get((int) _position).get("url").toString());
        currentlyPlaying = _key;
        text_title.setText(currentlyMap.get((int) _position).get("name").toString());
        if (usrname_list.contains(currentlyMap.get((int) _position).get("uid").toString())) {
            text_artist.setText(profile_map.get(usrname_list.indexOf(currentlyMap.get((int) _position).get("uid").toString())).get("username").toString());
        } else {
            text_artist.setText(currentlyMap.get((int) _position).get("uid").toString());
        }
        if (adminsList.contains(currentlyMap.get((int) _position).get("uid").toString())) {
            text_artist.setTextColor(Color.parseColor(theme_map.get(0).get("colorButton").toString()));
        } else {
            text_artist.setTextColor(Color.parseColor(theme_map.get(0).get("colorPrimaryCardText").toString()));
        }
        if (currentlyMap.get((int) _position).containsKey("img")) {
            image_album.clearColorFilter();
            Glide.with(getApplicationContext()).load(currentlyMap.get((int) _position).get("img").toString()).centerCrop().into(image_album);
        } else {
            image_album.setImageResource(R.drawable.ic_album_white);
            image_album.setColorFilter(Color.parseColor(theme_map.get(0).get("colorButtonText").toString()), PorterDuff.Mode.MULTIPLY);
        }
    }


}