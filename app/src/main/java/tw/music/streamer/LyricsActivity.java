package tw.music.streamer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class LyricsActivity extends AppCompatActivity {


    private String musicName = "";
    private boolean isShown = false;

    private ArrayList<HashMap<String, Object>> theme_map = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> lyricsListmap = new ArrayList<>();

    private LinearLayout linear1;
    private LinearLayout linear2;
    private ImageView imageview1;
    private TextView textview1;
    private ImageView imageview2;
    private TextView textview2;
    private ListView listview1;
    private TextView textview3;

    private RequestNetwork rn;
    private RequestNetwork.RequestListener _rn_request_listener;
    private SharedPreferences data;
    private Intent intent = new Intent();
    private AlertDialog.Builder dialog;
    private org.json.JSONArray _resultArray;

    {
    }

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.lyrics);
        com.google.firebase.FirebaseApp.initializeApp(this);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {

        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        imageview1 = findViewById(R.id.imageview1);
        textview1 = findViewById(R.id.textview1);
        imageview2 = findViewById(R.id.imageview2);
        textview2 = findViewById(R.id.textview2);
        listview1 = findViewById(R.id.listview1);
        textview3 = findViewById(R.id.textview3);
        rn = new RequestNetwork(this);
        data = getSharedPreferences("teamdata", Activity.MODE_PRIVATE);
        dialog = new AlertDialog.Builder(this);

        imageview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                onBackPressed();
            }
        });

        imageview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                isShown = false;
                listview1.setVisibility(View.GONE);
                textview3.setVisibility(View.VISIBLE);
                rn.startRequestNetwork(RequestNetworkController.GET, "https://api.happi.dev/v1/music?q=".concat(musicName.concat("&apikey=".concat(data.getString("apiKey", "")))), "A", _rn_request_listener);
                textview3.setText("Searching...");
            }
        });

        textview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {

                LinearLayout mylayout = new LinearLayout(LyricsActivity.this);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                mylayout.setLayoutParams(params);
                mylayout.setOrientation(LinearLayout.VERTICAL);

                final EditText myedittext = new EditText(LyricsActivity.this);
                myedittext.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
                myedittext.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myedittext.setText(musicName);

                mylayout.addView(myedittext);
                dialog.setView(mylayout);
                myedittext.setHint("Music name");
                dialog.setTitle("Search music lyrics");
                dialog.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface _dialog, int _which) {
                        _abandonFocus();
                        if (myedittext.getText().toString().trim().equals("")) {
                            _customSnack("Fill those blanks!", 2);
                        } else {
                            musicName = myedittext.getText().toString().trim();
                            isShown = false;
                            listview1.setVisibility(View.GONE);
                            textview3.setVisibility(View.VISIBLE);
                            rn.startRequestNetwork(RequestNetworkController.GET, "https://api.happi.dev/v1/music?q=".concat(musicName.concat("&apikey=".concat(data.getString("apiKey", "")))), "A", _rn_request_listener);
                            textview3.setText("Searching...");
                        }
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface _dialog, int _which) {
                        _abandonFocus();
                    }
                });
                dialog.create().show();
            }
        });

        _rn_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _response = _param2;
                if (_tag.equals("A")) {
                    try {
                        org.json.JSONObject _json = new org.json.JSONObject(_response);
                        if (_json.getBoolean("success")) {
                            if (_json.getInt("length") > 0) {
                                lyricsListmap.clear();
                                _resultArray = _json.getJSONArray("result");
                                double _tmpDouble = 0;
                                isShown = false;
                                textview3.setVisibility(View.GONE);
                                listview1.setVisibility(View.VISIBLE);
                                for (int _repeat21 = 0; _repeat21 < _json.getInt("length"); _repeat21++) {
                                    org.json.JSONObject _resultObject = _resultArray.getJSONObject((int) _tmpDouble);
                                    {
                                        HashMap<String, Object> _item = new HashMap<>();
                                        _item.put("track", _resultObject.getString("track"));
                                        lyricsListmap.add(_item);
                                    }

                                    lyricsListmap.get(lyricsListmap.size() - 1).put("artist", _resultObject.getString("artist"));
                                    lyricsListmap.get(lyricsListmap.size() - 1).put("album", _resultObject.getString("album"));
                                    ((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
                                    _tmpDouble++;
                                }
                            } else {
                                lyricsListmap.clear();
                                ((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
                                isShown = false;
                                listview1.setVisibility(View.GONE);
                                textview3.setVisibility(View.VISIBLE);
                                textview3.setText("Lyrics not found or wrong music name");
                            }
                        } else {
                            isShown = false;
                            listview1.setVisibility(View.GONE);
                            textview3.setVisibility(View.VISIBLE);
                            textview3.setText("Not success to retrieve lyrics! (Wrong API Key?)");
                        }
                    } catch (Exception _e) {
                        textview1.setText(_e.toString());
                    }
                } else {
                    if (_tag.equals("B")) {
                        try {
                            org.json.JSONObject _json = new org.json.JSONObject(_response);
                            if (_json.getBoolean("success")) {
                                org.json.JSONObject _result = _json.getJSONObject("result");
                                isShown = true;
                                listview1.setVisibility(View.GONE);
                                textview3.setVisibility(View.VISIBLE);
                                textview3.setText(_result.getString("lyrics"));
                            } else {
                                isShown = false;
                                listview1.setVisibility(View.GONE);
                                textview3.setVisibility(View.VISIBLE);
                                textview3.setText("Not success to retrieve lyrics! (Wrong API key?)");
                            }
                        } catch (Exception _e) {
                            textview1.setText(_e.toString());
                        }
                    }
                }
            }

            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
                isShown = false;
                listview1.setVisibility(View.GONE);
                textview3.setVisibility(View.VISIBLE);
                textview3.setText("Failed to retrieve lyrics (Internet connection? Or invalid API key?)");
            }
        };
    }

    private void initializeLogic() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        textview3.setTextIsSelectable(true);
        _loadTheme();
        textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        listview1.setAdapter(new Listview1Adapter(lyricsListmap));
        musicName = getIntent().getStringExtra("musicName");
        if (data.getString("apiKey", "").equals("")) {
            SketchwareUtil.showMessage(getApplicationContext(), "Setup the api key first!");
            intent.setClass(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            finish();
        } else {
            listview1.setVisibility(View.GONE);
            rn.startRequestNetwork(RequestNetworkController.GET, "https://api.happi.dev/v1/music?q=".concat(musicName.concat("&apikey=".concat(data.getString("apiKey", "")))), "A", _rn_request_listener);
            textview3.setText("Searching...");
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
        if (isShown) {
            isShown = false;
            textview3.setVisibility(View.GONE);
            listview1.setVisibility(View.VISIBLE);
        } else {
            finish();
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


    private void _customNav(final String _color) {
        //Code From StackOverFlow.com And Converted By TeamWorks DEV
        if (Build.VERSION.SDK_INT >= 21) {
            Window w = this.getWindow();
            w.setNavigationBarColor(Color.parseColor(_color));
        }
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


    private void _rippleEffect(final View _view, final String _color) {
        _view.setBackground(Drawables.getSelectableDrawableFor(Color.parseColor(_color)));
        _view.setClickable(true);
    }

    public void drawableclass() {
    }

    private void _loadTheme() {
        theme_map = new Gson().fromJson(data.getString("themesjson", ""), new TypeToken<ArrayList<HashMap<String, Object>>>() {
        }.getType());
        _customNav(theme_map.get(0).get("colorBackground").toString());
        _circleRipple(theme_map.get(0).get("colorRipple").toString(), imageview1);
        _circleRipple(theme_map.get(0).get("colorRipple").toString(), imageview2);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window w = this.getWindow();
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.parseColor(theme_map.get(0).get("colorPrimaryDark").toString()));
        }
        if (theme_map.get(0).get("shadow").toString().equals("1")) {
            _shadow(linear1, 10);
        }
        linear1.setBackgroundColor(Color.parseColor(theme_map.get(0).get("colorPrimary").toString()));
        linear2.setBackgroundColor(Color.parseColor(theme_map.get(0).get("colorBackground").toString()));
        textview1.setTextColor(Color.parseColor(theme_map.get(0).get("colorPrimaryText").toString()));
        textview2.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        textview3.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        if (theme_map.get(0).get("statusbarIcon").toString().equals("0")) {
            _BlackStatusBarIcons();
        }
        imageview1.setColorFilter(Color.parseColor(theme_map.get(0).get("colorPrimaryImage").toString()), PorterDuff.Mode.MULTIPLY);
        imageview2.setColorFilter(Color.parseColor(theme_map.get(0).get("colorPrimaryImage").toString()), PorterDuff.Mode.MULTIPLY);
    }

    private void _customSnack(final String _txt, final double _icon) {
        // Create the Snackbar
        ViewGroup containerLayout = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);

        com.google.android.material.snackbar.Snackbar snackbar = com.google.android.material.snackbar.Snackbar.make(containerLayout, "", com.google.android.material.snackbar.Snackbar.LENGTH_LONG);
        // Get the Snackbar's layout view
        com.google.android.material.snackbar.Snackbar.SnackbarLayout layout = (com.google.android.material.snackbar.Snackbar.SnackbarLayout) snackbar.getView();
        // Inflate our custom view
        View snackview = getLayoutInflater().inflate(R.layout.custom_snack, null);
        // Configure the view
        ImageView image = snackview.findViewById(R.id.imageview);
        if (_icon == 0) {
            image.setImageResource(R.drawable.ic_info_outline_white);
            layout.setBackgroundColor(Color.parseColor("#2090F0"));
        } else {
            if (_icon == 1) {
                image.setImageResource(R.drawable.ic_done_white);
                layout.setBackgroundColor(Color.parseColor("#48B048"));
            } else {
                if (_icon == 2) {
                    image.setImageResource(R.drawable.ic_exit_white);
                    layout.setBackgroundColor(Color.parseColor("#E03830"));
                }
            }
        }
        TextView text = snackview.findViewById(R.id.textview);
        text.setText(_txt);
        text.setTextColor(Color.WHITE);
        text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        layout.setPadding(0, 0, 0, 0);
        // Add the view to the Snackbar's layout
        layout.addView(snackview, 0);
        // Show the Snackbar
        snackbar.show();
    }

    private void _retrieveLyrics(final double _position) {
        listview1.setVisibility(View.GONE);
        textview3.setVisibility(View.GONE);
        textview3.setText("Please Wait...");
        try {
            org.json.JSONObject _resultObject = _resultArray.getJSONObject((int) _position);
            rn.startRequestNetwork(RequestNetworkController.GET, _resultObject.getString("api_lyrics").concat("?apikey=06349bEfBAJKGBdckYz1qtj5acwL8x9VxFFUIBULWxW0HerKCRAEZgG9"), "B", _rn_request_listener);
        } catch (Exception _e) {
            isShown = false;
            textview3.setText("Error while parsing json. (Wrong API key?)");
        }
    }

    private void _abandonFocus() {
        View _tmpView = this.getCurrentFocus();
        if (_tmpView != null) {
            android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(_tmpView.getWindowToken(), 0);
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

    public class Listview1Adapter extends BaseAdapter {
        ArrayList<HashMap<String, Object>> _data;

        public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }

        @Override
        public int getCount() {
            return _data.size();
        }

        @Override
        public HashMap<String, Object> getItem(int _index) {
            return _data.get(_index);
        }

        @Override
        public long getItemId(int _index) {
            return _index;
        }

        @Override
        public View getView(final int _position, View _view, ViewGroup _viewGroup) {
            LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View _v = _view;
            if (_v == null) {
                _v = _inflater.inflate(R.layout.lyricslist, null);
            }

            final LinearLayout linear1 = _v.findViewById(R.id.linear1);
            final TextView textview1 = _v.findViewById(R.id.textview1);
            final TextView textview2 = _v.findViewById(R.id.textview2);

            textview1.setText(_data.get(_position).get("track").toString());
            textview2.setText(_data.get(_position).get("artist").toString().concat(" - ".concat(_data.get(_position).get("album").toString())));
            _rippleEffect(linear1, theme_map.get(0).get("colorRipple").toString());
            textview1.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
            textview2.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
            textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
            textview2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
            linear1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    _retrieveLyrics(_position);
                }
            });

            return _v;
        }
    }

}
