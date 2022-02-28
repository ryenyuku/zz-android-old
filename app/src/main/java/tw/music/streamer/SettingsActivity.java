package tw.music.streamer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class SettingsActivity extends AppCompatActivity {


    private ArrayList<HashMap<String, Object>> theme_map = new ArrayList<>();

    private LinearLayout linear1;
    private ScrollView vscroll1;
    private ImageView imageview1;
    private TextView textview1;
    private LinearLayout linear2;
    private Switch switch1;
    private LinearLayout linear3;
    private TextView textview2;
    private EditText edittext1;
    private LinearLayout linear4;
    private LinearLayout linear6;
    private LinearLayout linear7;
    private LinearLayout linear11;
    private LinearLayout linear12;
    private LinearLayout linear13;
    private LinearLayout linear14;
    private LinearLayout linear15;
    private LinearLayout linear16;
    private Switch switch2;
    private LinearLayout linear17;
    private LinearLayout linear8;
    private LinearLayout linear9;
    private LinearLayout linear10;
    private ImageView imageview2;
    private LinearLayout linear5;
    private TextView textview3;
    private SeekBar seekbar1;
    private TextView textview7;
    private TextView textview8;
    private TextView textview9;
    private SeekBar seekbar2;
    private ImageView imageview3;
    private TextView textview4;
    private ImageView imageview4;
    private TextView textview5;
    private ImageView imageview5;
    private TextView textview6;

    private SharedPreferences data;
    private Intent intent = new Intent();
    private FirebaseAuth Auth;
    private OnCompleteListener<AuthResult> _Auth_create_user_listener;
    private OnCompleteListener<AuthResult> _Auth_sign_in_listener;
    private OnCompleteListener<Void> _Auth_reset_password_listener;
    private RadioButton radiobutton1;
    private RadioButton radiobutton2;
    private RadioGroup radiogroup1;
    private RadioButton radiobutton3;
    private RadioButton radiobutton4;
    private RadioGroup radiogroup2;

    {
    }

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.settings);
        com.google.firebase.FirebaseApp.initializeApp(this);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {

        linear1 = findViewById(R.id.linear1);
        vscroll1 = findViewById(R.id.vscroll1);
        imageview1 = findViewById(R.id.imageview1);
        textview1 = findViewById(R.id.textview1);
        linear2 = findViewById(R.id.linear2);
        switch1 = findViewById(R.id.switch1);
        linear3 = findViewById(R.id.linear3);
        textview2 = findViewById(R.id.textview2);
        edittext1 = findViewById(R.id.edittext1);
        linear4 = findViewById(R.id.linear4);
        linear6 = findViewById(R.id.linear6);
        linear7 = findViewById(R.id.linear7);
        linear11 = findViewById(R.id.linear11);
        linear12 = findViewById(R.id.linear12);
        linear13 = findViewById(R.id.linear13);
        linear14 = findViewById(R.id.linear14);
        linear15 = findViewById(R.id.linear15);
        linear16 = findViewById(R.id.linear16);
        switch2 = findViewById(R.id.switch2);
        linear17 = findViewById(R.id.linear17);
        linear8 = findViewById(R.id.linear8);
        linear9 = findViewById(R.id.linear9);
        linear10 = findViewById(R.id.linear10);
        imageview2 = findViewById(R.id.imageview2);
        linear5 = findViewById(R.id.linear5);
        textview3 = findViewById(R.id.textview3);
        seekbar1 = findViewById(R.id.seekbar1);
        textview7 = findViewById(R.id.textview7);
        textview8 = findViewById(R.id.textview8);
        textview9 = findViewById(R.id.textview9);
        seekbar2 = findViewById(R.id.seekbar2);
        imageview3 = findViewById(R.id.imageview3);
        textview4 = findViewById(R.id.textview4);
        imageview4 = findViewById(R.id.imageview4);
        textview5 = findViewById(R.id.textview5);
        imageview5 = findViewById(R.id.imageview5);
        textview6 = findViewById(R.id.textview6);
        data = getSharedPreferences("teamdata", Activity.MODE_PRIVATE);
        Auth = FirebaseAuth.getInstance();

        imageview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                finish();
            }
        });

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
                final boolean _isChecked = _param2;
                if (_isChecked) {
                    data.edit().putString("showPreview", "1").commit();
                    switch1.getThumbDrawable().setColorFilter(Color.parseColor(theme_map.get(0).get("colorButton").toString()), PorterDuff.Mode.SRC_IN);
                    switch1.getTrackDrawable().setColorFilter(Color.parseColor(theme_map.get(0).get("colorButton").toString()), PorterDuff.Mode.SRC_IN);
                } else {
                    data.edit().putString("showPreview", "0").commit();
                    switch1.getThumbDrawable().clearColorFilter();
                    switch1.getTrackDrawable().clearColorFilter();
                }
            }
        });

        edittext1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
                final String _charSeq = _param1.toString();
                data.edit().putString("apiKey", _charSeq).commit();
            }

            @Override
            public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {

            }

            @Override
            public void afterTextChanged(Editable _param1) {

            }
        });

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
                final boolean _isChecked = _param2;
                if (_isChecked) {
                    data.edit().putString("showVisualizer", "1").commit();
                    switch2.getThumbDrawable().setColorFilter(Color.parseColor(theme_map.get(0).get("colorButton").toString()), PorterDuff.Mode.SRC_IN);
                    switch2.getTrackDrawable().setColorFilter(Color.parseColor(theme_map.get(0).get("colorButton").toString()), PorterDuff.Mode.SRC_IN);
                } else {
                    data.edit().putString("showVisualizer", "0").commit();
                    switch2.getThumbDrawable().clearColorFilter();
                    switch2.getTrackDrawable().clearColorFilter();
                }
            }
        });

        linear8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intent.setClass(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                startActivity(intent);
            }
        });

        linear9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intent.setClass(getApplicationContext(), LogcatActivity.class);
                startActivity(intent);
            }
        });

        linear10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intent.setClass(getApplicationContext(), DaeditActivity.class);
                startActivity(intent);
            }
        });

        seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar _param1, int _param2, boolean _param3) {
                final int _progressValue = _param2;
                data.edit().putString("colorTransparency", String.valueOf((long) (seekbar2.getProgress()))).commit();
                textview9.setText("FPU alpha color: ".concat(data.getString("colorTransparency", "")));
            }

            @Override
            public void onStartTrackingTouch(SeekBar _param1) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar _param2) {

            }
        });

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
        radiogroup1 = new RadioGroup(this);
        radiogroup1.setOrientation(RadioGroup.VERTICAL);
        radiobutton1 = new RadioButton(this);
        radiobutton1.setText("Full-screen blurred styled");
        radiobutton1.setId(0);
        radiobutton2 = new RadioButton(this);
        radiobutton2.setText("Compact bottom-sheet styled");
        radiobutton2.setId(1);
        radiogroup1.addView(radiobutton1);
        radiogroup1.addView(radiobutton2);
        linear11.addView(radiogroup1);
        radiogroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                data.edit().putString("playerUI", String.valueOf((long) (checkedId))).commit();
                if (checkedId == 0) {
                    radiobutton3.setEnabled(true);
                    radiobutton4.setEnabled(true);
                    linear13.setAlpha((float) (1.0d));
                } else {
                    radiobutton3.setEnabled(false);
                    radiobutton4.setEnabled(false);
                    linear13.setAlpha((float) (0.5d));
                }
            }
        });

        radiogroup2 = new RadioGroup(this);
        radiogroup2.setOrientation(RadioGroup.VERTICAL);
        radiobutton3 = new RadioButton(this);
        radiobutton3.setText("Blur image and set buttons & other controls color from detecting light on dominants color");
        radiobutton3.setId(0);
        radiobutton4 = new RadioButton(this);
        radiobutton4.setText("Blur image with color effect from current theme and set buttons & other controls color from current theme");
        radiobutton4.setId(1);
        radiogroup2.addView(radiobutton3);
        radiogroup2.addView(radiobutton4);
        linear13.addView(radiogroup2);
        radiogroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                data.edit().putString("fpuMode", String.valueOf((long) (checkedId))).commit();
                if (checkedId == 0) {
                    seekbar2.setEnabled(false);
                    linear15.setAlpha((float) (0.5d));
                } else {
                    seekbar2.setEnabled(true);
                    linear15.setAlpha((float) (1.0d));
                }
            }
        });
        _loadTheme();
        textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        switch1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        switch2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        edittext1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview4.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview5.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview6.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview7.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview8.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview9.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        radiobutton1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        radiobutton2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        radiobutton3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        radiobutton4.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        seekbar1.setProgress((int) Double.parseDouble(data.getString("nightcoreSpeed", "")));
        textview3.setText("Nightcore Speed: ".concat(new DecimalFormat("0.00").format(1.10d + (0.05d * Double.parseDouble(data.getString("nightcoreSpeed", "")))).concat("x")));
        seekbar2.setProgress((int) Double.parseDouble(data.getString("colorTransparency", "")));
        if (data.getString("showPreview", "").equals("1")) {
            switch1.setChecked(true);
        } else {
            switch1.setChecked(false);
        }
        if (data.getString("showVisualizer", "").equals("1")) {
            switch2.setChecked(true);
        } else {
            switch2.setChecked(false);
        }
        edittext1.setText(data.getString("apiKey", ""));
        seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar _param1, int _progressValue, boolean _param3) {
                data.edit().putString("nightcoreSpeed", String.valueOf((long) (seekbar1.getProgress()))).commit();
                textview3.setText("Nightcore Speed: ".concat(new DecimalFormat("0.00").format(1.10d + (0.05d * Double.parseDouble(data.getString("nightcoreSpeed", "")))).concat("x")));
            }

            @Override
            public void onStartTrackingTouch(SeekBar _param1) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar _param1) {
            }
        });
        if (data.getString("playerUI", "").equals("0")) {
            radiogroup1.check(radiobutton1.getId());
        } else {
            radiogroup1.check(radiobutton2.getId());
        }
        if (data.getString("fpuMode", "").equals("0")) {
            radiogroup2.check(radiobutton3.getId());
        } else {
            radiogroup2.check(radiobutton4.getId());
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

    private void _shadow(final View _v, final double _n) {
        _v.setElevation((float) _n);
    }


    private void _circleRipple(final String _color, final View _v) {
        android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_color)});
        android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb, null, null);
        _v.setBackground(ripdrb);
    }


    private void _BlackStatusBarIcons() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    private void _customNav(final String _color) {
        //Code From StackOverFlow.com And Converted By TeamWorks DEV
        if (Build.VERSION.SDK_INT >= 21) {
            Window w = this.getWindow();
            w.setNavigationBarColor(Color.parseColor(_color));
        }
    }

    private boolean isDark(int color) {
        return androidx.core.graphics.ColorUtils.calculateLuminance(color) < 0.5;
    }


    private void _loadTheme() {
        theme_map = new Gson().fromJson(data.getString("themesjson", ""), new TypeToken<ArrayList<HashMap<String, Object>>>() {
        }.getType());
        _customNav(theme_map.get(0).get("colorBackground").toString());
        _circleRipple(theme_map.get(0).get("colorRipple").toString(), imageview1);
        _rippleEffect(linear8, theme_map.get(0).get("colorRipple").toString());
        _rippleEffect(linear9, theme_map.get(0).get("colorRipple").toString());
        _rippleEffect(linear10, theme_map.get(0).get("colorRipple").toString());
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window w = this.getWindow();
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.parseColor(theme_map.get(0).get("colorPrimaryDark").toString()));
        }
        if (theme_map.get(0).get("shadow").toString().equals("1")) {
            _shadow(linear1, 10);
            if (isDark(Color.parseColor(theme_map.get(0).get("colorBackground").toString()))) {
                linear3.setBackgroundColor(0xFF808080);
                linear4.setBackgroundColor(0xFF808080);
                linear7.setBackgroundColor(0xFF808080);
                linear12.setBackgroundColor(0xFF808080);
                linear14.setBackgroundColor(0xFF808080);
                linear16.setBackgroundColor(0xFF808080);
                linear17.setBackgroundColor(0xFF808080);
            } else {
                linear3.setBackgroundColor(0xFFBDBDBD);
                linear4.setBackgroundColor(0xFFBDBDBD);
                linear7.setBackgroundColor(0xFFBDBDBD);
                linear12.setBackgroundColor(0xFFBDBDBD);
                linear14.setBackgroundColor(0xFFBDBDBD);
                linear16.setBackgroundColor(0xFFBDBDBD);
                linear17.setBackgroundColor(0xFFBDBDBD);
            }
        } else {
            linear3.setBackgroundColor(Color.TRANSPARENT);
            linear4.setBackgroundColor(Color.TRANSPARENT);
            linear7.setBackgroundColor(Color.TRANSPARENT);
            linear12.setBackgroundColor(Color.TRANSPARENT);
            linear14.setBackgroundColor(Color.TRANSPARENT);
            linear16.setBackgroundColor(Color.TRANSPARENT);
            linear17.setBackgroundColor(Color.TRANSPARENT);
        }
        linear1.setBackgroundColor(Color.parseColor(theme_map.get(0).get("colorPrimary").toString()));
        vscroll1.setBackgroundColor(Color.parseColor(theme_map.get(0).get("colorBackground").toString()));
        textview1.setTextColor(Color.parseColor(theme_map.get(0).get("colorPrimaryText").toString()));
        textview2.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        textview3.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        textview4.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        textview5.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        textview6.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        textview7.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        textview8.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        textview9.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        radiobutton1.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        radiobutton2.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        radiobutton3.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        radiobutton4.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        {
            android.content.res.ColorStateList colorStateList = new android.content.res.ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_enabled},
                            new int[]{android.R.attr.state_enabled}
                    },
                    new int[]{
                            Color.parseColor(theme_map.get(0).get("colorButton").toString()),
                            Color.parseColor(theme_map.get(0).get("colorButton").toString())
                    }
            );
            radiobutton1.setButtonTintList(colorStateList);
            radiobutton1.invalidate();
        }

        {
            android.content.res.ColorStateList colorStateList = new android.content.res.ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_enabled},
                            new int[]{android.R.attr.state_enabled}
                    },
                    new int[]{
                            Color.parseColor(theme_map.get(0).get("colorButton").toString()),
                            Color.parseColor(theme_map.get(0).get("colorButton").toString())
                    }
            );
            radiobutton2.setButtonTintList(colorStateList);
            radiobutton2.invalidate();
        }

        {
            android.content.res.ColorStateList colorStateList = new android.content.res.ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_enabled},
                            new int[]{android.R.attr.state_enabled}
                    },
                    new int[]{
                            Color.parseColor(theme_map.get(0).get("colorButton").toString()),
                            Color.parseColor(theme_map.get(0).get("colorButton").toString())
                    }
            );
            radiobutton3.setButtonTintList(colorStateList);
            radiobutton3.invalidate();
        }

        {
            android.content.res.ColorStateList colorStateList = new android.content.res.ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_enabled},
                            new int[]{android.R.attr.state_enabled}
                    },
                    new int[]{
                            Color.parseColor(theme_map.get(0).get("colorButton").toString()),
                            Color.parseColor(theme_map.get(0).get("colorButton").toString())
                    }
            );
            radiobutton4.setButtonTintList(colorStateList);
            radiobutton4.invalidate();
        }

        {
            android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(theme_map.get(0).get("colorRipple").toString())});
            android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb, null, null);
            radiobutton1.setBackground(ripdrb);
        }

        {
            android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(theme_map.get(0).get("colorRipple").toString())});
            android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb, null, null);
            radiobutton2.setBackground(ripdrb);
        }

        {
            android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(theme_map.get(0).get("colorRipple").toString())});
            android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb, null, null);
            radiobutton3.setBackground(ripdrb);
        }

        {
            android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(theme_map.get(0).get("colorRipple").toString())});
            android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb, null, null);
            radiobutton4.setBackground(ripdrb);
        }
        switch1.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        switch2.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        edittext1.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        edittext1.setHintTextColor(Color.parseColor(theme_map.get(0).get("colorHint").toString()));
        seekbar1.getProgressDrawable().setColorFilter(Color.parseColor(theme_map.get(0).get("colorButton").toString()), PorterDuff.Mode.SRC_IN);

        seekbar1.getThumb().setColorFilter(Color.parseColor(theme_map.get(0).get("colorButton").toString()), PorterDuff.Mode.SRC_IN);

        {
            android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(theme_map.get(0).get("colorRipple").toString())});
            android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb, null, null);
            seekbar1.setBackground(ripdrb);
        }

        seekbar2.getProgressDrawable().setColorFilter(Color.parseColor(theme_map.get(0).get("colorButton").toString()), PorterDuff.Mode.SRC_IN);

        seekbar2.getThumb().setColorFilter(Color.parseColor(theme_map.get(0).get("colorButton").toString()), PorterDuff.Mode.SRC_IN);

        {
            android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(theme_map.get(0).get("colorRipple").toString())});
            android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb, null, null);
            seekbar2.setBackground(ripdrb);
        }

        {
            android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(theme_map.get(0).get("colorRipple").toString())});
            android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb, null, null);
            switch1.setBackground(ripdrb);
        }

        {
            android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(theme_map.get(0).get("colorRipple").toString())});
            android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb, null, null);
            switch2.setBackground(ripdrb);
        }
        edittext1.getBackground().setColorFilter(Color.parseColor(theme_map.get(0).get("colorButton").toString()), PorterDuff.Mode.SRC_ATOP);
        if (theme_map.get(0).get("statusbarIcon").toString().equals("0")) {
            _BlackStatusBarIcons();
        }
        imageview1.setColorFilter(Color.parseColor(theme_map.get(0).get("colorPrimaryImage").toString()), PorterDuff.Mode.MULTIPLY);
        imageview2.setColorFilter(Color.parseColor(theme_map.get(0).get("colorBackgroundImage").toString()), PorterDuff.Mode.MULTIPLY);
        imageview3.setColorFilter(Color.parseColor(theme_map.get(0).get("colorBackgroundImage").toString()), PorterDuff.Mode.MULTIPLY);
        imageview4.setColorFilter(Color.parseColor(theme_map.get(0).get("colorBackgroundImage").toString()), PorterDuff.Mode.MULTIPLY);
        imageview5.setColorFilter(Color.parseColor(theme_map.get(0).get("colorBackgroundImage").toString()), PorterDuff.Mode.MULTIPLY);
    }


    private void _rippleEffect(final View _view, final String _color) {
        _view.setBackground(Drawables.getSelectableDrawableFor(Color.parseColor(_color)));
        _view.setClickable(true);
    }

    public void drawableclass() {
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

    public static class Drawables {
        public static android.graphics.drawable.Drawable getSelectableDrawableFor(int color) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                android.graphics.drawable.StateListDrawable stateListDrawable = new android.graphics.drawable.StateListDrawable();
                stateListDrawable.addState(
                        new int[]{android.R.attr.state_pressed},
                        new android.graphics.drawable.ColorDrawable(Color.parseColor("#ffffff"))
                );
                stateListDrawable.addState(
                        new int[]{android.R.attr.state_focused},
                        new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
                );
                stateListDrawable.addState(
                        new int[]{},
                        new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
                );
                return stateListDrawable;
            } else {
                android.content.res.ColorStateList pressedColor = android.content.res.ColorStateList.valueOf(color);
                android.graphics.drawable.ColorDrawable defaultColor = new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"));

                android.graphics.drawable.Drawable rippleColor = getRippleColor(color);
                return new android.graphics.drawable.RippleDrawable(
                        pressedColor,
                        defaultColor,
                        rippleColor
                );
            }
        }

        private static android.graphics.drawable.Drawable getRippleColor(int color) {
            float[] outerRadii = new float[8];
            Arrays.fill(outerRadii, 0);
            android.graphics.drawable.shapes.RoundRectShape r = new android.graphics.drawable.shapes.RoundRectShape(outerRadii, null, null);

            android.graphics.drawable.ShapeDrawable shapeDrawable = new
                    android.graphics.drawable.ShapeDrawable(r);
            shapeDrawable.getPaint().setColor(color);
            return shapeDrawable;
        }

        private static int lightenOrDarken(int color, double fraction) {
            if (canLighten(color, fraction)) {
                return lighten(color, fraction);
            } else {
                return darken(color, fraction);
            }
        }

        private static int lighten(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            red = lightenColor(red, fraction);
            green = lightenColor(green, fraction);
            blue = lightenColor(blue, fraction);
            int alpha = Color.alpha(color);
            return Color.argb(alpha, red, green, blue);
        }

        private static int darken(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            red = darkenColor(red, fraction);
            green = darkenColor(green, fraction);
            blue = darkenColor(blue, fraction);
            int alpha = Color.alpha(color);

            return Color.argb(alpha, red, green, blue);
        }

        private static boolean canLighten(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            return canLightenComponent(red, fraction)
                    && canLightenComponent(green, fraction)
                    && canLightenComponent(blue, fraction);
        }

        private static boolean canLightenComponent(int colorComponent, double fraction) {
            int red = Color.red(colorComponent);
            int green = Color.green(colorComponent);
            int blue = Color.blue(colorComponent);
            return red + (red * fraction) < 255
                    && green + (green * fraction) < 255
                    && blue + (blue * fraction) < 255;
        }

        private static int darkenColor(int color, double fraction) {
            return (int) Math.max(color - (color * fraction), 0);
        }

        private static int lightenColor(int color, double fraction) {
            return (int) Math.min(color + (color * fraction), 255);
        }
    }

    public static class CircleDrawables {
        public static android.graphics.drawable.Drawable getSelectableDrawableFor(int color) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                android.graphics.drawable.StateListDrawable stateListDrawable = new android.graphics.drawable.StateListDrawable();
                stateListDrawable.addState(
                        new int[]{android.R.attr.state_pressed},
                        new android.graphics.drawable.ColorDrawable(Color.parseColor("#ffffff"))
                );
                stateListDrawable.addState(
                        new int[]{android.R.attr.state_focused},
                        new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
                );
                stateListDrawable.addState(
                        new int[]{},
                        new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
                );
                return stateListDrawable;
            } else {
                android.content.res.ColorStateList pressedColor = android.content.res.ColorStateList.valueOf(color);
                android.graphics.drawable.ColorDrawable defaultColor = new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"));

                android.graphics.drawable.Drawable rippleColor = getRippleColor(color);
                return new android.graphics.drawable.RippleDrawable(
                        pressedColor,
                        defaultColor,
                        rippleColor
                );
            }
        }

        private static android.graphics.drawable.Drawable getRippleColor(int color) {
            float[] outerRadii = new float[180];
            Arrays.fill(outerRadii, 80);
            android.graphics.drawable.shapes.RoundRectShape r = new android.graphics.drawable.shapes.RoundRectShape(outerRadii, null, null);

            android.graphics.drawable.ShapeDrawable shapeDrawable = new
                    android.graphics.drawable.ShapeDrawable(r);
            shapeDrawable.getPaint().setColor(color);
            return shapeDrawable;
        }

        private static int lightenOrDarken(int color, double fraction) {
            if (canLighten(color, fraction)) {
                return lighten(color, fraction);
            } else {
                return darken(color, fraction);
            }
        }

        private static int lighten(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            red = lightenColor(red, fraction);
            green = lightenColor(green, fraction);
            blue = lightenColor(blue, fraction);
            int alpha = Color.alpha(color);
            return Color.argb(alpha, red, green, blue);
        }

        private static int darken(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            red = darkenColor(red, fraction);
            green = darkenColor(green, fraction);
            blue = darkenColor(blue, fraction);
            int alpha = Color.alpha(color);

            return Color.argb(alpha, red, green, blue);
        }

        private static boolean canLighten(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            return canLightenComponent(red, fraction)
                    && canLightenComponent(green, fraction)
                    && canLightenComponent(blue, fraction);
        }

        private static boolean canLightenComponent(int colorComponent, double fraction) {
            int red = Color.red(colorComponent);
            int green = Color.green(colorComponent);
            int blue = Color.blue(colorComponent);
            return red + (red * fraction) < 255
                    && green + (green * fraction) < 255
                    && blue + (blue * fraction) < 255;
        }

        private static int darkenColor(int color, double fraction) {
            return (int) Math.max(color - (color * fraction), 0);
        }

        private static int lightenColor(int color, double fraction) {
            return (int) Math.min(color + (color * fraction), 255);
        }
    }

}
