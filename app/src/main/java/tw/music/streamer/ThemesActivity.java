package tw.music.streamer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ThemesActivity extends AppCompatActivity {


    private FloatingActionButton _fab;
    private boolean isPanel = false;
    private boolean isEditing = false;
    private double posEdit = 0;
    private String tmpPath = "";
    private HashMap<String, Object> tmpMAPM = new HashMap<>();

    private ArrayList<HashMap<String, Object>> themes_map = new ArrayList<>();
    private ArrayList<String> spinnerList = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> gridMap = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> gridtmp = new ArrayList<>();
    private ArrayList<String> indexDataList = new ArrayList<>();
    private ArrayList<String> dialog_list = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> tmpMAPC = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> tmpMAPJ = new ArrayList<>();

    private LinearLayout linear_title;
    private LinearLayout linear_grid;
    private ScrollView vscroll1;
    private ImageView image_back;
    private TextView text_back;
    private ImageView image_import;
    private LinearLayout linear_panel;
    private LinearLayout linear15;
    private LinearLayout colprimlin;
    private LinearLayout colprimtextlin;
    private LinearLayout colprimdarklin;
    private LinearLayout sttsbrlin;
    private LinearLayout colbglin;
    private LinearLayout colbgtxtlin;
    private LinearLayout colbttnlin;
    private LinearLayout colbttntxtlin;
    private LinearLayout sdwlin;
    private LinearLayout colriplin;
    private LinearLayout colhntlin;
    private LinearLayout colprimimglin;
    private LinearLayout colbgimglin;
    private LinearLayout linear9;
    private LinearLayout linear10;
    private LinearLayout linear11;
    private LinearLayout linear12;
    private LinearLayout linear13;
    private LinearLayout linear14;
    private Button button_save;
    private LinearLayout linear18;
    private LinearLayout linear16;
    private TextView textview20;
    private LinearLayout linear17;
    private ImageView imageview2;
    private ImageView imageview3;
    private LinearLayout linear19;
    private ImageView imageview6;
    private ImageView imageview5;
    private ImageView imageview4;
    private TextView textview21;
    private ImageView imageview1;
    private TextView textview18;
    private TextView textview19;
    private TextView colprimtext;
    private TextView textview1;
    private Button button1;
    private TextView colprimtxttext;
    private TextView textview9;
    private Button button9;
    private TextView colprimdarktext;
    private TextView textview2;
    private Button button2;
    private TextView sttsbrtext;
    private Spinner sttsbrspin;
    private TextView colbgtext;
    private TextView textview3;
    private Button button3;
    private TextView colbgtxttext;
    private TextView textview10;
    private Button button10;
    private TextView colbttntext;
    private TextView textview4;
    private Button button4;
    private TextView colbttntxttext;
    private TextView textview11;
    private Button button11;
    private TextView sdwtext;
    private CheckBox sdwchkbx;
    private TextView colriptext;
    private TextView textview5;
    private Button button5;
    private TextView colhnttext;
    private TextView textview6;
    private Button button6;
    private TextView colprimimgtxt;
    private TextView textview7;
    private Button button7;
    private TextView colbgimgtxt;
    private TextView textview8;
    private Button button8;
    private TextView colprimcrd;
    private TextView textview12;
    private Button button12;
    private TextView colbgcrd;
    private TextView textview13;
    private Button button13;
    private TextView colprimcrdtxt;
    private TextView textview14;
    private Button button14;
    private TextView colbgcrdtxt;
    private TextView textview15;
    private Button button15;
    private TextView colprimcrdimg;
    private TextView textview16;
    private Button button16;
    private TextView colbgcrdimg;
    private TextView textview17;
    private Button button17;

    private SharedPreferences data;
    private AlertDialog.Builder dialog;
    private Intent i = new Intent();
    private AlertDialog.Builder dialog2;
    private AlertDialog.Builder dialog3;
    private AlertDialog.Builder colorPicker;
    private AlertDialog.Builder dialog4;
    private GridView grid;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.themes);
        com.google.firebase.FirebaseApp.initializeApp(this);
        initialize(_savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        } else {
            initializeLogic();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            initializeLogic();
        }
    }

    private void initialize(Bundle _savedInstanceState) {

        _fab = findViewById(R.id._fab);

        linear_title = findViewById(R.id.linear_title);
        linear_grid = findViewById(R.id.linear_grid);
        vscroll1 = findViewById(R.id.vscroll1);
        image_back = findViewById(R.id.image_back);
        text_back = findViewById(R.id.text_back);
        image_import = findViewById(R.id.image_import);
        linear_panel = findViewById(R.id.linear_panel);
        linear15 = findViewById(R.id.linear15);
        colprimlin = findViewById(R.id.colprimlin);
        colprimtextlin = findViewById(R.id.colprimtextlin);
        colprimdarklin = findViewById(R.id.colprimdarklin);
        sttsbrlin = findViewById(R.id.sttsbrlin);
        colbglin = findViewById(R.id.colbglin);
        colbgtxtlin = findViewById(R.id.colbgtxtlin);
        colbttnlin = findViewById(R.id.colbttnlin);
        colbttntxtlin = findViewById(R.id.colbttntxtlin);
        sdwlin = findViewById(R.id.sdwlin);
        colriplin = findViewById(R.id.colriplin);
        colhntlin = findViewById(R.id.colhntlin);
        colprimimglin = findViewById(R.id.colprimimglin);
        colbgimglin = findViewById(R.id.colbgimglin);
        linear9 = findViewById(R.id.linear9);
        linear10 = findViewById(R.id.linear10);
        linear11 = findViewById(R.id.linear11);
        linear12 = findViewById(R.id.linear12);
        linear13 = findViewById(R.id.linear13);
        linear14 = findViewById(R.id.linear14);
        button_save = findViewById(R.id.button_save);
        linear18 = findViewById(R.id.linear18);
        linear16 = findViewById(R.id.linear16);
        textview20 = findViewById(R.id.textview20);
        linear17 = findViewById(R.id.linear17);
        imageview2 = findViewById(R.id.imageview2);
        imageview3 = findViewById(R.id.imageview3);
        linear19 = findViewById(R.id.linear19);
        imageview6 = findViewById(R.id.imageview6);
        imageview5 = findViewById(R.id.imageview5);
        imageview4 = findViewById(R.id.imageview4);
        textview21 = findViewById(R.id.textview21);
        imageview1 = findViewById(R.id.imageview1);
        textview18 = findViewById(R.id.textview18);
        textview19 = findViewById(R.id.textview19);
        colprimtext = findViewById(R.id.colprimtext);
        textview1 = findViewById(R.id.textview1);
        button1 = findViewById(R.id.button1);
        colprimtxttext = findViewById(R.id.colprimtxttext);
        textview9 = findViewById(R.id.textview9);
        button9 = findViewById(R.id.button9);
        colprimdarktext = findViewById(R.id.colprimdarktext);
        textview2 = findViewById(R.id.textview2);
        button2 = findViewById(R.id.button2);
        sttsbrtext = findViewById(R.id.sttsbrtext);
        sttsbrspin = findViewById(R.id.sttsbrspin);
        colbgtext = findViewById(R.id.colbgtext);
        textview3 = findViewById(R.id.textview3);
        button3 = findViewById(R.id.button3);
        colbgtxttext = findViewById(R.id.colbgtxttext);
        textview10 = findViewById(R.id.textview10);
        button10 = findViewById(R.id.button10);
        colbttntext = findViewById(R.id.colbttntext);
        textview4 = findViewById(R.id.textview4);
        button4 = findViewById(R.id.button4);
        colbttntxttext = findViewById(R.id.colbttntxttext);
        textview11 = findViewById(R.id.textview11);
        button11 = findViewById(R.id.button11);
        sdwtext = findViewById(R.id.sdwtext);
        sdwchkbx = findViewById(R.id.sdwchkbx);
        colriptext = findViewById(R.id.colriptext);
        textview5 = findViewById(R.id.textview5);
        button5 = findViewById(R.id.button5);
        colhnttext = findViewById(R.id.colhnttext);
        textview6 = findViewById(R.id.textview6);
        button6 = findViewById(R.id.button6);
        colprimimgtxt = findViewById(R.id.colprimimgtxt);
        textview7 = findViewById(R.id.textview7);
        button7 = findViewById(R.id.button7);
        colbgimgtxt = findViewById(R.id.colbgimgtxt);
        textview8 = findViewById(R.id.textview8);
        button8 = findViewById(R.id.button8);
        colprimcrd = findViewById(R.id.colprimcrd);
        textview12 = findViewById(R.id.textview12);
        button12 = findViewById(R.id.button12);
        colbgcrd = findViewById(R.id.colbgcrd);
        textview13 = findViewById(R.id.textview13);
        button13 = findViewById(R.id.button13);
        colprimcrdtxt = findViewById(R.id.colprimcrdtxt);
        textview14 = findViewById(R.id.textview14);
        button14 = findViewById(R.id.button14);
        colbgcrdtxt = findViewById(R.id.colbgcrdtxt);
        textview15 = findViewById(R.id.textview15);
        button15 = findViewById(R.id.button15);
        colprimcrdimg = findViewById(R.id.colprimcrdimg);
        textview16 = findViewById(R.id.textview16);
        button16 = findViewById(R.id.button16);
        colbgcrdimg = findViewById(R.id.colbgcrdimg);
        textview17 = findViewById(R.id.textview17);
        button17 = findViewById(R.id.button17);
        data = getSharedPreferences("teamdata", Activity.MODE_PRIVATE);
        dialog = new AlertDialog.Builder(this);
        dialog2 = new AlertDialog.Builder(this);
        dialog3 = new AlertDialog.Builder(this);
        colorPicker = new AlertDialog.Builder(this);
        dialog4 = new AlertDialog.Builder(this);

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                onBackPressed();
            }
        });

        image_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setClass(getApplicationContext(), FilepickerActivity.class);
                i.putExtra("fileType", "os-thm");
                startActivity(i);
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {

                LinearLayout mylayout = new LinearLayout(ThemesActivity.this);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                mylayout.setLayoutParams(params);
                mylayout.setOrientation(LinearLayout.VERTICAL);

                final EditText myedittext = new EditText(ThemesActivity.this);
                myedittext.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
                myedittext.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

                final EditText myedittext_author = new EditText(ThemesActivity.this);
                myedittext_author.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
                myedittext_author.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

                final EditText myedittext_info = new EditText(ThemesActivity.this);
                myedittext_info.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
                myedittext_info.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                if (isEditing) {
                    myedittext.setText(gridMap.get((int) posEdit).get("themesname").toString());
                    if (gridMap.get((int) posEdit).containsKey("themesauthor"))
                        myedittext_author.setText(gridMap.get((int) posEdit).get("themesauthor").toString());
                    if (gridMap.get((int) posEdit).containsKey("themesinfo"))
                        myedittext_info.setText(gridMap.get((int) posEdit).get("themesinfo").toString());
                }

                mylayout.addView(myedittext);
                mylayout.addView(myedittext_author);
                mylayout.addView(myedittext_info);
                dialog2.setView(mylayout);
                myedittext.setHint("Theme Name");
                myedittext_author.setHint("Author Name");
                myedittext_info.setHint("Theme Info");
                dialog2.setCancelable(false);
                dialog2.setTitle("Enter your theme metadata");
                dialog2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface _dialog, int _which) {
                        _abandonFocus();
                        if (!((myedittext.getText().toString().trim().length() == 0) || myedittext.getText().toString().contains("$")) && (!(myedittext_author.getText().toString().trim().length() == 0) && !(myedittext_info.getText().toString().trim().length() == 0))) {
                            themes_map.clear();
                            {
                                HashMap<String, Object> _item = new HashMap<>();
                                _item.put("colorPrimary", textview1.getText().toString());
                                themes_map.add(_item);
                            }

                            themes_map.get(0).put("colorPrimaryText", textview9.getText().toString());
                            themes_map.get(0).put("colorPrimaryDark", textview2.getText().toString());
                            themes_map.get(0).put("statusbarIcon", String.valueOf((long) (sttsbrspin.getSelectedItemPosition())));
                            themes_map.get(0).put("colorBackground", textview3.getText().toString());
                            themes_map.get(0).put("colorBackgroundText", textview10.getText().toString());
                            themes_map.get(0).put("colorButton", textview4.getText().toString());
                            themes_map.get(0).put("colorButtonText", textview11.getText().toString());
                            if (sdwchkbx.isChecked()) {
                                themes_map.get(0).put("shadow", "1");
                            } else {
                                themes_map.get(0).put("shadow", "0");
                            }
                            themes_map.get(0).put("colorRipple", textview5.getText().toString());
                            themes_map.get(0).put("colorHint", textview6.getText().toString());
                            themes_map.get(0).put("colorPrimaryImage", textview7.getText().toString());
                            themes_map.get(0).put("colorBackgroundImage", textview8.getText().toString());
                            themes_map.get(0).put("colorPrimaryCard", textview12.getText().toString());
                            themes_map.get(0).put("colorBackgroundCard", textview13.getText().toString());
                            themes_map.get(0).put("colorPrimaryCardText", textview14.getText().toString());
                            themes_map.get(0).put("colorBackgroundCardText", textview15.getText().toString());
                            themes_map.get(0).put("colorPrimaryCardImage", textview16.getText().toString());
                            themes_map.get(0).put("colorBackgroundCardImage", textview17.getText().toString());
                            themes_map.get(0).put("version", "2");
                            if (isEditing) {
                                gridMap.get((int) posEdit).put("themesname", myedittext.getText().toString());
                                gridMap.get((int) posEdit).put("themesjson", new Gson().toJson(themes_map));
                                gridMap.get((int) posEdit).put("themesinfo", myedittext_info.getText().toString());
                                gridMap.get((int) posEdit).put("themesauthor", myedittext_author.getText().toString());
                                gridMap.get((int) posEdit).put("os-thm-version", "2");
                                data.edit().putString("griddata", new Gson().toJson(gridMap)).commit();
                                indexDataList.remove((int) (posEdit));
                                indexDataList.add((int) (posEdit), new Gson().toJson(themes_map));
                                isPanel = false;
                                _fab.show();
                                vscroll1.setVisibility(View.GONE);
                                linear_grid.setVisibility(View.VISIBLE);
                                ((BaseAdapter) grid.getAdapter()).notifyDataSetChanged();
                                SketchwareUtil.showMessage(getApplicationContext(), "Saved! ");
                            } else {
                                if (indexDataList.contains(new Gson().toJson(themes_map))) {
                                    SketchwareUtil.showMessage(getApplicationContext(), "Same theme scheme detected!");
                                } else {
                                    {
                                        HashMap<String, Object> _item = new HashMap<>();
                                        _item.put("themesname", myedittext.getText().toString());
                                        gridMap.add(_item);
                                    }

                                    gridMap.get(gridMap.size() - 1).put("themesjson", new Gson().toJson(themes_map));
                                    gridMap.get(gridMap.size() - 1).put("themesinfo", myedittext_info.getText().toString());
                                    gridMap.get(gridMap.size() - 1).put("themesauthor", myedittext_author.getText().toString());
                                    gridMap.get(gridMap.size() - 1).put("os-thm-version", "2");
                                    data.edit().putString("griddata", new Gson().toJson(gridMap)).commit();
                                    indexDataList.add(new Gson().toJson(themes_map));
                                    isPanel = false;
                                    _fab.show();
                                    vscroll1.setVisibility(View.GONE);
                                    linear_grid.setVisibility(View.VISIBLE);
                                    ((BaseAdapter) grid.getAdapter()).notifyDataSetChanged();
                                    SketchwareUtil.showMessage(getApplicationContext(), "Saved! ");
                                }
                            }
                        } else {
                            SketchwareUtil.showMessage(getApplicationContext(), "Name can't empty or contains \"$\"! ");
                        }
                    }
                });
                dialog2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface _dialog, int _which) {
                        _abandonFocus();
                    }
                });
                dialog2.create().show();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _pickColor(1);
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _pickColor(9);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _pickColor(2);
            }
        });

        sttsbrspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
                final int _position = _param3;
                _updatePreview();
            }

            @Override
            public void onNothingSelected(AdapterView<?> _param1) {

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _pickColor(3);
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _pickColor(10);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _pickColor(4);
            }
        });

        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _pickColor(11);
            }
        });

        sdwchkbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
                final boolean _isChecked = _param2;
                _updatePreview();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _pickColor(5);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _pickColor(6);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _pickColor(7);
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _pickColor(8);
            }
        });

        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _pickColor(12);
            }
        });

        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _pickColor(13);
            }
        });

        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _pickColor(14);
            }
        });

        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _pickColor(15);
            }
        });

        button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _pickColor(16);
            }
        });

        button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _pickColor(17);
            }
        });

        _fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                isPanel = true;
                isEditing = false;
                _fab.hide();
                linear_grid.setVisibility(View.GONE);
                vscroll1.setVisibility(View.VISIBLE);
                _resetPanel();
            }
        });
    }

    private void initializeLogic() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        _shadow(linear_title, 10);
        vscroll1.setVisibility(View.GONE);
        gridMap = new Gson().fromJson(data.getString("griddata", ""), new TypeToken<ArrayList<HashMap<String, Object>>>() {
        }.getType());
        grid = new GridView(ThemesActivity.this);

        grid.setLayoutParams(new GridView.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT));

        grid.setNumColumns(GridView.AUTO_FIT);

        grid.setVerticalSpacing(2);

        grid.setHorizontalSpacing(2);

        grid.setColumnWidth((int) SketchwareUtil.getDip(getApplicationContext(), 118));

        grid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

        linear_grid.addView(grid);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int _pos, long id) {
                _gridSelected(_pos);
            }
        });
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int _pos, long id) {
                _gridLongClick(_pos);
                return true;
            }
        });
        grid.setAdapter(new Gridview1Adapter(gridMap));
        for (int _repeat158 = 0; _repeat158 < gridMap.size(); _repeat158++) {
            indexDataList.add(gridMap.get(indexDataList.size()).get("themesjson").toString());
        }
        spinnerList.add("Black");
        spinnerList.add("White");
        sttsbrspin.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, spinnerList));
        sttsbrspin.setSelection(1);
        dialog_list.add("Delete");
        dialog_list.add("Edit");
        dialog_list.add("More info");
        dialog_list.add("Export theme");
        dialog_list.add("Cancel");
        _customNav("#FFFFFF");
        _circleRipple("#40000000", image_back);
        _circleRipple("#40000000", image_import);
        text_back.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview18.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview19.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesansbold.ttf"), 0);
        textview20.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview21.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        _setFont();
    }

    private String getHexFromARGB(int _a, int _r, int _g, int _b) {
        int alp = _a;
        int red = _r;
        int grn = _g;
        int blu = _b;
        if (alp != 255) {
            return String.format("%02x%02x%02x%02x", alp, red, grn, blu);
        } else {
            return String.format("%02x%02x%02x", red, grn, blu);
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
        if (isPanel) {
            isPanel = false;
            _fab.show();
            vscroll1.setVisibility(View.GONE);
            linear_grid.setVisibility(View.VISIBLE);
            _resetPanel();
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
        if (!data.getString("tmpPath", "").equals("")) {
            tmpPath = data.getString("tmpPath", "");
            data.edit().remove("tmpPath").commit();
            try {
                tmpMAPC.clear();
                tmpMAPC = new Gson().fromJson(FileUtil.readFile(tmpPath), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                double _tmpDouble = 0;
                String _testJSON = "";
                for (int _repeat62 = 0; _repeat62 < tmpMAPC.size(); _repeat62++) {
                    if (tmpMAPC.get((int) _tmpDouble).containsKey("os-thm-version")) {
                        if (tmpMAPC.get((int) _tmpDouble).get("os-thm-version").toString().equals("2")) {
                            if (!(tmpMAPC.get((int) _tmpDouble).get("themesname").toString().contains("$") || (tmpMAPC.get((int) _tmpDouble).get("themesname").toString().trim().length() == 0))) {
                                if (indexDataList.contains(tmpMAPC.get((int) _tmpDouble).get("themesjson").toString())) {
                                    SketchwareUtil.showMessage(getApplicationContext(), "Same theme scheme detected!");
                                } else {
                                    tmpMAPJ.clear();
                                    _testJSON = tmpMAPC.get((int) _tmpDouble).get("themesname").toString();
                                    _testJSON = tmpMAPC.get((int) _tmpDouble).get("themesauthor").toString();
                                    _testJSON = tmpMAPC.get((int) _tmpDouble).get("themesinfo").toString();
                                    tmpMAPJ = new Gson().fromJson(tmpMAPC.get((int) _tmpDouble).get("themesjson").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                                    }.getType());
                                    if (tmpMAPJ.get(0).containsKey("version")) {
                                        if (tmpMAPJ.get(0).get("version").toString().equals("2")) {
                                            _testJSON = tmpMAPJ.get(0).get("colorPrimary").toString();
                                            _testJSON = tmpMAPJ.get(0).get("colorPrimaryText").toString();
                                            _testJSON = tmpMAPJ.get(0).get("colorPrimaryDark").toString();
                                            _testJSON = tmpMAPJ.get(0).get("statusbarIcon").toString();
                                            _testJSON = tmpMAPJ.get(0).get("colorBackground").toString();
                                            _testJSON = tmpMAPJ.get(0).get("colorBackgroundText").toString();
                                            _testJSON = tmpMAPJ.get(0).get("colorButton").toString();
                                            _testJSON = tmpMAPJ.get(0).get("colorButtonText").toString();
                                            _testJSON = tmpMAPJ.get(0).get("shadow").toString();
                                            _testJSON = tmpMAPJ.get(0).get("colorRipple").toString();
                                            _testJSON = tmpMAPJ.get(0).get("colorHint").toString();
                                            _testJSON = tmpMAPJ.get(0).get("colorPrimaryImage").toString();
                                            _testJSON = tmpMAPJ.get(0).get("colorBackgroundImage").toString();
                                            _testJSON = tmpMAPJ.get(0).get("colorPrimaryCard").toString();
                                            _testJSON = tmpMAPJ.get(0).get("colorBackgroundCard").toString();
                                            _testJSON = tmpMAPJ.get(0).get("colorPrimaryCardText").toString();
                                            _testJSON = tmpMAPJ.get(0).get("colorBackgroundCardText").toString();
                                            _testJSON = tmpMAPJ.get(0).get("colorPrimaryCardImage").toString();
                                            _testJSON = tmpMAPJ.get(0).get("colorBackgroundCardImage").toString();
                                            tmpMAPM = tmpMAPC.get((int) _tmpDouble);
                                            gridMap.add(tmpMAPM);
                                            data.edit().putString("griddata", new Gson().toJson(gridMap)).commit();
                                            indexDataList.add(new Gson().toJson(tmpMAPJ));
                                            ((BaseAdapter) grid.getAdapter()).notifyDataSetChanged();
                                            if (_tmpDouble == (tmpMAPC.size() - 1)) {
                                                SketchwareUtil.showMessage(getApplicationContext(), "Saved! ");
                                            }
                                        } else {
                                            SketchwareUtil.showMessage(getApplicationContext(), "Incompatible theme version!");
                                        }
                                    } else {
                                        SketchwareUtil.showMessage(getApplicationContext(), "Incompatible theme version!");
                                    }
                                }
                            } else {
                                SketchwareUtil.showMessage(getApplicationContext(), "Reserved word in theme name detected!");
                            }
                        } else {
                            SketchwareUtil.showMessage(getApplicationContext(), "Incompatible theme version!");
                        }
                    } else {
                        SketchwareUtil.showMessage(getApplicationContext(), "Incompatible theme version!");
                    }
                    _tmpDouble++;
                }
            } catch (Exception _e) {
                showMessage("Invalid theme!");
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private void _circleRipple(final String _color, final View _v) {
        android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_color)});
        android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb, null, null);
        _v.setBackground(ripdrb);
    }

    private void _pickColor(final double _numberlol) {
        LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View _colPickView = _inflater.inflate(R.layout.colpick, null);
        final TextView text_hastag = _colPickView.findViewById(R.id.text_hastag);
        final EditText edit_hex = _colPickView.findViewById(R.id.edit_hex);
        final SeekBar seek_a = _colPickView.findViewById(R.id.seek_a);
        final SeekBar seek_r = _colPickView.findViewById(R.id.seek_r);
        final SeekBar seek_g = _colPickView.findViewById(R.id.seek_g);
        final SeekBar seek_b = _colPickView.findViewById(R.id.seek_b);
        final TextView text_a = _colPickView.findViewById(R.id.text_a);
        final TextView text_r = _colPickView.findViewById(R.id.text_r);
        final TextView text_g = _colPickView.findViewById(R.id.text_g);
        final TextView text_b = _colPickView.findViewById(R.id.text_b);
        final TextView textn_a = _colPickView.findViewById(R.id.textn_a);
        final TextView textn_r = _colPickView.findViewById(R.id.textn_r);
        final TextView textn_g = _colPickView.findViewById(R.id.textn_g);
        final TextView textn_b = _colPickView.findViewById(R.id.textn_b);
        final TextView text_plhwarna = _colPickView.findViewById(R.id.text_plhwarna);
        final TextView text_axhyre = _colPickView.findViewById(R.id.text_axhyre);
        final LinearLayout bg_result = _colPickView.findViewById(R.id.bg_result);
        text_hastag.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        edit_hex.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        text_a.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesansbold.ttf"), 0);
        text_r.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesansbold.ttf"), 0);
        text_g.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesansbold.ttf"), 0);
        text_b.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesansbold.ttf"), 0);
        textn_a.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesansbold.ttf"), 0);
        textn_r.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesansbold.ttf"), 0);
        textn_g.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesansbold.ttf"), 0);
        textn_b.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesansbold.ttf"), 0);
        text_plhwarna.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        text_axhyre.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        try {
            String HEX = "";
            if (_numberlol == 1) {
                HEX = textview1.getText().toString().toLowerCase();
            }
            if (_numberlol == 2) {
                HEX = textview2.getText().toString().toLowerCase();
            }
            if (_numberlol == 3) {
                HEX = textview3.getText().toString().toLowerCase();
            }
            if (_numberlol == 4) {
                HEX = textview4.getText().toString().toLowerCase();
            }
            if (_numberlol == 5) {
                HEX = textview5.getText().toString().toLowerCase();
            }
            if (_numberlol == 6) {
                HEX = textview6.getText().toString().toLowerCase();
            }
            if (_numberlol == 7) {
                HEX = textview7.getText().toString().toLowerCase();
            }
            if (_numberlol == 8) {
                HEX = textview8.getText().toString().toLowerCase();
            }
            if (_numberlol == 9) {
                HEX = textview9.getText().toString().toLowerCase();
            }
            if (_numberlol == 10) {
                HEX = textview10.getText().toString().toLowerCase();
            }
            if (_numberlol == 11) {
                HEX = textview11.getText().toString().toLowerCase();
            }
            if (_numberlol == 12) {
                HEX = textview12.getText().toString().toLowerCase();
            }
            if (_numberlol == 13) {
                HEX = textview13.getText().toString().toLowerCase();
            }
            if (_numberlol == 14) {
                HEX = textview14.getText().toString().toLowerCase();
            }
            if (_numberlol == 15) {
                HEX = textview15.getText().toString().toLowerCase();
            }
            if (_numberlol == 16) {
                HEX = textview16.getText().toString().toLowerCase();
            }
            if (_numberlol == 17) {
                HEX = textview17.getText().toString().toLowerCase();
            }
            int COLORINT = Color.parseColor(HEX);
            edit_hex.setText(HEX.replace("#", ""));
        } catch (Exception _e) {
            edit_hex.setText("000000");
        }
        {
            int intColor = Color.parseColor("#" + edit_hex.getText().toString());
            bg_result.setBackgroundColor(intColor);
            int a_s = Color.alpha(intColor);
            int r_s = Color.red(intColor);
            int g_s = Color.green(intColor);
            int b_s = Color.blue(intColor);
            seek_a.setProgress(a_s);
            seek_r.setProgress(r_s);
            seek_g.setProgress(g_s);
            seek_b.setProgress(b_s);
            textn_a.setText(String.valueOf((long) a_s));
            textn_r.setText(String.valueOf((long) r_s));
            textn_g.setText(String.valueOf((long) g_s));
            textn_b.setText(String.valueOf((long) b_s));
        }

        seek_a.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar _param1, int _progressValue, boolean _param3) {
                textn_a.setText(String.valueOf((long) _progressValue));
                edit_hex.setText(getHexFromARGB(seek_a.getProgress(), seek_r.getProgress(), seek_g.getProgress(), seek_b.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar _param1) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar _param1) {
            }
        });
        seek_r.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar _param1, int _progressValue, boolean _param3) {
                textn_r.setText(String.valueOf((long) _progressValue));
                edit_hex.setText(getHexFromARGB(seek_a.getProgress(), seek_r.getProgress(), seek_g.getProgress(), seek_b.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar _param1) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar _param1) {
            }
        });
        seek_g.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar _param1, int _progressValue, boolean _param3) {
                textn_g.setText(String.valueOf((long) _progressValue));
                edit_hex.setText(getHexFromARGB(seek_a.getProgress(), seek_r.getProgress(), seek_g.getProgress(), seek_b.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar _param1) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar _param1) {
            }
        });
        seek_b.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar _param1, int _progressValue, boolean _param3) {
                textn_b.setText(String.valueOf((long) _progressValue));
                edit_hex.setText(getHexFromARGB(seek_a.getProgress(), seek_r.getProgress(), seek_g.getProgress(), seek_b.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar _param1) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar _param1) {
            }
        });
        edit_hex.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
                final String _charSeq = _param1.toString();
                try {
                    int intColor = Color.parseColor("#" + _charSeq);
                    bg_result.setBackgroundColor(intColor);
                    int a_s = Color.alpha(intColor);
                    int r_s = Color.red(intColor);
                    int g_s = Color.green(intColor);
                    int b_s = Color.blue(intColor);
                    seek_a.setProgress(a_s);
                    seek_r.setProgress(r_s);
                    seek_g.setProgress(g_s);
                    seek_b.setProgress(b_s);
                } catch (Exception _e) {
                }
            }

            @Override
            public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
            }

            @Override
            public void afterTextChanged(Editable _param1) {
            }
        });
        colorPicker.setView(_colPickView);
        colorPicker.setCancelable(false);
        colorPicker.setPositiveButton("Pick", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface _dialog, int _which) {
                _abandonFocus();
                try {
                    int ColorINT = Color.parseColor("#" + edit_hex.getText().toString());
                } catch (Exception _e) {
                    edit_hex.setText(getHexFromARGB(seek_a.getProgress(), seek_r.getProgress(), seek_g.getProgress(), seek_b.getProgress()));
                }
                String hexCOLOR = "#" + edit_hex.getText().toString().toUpperCase();
                if (_numberlol == 1) {
                    textview1.setText(hexCOLOR);
                }
                if (_numberlol == 2) {
                    textview2.setText(hexCOLOR);
                }
                if (_numberlol == 3) {
                    textview3.setText(hexCOLOR);
                }
                if (_numberlol == 4) {
                    textview4.setText(hexCOLOR);
                }
                if (_numberlol == 5) {
                    textview5.setText(hexCOLOR);
                }
                if (_numberlol == 6) {
                    textview6.setText(hexCOLOR);
                }
                if (_numberlol == 7) {
                    textview7.setText(hexCOLOR);
                }
                if (_numberlol == 8) {
                    textview8.setText(hexCOLOR);
                }
                if (_numberlol == 9) {
                    textview9.setText(hexCOLOR);
                }
                if (_numberlol == 10) {
                    textview10.setText(hexCOLOR);
                }
                if (_numberlol == 11) {
                    textview11.setText(hexCOLOR);
                }
                if (_numberlol == 12) {
                    textview12.setText(hexCOLOR);
                }
                if (_numberlol == 13) {
                    textview13.setText(hexCOLOR);
                }
                if (_numberlol == 14) {
                    textview14.setText(hexCOLOR);
                }
                if (_numberlol == 15) {
                    textview15.setText(hexCOLOR);
                }
                if (_numberlol == 16) {
                    textview16.setText(hexCOLOR);
                }
                if (_numberlol == 17) {
                    textview17.setText(hexCOLOR);
                }
                _updatePreview();
            }
        });
        colorPicker.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface _dialog, int _which) {
                _abandonFocus();
            }
        });
        colorPicker.create().show();
    }

    private void _gridSelected(final double _num) {
        if (gridMap.get((int) _num).containsKey("os-thm-version")) {
            if (gridMap.get((int) _num).get("os-thm-version").toString().equals("2")) {
                themes_map.clear();
                themes_map = new Gson().fromJson(gridMap.get((int) _num).get("themesjson").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                if (themes_map.get(0).containsKey("version")) {
                    if (themes_map.get(0).get("version").toString().equals("2")) {
                        if (!gridMap.get((int) _num).get("themesjson").toString().equals(data.getString("themesjson", ""))) {
                            data.edit().putString("themesjson", gridMap.get((int) _num).get("themesjson").toString()).commit();
                            SketchwareUtil.showMessage(getApplicationContext(), "Theme Applied! ");
                        }
                    } else {
                        _updateTheme(_num);
                    }
                } else {
                    _updateTheme(_num);
                }
            } else {
                _updateTheme(_num);
            }
        } else {
            _updateTheme(_num);
        }
    }

    private void _resetPanel() {
        sttsbrspin.setSelection(1);
        sdwchkbx.setChecked(true);
        textview1.setText("#2196F3");
        textview2.setText("#1769AA");
        textview3.setText("#FFFFFF");
        textview4.setText("#F50057");
        textview5.setText("#40000000");
        textview6.setText("#A8A8A8");
        textview7.setText("#FFFFFF");
        textview8.setText("#2196F3");
        textview9.setText("#FFFFFF");
        textview10.setText("#000000");
        textview11.setText("#FFFFFF");
        textview12.setText("#FFFFFF");
        textview13.setText("#FFFFFF");
        textview14.setText("#000000");
        textview15.setText("#000000");
        textview16.setText("#000000");
        textview17.setText("#000000");
        _updatePreview();
    }

    private void _shadow(final View _v, final double _n) {
        _v.setElevation((float) _n);
    }

    private void _gridLongClick(final double _pos) {
        dialog3.setAdapter(new ArrayAdapter(ThemesActivity.this, android.R.layout.simple_list_item_1, dialog_list), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dia, int _pos_dialog) {
                if (_pos_dialog == 0) {
                    if (gridMap.get((int) _pos).get("themesname").toString().contains("$")) {
                        SketchwareUtil.showMessage(getApplicationContext(), "Can't edit or delete default theme! ");
                    } else {
                        if (gridMap.get((int) _pos).get("themesjson").toString().equals(data.getString("themesjson", ""))) {
                            SketchwareUtil.showMessage(getApplicationContext(), "Theme is used! ");
                        } else {
                            gridMap.remove((int) (_pos));
                            indexDataList.remove((int) (_pos));
                            data.edit().putString("griddata", new Gson().toJson(gridMap)).commit();
                            ((BaseAdapter) grid.getAdapter()).notifyDataSetChanged();
                            SketchwareUtil.showMessage(getApplicationContext(), "Deleted! ");
                        }
                    }
                } else {
                    if (_pos_dialog == 1) {
                        if (gridMap.get((int) _pos).get("themesname").toString().contains("$")) {
                            SketchwareUtil.showMessage(getApplicationContext(), "Can't edit or delete default theme! ");
                        } else {
                            if (gridMap.get((int) _pos).get("themesjson").toString().equals(data.getString("themesjson", ""))) {
                                SketchwareUtil.showMessage(getApplicationContext(), "Theme is used! ");
                            } else {
                                _edit(_pos);
                            }
                        }
                    } else {
                        if (_pos_dialog == 2) {
                            if (gridMap.get((int) _pos).containsKey("os-thm-version")) {
                                if (gridMap.get((int) _pos).get("os-thm-version").toString().equals("2")) {
                                    String _tmpStr = "";
                                    if (gridMap.get((int) _pos).get("themesname").toString().equals("$default_1$")) {
                                        _tmpStr = "Vanilla";
                                    } else {
                                        if (gridMap.get((int) _pos).get("themesname").toString().equals("$default_2$")) {
                                            _tmpStr = "Dark Mode";
                                        } else {
                                            _tmpStr = gridMap.get((int) _pos).get("themesname").toString();
                                        }
                                    }
                                    dialog4.setTitle("Theme metadata");
                                    dialog4.setMessage("Theme name : ".concat(_tmpStr.concat("\nTheme author : ".concat(gridMap.get((int) _pos).get("themesauthor").toString().concat("\nTheme info : ".concat(gridMap.get((int) _pos).get("themesinfo").toString().concat("\nos-thm-version : ".concat(gridMap.get((int) _pos).get("os-thm-version").toString()))))))));
                                    dialog4.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface _dialog, int _which) {

                                        }
                                    });
                                    dialog4.create().show();
                                } else {
                                    SketchwareUtil.showMessage(getApplicationContext(), "This theme version isn't compatible with the current theme engine!");
                                }
                            } else {
                                SketchwareUtil.showMessage(getApplicationContext(), "This theme version isn't compatible with the current theme engine!");
                            }
                        } else {
                            if (_pos_dialog == 3) {
                                if (gridMap.get((int) _pos).containsKey("os-thm-version")) {
                                    if (gridMap.get((int) _pos).get("os-thm-version").toString().equals("2")) {
                                        themes_map.clear();
                                        themes_map = new Gson().fromJson(gridMap.get((int) _pos).get("themesjson").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                                        }.getType());
                                        if (themes_map.get(0).containsKey("version")) {
                                            if (themes_map.get(0).get("version").toString().equals("2")) {
                                                tmpMAPC.clear();
                                                tmpMAPM = gridMap.get((int) _pos);
                                                tmpMAPC.add(tmpMAPM);
                                                FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/os-thm/".concat(gridMap.get((int) _pos).get("themesname").toString().replace("/", "_").concat(".os-thm"))), new Gson().toJson(tmpMAPC));
                                                SketchwareUtil.showMessage(getApplicationContext(), "Exported in ".concat(FileUtil.getExternalStorageDir().concat("/os-thm/".concat(gridMap.get((int) _pos).get("themesname").toString().replace("/", "_").concat(".os-thm")))));
                                            } else {
                                                SketchwareUtil.showMessage(getApplicationContext(), "This theme version isn't compatible with the current theme engine!");
                                            }
                                        } else {
                                            SketchwareUtil.showMessage(getApplicationContext(), "This theme version isn't compatible with the current theme engine!");
                                        }
                                    } else {
                                        SketchwareUtil.showMessage(getApplicationContext(), "This theme version isn't compatible with the current theme engine!");
                                    }
                                } else {
                                    SketchwareUtil.showMessage(getApplicationContext(), "This theme version isn't compatible with the current theme engine!");
                                }
                            } else {

                            }
                        }
                    }
                }
            }
        });
        dialog3.create().show();
    }

    private void _shape(final double _tl, final double _tr, final double _bl, final double _br, final String _BGcolor, final String _Scolor, final double _Swidth, final View _view) {
        Double tlr = _tl;
        Double trr = _tr;
        Double blr = _bl;
        Double brr = _br;
        Double sw = _Swidth;
        android.graphics.drawable.GradientDrawable s = new android.graphics.drawable.GradientDrawable();
        s.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
        s.setCornerRadii(new float[]{tlr.floatValue(), tlr.floatValue(), trr.floatValue(), trr.floatValue(), blr.floatValue(), blr.floatValue(), brr.floatValue(), brr.floatValue()});
        s.setColor(Color.parseColor(_BGcolor));
        s.setStroke(sw.intValue(), Color.parseColor(_Scolor));
        _view.setBackground(s);
    }

    private void _updatePreview() {
        if (sttsbrspin.getSelectedItemPosition() == 1) {
            imageview3.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
            imageview4.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
            imageview5.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
            imageview6.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
            textview21.setTextColor(0xFFFFFFFF);
        } else {
            imageview3.setColorFilter(0xFF000000, PorterDuff.Mode.MULTIPLY);
            imageview4.setColorFilter(0xFF000000, PorterDuff.Mode.MULTIPLY);
            imageview5.setColorFilter(0xFF000000, PorterDuff.Mode.MULTIPLY);
            imageview6.setColorFilter(0xFF000000, PorterDuff.Mode.MULTIPLY);
            textview21.setTextColor(0xFF000000);
        }
        _shape(SketchwareUtil.getDip(getApplicationContext(), 25), SketchwareUtil.getDip(getApplicationContext(), 25), SketchwareUtil.getDip(getApplicationContext(), 25), SketchwareUtil.getDip(getApplicationContext(), 25), textview4.getText().toString(), "#FFFFFF", 0, imageview2);
        imageview1.setColorFilter(Color.parseColor(textview7.getText().toString()), PorterDuff.Mode.MULTIPLY);
        imageview2.setColorFilter(Color.parseColor(textview11.getText().toString()), PorterDuff.Mode.MULTIPLY);
        textview18.setTextColor(Color.parseColor(textview9.getText().toString()));
        textview19.setTextColor(Color.parseColor(textview9.getText().toString()));
        textview20.setTextColor(Color.parseColor(textview10.getText().toString()));
        linear18.setBackgroundColor(Color.parseColor(textview2.getText().toString()));
        linear16.setBackgroundColor(Color.parseColor(textview1.getText().toString()));
        linear15.setBackgroundColor(Color.parseColor(textview3.getText().toString()));
        if (sdwchkbx.isChecked()) {
            _shadow(linear16, 10);
            _shadow(imageview2, 10);
        } else {
            _shadow(linear16, 0);
            _shadow(imageview2, 0);
        }
    }

    private void _edit(final double _position) {
        if (gridMap.get((int) _position).containsKey("os-thm-version")) {
            if (gridMap.get((int) _position).get("os-thm-version").toString().equals("2")) {
                themes_map.clear();
                themes_map = new Gson().fromJson(gridMap.get((int) _position).get("themesjson").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                if (themes_map.get(0).containsKey("version")) {
                    if (themes_map.get(0).get("version").toString().equals("2")) {
                        isPanel = true;
                        _fab.hide();
                        linear_grid.setVisibility(View.GONE);
                        vscroll1.setVisibility(View.VISIBLE);
                        isEditing = true;
                        posEdit = _position;
                        textview1.setText(themes_map.get(0).get("colorPrimary").toString());
                        textview9.setText(themes_map.get(0).get("colorPrimaryText").toString());
                        textview2.setText(themes_map.get(0).get("colorPrimaryDark").toString());
                        sttsbrspin.setSelection((int) (Double.parseDouble(themes_map.get(0).get("statusbarIcon").toString())));
                        textview3.setText(themes_map.get(0).get("colorBackground").toString());
                        textview10.setText(themes_map.get(0).get("colorBackgroundText").toString());
                        textview4.setText(themes_map.get(0).get("colorButton").toString());
                        textview11.setText(themes_map.get(0).get("colorButtonText").toString());
                        if (themes_map.get(0).get("shadow").toString().equals("1")) {
                            sdwchkbx.setChecked(true);
                        } else {
                            sdwchkbx.setChecked(false);
                        }
                        textview5.setText(themes_map.get(0).get("colorRipple").toString());
                        textview6.setText(themes_map.get(0).get("colorHint").toString());
                        textview7.setText(themes_map.get(0).get("colorPrimaryImage").toString());
                        textview8.setText(themes_map.get(0).get("colorBackgroundImage").toString());
                        textview12.setText(themes_map.get(0).get("colorPrimaryCard").toString());
                        textview13.setText(themes_map.get(0).get("colorBackgroundCard").toString());
                        textview14.setText(themes_map.get(0).get("colorPrimaryCardText").toString());
                        textview15.setText(themes_map.get(0).get("colorBackgroundCardText").toString());
                        textview16.setText(themes_map.get(0).get("colorPrimaryCardImage").toString());
                        textview17.setText(themes_map.get(0).get("colorBackgroundCardImage").toString());
                        _updatePreview();
                    } else {
                        SketchwareUtil.showMessage(getApplicationContext(), "This theme version isn't compatible with the current theme engine!");
                    }
                } else {
                    SketchwareUtil.showMessage(getApplicationContext(), "This theme version isn't compatible with the current theme engine!");
                }
            } else {
                SketchwareUtil.showMessage(getApplicationContext(), "This theme version isn't compatible with the current theme engine!");
            }
        } else {
            SketchwareUtil.showMessage(getApplicationContext(), "This theme version isn't compatible with the current theme engine!");
        }
    }

    private void _updateTheme(final double _position) {
        _resetPanel();
        themes_map.clear();
        themes_map = new Gson().fromJson(gridMap.get((int) _position).get("themesjson").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {
        }.getType());
        isPanel = true;
        _fab.hide();
        linear_grid.setVisibility(View.GONE);
        vscroll1.setVisibility(View.VISIBLE);
        isEditing = true;
        posEdit = _position;
        if (themes_map.get(0).containsKey("colorPrimary")) {
            textview1.setText(themes_map.get(0).get("colorPrimary").toString());
        }
        if (themes_map.get(0).containsKey("colorPrimaryDark")) {
            textview2.setText(themes_map.get(0).get("colorPrimaryDark").toString());
        }
        if (themes_map.get(0).containsKey("statusbarIcon")) {
            sttsbrspin.setSelection((int) (Double.parseDouble(themes_map.get(0).get("statusbarIcon").toString())));
        }
        if (themes_map.get(0).containsKey("colorBackground")) {
            textview3.setText(themes_map.get(0).get("colorBackground").toString());
        }
        if (themes_map.get(0).containsKey("colorButton")) {
            textview4.setText(themes_map.get(0).get("colorButton").toString());
        }
        if (themes_map.get(0).containsKey("shadow")) {
            if (themes_map.get(0).get("shadow").toString().equals("1")) {
                sdwchkbx.setChecked(true);
            } else {
                sdwchkbx.setChecked(false);
            }
        }
        if (themes_map.get(0).containsKey("colorRipple")) {
            textview5.setText(themes_map.get(0).get("colorRipple").toString());
        }
        if (themes_map.get(0).containsKey("colorHint")) {
            textview6.setText(themes_map.get(0).get("colorHint").toString());
        }
        if (themes_map.get(0).containsKey("colorPrimaryImage")) {
            textview7.setText(themes_map.get(0).get("colorPrimaryImage").toString());
        }
        if (themes_map.get(0).containsKey("colorBackgroundImage")) {
            textview8.setText(themes_map.get(0).get("colorBackgroundImage").toString());
        }
        if (themes_map.get(0).containsKey("colorPrimaryCard")) {
            textview12.setText(themes_map.get(0).get("colorPrimaryCard").toString());
        }
        if (themes_map.get(0).containsKey("colorBackgroundCard")) {
            textview13.setText(themes_map.get(0).get("colorBackgroundCard").toString());
        }
        if (themes_map.get(0).containsKey("colorPrimaryCardText")) {
            textview14.setText(themes_map.get(0).get("colorPrimaryCardText").toString());
        }
        if (themes_map.get(0).containsKey("colorBackgroundCardText")) {
            textview15.setText(themes_map.get(0).get("colorBackgroundCardText").toString());
        }
        if (themes_map.get(0).containsKey("colorPrimaryCardImage")) {
            textview16.setText(themes_map.get(0).get("colorPrimaryCardImage").toString());
        }
        if (themes_map.get(0).containsKey("colorBackgroundCardImage")) {
            textview17.setText(themes_map.get(0).get("colorBackgroundCardImage").toString());
        }
        if (themes_map.get(0).containsKey("version")) {
            if (themes_map.get(0).containsKey("colorPrimaryText")) {
                textview9.setText(themes_map.get(0).get("colorPrimaryText").toString());
            }
            if (themes_map.get(0).containsKey("colorBackgroundText")) {
                textview10.setText(themes_map.get(0).get("colorBackgroundText").toString());
            }
            if (themes_map.get(0).containsKey("colorButtonText")) {
                textview11.setText(themes_map.get(0).get("colorButtonText").toString());
            }
        } else {
            if (themes_map.get(0).containsKey("colorPrimaryText")) {
                if (themes_map.get(0).get("colorPrimaryText").toString().equals("1")) {
                    textview9.setText("#FFFFFF");
                } else {
                    textview9.setText("#000000");
                }
            }
            if (themes_map.get(0).containsKey("colorBackgroundText")) {
                if (themes_map.get(0).get("colorBackgroundText").toString().equals("1")) {
                    textview10.setText("#FFFFFF");
                } else {
                    textview10.setText("#000000");
                }
            }
            if (themes_map.get(0).containsKey("colorButtonText")) {
                if (themes_map.get(0).get("colorButtonText").toString().equals("1")) {
                    textview11.setText("#FFFFFF");
                } else {
                    textview11.setText("#000000");
                }
            }
        }
        _updatePreview();
    }

    private void _customNav(final String _color) {
        //Code From StackOverFlow.com And Converted By TeamWorks DEV
        if (Build.VERSION.SDK_INT >= 21) {
            Window w = this.getWindow();
            w.setNavigationBarColor(Color.parseColor(_color));
        }
    }

    private void _abandonFocus() {
        View _tmpView = this.getCurrentFocus();
        if (_tmpView != null) {
            android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(_tmpView.getWindowToken(), 0);
        }
    }

    private void _setFont() {
        colprimtext.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        colprimtxttext.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        colprimdarktext.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        sttsbrtext.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        colbgtext.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        colbgtxttext.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        colbttntext.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        colbttntxttext.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        sdwtext.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        colriptext.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        colhnttext.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        colprimimgtxt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        colbgimgtxt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        colprimcrd.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        colbgcrd.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        colprimcrdtxt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        colbgcrdtxt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        colprimcrdimg.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        colbgcrdimg.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        sdwchkbx.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        button1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        button2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        button3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        button4.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        button5.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        button6.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        button7.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        button8.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        button9.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        button10.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        button11.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        button12.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        button13.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        button14.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        button15.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        button16.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        button17.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview4.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview5.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview6.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview7.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview8.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview9.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview10.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview11.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview12.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview13.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview14.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview15.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview16.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview17.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
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

    public class Gridview1Adapter extends BaseAdapter {
        ArrayList<HashMap<String, Object>> _data;

        public Gridview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
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
                _v = _inflater.inflate(R.layout.theme_preview, null);
            }

            final ImageView imageview_plus = _v.findViewById(R.id.imageview_plus);
            final ImageView imageview_back = _v.findViewById(R.id.imageview_back);
            final TextView textview_title = _v.findViewById(R.id.textview_title);
            final TextView textview_name = _v.findViewById(R.id.textview_name);
            final LinearLayout linear_title = _v.findViewById(R.id.linear_title);
            final LinearLayout linear_base = _v.findViewById(R.id.linear_base);

            textview_title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);

            textview_name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);

            if (gridMap.get(_position).get("themesname").toString().equals("$default_1$")) {
                textview_name.setText("Vanilla");
            } else {
                if (gridMap.get(_position).get("themesname").toString().equals("$default_2$")) {
                    textview_name.setText("Dark Mode");
                } else {
                    textview_name.setText(gridMap.get(_position).get("themesname").toString());
                }
            }
            gridtmp = new Gson().fromJson(gridMap.get(_position).get("themesjson").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {
            }.getType());
            if (!gridtmp.get(0).containsKey("version")) {
                android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
                gd.setColor(Color.parseColor("#F50057"));
                gd.setCornerRadius(50);
                imageview_plus.setBackground(gd);
                imageview_plus.setElevation(10);
                _shape(8, 8, 0, 0, "#2196F3", "#FFFFFF", 0, linear_title);
                _shape(8, 8, 8, 8, "#FAFAFA", "#FFFFFF", 0, linear_base);
                linear_title.setElevation(10);
                linear_base.setElevation(10);
            } else {
                if (!gridtmp.get(0).get("version").toString().equals("2")) {
                    android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
                    gd.setColor(Color.parseColor("#F50057"));
                    gd.setCornerRadius(50);
                    imageview_plus.setBackground(gd);
                    imageview_plus.setElevation(10);
                    _shape(8, 8, 0, 0, "#2196F3", "#FFFFFF", 0, linear_title);
                    _shape(8, 8, 8, 8, "#FAFAFA", "#FFFFFF", 0, linear_base);
                    linear_title.setElevation(10);
                    linear_base.setElevation(10);
                } else {
                    android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
                    gd.setColor(Color.parseColor(gridtmp.get(0).get("colorButton").toString()));
                    gd.setCornerRadius(50);
                    imageview_plus.setBackground(gd);
                    imageview_plus.setElevation(10);
                    linear_base.setElevation(10);
                    if (gridtmp.get(0).get("shadow").toString().equals("1"))
                        linear_title.setElevation(10);
                    else
                        linear_title.setElevation(0);
                    imageview_back.setColorFilter(Color.parseColor(gridtmp.get(0).get("colorPrimaryImage").toString()), PorterDuff.Mode.MULTIPLY);
                    imageview_plus.setColorFilter(Color.parseColor(gridtmp.get(0).get("colorButtonText").toString()), PorterDuff.Mode.MULTIPLY);
                    textview_title.setTextColor(Color.parseColor(gridtmp.get(0).get("colorPrimaryText").toString()));
                    textview_name.setTextColor(Color.parseColor(gridtmp.get(0).get("colorBackgroundText").toString()));
                    _shape(8, 8, 0, 0, gridtmp.get(0).get("colorPrimary").toString(), "#FFFFFF", 0, linear_title);
                    _shape(8, 8, 8, 8, gridtmp.get(0).get("colorBackground").toString(), "#FFFFFF", 0, linear_base);
                }
            }

            return _v;
        }
    }

}
