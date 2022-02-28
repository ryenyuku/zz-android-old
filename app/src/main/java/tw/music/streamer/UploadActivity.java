package tw.music.streamer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class UploadActivity extends AppCompatActivity {

    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
    private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();

    private String path = "";
    private String name = "";
    private HashMap<String, Object> upload_map = new HashMap<>();
    private boolean isExitDisabled = false;
    private String img_path = "";
    private String tmpurl = "";

    private ArrayList<HashMap<String, Object>> theme_map = new ArrayList<>();
    private ArrayList<String> indexName = new ArrayList<>();

    private LinearLayout linear1;
    private LinearLayout linear2;
    private ImageView image_back;
    private TextView textview_title;
    private LinearLayout linear7;
    private LinearLayout linear5;
    private LinearLayout linear4;
    private EditText edittext_name;
    private Button button_upload;
    private TextView textview_path;
    private ImageView imageview1;
    private TextView textview_upload;
    private TextView textview_prog;
    private ProgressBar progressbar1;

    private StorageReference upload_storage = _firebase_storage.getReference("upload/music");
    private OnCompleteListener<Uri> _upload_storage_upload_success_listener;
    private OnSuccessListener<FileDownloadTask.TaskSnapshot> _upload_storage_download_success_listener;
    private OnSuccessListener _upload_storage_delete_success_listener;
    private OnProgressListener _upload_storage_upload_progress_listener;
    private OnProgressListener _upload_storage_download_progress_listener;
    private OnFailureListener _upload_storage_failure_listener;
    private DatabaseReference upload_text = _firebase.getReference("upload/text");
    private ChildEventListener _upload_text_child_listener;
    private FirebaseAuth Auth;
    private OnCompleteListener<AuthResult> _Auth_create_user_listener;
    private OnCompleteListener<AuthResult> _Auth_sign_in_listener;
    private OnCompleteListener<Void> _Auth_reset_password_listener;
    private StorageReference music_image = _firebase_storage.getReference("upload/image");
    private OnCompleteListener<Uri> _music_image_upload_success_listener;
    private OnSuccessListener<FileDownloadTask.TaskSnapshot> _music_image_download_success_listener;
    private OnSuccessListener _music_image_delete_success_listener;
    private OnProgressListener _music_image_upload_progress_listener;
    private OnProgressListener _music_image_download_progress_listener;
    private OnFailureListener _music_image_failure_listener;
    private SharedPreferences data;
    private Intent intent = new Intent();
    private AlertDialog.Builder warn_dialog;
    private String _reservedFilename = "";

    {
    }

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.upload);
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

        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        image_back = findViewById(R.id.image_back);
        textview_title = findViewById(R.id.textview_title);
        linear7 = findViewById(R.id.linear7);
        linear5 = findViewById(R.id.linear5);
        linear4 = findViewById(R.id.linear4);
        edittext_name = findViewById(R.id.edittext_name);
        button_upload = findViewById(R.id.button_upload);
        textview_path = findViewById(R.id.textview_path);
        imageview1 = findViewById(R.id.imageview1);
        textview_upload = findViewById(R.id.textview_upload);
        textview_prog = findViewById(R.id.textview_prog);
        progressbar1 = findViewById(R.id.progressbar1);
        Auth = FirebaseAuth.getInstance();
        data = getSharedPreferences("teamdata", Activity.MODE_PRIVATE);
        warn_dialog = new AlertDialog.Builder(this);

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (!isExitDisabled) {
                    finish();
                }
            }
        });

        button_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (21 > (FileUtil.getFileLength(path) / 1048576)) {
                    if (edittext_name.getText().toString().trim().length() > 0) {
                        if (indexName.contains(edittext_name.getText().toString().trim())) {
                            _customSnack("Music name already exist.", 2);
                        } else {
                            warn_dialog.setTitle("Warning");
                            warn_dialog.setMessage("Before you upload this music, we just want to warn you. Any copyrighted contents will be DELETED fastly or slowly, so please upload any contents that are free of any copyright instead. If you still wants to upload any copyrighted contents, please ask the creator for permission.");
                            warn_dialog.setPositiveButton("I understand", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface _dialog, int _which) {
                                    name = edittext_name.getText().toString().trim();
                                    _reservedFilename = name.replace("/", " ");
                                    upload_storage.child(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(_reservedFilename.concat(Uri.parse(path).getLastPathSegment().replace(Uri.parse(path).getLastPathSegment().substring(0, Uri.parse(path).getLastPathSegment().lastIndexOf(".")), ""))))).putFile(Uri.fromFile(new File(path))).addOnFailureListener(_upload_storage_failure_listener).addOnProgressListener(_upload_storage_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                        @Override
                                        public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
                                            return upload_storage.child(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(_reservedFilename.concat(Uri.parse(path).getLastPathSegment().replace(Uri.parse(path).getLastPathSegment().substring(0, Uri.parse(path).getLastPathSegment().lastIndexOf(".")), ""))))).getDownloadUrl();
                                        }
                                    }).addOnCompleteListener(_upload_storage_upload_success_listener);
                                    textview_prog.setText(edittext_name.getText().toString().trim());
                                    linear7.setVisibility(View.GONE);
                                    linear5.setVisibility(View.VISIBLE);
                                    isExitDisabled = true;
                                }
                            });
                            warn_dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface _dialog, int _which) {

                                }
                            });
                            warn_dialog.create().show();
                        }
                    } else {
                        _customSnack("Music name can't be empty.", 2);
                    }
                } else {
                    _customSnack("File size can't be bigger than 20 MB.", 2);
                }
            }
        });

        imageview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intent.setClass(getApplicationContext(), FilepickerActivity.class);
                intent.putExtra("fileType", "audio/*");
                startActivity(intent);
            }
        });

        _upload_storage_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot _param1) {
                double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
                if (img_path.equals("")) {
                    progressbar1.setProgress((int) _progressValue);
                } else {
                    progressbar1.setProgress((int) _progressValue / 2);
                }
            }
        };

        _upload_storage_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
                double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();

            }
        };

        _upload_storage_upload_success_listener = new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(Task<Uri> _param1) {
                final String _downloadUrl = _param1.getResult().toString();
                if (img_path.equals("")) {
                    upload_map = new HashMap<>();
                    upload_map.put("url", _downloadUrl);
                    upload_map.put("name", name);
                    upload_map.put("view", "0");
                    upload_map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    upload_text.push().updateChildren(upload_map);
                    SketchwareUtil.showMessage(getApplicationContext(), "Upload Completed!");
                    finish();
                } else {
                    tmpurl = _downloadUrl;
                    music_image.child(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(_reservedFilename.concat(".png")))).putFile(Uri.fromFile(new File(img_path))).addOnFailureListener(_music_image_failure_listener).addOnProgressListener(_music_image_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
                            return music_image.child(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(_reservedFilename.concat(".png")))).getDownloadUrl();
                        }
                    }).addOnCompleteListener(_music_image_upload_success_listener);
                }
            }
        };

        _upload_storage_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
                final long _totalByteCount = _param1.getTotalByteCount();

            }
        };

        _upload_storage_delete_success_listener = new OnSuccessListener() {
            @Override
            public void onSuccess(Object _param1) {

            }
        };

        _upload_storage_failure_listener = new OnFailureListener() {
            @Override
            public void onFailure(Exception _param1) {
                final String _message = _param1.getMessage();
                SketchwareUtil.showMessage(getApplicationContext(), _message);
                finish();
            }
        };

        _upload_text_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                indexName.add(_childValue.get("name").toString());
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
                if (indexName.contains(_childValue.get("name").toString())) {
                    indexName.remove(indexName.indexOf(_childValue.get("name").toString()));
                }
            }

            @Override
            public void onCancelled(DatabaseError _param1) {
                final int _errorCode = _param1.getCode();
                final String _errorMessage = _param1.getMessage();

            }
        };
        upload_text.addChildEventListener(_upload_text_child_listener);

        _music_image_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot _param1) {
                double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
                progressbar1.setProgress((int) (50 + (_progressValue / 2)));
            }
        };

        _music_image_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
                double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();

            }
        };

        _music_image_upload_success_listener = new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(Task<Uri> _param1) {
                final String _downloadUrl = _param1.getResult().toString();
                FileUtil.deleteFile(img_path);
                upload_map = new HashMap<>();
                upload_map.put("url", tmpurl);
                upload_map.put("name", name);
                upload_map.put("view", "0");
                upload_map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                upload_map.put("img", _downloadUrl);
                upload_text.push().updateChildren(upload_map);
                SketchwareUtil.showMessage(getApplicationContext(), "Upload Completed!");
                finish();
            }
        };

        _music_image_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
                final long _totalByteCount = _param1.getTotalByteCount();

            }
        };

        _music_image_delete_success_listener = new OnSuccessListener() {
            @Override
            public void onSuccess(Object _param1) {

            }
        };

        _music_image_failure_listener = new OnFailureListener() {
            @Override
            public void onFailure(Exception _param1) {
                final String _message = _param1.getMessage();

            }
        };

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
        linear5.setVisibility(View.GONE);
        textview_title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        edittext_name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        button_upload.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview_path.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview_upload.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview_prog.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
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
        if (!isExitDisabled) {
            finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!data.getString("tmpPath", "").equals("")) {
            path = data.getString("tmpPath", "");
            data.edit().remove("tmpPath").commit();
            if (FileUtil.isExistFile(path)) {
                if (FileUtil.isExistFile(FileUtil.getPackageDataDir(getApplicationContext()).concat("/tmp.png"))) {
                    FileUtil.deleteFile(FileUtil.getPackageDataDir(getApplicationContext()).concat("/tmp.png"));
                }
                edittext_name.setEnabled(true);
                button_upload.setEnabled(true);
                edittext_name.setAlpha((float) (1));
                button_upload.setAlpha((float) (1));
                textview_path.setText(path);
                edittext_name.setText(Uri.parse(path).getLastPathSegment().substring(0, Uri.parse(path).getLastPathSegment().lastIndexOf(".")).replace("_", " "));
                try {
                    img_path = FileUtil.getPackageDataDir(getApplicationContext()).concat("/tmp.png");
                    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                    mmr.setDataSource(path);
                    byte[] data = mmr.getEmbeddedPicture();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    imageview1.clearColorFilter();
                    {
                        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(output);
                        final int color = 0xff424242;
                        final Paint paint = new Paint();
                        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                        final RectF rectF = new RectF(rect);
                        final float roundPx = 15;
                        paint.setAntiAlias(true);
                        canvas.drawARGB(0, 0, 0, 0);
                        paint.setColor(color);
                        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                        canvas.drawBitmap(bitmap, rect, rect, paint);
                        imageview1.setImageBitmap(output);
                    }

                    java.io.File file = new java.io.File(img_path);
                    java.io.FileOutputStream fOut = new java.io.FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                } catch (Exception e) {
                    imageview1.setImageResource(R.drawable.ic_album_white);
                    img_path = "";
                    if (data.getString("themesactive", "").equals("1")) {
                        imageview1.setColorFilter(Color.parseColor(theme_map.get(0).get("colorButtonText").toString()), PorterDuff.Mode.MULTIPLY);
                    } else {
                        imageview1.clearColorFilter();
                    }
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

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


    private void _shadow(final View _v, final double _n) {
        _v.setElevation((float) _n);
    }


    private void _circleRipple(final String _color, final View _v) {
        android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_color)});
        android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb, null, null);
        _v.setBackground(ripdrb);
    }


    private void _loadTheme() {
        theme_map = new Gson().fromJson(data.getString("themesjson", ""), new TypeToken<ArrayList<HashMap<String, Object>>>() {
        }.getType());
        _circleRipple(theme_map.get(0).get("colorRipple").toString(), image_back);
        _customNav(theme_map.get(0).get("colorBackground").toString());
        _shape(SketchwareUtil.getDip(getApplicationContext(), (int) (2.5d)), SketchwareUtil.getDip(getApplicationContext(), (int) (2.5d)), SketchwareUtil.getDip(getApplicationContext(), (int) (2.5d)), SketchwareUtil.getDip(getApplicationContext(), (int) (2.5d)), theme_map.get(0).get("colorBackgroundCard").toString(), "#FFFFFF", 0, linear5);
        _shape(SketchwareUtil.getDip(getApplicationContext(), (int) (2.5d)), SketchwareUtil.getDip(getApplicationContext(), (int) (2.5d)), SketchwareUtil.getDip(getApplicationContext(), (int) (2.5d)), SketchwareUtil.getDip(getApplicationContext(), (int) (2.5d)), theme_map.get(0).get("colorBackgroundCard").toString(), "#FFFFFF", 0, linear7);
        _shape(SketchwareUtil.getDip(getApplicationContext(), 5), SketchwareUtil.getDip(getApplicationContext(), 5), SketchwareUtil.getDip(getApplicationContext(), 5), SketchwareUtil.getDip(getApplicationContext(), 5), theme_map.get(0).get("colorButton").toString(), "#FFFFFF", 0, imageview1);
        _shape(SketchwareUtil.getDip(getApplicationContext(), 5), SketchwareUtil.getDip(getApplicationContext(), 5), SketchwareUtil.getDip(getApplicationContext(), 5), SketchwareUtil.getDip(getApplicationContext(), 5), theme_map.get(0).get("colorButton").toString(), "#FFFFFF", 0, button_upload);
        if (theme_map.get(0).get("shadow").toString().equals("1")) {
            _shadow(linear1, 10);
            _shadow(linear5, 8);
            _shadow(linear7, 8);
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window w = this.getWindow();
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.parseColor(theme_map.get(0).get("colorPrimaryDark").toString()));
        }
        linear1.setBackgroundColor(Color.parseColor(theme_map.get(0).get("colorPrimary").toString()));
        linear2.setBackgroundColor(Color.parseColor(theme_map.get(0).get("colorBackground").toString()));
        edittext_name.setHintTextColor(Color.parseColor(theme_map.get(0).get("colorHint").toString()));
        textview_title.setTextColor(Color.parseColor(theme_map.get(0).get("colorPrimaryText").toString()));
        textview_upload.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundCardText").toString()));
        textview_prog.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundCardText").toString()));
        progressbar1.getProgressDrawable().setColorFilter(Color.parseColor(theme_map.get(0).get("colorButton").toString()), PorterDuff.Mode.SRC_IN);
        textview_path.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundCardText").toString()));
        edittext_name.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundCardText").toString()));
        button_upload.setTextColor(Color.parseColor(theme_map.get(0).get("colorButtonText").toString()));
        imageview1.setColorFilter(Color.parseColor(theme_map.get(0).get("colorButtonText").toString()), PorterDuff.Mode.MULTIPLY);
        if (theme_map.get(0).get("statusbarIcon").toString().equals("0")) {
            _BlackStatusBarIcons();
        }
        image_back.setColorFilter(Color.parseColor(theme_map.get(0).get("colorPrimaryImage").toString()), PorterDuff.Mode.MULTIPLY);
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
