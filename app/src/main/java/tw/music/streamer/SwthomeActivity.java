package tw.music.streamer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SwthomeActivity extends AppCompatActivity {

    private Timer _timer = new Timer();

    private LinearLayout linear1;
    private LinearLayout linear2;
    private LinearLayout linear4;
    private TextView textview1;
    private LinearLayout linear3;
    private TextView textview2;
    private TextView textview3;
    private TextView textview4;

    private TimerTask delay;
    private MediaPlayer mp;
    private VideoView videoview1;
    private MediaController vidcontrol;
    private ProgressDialog coreprog;

    {
    }

    {
    }

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.swthome);
        com.google.firebase.FirebaseApp.initializeApp(this);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {

        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        linear4 = findViewById(R.id.linear4);
        textview1 = findViewById(R.id.textview1);
        linear3 = findViewById(R.id.linear3);
        textview2 = findViewById(R.id.textview2);
        textview3 = findViewById(R.id.textview3);
        textview4 = findViewById(R.id.textview4);
    }

    private void initializeLogic() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        _customNav("#000000");
        textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesansbold.ttf"), 0);
        textview4.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        if (getIntent().getStringExtra("eg").equals("0")) {
            linear2.setVisibility(View.GONE);
            linear4.setVisibility(View.GONE);
        } else {
            if (getIntent().getStringExtra("eg").equals("1")) {
                linear1.setVisibility(View.GONE);
                linear4.setVisibility(View.GONE);
                try {
                    mp = new MediaPlayer();
                    mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mp.setDataSource("https://rainymood.com/audio1112/0.m4a");
                    _CoreProgressLoading(true);
                    mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        public void onPrepared(MediaPlayer _unmp) {
                            _CoreProgressLoading(false);
                            mp.setLooping(true);
                            mp.start();
                        }
                    });
                    mp.prepareAsync();
                } catch (Exception _e) {
                }
            } else {
                if (getIntent().getStringExtra("eg").equals("2")) {
                    linear1.setVisibility(View.GONE);
                    linear2.setVisibility(View.GONE);
                    textview4.setText("Axhyre");
                    delay = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textview4.setText("is");
                                    delay = new TimerTask() {
                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    textview4.setText("a");
                                                    delay = new TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    textview4.setText("gud");
                                                                    delay = new TimerTask() {
                                                                        @Override
                                                                        public void run() {
                                                                            runOnUiThread(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    textview4.setText("boy");
                                                                                    delay = new TimerTask() {
                                                                                        @Override
                                                                                        public void run() {
                                                                                            runOnUiThread(new Runnable() {
                                                                                                @Override
                                                                                                public void run() {
                                                                                                    textview4.setText("ax.");
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    };
                                                                                    _timer.schedule(delay, 2000);
                                                                                }
                                                                            });
                                                                        }
                                                                    };
                                                                    _timer.schedule(delay, 2000);
                                                                }
                                                            });
                                                        }
                                                    };
                                                    _timer.schedule(delay, 2000);
                                                }
                                            });
                                        }
                                    };
                                    _timer.schedule(delay, 2000);
                                }
                            });
                        }
                    };
                    _timer.schedule(delay, 2000);
                } else {

                }
            }
        }
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (getIntent().getStringExtra("eg").equals("0")) {
            try {
                delay.cancel();
            } catch (Exception _e) {
            }
            finish();
        } else {
            if (getIntent().getStringExtra("eg").equals("1")) {
                mp.stop();
                finish();
            } else {
                if (getIntent().getStringExtra("eg").equals("2")) {
                    try {
                        delay.cancel();
                    } catch (Exception _e) {
                    }
                    finish();
                } else {

                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private void _CoreProgressLoading(final boolean _ifShow) {
        if (_ifShow) {
            if (coreprog == null) {
                coreprog = new ProgressDialog(this);
                coreprog.setCancelable(false);
                coreprog.setCanceledOnTouchOutside(false);

                coreprog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                coreprog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));

            }
            coreprog.setMessage(null);
            coreprog.show();
            LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View _coreView = _inflater.inflate(R.layout.custom_dialog, null);
            final ImageView imageview1 = _coreView.findViewById(R.id.imageview1);
            com.bumptech.glide.Glide.with(getApplicationContext()).load(R.raw.partyblob).into(imageview1);
            coreprog.setContentView(_coreView);
        } else {
            if (coreprog != null) {
                coreprog.dismiss();
            }
        }
    }

    private void _customNav(final String _color) {
        //Code From StackOverFlow.com And Converted By TeamWorks DEV
        if (Build.VERSION.SDK_INT >= 21) {
            Window w = this.getWindow();
            w.setNavigationBarColor(Color.parseColor(_color));
        }
    }


    @Deprecated
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }

    @Deprecated
    public int getLocationX(View _v) {
        int[] _location = new int[2];
        _v.getLocationInWindow(_location);
        return _location[0];
    }

    @Deprecated
    public int getLocationY(View _v) {
        int[] _location = new int[2];
        _v.getLocationInWindow(_location);
        return _location[1];
    }

    @Deprecated
    public int getRandom(int _min, int _max) {
        Random random = new Random();
        return random.nextInt(_max - _min + 1) + _min;
    }

    @Deprecated
    public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
        ArrayList<Double> _result = new ArrayList<Double>();
        SparseBooleanArray _arr = _list.getCheckedItemPositions();
        for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
            if (_arr.valueAt(_iIdx))
                _result.add((double) _arr.keyAt(_iIdx));
        }
        return _result;
    }

    @Deprecated
    public float getDip(int _input) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
    }

    @Deprecated
    public int getDisplayWidthPixels() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Deprecated
    public int getDisplayHeightPixels() {
        return getResources().getDisplayMetrics().heightPixels;
    }

}
