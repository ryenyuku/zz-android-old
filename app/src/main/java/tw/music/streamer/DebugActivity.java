package tw.music.streamer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class DebugActivity extends AppCompatActivity {

    private Timer _timer = new Timer();
    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();

    private HashMap<String, Object> mapvar = new HashMap<>();
    private double count = 0;

    private LinearLayout linear1;
    private LinearLayout linear3;
    private TextView textview4;
    private TextView textview2;
    private ScrollView vscroll1;
    private LinearLayout linear2;
    private ImageView imageview1;
    private TextView textview1;
    private TextView textview3;

    private SharedPreferences data;
    private Calendar cal = Calendar.getInstance();
    private DatabaseReference fb_error = _firebase.getReference("fb/crashdata");
    private ChildEventListener _fb_error_child_listener;
    private FirebaseAuth Auth;
    private OnCompleteListener<AuthResult> _Auth_create_user_listener;
    private OnCompleteListener<AuthResult> _Auth_sign_in_listener;
    private OnCompleteListener<Void> _Auth_reset_password_listener;
    private TimerTask timer;
    private Intent intent = new Intent();
    private android.content.pm.PackageInfo packageInfo;

    {
    }

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.debug);
        com.google.firebase.FirebaseApp.initializeApp(this);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {

        linear1 = findViewById(R.id.linear1);
        linear3 = findViewById(R.id.linear3);
        textview4 = findViewById(R.id.textview4);
        textview2 = findViewById(R.id.textview2);
        vscroll1 = findViewById(R.id.vscroll1);
        linear2 = findViewById(R.id.linear2);
        imageview1 = findViewById(R.id.imageview1);
        textview1 = findViewById(R.id.textview1);
        textview3 = findViewById(R.id.textview3);
        data = getSharedPreferences("teamdata", Activity.MODE_PRIVATE);
        Auth = FirebaseAuth.getInstance();

        textview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                try {
                    timer.cancel();
                } catch (Exception _e) {
                }
                intent.setClass(getApplicationContext(), DaeditActivity.class);
                startActivity(intent);
                finish();
            }
        });

        _fb_error_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);

            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);

            }

            @Override
            public void onChildMoved(DataSnapshot _param1, String _param2) {

            }

            @Override
            public void onChildRemoved(DataSnapshot _param1) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);

            }

            @Override
            public void onCancelled(DatabaseError _param1) {
                final int _errorCode = _param1.getCode();
                final String _errorMessage = _param1.getMessage();

            }
        };
        fb_error.addChildEventListener(_fb_error_child_listener);

        _Auth_create_user_listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        _Auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        _Auth_reset_password_listener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();

            }
        };
    }

    private void initializeLogic() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setTheme(android.R.style.ThemeOverlay_Material_Dark);
        try {
            packageInfo = DebugActivity.this.getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (Exception _e) {
        }
        textview3.setTextIsSelectable(true);
        setTitle("DebugActivity");
        cal = Calendar.getInstance();
        mapvar = new HashMap<>();
        mapvar.put("error", getIntent().getStringExtra("error"));
        mapvar.put("user", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        mapvar.put("android", Build.VERSION.RELEASE);
        mapvar.put("versionCode", Integer.toString(packageInfo.versionCode));
        mapvar.put("versionName", packageInfo.versionName);
        mapvar.put("id", Build.ID);
        mapvar.put("product", Build.PRODUCT);
        mapvar.put("serial", Build.SERIAL);
        mapvar.put("time", String.valueOf(cal.getTimeInMillis()));
        mapvar.put("package", getApplicationContext().getPackageName());
        fb_error.push().updateChildren(mapvar);
        data.edit().putString("errorLog", data.getString("errorLog", "").concat("[".concat(new SimpleDateFormat("E, dd MMM yyyy HH:mm").format(cal.getTime()).concat("] ".concat(getIntent().getStringExtra("error").concat("\n\n")))))).commit();
        textview3.setText(getIntent().getStringExtra("error"));
        textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview4.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        imageview1.setColorFilter(0xFFF44336, PorterDuff.Mode.MULTIPLY);
        if (data.getString("errorCountdown", "").contains("-")) {
            count = 30;
        } else {
            try {
                count = Double.parseDouble(data.getString("errorCountdown", ""));
            } catch (Exception _e) {
                count = 30;
            }
        }
        textview4.setText("Restarting on... ".concat(String.valueOf((long) (count))));
        timer = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        count--;
                        if (count > 0) {
                            textview4.setText("Restarting on... ".concat(String.valueOf((long) (count))));
                        } else {
                            intent.setClass(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        };
        _timer.scheduleAtFixedRate(timer, 1000, 1000);
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
    public void onStart() {
        super.onStart();
        _DarkMode();
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

    private void _DarkMode() {
        int nightModeFlags = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            textview1.setTextColor(0xFFFFFFFF);
            textview4.setTextColor(0xFFFFFFFF);
            linear1.setBackgroundColor(0xFF252525);
            getWindow().setStatusBarColor(Color.parseColor("#252525"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(0);
            }
            _customNav("#252525");
        } else {
            textview1.setTextColor(0xFF000000);
            textview4.setTextColor(0xFF000000);
            linear1.setBackgroundColor(0xFFFFFFFF);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                getWindow().setStatusBarColor(Color.parseColor("#BDBDBD"));
            }
            _customNav("#FFFFFF");
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
