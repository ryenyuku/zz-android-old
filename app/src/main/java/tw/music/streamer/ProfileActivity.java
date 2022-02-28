package tw.music.streamer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ProfileActivity extends AppCompatActivity {

    private Timer _timer = new Timer();
    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();

    private String childKey = "";
    private HashMap<String, Object> map_var = new HashMap<>();
    private boolean isPictured = false;
    private boolean isAdmin = false;
    private boolean isBanned = false;
    private String user_uid = "";
    private String user_email = "";
    private String currentusertype = "";
    private String thisusertype = "";
    private boolean isThisUserAdmin = false;

    private ArrayList<HashMap<String, Object>> theme_map = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> rolesmap = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> roletmpmap = new ArrayList<>();

    private LinearLayout linear1;
    private ScrollView vscroll1;
    private LinearLayout linear3;
    private ImageView imageview_back;
    private TextView textview_back;
    private ImageView imageview_menu;
    private LinearLayout linear2;
    private LinearLayout linear4;
    private LinearLayout linear6;
    private TextView textview_bio;
    private LinearLayout linear8;
    private LinearLayout linear_name;
    private LinearLayout linear_chgpass;
    private LinearLayout linear_pic;
    private LinearLayout linear_chgbio;
    private LinearLayout linear_ban;
    private LinearLayout linear7;
    private LinearLayout linear_logout;
    private LinearLayout linear_usr;
    private LinearLayout linear5;
    private ImageView imageview_usr;
    private LinearLayout linear9;
    private TextView textview_roles;
    private TextView textview_usr;
    private ImageView imageview_roles;
    private ImageView imageview_name;
    private TextView textview_name;
    private ImageView imageview_chgpass;
    private TextView textview_chgpass;
    private ImageView imageview_pic;
    private TextView textview_pic;
    private ImageView imageview_chgbio;
    private TextView textview_chgbio;
    private ImageView imageview_ban;
    private TextView textview_ban;
    private ImageView imageview_logout;
    private TextView textview_logout;
    private TextView textview_loading;

    private FirebaseAuth Auth;
    private OnCompleteListener<AuthResult> _Auth_create_user_listener;
    private OnCompleteListener<AuthResult> _Auth_sign_in_listener;
    private OnCompleteListener<Void> _Auth_reset_password_listener;
    private AlertDialog.Builder d;
    private DatabaseReference profile = _firebase.getReference("profile/text");
    private ChildEventListener _profile_child_listener;
    private AlertDialog.Builder dialog;
    private DatabaseReference prof_img = _firebase.getReference("profile/image");
    private ChildEventListener _prof_img_child_listener;
    private Intent i = new Intent();
    private SharedPreferences data;
    private DatabaseReference prof_bans = _firebase.getReference("profile/bans");
    private ChildEventListener _prof_bans_child_listener;
    private DatabaseReference profile_admins = _firebase.getReference("profile/admins");
    private ChildEventListener _profile_admins_child_listener;
    private AlertDialog.Builder dban;
    private DatabaseReference prof_roles = _firebase.getReference("profile/roles");
    private ChildEventListener _prof_roles_child_listener;
    private DatabaseReference prof_rolesinfo = _firebase.getReference("profile/rolesinfo");
    private ChildEventListener _prof_rolesinfo_child_listener;
    private TimerTask delay;
    private PopupMenu _imageview_menu_popup;
    private Menu _imageview_menu_menu;
    private boolean _isListeningToAdmin = false;

    {
    }

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.profile);
        com.google.firebase.FirebaseApp.initializeApp(this);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {

        linear1 = findViewById(R.id.linear1);
        vscroll1 = findViewById(R.id.vscroll1);
        linear3 = findViewById(R.id.linear3);
        imageview_back = findViewById(R.id.imageview_back);
        textview_back = findViewById(R.id.textview_back);
        imageview_menu = findViewById(R.id.imageview_menu);
        linear2 = findViewById(R.id.linear2);
        linear4 = findViewById(R.id.linear4);
        linear6 = findViewById(R.id.linear6);
        textview_bio = findViewById(R.id.textview_bio);
        linear8 = findViewById(R.id.linear8);
        linear_name = findViewById(R.id.linear_name);
        linear_chgpass = findViewById(R.id.linear_chgpass);
        linear_pic = findViewById(R.id.linear_pic);
        linear_chgbio = findViewById(R.id.linear_chgbio);
        linear_ban = findViewById(R.id.linear_ban);
        linear7 = findViewById(R.id.linear7);
        linear_logout = findViewById(R.id.linear_logout);
        linear_usr = findViewById(R.id.linear_usr);
        linear5 = findViewById(R.id.linear5);
        imageview_usr = findViewById(R.id.imageview_usr);
        linear9 = findViewById(R.id.linear9);
        textview_roles = findViewById(R.id.textview_roles);
        textview_usr = findViewById(R.id.textview_usr);
        imageview_roles = findViewById(R.id.imageview_roles);
        imageview_name = findViewById(R.id.imageview_name);
        textview_name = findViewById(R.id.textview_name);
        imageview_chgpass = findViewById(R.id.imageview_chgpass);
        textview_chgpass = findViewById(R.id.textview_chgpass);
        imageview_pic = findViewById(R.id.imageview_pic);
        textview_pic = findViewById(R.id.textview_pic);
        imageview_chgbio = findViewById(R.id.imageview_chgbio);
        textview_chgbio = findViewById(R.id.textview_chgbio);
        imageview_ban = findViewById(R.id.imageview_ban);
        textview_ban = findViewById(R.id.textview_ban);
        imageview_logout = findViewById(R.id.imageview_logout);
        textview_logout = findViewById(R.id.textview_logout);
        textview_loading = findViewById(R.id.textview_loading);
        Auth = FirebaseAuth.getInstance();
        d = new AlertDialog.Builder(this);
        dialog = new AlertDialog.Builder(this);
        data = getSharedPreferences("teamdata", Activity.MODE_PRIVATE);
        dban = new AlertDialog.Builder(this);

        imageview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                finish();
            }
        });

        imageview_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _imageview_menu_popup.show();
            }
        });

        linear_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {

                LinearLayout mylayout = new LinearLayout(ProfileActivity.this);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                mylayout.setLayoutParams(params);
                mylayout.setOrientation(LinearLayout.VERTICAL);

                final EditText myedittext = new EditText(ProfileActivity.this);
                myedittext.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
                myedittext.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myedittext.setText(textview_usr.getText().toString());

                mylayout.addView(myedittext);
                dialog.setView(mylayout);
                myedittext.setHint("Enter your new Username");
                dialog.setTitle("Change username");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface _dialog, int _which) {
                        _abandonFocus();
                        if (myedittext.getText().toString().trim().equals("")) {
                            _customSnack("Fill those blanks!", 2);
                        } else {
                            map_var = new HashMap<>();
                            map_var.put("profile", FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            map_var.put("username", myedittext.getText().toString().trim());
                            map_var.put("bio", textview_bio.getText().toString());
                            profile.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map_var);
                            _customSnack("Username changed.", 1);
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

        linear_chgpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                d.setTitle("Change password?");
                d.setMessage("Are you sure to change your password?");
                d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface _dialog, int _which) {
                        Auth.sendPasswordResetEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()).addOnCompleteListener(_Auth_reset_password_listener);
                        _customSnack("Check your e-mail to change your password.", 0);
                    }
                });
                d.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface _dialog, int _which) {

                    }
                });
                d.create().show();
            }
        });

        linear_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (isPictured) {
                    i.setClass(getApplicationContext(), ChgpicActivity.class);
                    i.putExtra("uid", getIntent().getStringExtra("uid"));
                    startActivity(i);
                } else {
                    if (getIntent().getStringExtra("uid").equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        i.setClass(getApplicationContext(), FilepickerActivity.class);
                        i.putExtra("fileType", "image/*");
                        startActivity(i);
                    }
                }
            }
        });

        linear_chgbio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {

                LinearLayout mylayout = new LinearLayout(ProfileActivity.this);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                mylayout.setLayoutParams(params);
                mylayout.setOrientation(LinearLayout.VERTICAL);

                final EditText myedittext = new EditText(ProfileActivity.this);
                myedittext.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
                myedittext.setText(textview_bio.getText().toString());

                mylayout.addView(myedittext);
                dialog.setView(mylayout);
                myedittext.setHint("Hi, i'm using TeamMusic now!");
                dialog.setTitle("Change bio");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface _dialog, int _which) {
                        _abandonFocus();
                        if (myedittext.getText().toString().trim().equals("")) {
                            _customSnack("Fill those blanks!", 2);
                        } else {
                            map_var = new HashMap<>();
                            map_var.put("profile", FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            map_var.put("username", textview_usr.getText().toString());
                            map_var.put("bio", myedittext.getText().toString().trim());
                            profile.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map_var);
                            _customSnack("Bio changed.", 1);
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

        linear_ban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (isBanned) {
                    prof_bans.child(getIntent().getStringExtra("uid")).removeValue();
                } else {

                    LinearLayout mylayout = new LinearLayout(ProfileActivity.this);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    mylayout.setLayoutParams(params);
                    mylayout.setOrientation(LinearLayout.VERTICAL);

                    final EditText myedittext = new EditText(ProfileActivity.this);
                    myedittext.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));

                    mylayout.addView(myedittext);
                    dban.setView(mylayout);
                    myedittext.setHint("Reason");
                    dban.setTitle("Ban this user with following reason :");
                    dban.setPositiveButton("Ban user", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface _dialog, int _which) {
                            _abandonFocus();
                            if (myedittext.getText().toString().trim().equals("")) {
                                _customSnack("Fill those blanks!", 2);
                            } else {
                                map_var = new HashMap<>();
                                map_var.put("reason", myedittext.getText().toString().trim());
                                map_var.put("banned", true);
                                prof_bans.child(getIntent().getStringExtra("uid")).updateChildren(map_var);
                                _customSnack("User banned.", 1);
                            }
                        }
                    });
                    dban.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface _dialog, int _which) {
                            _abandonFocus();
                        }
                    });
                    dban.create().show();
                }
            }
        });

        linear_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                FirebaseAuth.getInstance().signOut();
                finishAffinity();
            }
        });

        linear_usr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (isPictured) {
                    i.setClass(getApplicationContext(), ChgpicActivity.class);
                    i.putExtra("uid", getIntent().getStringExtra("uid"));
                    startActivity(i);
                } else {
                    if (getIntent().getStringExtra("uid").equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        i.setClass(getApplicationContext(), FilepickerActivity.class);
                        i.putExtra("fileType", "image/*");
                        startActivity(i);
                    }
                }
            }
        });

        _profile_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals(getIntent().getStringExtra("uid"))) {
                    user_uid = _childKey;
                    user_email = _childValue.get("profile").toString();
                    textview_usr.setText(_childValue.get("username").toString());
                    if (_childValue.containsKey("bio")) {
                        textview_bio.setText(_childValue.get("bio").toString());
                    } else {
                        textview_bio.setText("Hi, i'm using ZryteZene now!");
                    }
                    linear3.setVisibility(View.GONE);
                    vscroll1.setVisibility(View.VISIBLE);
                    imageview_menu.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals(getIntent().getStringExtra("uid"))) {
                    textview_usr.setText(_childValue.get("username").toString());
                    if (_childValue.containsKey("bio")) {
                        textview_bio.setText(_childValue.get("bio").toString());
                    } else {
                        textview_bio.setText("Hi, i'm using ZryteZene now!");
                    }
                }
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
        profile.addChildEventListener(_profile_child_listener);

        _prof_img_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals(getIntent().getStringExtra("uid"))) {
                    imageview_usr.clearColorFilter();
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview_usr) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview_usr.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                    isPictured = true;
                    textview_pic.setText("View account picture");
                    if (!getIntent().getStringExtra("uid").equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        linear_pic.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals(getIntent().getStringExtra("uid"))) {
                    Glide.with(getApplicationContext()).asBitmap().load(_childValue.get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview_usr) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageview_usr.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
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
        prof_img.addChildEventListener(_prof_img_child_listener);

        _prof_bans_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals(getIntent().getStringExtra("uid"))) {
                    isBanned = true;
                    textview_ban.setText("Un-ban account");
                }
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
                if (_childKey.equals(getIntent().getStringExtra("uid"))) {
                    isBanned = false;
                    textview_ban.setText("Ban account");
                }
            }

            @Override
            public void onCancelled(DatabaseError _param1) {
                final int _errorCode = _param1.getCode();
                final String _errorMessage = _param1.getMessage();

            }
        };
        prof_bans.addChildEventListener(_prof_bans_child_listener);

        _profile_admins_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    isAdmin = true;
                }
                if (_childKey.equals(getIntent().getStringExtra("uid"))) {
                    isThisUserAdmin = true;
                    android.graphics.drawable.GradientDrawable _gD = new android.graphics.drawable.GradientDrawable();
                    _gD.setColor(Color.parseColor(theme_map.get(0).get("colorBackground").toString()));
                    _gD.setShape(android.graphics.drawable.GradientDrawable.OVAL);
                    _gD.setStroke(5, Color.parseColor(theme_map.get(0).get("colorButton").toString()));
                    imageview_usr.setBackground(_gD);
                }
                if (isAdmin && (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) || _childKey.equals(getIntent().getStringExtra("uid")))) {
                    linear_ban.setVisibility(View.GONE);
                    if (!getIntent().getStringExtra("uid").equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) && true) {
                        if (currentusertype.equals("0")) {
                            linear_ban.setVisibility(View.VISIBLE);
                        } else {
                            if (thisusertype.equals("")) {
                                if (currentusertype.equals("")) {
                                    if (!isThisUserAdmin) {
                                        linear_ban.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    linear_ban.setVisibility(View.VISIBLE);
                                }
                            } else {
                                if (!currentusertype.equals("")) {
                                    if (Double.parseDouble(thisusertype) > Double.parseDouble(currentusertype)) {
                                        linear_ban.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                    }
                }
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
        profile_admins.addChildEventListener(_profile_admins_child_listener);

        _prof_roles_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals(getIntent().getStringExtra("uid"))) {
                    thisusertype = _childValue.get("type").toString();
                    imageview_roles.setVisibility(View.VISIBLE);
                    prof_rolesinfo.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot _dataSnapshot) {
                            rolesmap = new ArrayList<>();
                            try {
                                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                                };
                                for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                    HashMap<String, Object> _map = _data.getValue(_ind);
                                    rolesmap.add(_map);
                                }
                            } catch (Exception _e) {
                                _e.printStackTrace();
                            }
                            if (rolesmap.size() > 0) {
                                double _tmpNum = 0;
                                boolean _tmpBoolean = false;
                                for (int _repeat25 = 0; _repeat25 < rolesmap.size(); _repeat25++) {
                                    if (rolesmap.get((int) _tmpNum).get("type").toString().equals(_childValue.get("type").toString())) {
                                        textview_roles.setText(rolesmap.get((int) _tmpNum).get("name").toString());
                                        Glide.with(getApplicationContext()).load(Uri.parse(rolesmap.get((int) _tmpNum).get("img").toString())).into(imageview_roles);
                                        textview_roles.setTextColor(Color.parseColor(rolesmap.get((int) _tmpNum).get("color").toString()));
                                        _tmpBoolean = true;
                                    } else {
                                        if ((_tmpNum == (rolesmap.size() - 1)) && !_tmpBoolean) {
                                            textview_roles.setText("Unknown role (".concat(_childValue.get("type").toString().concat(")")));
                                            imageview_roles.setImageResource(R.drawable.ic_unknown_roles);
                                        }
                                    }
                                    _tmpNum++;
                                }
                            } else {
                                textview_roles.setText("Unknown role (".concat(_childValue.get("type").toString().concat(")")));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError _databaseError) {
                        }
                    });
                }
                if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    currentusertype = _childValue.get("type").toString();
                }
                if (!_isListeningToAdmin) {
                    _isListeningToAdmin = true;
                    profile_admins.addChildEventListener(_profile_admins_child_listener);
                }
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
        prof_roles.addChildEventListener(_prof_roles_child_listener);

        _prof_rolesinfo_child_listener = new ChildEventListener() {
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
        prof_rolesinfo.addChildEventListener(_prof_rolesinfo_child_listener);

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
        _loadTheme();
        profile_admins.removeEventListener(_profile_admins_child_listener);
        if (!getIntent().getStringExtra("uid").equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            linear_name.setVisibility(View.GONE);
            linear_pic.setVisibility(View.GONE);
            linear_chgpass.setVisibility(View.GONE);
            linear_chgbio.setVisibility(View.GONE);
            linear7.setVisibility(View.GONE);
            linear_logout.setVisibility(View.GONE);
        }
        vscroll1.setVisibility(View.GONE);
        linear_ban.setVisibility(View.GONE);
        imageview_roles.setVisibility(View.GONE);
        textview_back.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview_usr.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview_bio.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview_name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview_pic.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview_chgpass.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview_logout.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview_loading.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview_roles.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview_chgbio.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview_ban.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview_roles.setText("Member");
        imageview_menu.setVisibility(View.GONE);
        _Linkify(textview_bio, "#2196F3");
        _imageview_menu_popup = new PopupMenu(ProfileActivity.this, imageview_menu);

        _imageview_menu_menu = _imageview_menu_popup.getMenu();
        _imageview_menu_menu.add("Copy uid");
        _imageview_menu_menu.add("Copy email");


        _imageview_menu_popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getTitle().toString()) {
                    case "Copy uid":

                        ((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", user_uid));
                        _customSnack("Copied!", 0);
                        return true;

                    case "Copy email":

                        ((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", user_email));
                        _customSnack("Copied!", 0);
                        return true;

                    default:
                        return false;
                }
            }
        });

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
        if (!data.getString("tmpPath", "").equals("")) {
            i.setClass(getApplicationContext(), CropActivity.class);
            i.putExtra("path", data.getString("tmpPath", ""));
            startActivity(i);
            data.edit().remove("tmpPath").commit();
            finish();
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


    private void _shadow(final View _v, final double _n) {
        _v.setElevation((float) _n);
    }


    private void _rippleEffect(final View _view, final String _color) {
        _view.setBackground(Drawables.getSelectableDrawableFor(Color.parseColor(_color)));
        _view.setClickable(true);
    }


    private void _rippleLib() {
    }

    public void drawableclass() {
    }

    private void _loadTheme() {
        theme_map = new Gson().fromJson(data.getString("themesjson", ""), new TypeToken<ArrayList<HashMap<String, Object>>>() {
        }.getType());
        _customNav(theme_map.get(0).get("colorBackground").toString());
        _circleRipple(theme_map.get(0).get("colorRipple").toString(), imageview_back);
        _circleRipple(theme_map.get(0).get("colorRipple").toString(), imageview_menu);
        _circleRipple(theme_map.get(0).get("colorRipple").toString(), linear_usr);
        _rippleEffect(linear_name, theme_map.get(0).get("colorRipple").toString());
        _rippleEffect(linear_pic, theme_map.get(0).get("colorRipple").toString());
        _rippleEffect(linear_chgpass, theme_map.get(0).get("colorRipple").toString());
        _rippleEffect(linear_chgbio, theme_map.get(0).get("colorRipple").toString());
        _rippleEffect(linear_ban, theme_map.get(0).get("colorRipple").toString());
        _rippleEffect(linear_logout, theme_map.get(0).get("colorRipple").toString());
        if (theme_map.get(0).get("shadow").toString().equals("1")) {
            _shadow(linear1, 10);
            if (isDark(Color.parseColor(theme_map.get(0).get("colorBackground").toString()))) {
                linear6.setBackgroundColor(0xFF808080);
                linear7.setBackgroundColor(0xFF808080);
                linear8.setBackgroundColor(0xFF808080);
            } else {
                linear6.setBackgroundColor(0xFFBDBDBD);
                linear7.setBackgroundColor(0xFFBDBDBD);
                linear8.setBackgroundColor(0xFFBDBDBD);
            }
        } else {
            linear6.setBackgroundColor(Color.TRANSPARENT);
            linear7.setBackgroundColor(Color.TRANSPARENT);
            linear8.setBackgroundColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window w = this.getWindow();
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.parseColor(theme_map.get(0).get("colorPrimaryDark").toString()));
        }
        linear1.setBackgroundColor(Color.parseColor(theme_map.get(0).get("colorPrimary").toString()));
        vscroll1.setBackgroundColor(Color.parseColor(theme_map.get(0).get("colorBackground").toString()));
        linear3.setBackgroundColor(Color.parseColor(theme_map.get(0).get("colorBackground").toString()));
        textview_back.setTextColor(Color.parseColor(theme_map.get(0).get("colorPrimaryText").toString()));
        textview_usr.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        textview_roles.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        textview_bio.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        textview_name.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        textview_pic.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        textview_chgbio.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        textview_ban.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        textview_chgpass.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        textview_logout.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        textview_loading.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        if (theme_map.get(0).get("statusbarIcon").toString().equals("0")) {
            _BlackStatusBarIcons();
        }
        imageview_name.setColorFilter(Color.parseColor(theme_map.get(0).get("colorBackgroundImage").toString()), PorterDuff.Mode.MULTIPLY);
        imageview_pic.setColorFilter(Color.parseColor(theme_map.get(0).get("colorBackgroundImage").toString()), PorterDuff.Mode.MULTIPLY);
        imageview_chgpass.setColorFilter(Color.parseColor(theme_map.get(0).get("colorBackgroundImage").toString()), PorterDuff.Mode.MULTIPLY);
        imageview_chgbio.setColorFilter(Color.parseColor(theme_map.get(0).get("colorBackgroundImage").toString()), PorterDuff.Mode.MULTIPLY);
        imageview_ban.setColorFilter(Color.parseColor(theme_map.get(0).get("colorBackgroundImage").toString()), PorterDuff.Mode.MULTIPLY);
        imageview_logout.setColorFilter(Color.parseColor(theme_map.get(0).get("colorBackgroundImage").toString()), PorterDuff.Mode.MULTIPLY);
        imageview_usr.setColorFilter(Color.parseColor(theme_map.get(0).get("colorBackgroundImage").toString()), PorterDuff.Mode.MULTIPLY);
        imageview_back.setColorFilter(Color.parseColor(theme_map.get(0).get("colorPrimaryImage").toString()), PorterDuff.Mode.MULTIPLY);
        imageview_menu.setColorFilter(Color.parseColor(theme_map.get(0).get("colorPrimaryImage").toString()), PorterDuff.Mode.MULTIPLY);
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

    private void _Linkify(final TextView _text, final String _color) {
        _text.setClickable(true);

        android.text.util.Linkify.addLinks(_text, android.text.util.Linkify.ALL);

        _text.setLinkTextColor(Color.parseColor("#" + _color.replace("#", "")));

        _text.setLinksClickable(true);
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

}
