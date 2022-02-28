package tw.music.streamer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Binder;
import android.os.Handler;
import android.os.Message;

public class ZryteZeneServiceBinder extends Binder {
    public String _tmpFour = "";
    public String _tmpFive = "";
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener audioListener;
    private MediaPlayer mp;
    private Thread mHandler;
    private Context context;
    private boolean testBool = false;
    private Handler dataHandlerSenderZero;
    private Handler dataHandlerSenderOne;
    private Handler dataHandlerSenderTwo;
    private Handler dataHandlerSenderThree;
    private Handler dataHandlerSenderFour;
    private Handler dataHandlerSenderFive;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private void sendDataToActivity(int dtype, String data) {
        Message message = new Message();
        message.what = 1;
        if (dtype == 0) {
            dataHandlerSenderZero.sendMessage(message);
        }
        if (dtype == 1) {
            dataHandlerSenderOne.sendMessage(message);
        }
        if (dtype == 2) {
            message.what = (int) Double.parseDouble(data);
            dataHandlerSenderTwo.sendMessage(message);
        }
        if (dtype == 3) {
            dataHandlerSenderThree.sendMessage(message);
        }
        if (dtype == 4) {
            _tmpFour = data;
            dataHandlerSenderFour.sendMessage(message);
        }
        if (dtype == 5) {
            _tmpFive = data;
            dataHandlerSenderFive.sendMessage(message);
        }
    }

    public void setDataHandler(Handler dataHandlerSender, int dtype) {
        if (dtype == 0)
            dataHandlerSenderZero = dataHandlerSender;
        if (dtype == 1)
            dataHandlerSenderOne = dataHandlerSender;
        if (dtype == 2)
            dataHandlerSenderTwo = dataHandlerSender;
        if (dtype == 3)
            dataHandlerSenderThree = dataHandlerSender;
        if (dtype == 4)
            dataHandlerSenderFour = dataHandlerSender;
        if (dtype == 5)
            dataHandlerSenderFive = dataHandlerSender;
    }

    public void initializeTM() {
        testBool = true;
        mp = null;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusState) {
                switch (focusState) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                        if (mp != null) {
                            if (!mp.isPlaying()) {
                                mp.start();
                            }
                            mp.setVolume(1.0f, 1.0f);
                        }
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        if (mp != null) {
                            if (mp.isPlaying()) {
                                mp.pause();
                            }
                        }
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        if (mp != null) {
                            if (mp.isPlaying()) {
                                mp.pause();
                            }
                        }
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        if (mp != null) {
                            mp.setVolume(0.1f, 0.1f);
                        }
                        break;
                }
            }
        };
        mHandler = new Thread() {
            @Override
            public void run() {
                while (true) {
                    sendDataToActivity(0, "");
                    try {
                        Thread.sleep(10);
                    } catch (Exception _e) {
                    }
                }
            }
        };
        mHandler.start();
    }

    public void stopService() {
        if (mHandler != null) {
            mHandler.interrupt();
            mHandler = null;
        }
    }

    //some public methods

    public int _getCurrentDuration() {
        return mp.getCurrentPosition();
    }

    public int _getSongDuration() {
        return mp.getDuration();
    }

    public boolean _isPlaying() {
        if (mp != null)
            return mp.isPlaying();
        return false;
    }

    public void _mpStart() {
        mp.start();
    }

    public void _mpPause() {
        mp.pause();
    }

    public boolean _isMpNull() {
        return (mp == null);
    }

    public void _mpSeek(int _duration) {
        mp.seekTo(_duration);
    }

    public void _resetMp() {
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
            }
            mp.release();
            mp = null;
        }
    }

    public void _requestFocus() {
        int _result = audioManager.requestAudioFocus(audioListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (_result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mp.start();
        } else {
            mp.pause();
        }
    }

    public void _removeFocus() {
        audioManager.abandonAudioFocus(audioListener);
    }

    public void _setNightcore(float _speed) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            PlaybackParams params = new PlaybackParams();
            params.setPitch(_speed);
            params.setSpeed(_speed);
            if (mp.isPlaying()) {
                mp.setPlaybackParams(params);
                mp.start();
            } else {
                mp.setPlaybackParams(params);
                mp.pause();
            }
        }
    }

    public void _playSongFromURL(String _url) {
        try {
            mp = new MediaPlayer();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setDataSource(_url);
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer _unmp) {
                    sendDataToActivity(1, "");
                    mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                        @Override
                        public void onBufferingUpdate(MediaPlayer _unmp, int _percent) {
                            sendDataToActivity(2, String.valueOf((long) _percent));
                        }
                    });
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer _unmp) {
                            sendDataToActivity(3, "");
                        }
                    });
                }
            });
            mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer _unmp, int _param1, int _param2) {
                    sendDataToActivity(4, String.format("Error(%s%s)", _param1, _param2));
                    return true;
                }
            });
            mp.prepareAsync();
        } catch (Exception _e) {
            sendDataToActivity(1, _e.toString());
        }
    }
}
