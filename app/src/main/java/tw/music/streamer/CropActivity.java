package tw.music.streamer;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class CropActivity extends AppCompatActivity {

    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
    private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();

    private boolean isUploading = false;
    private String path_file = "";
    private String url = "";
    private HashMap<String, Object> map = new HashMap<>();
    private String file_path = "";
    private boolean isReviewing = false;

    private ArrayList<HashMap<String, Object>> img = new ArrayList<>();

    private LinearLayout linear1;
    private LinearLayout linear2;
    private ImageView imageview1;
    private Button button1;
    private LinearLayout linear3;
    private Button button2;

    private FirebaseAuth Auth;
    private OnCompleteListener<AuthResult> _Auth_create_user_listener;
    private OnCompleteListener<AuthResult> _Auth_sign_in_listener;
    private OnCompleteListener<Void> _Auth_reset_password_listener;
    private DatabaseReference prof_img = _firebase.getReference("profile/image");
    private ChildEventListener _prof_img_child_listener;
    private StorageReference fbst_usr_img = _firebase_storage.getReference("profile/image");
    private OnCompleteListener<Uri> _fbst_usr_img_upload_success_listener;
    private OnSuccessListener<FileDownloadTask.TaskSnapshot> _fbst_usr_img_download_success_listener;
    private OnSuccessListener _fbst_usr_img_delete_success_listener;
    private OnProgressListener _fbst_usr_img_upload_progress_listener;
    private OnProgressListener _fbst_usr_img_download_progress_listener;
    private OnFailureListener _fbst_usr_img_failure_listener;
    private CropImageView crp;
    private ProgressDialog coreprog;

    {
    }

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.crop);
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
        imageview1 = findViewById(R.id.imageview1);
        button1 = findViewById(R.id.button1);
        linear3 = findViewById(R.id.linear3);
        button2 = findViewById(R.id.button2);
        Auth = FirebaseAuth.getInstance();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                onBackPressed();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (isReviewing) {
                    try {
                        isUploading = true;
                        if (FileUtil.isExistFile(file_path)) {
                            if (FileUtil.isFile(file_path)) {
                                if (21 > (FileUtil.getFileLength(file_path) / 1048576)) {
                                    if (url.length() > 0) {
                                        _firebase_storage.getReferenceFromUrl(url).delete().addOnSuccessListener(_fbst_usr_img_delete_success_listener).addOnFailureListener(_fbst_usr_img_failure_listener);
                                    }
                                    fbst_usr_img.child(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat("profile-img.png"))).putFile(Uri.fromFile(new File(file_path))).addOnFailureListener(_fbst_usr_img_failure_listener).addOnProgressListener(_fbst_usr_img_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                        @Override
                                        public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
                                            return fbst_usr_img.child(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat("profile-img.png"))).getDownloadUrl();
                                        }
                                    }).addOnCompleteListener(_fbst_usr_img_upload_success_listener);
                                    _CoreProgressLoading(true);
                                } else {
                                    SketchwareUtil.showMessage(getApplicationContext(), "File must be lower than 20MB!");
                                    _CoreProgressLoading(false);
                                    isUploading = false;
                                    finish();
                                }
                            } else {
                                _CoreProgressLoading(false);
                                isUploading = false;
                                SketchwareUtil.showMessage(getApplicationContext(), "The following path isn't a file! (WTF are you doing bruh)");
                                finish();
                            }
                        } else {
                            _CoreProgressLoading(false);
                            isUploading = false;
                            SketchwareUtil.showMessage(getApplicationContext(), "File isn't exist! (Deleted or moved)");
                            finish();
                        }
                    } catch (Exception _e) {
                        _CoreProgressLoading(false);
                        isUploading = false;
                        SketchwareUtil.showMessage(getApplicationContext(), _e.getMessage());
                        finish();
                    }
                } else {
                    try {
                        Bitmap croppedImage = crp.getCroppedImage();
                        imageview1.setImageBitmap(croppedImage);
                        java.io.File file = new java.io.File(file_path);
                        java.io.FileOutputStream fOut = new java.io.FileOutputStream(file);
                        croppedImage.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                        crp.setVisibility(View.GONE);
                        imageview1.setVisibility(View.VISIBLE);
                        button1.setText("Back");
                        button2.setText("Done");
                        isReviewing = true;
                    } catch (Exception _e) {
                        _CoreProgressLoading(false);
                        SketchwareUtil.showMessage(getApplicationContext(), _e.getMessage());
                        finish();
                    }
                }
            }
        });

        _prof_img_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    url = _childValue.get("url").toString();
                }
                button2.setEnabled(true);
                button2.setAlpha((float) (1.0d));
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
        prof_img.addChildEventListener(_prof_img_child_listener);

        _fbst_usr_img_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot _param1) {
                double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();

            }
        };

        _fbst_usr_img_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
                double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();

            }
        };

        _fbst_usr_img_upload_success_listener = new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(Task<Uri> _param1) {
                final String _downloadUrl = _param1.getResult().toString();
                map = new HashMap<>();
                map.put("url", _downloadUrl);
                prof_img.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map);
                if (FileUtil.isExistFile(file_path)) {
                    FileUtil.deleteFile(file_path);
                }
                _CoreProgressLoading(false);
                isUploading = false;
                SketchwareUtil.showMessage(getApplicationContext(), "Success!");
                finish();
            }
        };

        _fbst_usr_img_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
                final long _totalByteCount = _param1.getTotalByteCount();

            }
        };

        _fbst_usr_img_delete_success_listener = new OnSuccessListener() {
            @Override
            public void onSuccess(Object _param1) {

            }
        };

        _fbst_usr_img_failure_listener = new OnFailureListener() {
            @Override
            public void onFailure(Exception _param1) {
                final String _message = _param1.getMessage();
                SketchwareUtil.showMessage(getApplicationContext(), _message);
                finish();
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
        file_path = FileUtil.getPackageDataDir(getApplicationContext()).concat("/tmp_user.png");
        imageview1.setVisibility(View.GONE);
        crp = new CropImageView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 16, 0, 0);
        crp.setLayoutParams(lp);
        crp.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        crp.setAdjustViewBounds(true);
        crp.setFixedAspectRatio(true);
        linear1.removeView(imageview1);
        linear1.addView(crp, 0);
        linear1.addView(imageview1);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window w = this.getWindow();
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.parseColor("#000000"));
        }
        _customNav("#000000");
        path_file = getIntent().getStringExtra("path");
        try {
            crp.setImageBitmap(BitmapFactory.decodeFile(path_file));
        } catch (Exception _e) {
            showMessage(_e.getMessage());
        }
        button1.setAllCaps(false);
        button2.setAllCaps(false);
        button2.setEnabled(false);
        button2.setAlpha((float) (0.5d));
        button1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        button2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
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
        if (!isUploading) {
            if (isReviewing) {
                isReviewing = false;
                if (FileUtil.isExistFile(file_path)) {
                    FileUtil.deleteFile(file_path);
                }
                crp.setVisibility(View.VISIBLE);
                imageview1.setVisibility(View.GONE);
                button1.setText("Cancel");
                button2.setText("Next");
            } else {
                finish();
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

    private void _customNav(final String _color) {
        //Code From StackOverFlow.com And Converted By TeamWorks DEV
        if (Build.VERSION.SDK_INT >= 21) {
            Window w = this.getWindow();
            w.setNavigationBarColor(Color.parseColor(_color));
        }
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


    public enum Handle {
        TOP_LEFT(new CornerHandleHelper(Edge.TOP, Edge.LEFT)),
        TOP_RIGHT(new CornerHandleHelper(Edge.TOP, Edge.RIGHT)),
        BOTTOM_LEFT(new CornerHandleHelper(Edge.BOTTOM, Edge.LEFT)),
        BOTTOM_RIGHT(new CornerHandleHelper(Edge.BOTTOM, Edge.RIGHT)),
        LEFT(new VerticalHandleHelper(Edge.LEFT)),
        TOP(new HorizontalHandleHelper(Edge.TOP)),
        RIGHT(new VerticalHandleHelper(Edge.RIGHT)),
        BOTTOM(new HorizontalHandleHelper(Edge.BOTTOM)),
        CENTER(new CenterHandleHelper());
        private HandleHelper mHelper;

        Handle(HandleHelper helper) {
            mHelper = helper;
        }

        public void updateCropWindow(float x,
                                     float y,
                                     RectF imageRect,
                                     float snapRadius) {
            mHelper.updateCropWindow(x, y, imageRect, snapRadius);
        }

        public void updateCropWindow(float x,
                                     float y,
                                     float targetAspectRatio,
                                     RectF imageRect,
                                     float snapRadius) {
            mHelper.updateCropWindow(x, y, targetAspectRatio, imageRect, snapRadius);
        }
    }


    public enum Edge {
        LEFT,
        TOP,
        RIGHT,
        BOTTOM;
        public static final int MIN_CROP_LENGTH_PX = 40;
        private float mCoordinate;

        public static float getWidth() {
            return Edge.RIGHT.getCoordinate() - Edge.LEFT.getCoordinate();
        }

        public static float getHeight() {
            return Edge.BOTTOM.getCoordinate() - Edge.TOP.getCoordinate();
        }

        private static float adjustLeft(float x, RectF imageRect, float imageSnapRadius, float aspectRatio) {
            final float resultX;
            if (x - imageRect.left < imageSnapRadius) {
                resultX = imageRect.left;
            } else {
                float resultXHoriz = Float.POSITIVE_INFINITY;
                float resultXVert = Float.POSITIVE_INFINITY;
                if (x >= Edge.RIGHT.getCoordinate() - MIN_CROP_LENGTH_PX) {
                    resultXHoriz = Edge.RIGHT.getCoordinate() - MIN_CROP_LENGTH_PX;
                }
                if (((Edge.RIGHT.getCoordinate() - x) / aspectRatio) <= MIN_CROP_LENGTH_PX) {
                    resultXVert = Edge.RIGHT.getCoordinate() - (MIN_CROP_LENGTH_PX * aspectRatio);
                }
                resultX = Math.min(x, Math.min(resultXHoriz, resultXVert));
            }
            return resultX;
        }

        private static float adjustRight(float x, RectF imageRect, float imageSnapRadius, float aspectRatio) {
            final float resultX;
            if (imageRect.right - x < imageSnapRadius) {
                resultX = imageRect.right;
            } else {
                float resultXHoriz = Float.NEGATIVE_INFINITY;
                float resultXVert = Float.NEGATIVE_INFINITY;
                if (x <= Edge.LEFT.getCoordinate() + MIN_CROP_LENGTH_PX) {
                    resultXHoriz = Edge.LEFT.getCoordinate() + MIN_CROP_LENGTH_PX;
                }
                if (((x - Edge.LEFT.getCoordinate()) / aspectRatio) <= MIN_CROP_LENGTH_PX) {
                    resultXVert = Edge.LEFT.getCoordinate() + (MIN_CROP_LENGTH_PX * aspectRatio);
                }
                resultX = Math.max(x, Math.max(resultXHoriz, resultXVert));
            }
            return resultX;
        }

        private static float adjustTop(float y, RectF imageRect, float imageSnapRadius, float aspectRatio) {
            final float resultY;
            if (y - imageRect.top < imageSnapRadius) {
                resultY = imageRect.top;
            } else {
                float resultYVert = Float.POSITIVE_INFINITY;
                float resultYHoriz = Float.POSITIVE_INFINITY;
                if (y >= Edge.BOTTOM.getCoordinate() - MIN_CROP_LENGTH_PX)
                    resultYHoriz = Edge.BOTTOM.getCoordinate() - MIN_CROP_LENGTH_PX;
                if (((Edge.BOTTOM.getCoordinate() - y) * aspectRatio) <= MIN_CROP_LENGTH_PX)
                    resultYVert = Edge.BOTTOM.getCoordinate() - (MIN_CROP_LENGTH_PX / aspectRatio);
                resultY = Math.min(y, Math.min(resultYHoriz, resultYVert));
            }
            return resultY;
        }

        private static float adjustBottom(float y, RectF imageRect, float imageSnapRadius, float aspectRatio) {
            final float resultY;
            if (imageRect.bottom - y < imageSnapRadius) {
                resultY = imageRect.bottom;
            } else {
                float resultYVert = Float.NEGATIVE_INFINITY;
                float resultYHoriz = Float.NEGATIVE_INFINITY;
                if (y <= Edge.TOP.getCoordinate() + MIN_CROP_LENGTH_PX) {
                    resultYVert = Edge.TOP.getCoordinate() + MIN_CROP_LENGTH_PX;
                }
                if (((y - Edge.TOP.getCoordinate()) * aspectRatio) <= MIN_CROP_LENGTH_PX) {
                    resultYHoriz = Edge.TOP.getCoordinate() + (MIN_CROP_LENGTH_PX / aspectRatio);
                }
                resultY = Math.max(y, Math.max(resultYHoriz, resultYVert));
            }
            return resultY;
        }

        public void offset(float distance) {
            mCoordinate += distance;
        }

        public float getCoordinate() {
            return mCoordinate;
        }

        public void setCoordinate(float coordinate) {
            mCoordinate = coordinate;
        }

        public void adjustCoordinate(float x, float y, RectF imageRect, float imageSnapRadius, float aspectRatio) {
            switch (this) {
                case LEFT:
                    mCoordinate = adjustLeft(x, imageRect, imageSnapRadius, aspectRatio);
                    break;
                case TOP:
                    mCoordinate = adjustTop(y, imageRect, imageSnapRadius, aspectRatio);
                    break;
                case RIGHT:
                    mCoordinate = adjustRight(x, imageRect, imageSnapRadius, aspectRatio);
                    break;
                case BOTTOM:
                    mCoordinate = adjustBottom(y, imageRect, imageSnapRadius, aspectRatio);
                    break;
            }
        }

        public void adjustCoordinate(float aspectRatio) {
            final float left = Edge.LEFT.getCoordinate();
            final float top = Edge.TOP.getCoordinate();
            final float right = Edge.RIGHT.getCoordinate();
            final float bottom = Edge.BOTTOM.getCoordinate();
            switch (this) {
                case LEFT:
                    mCoordinate = AspectRatioUtil.calculateLeft(top, right, bottom, aspectRatio);
                    break;
                case TOP:
                    mCoordinate = AspectRatioUtil.calculateTop(left, right, bottom, aspectRatio);
                    break;
                case RIGHT:
                    mCoordinate = AspectRatioUtil.calculateRight(left, top, bottom, aspectRatio);
                    break;
                case BOTTOM:
                    mCoordinate = AspectRatioUtil.calculateBottom(left, top, right, aspectRatio);
                    break;
            }
        }

        public boolean isNewRectangleOutOfBounds(Edge edge, RectF imageRect, float aspectRatio) {
            final float offset = edge.snapOffset(imageRect);
            switch (this) {
                case LEFT:
                    if (edge.equals(Edge.TOP)) {
                        final float top = imageRect.top;
                        final float bottom = Edge.BOTTOM.getCoordinate() - offset;
                        final float right = Edge.RIGHT.getCoordinate();
                        final float left = AspectRatioUtil.calculateLeft(top, right, bottom, aspectRatio);
                        return isOutOfBounds(top, left, bottom, right, imageRect);
                    } else if (edge.equals(Edge.BOTTOM)) {
                        final float bottom = imageRect.bottom;
                        final float top = Edge.TOP.getCoordinate() - offset;
                        final float right = Edge.RIGHT.getCoordinate();
                        final float left = AspectRatioUtil.calculateLeft(top, right, bottom, aspectRatio);
                        return isOutOfBounds(top, left, bottom, right, imageRect);
                    }
                    break;
                case TOP:
                    if (edge.equals(Edge.LEFT)) {
                        final float left = imageRect.left;
                        final float right = Edge.RIGHT.getCoordinate() - offset;
                        final float bottom = Edge.BOTTOM.getCoordinate();
                        final float top = AspectRatioUtil.calculateTop(left, right, bottom, aspectRatio);
                        return isOutOfBounds(top, left, bottom, right, imageRect);
                    } else if (edge.equals(Edge.RIGHT)) {
                        final float right = imageRect.right;
                        final float left = Edge.LEFT.getCoordinate() - offset;
                        final float bottom = Edge.BOTTOM.getCoordinate();
                        final float top = AspectRatioUtil.calculateTop(left, right, bottom, aspectRatio);
                        return isOutOfBounds(top, left, bottom, right, imageRect);
                    }
                    break;
                case RIGHT:
                    if (edge.equals(Edge.TOP)) {
                        final float top = imageRect.top;
                        final float bottom = Edge.BOTTOM.getCoordinate() - offset;
                        final float left = Edge.LEFT.getCoordinate();
                        final float right = AspectRatioUtil.calculateRight(left, top, bottom, aspectRatio);
                        return isOutOfBounds(top, left, bottom, right, imageRect);
                    } else if (edge.equals(Edge.BOTTOM)) {
                        final float bottom = imageRect.bottom;
                        final float top = Edge.TOP.getCoordinate() - offset;
                        final float left = Edge.LEFT.getCoordinate();
                        final float right = AspectRatioUtil.calculateRight(left, top, bottom, aspectRatio);
                        return isOutOfBounds(top, left, bottom, right, imageRect);
                    }
                    break;
                case BOTTOM:
                    if (edge.equals(Edge.LEFT)) {
                        final float left = imageRect.left;
                        final float right = Edge.RIGHT.getCoordinate() - offset;
                        final float top = Edge.TOP.getCoordinate();
                        final float bottom = AspectRatioUtil.calculateBottom(left, top, right, aspectRatio);
                        return isOutOfBounds(top, left, bottom, right, imageRect);
                    } else if (edge.equals(Edge.RIGHT)) {
                        final float right = imageRect.right;
                        final float left = Edge.LEFT.getCoordinate() - offset;
                        final float top = Edge.TOP.getCoordinate();
                        final float bottom = AspectRatioUtil.calculateBottom(left, top, right, aspectRatio);
                        return isOutOfBounds(top, left, bottom, right, imageRect);
                    }
                    break;
            }
            return true;
        }

        private boolean isOutOfBounds(float top, float left, float bottom, float right, RectF imageRect) {
            return (top < imageRect.top || left < imageRect.left || bottom > imageRect.bottom || right > imageRect.right);
        }

        public float snapToRect(RectF imageRect) {
            final float oldCoordinate = mCoordinate;
            switch (this) {
                case LEFT:
                    mCoordinate = imageRect.left;
                    break;
                case TOP:
                    mCoordinate = imageRect.top;
                    break;
                case RIGHT:
                    mCoordinate = imageRect.right;
                    break;
                case BOTTOM:
                    mCoordinate = imageRect.bottom;
                    break;
            }
            return mCoordinate - oldCoordinate;
        }

        public float snapOffset(RectF imageRect) {
            final float oldCoordinate = mCoordinate;
            final float newCoordinate;
            switch (this) {
                case LEFT:
                    newCoordinate = imageRect.left;
                    break;
                case TOP:
                    newCoordinate = imageRect.top;
                    break;
                case RIGHT:
                    newCoordinate = imageRect.right;
                    break;
                default: // BOTTOM
                    newCoordinate = imageRect.bottom;
                    break;
            }
            return newCoordinate - oldCoordinate;
        }

        public boolean isOutsideMargin(RectF rect, float margin) {
            final boolean result;
            switch (this) {
                case LEFT:
                    result = mCoordinate - rect.left < margin;
                    break;
                case TOP:
                    result = mCoordinate - rect.top < margin;
                    break;
                case RIGHT:
                    result = rect.right - mCoordinate < margin;
                    break;
                default: // BOTTOM
                    result = rect.bottom - mCoordinate < margin;
                    break;
            }
            return result;
        }
    }

    public static class CropImageView extends ImageView {
        @SuppressWarnings("unused")
        public static final int GUIDELINES_OFF = 0;
        public static final int GUIDELINES_ON_TOUCH = 1;
        public static final int GUIDELINES_ON = 2;
        @SuppressWarnings("unused")
        private static final String TAG = CropImageView.class.getName();
        private Paint mBorderPaint;
        private Paint mGuidelinePaint;
        private Paint mCornerPaint;
        private Paint mSurroundingAreaOverlayPaint;
        private float mHandleRadius;
        private float mSnapRadius;
        private float mCornerThickness;
        private float mBorderThickness;
        private float mCornerLength;
        private RectF mBitmapRect = new RectF();
        private PointF mTouchOffset = new PointF();
        private Handle mPressedHandle;
        private boolean mFixAspectRatio;
        private int mAspectRatioX = 1;
        private int mAspectRatioY = 1;
        private int mGuidelinesMode = 1;

        public CropImageView(Context context) {
            super(context);
            init(context, null);
        }

        public CropImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context, attrs);
        }

        public CropImageView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init(context, attrs);
        }

        private void init(Context context, AttributeSet attrs) {

            //final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CropImageView, 0, 0);
            mGuidelinesMode = 1;
            mFixAspectRatio = false;
            mAspectRatioX = 1;
            mAspectRatioY = 1;

            final android.content.res.Resources resources = context.getResources();

            mBorderPaint = PaintUtil.newBorderPaint(resources);
            mGuidelinePaint = PaintUtil.newGuidelinePaint(resources);
            mSurroundingAreaOverlayPaint = PaintUtil.newSurroundingAreaOverlayPaint(resources);
            mCornerPaint = PaintUtil.newCornerPaint(resources);

            mHandleRadius = 24;
            mSnapRadius = 3;
            mBorderThickness = 3;
            mCornerThickness = 5;
            mCornerLength = 20;
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            mBitmapRect = getBitmapRect();
            initCropWindow(mBitmapRect);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawDarkenedSurroundingArea(canvas);
            drawGuidelines(canvas);
            drawBorder(canvas);
            drawCorners(canvas);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (!isEnabled()) {
                return false;
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    onActionDown(event.getX(), event.getY());
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    getParent().requestDisallowInterceptTouchEvent(false);
                    onActionUp();
                    return true;
                case MotionEvent.ACTION_MOVE:
                    onActionMove(event.getX(), event.getY());
                    getParent().requestDisallowInterceptTouchEvent(true);
                    return true;
                default:
                    return false;
            }
        }

        public void setGuidelines(int guidelinesMode) {
            mGuidelinesMode = guidelinesMode;
            invalidate();
        }

        public void setFixedAspectRatio(boolean fixAspectRatio) {
            mFixAspectRatio = fixAspectRatio;
            requestLayout();
        }

        public void setAspectRatio(int aspectRatioX, int aspectRatioY) {
            if (aspectRatioX <= 0 || aspectRatioY <= 0) {
                throw new IllegalArgumentException("Cannot set aspect ratio value to a number less than or equal to 0.");
            }
            mAspectRatioX = aspectRatioX;
            mAspectRatioY = aspectRatioY;
            if (mFixAspectRatio) {
                requestLayout();
            }
        }

        public Bitmap getCroppedImage() {
            final android.graphics.drawable.Drawable drawable = getDrawable();
            if (drawable == null || !(drawable instanceof android.graphics.drawable.BitmapDrawable)) {
                return null;
            }
            final float[] matrixValues = new float[9];
            getImageMatrix().getValues(matrixValues);
            final float scaleX = matrixValues[Matrix.MSCALE_X];
            final float scaleY = matrixValues[Matrix.MSCALE_Y];
            final float transX = matrixValues[Matrix.MTRANS_X];
            final float transY = matrixValues[Matrix.MTRANS_Y];
            final float bitmapLeft = (transX < 0) ? Math.abs(transX) : 0;
            final float bitmapTop = (transY < 0) ? Math.abs(transY) : 0;
            final Bitmap originalBitmap = ((android.graphics.drawable.BitmapDrawable) drawable).getBitmap();
            final float cropX = (bitmapLeft + Edge.LEFT.getCoordinate()) / scaleX;
            final float cropY = (bitmapTop + Edge.TOP.getCoordinate()) / scaleY;
            final float cropWidth = Math.min(Edge.getWidth() / scaleX, originalBitmap.getWidth() - cropX);
            final float cropHeight = Math.min(Edge.getHeight() / scaleY, originalBitmap.getHeight() - cropY);
            return Bitmap.createBitmap(originalBitmap,
                    (int) cropX,
                    (int) cropY,
                    (int) cropWidth,
                    (int) cropHeight);
        }

        private RectF getBitmapRect() {
            final android.graphics.drawable.Drawable drawable = getDrawable();
            if (drawable == null) {
                return new RectF();
            }
            final float[] matrixValues = new float[9];
            getImageMatrix().getValues(matrixValues);
            final float scaleX = matrixValues[Matrix.MSCALE_X];
            final float scaleY = matrixValues[Matrix.MSCALE_Y];
            final float transX = matrixValues[Matrix.MTRANS_X];
            final float transY = matrixValues[Matrix.MTRANS_Y];
            final int drawableIntrinsicWidth = drawable.getIntrinsicWidth();
            final int drawableIntrinsicHeight = drawable.getIntrinsicHeight();
            final int drawableDisplayWidth = Math.round(drawableIntrinsicWidth * scaleX);
            final int drawableDisplayHeight = Math.round(drawableIntrinsicHeight * scaleY);
            final float left = Math.max(transX, 0);
            final float top = Math.max(transY, 0);
            final float right = Math.min(left + drawableDisplayWidth, getWidth());
            final float bottom = Math.min(top + drawableDisplayHeight, getHeight());
            return new RectF(left, top, right, bottom);
        }

        private void initCropWindow(RectF bitmapRect) {
            if (mFixAspectRatio) {
                initCropWindowWithFixedAspectRatio(bitmapRect);
            } else {
                final float horizontalPadding = 0.1f * bitmapRect.width();
                final float verticalPadding = 0.1f * bitmapRect.height();
                Edge.LEFT.setCoordinate(bitmapRect.left + horizontalPadding);
                Edge.TOP.setCoordinate(bitmapRect.top + verticalPadding);
                Edge.RIGHT.setCoordinate(bitmapRect.right - horizontalPadding);
                Edge.BOTTOM.setCoordinate(bitmapRect.bottom - verticalPadding);
            }
        }

        private void initCropWindowWithFixedAspectRatio(RectF bitmapRect) {
            if (AspectRatioUtil.calculateAspectRatio(bitmapRect) > getTargetAspectRatio()) {
                final float cropWidth = AspectRatioUtil.calculateWidth(bitmapRect.height(), getTargetAspectRatio());
                Edge.LEFT.setCoordinate(bitmapRect.centerX() - cropWidth / 2f);
                Edge.TOP.setCoordinate(bitmapRect.top);
                Edge.RIGHT.setCoordinate(bitmapRect.centerX() + cropWidth / 2f);
                Edge.BOTTOM.setCoordinate(bitmapRect.bottom);
            } else {
                final float cropHeight = AspectRatioUtil.calculateHeight(bitmapRect.width(), getTargetAspectRatio());
                Edge.LEFT.setCoordinate(bitmapRect.left);
                Edge.TOP.setCoordinate(bitmapRect.centerY() - cropHeight / 2f);
                Edge.RIGHT.setCoordinate(bitmapRect.right);
                Edge.BOTTOM.setCoordinate(bitmapRect.centerY() + cropHeight / 2f);
            }
        }

        private void drawDarkenedSurroundingArea(Canvas canvas) {
            final RectF bitmapRect = mBitmapRect;
            final float left = Edge.LEFT.getCoordinate();
            final float top = Edge.TOP.getCoordinate();
            final float right = Edge.RIGHT.getCoordinate();
            final float bottom = Edge.BOTTOM.getCoordinate();
            canvas.drawRect(bitmapRect.left, bitmapRect.top, bitmapRect.right, top, mSurroundingAreaOverlayPaint);
            canvas.drawRect(bitmapRect.left, bottom, bitmapRect.right, bitmapRect.bottom, mSurroundingAreaOverlayPaint);
            canvas.drawRect(bitmapRect.left, top, left, bottom, mSurroundingAreaOverlayPaint);
            canvas.drawRect(right, top, bitmapRect.right, bottom, mSurroundingAreaOverlayPaint);
        }

        private void drawGuidelines(Canvas canvas) {
            if (!shouldGuidelinesBeShown()) {
                return;
            }
            final float left = Edge.LEFT.getCoordinate();
            final float top = Edge.TOP.getCoordinate();
            final float right = Edge.RIGHT.getCoordinate();
            final float bottom = Edge.BOTTOM.getCoordinate();
            final float oneThirdCropWidth = Edge.getWidth() / 3;
            final float x1 = left + oneThirdCropWidth;
            canvas.drawLine(x1, top, x1, bottom, mGuidelinePaint);
            final float x2 = right - oneThirdCropWidth;
            canvas.drawLine(x2, top, x2, bottom, mGuidelinePaint);
            final float oneThirdCropHeight = Edge.getHeight() / 3;
            final float y1 = top + oneThirdCropHeight;
            canvas.drawLine(left, y1, right, y1, mGuidelinePaint);
            final float y2 = bottom - oneThirdCropHeight;
            canvas.drawLine(left, y2, right, y2, mGuidelinePaint);
        }

        private void drawBorder(Canvas canvas) {
            canvas.drawRect(Edge.LEFT.getCoordinate(),
                    Edge.TOP.getCoordinate(),
                    Edge.RIGHT.getCoordinate(),
                    Edge.BOTTOM.getCoordinate(),
                    mBorderPaint);
        }

        private void drawCorners(Canvas canvas) {
            final float left = Edge.LEFT.getCoordinate();
            final float top = Edge.TOP.getCoordinate();
            final float right = Edge.RIGHT.getCoordinate();
            final float bottom = Edge.BOTTOM.getCoordinate();
            final float lateralOffset = (mCornerThickness - mBorderThickness) / 2f;
            final float startOffset = mCornerThickness - (mBorderThickness / 2f);
            canvas.drawLine(left - lateralOffset, top - startOffset, left - lateralOffset, top + mCornerLength, mCornerPaint);
            canvas.drawLine(left - startOffset, top - lateralOffset, left + mCornerLength, top - lateralOffset, mCornerPaint);
            canvas.drawLine(right + lateralOffset, top - startOffset, right + lateralOffset, top + mCornerLength, mCornerPaint);
            canvas.drawLine(right + startOffset, top - lateralOffset, right - mCornerLength, top - lateralOffset, mCornerPaint);
            canvas.drawLine(left - lateralOffset, bottom + startOffset, left - lateralOffset, bottom - mCornerLength, mCornerPaint);
            canvas.drawLine(left - startOffset, bottom + lateralOffset, left + mCornerLength, bottom + lateralOffset, mCornerPaint);
            canvas.drawLine(right + lateralOffset, bottom + startOffset, right + lateralOffset, bottom - mCornerLength, mCornerPaint);
            canvas.drawLine(right + startOffset, bottom + lateralOffset, right - mCornerLength, bottom + lateralOffset, mCornerPaint);
        }

        private boolean shouldGuidelinesBeShown() {
            return ((mGuidelinesMode == GUIDELINES_ON)
                    || ((mGuidelinesMode == GUIDELINES_ON_TOUCH) && (mPressedHandle != null)));
        }

        private float getTargetAspectRatio() {
            return mAspectRatioX / (float) mAspectRatioY;
        }

        private void onActionDown(float x, float y) {
            final float left = Edge.LEFT.getCoordinate();
            final float top = Edge.TOP.getCoordinate();
            final float right = Edge.RIGHT.getCoordinate();
            final float bottom = Edge.BOTTOM.getCoordinate();
            mPressedHandle = HandleUtil.getPressedHandle(x, y, left, top, right, bottom, mHandleRadius);
            if (mPressedHandle != null) {
                HandleUtil.getOffset(mPressedHandle, x, y, left, top, right, bottom, mTouchOffset);
                invalidate();
            }
        }

        private void onActionUp() {
            if (mPressedHandle != null) {
                mPressedHandle = null;
                invalidate();
            }
        }

        private void onActionMove(float x, float y) {
            if (mPressedHandle == null) {
                return;
            }
            x += mTouchOffset.x;
            y += mTouchOffset.y;
            if (mFixAspectRatio) {
                mPressedHandle.updateCropWindow(x, y, getTargetAspectRatio(), mBitmapRect, mSnapRadius);
            } else {
                mPressedHandle.updateCropWindow(x, y, mBitmapRect, mSnapRadius);
            }
            invalidate();
        }
    }

    public static class PaintUtil {
        public static Paint newBorderPaint(android.content.res.Resources resources) {
            final Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);
            paint.setColor(Color.parseColor("#AAFFFFFF"));
            return paint;
        }

        public static Paint newGuidelinePaint(android.content.res.Resources resources) {
            final Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);
            paint.setColor(Color.parseColor("#AAFFFFFF"));
            return paint;
        }

        public static Paint newSurroundingAreaOverlayPaint(android.content.res.Resources resources) {
            final Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#B0000000"));
            return paint;
        }

        public static Paint newCornerPaint(android.content.res.Resources resources) {
            final Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            paint.setColor(Color.parseColor("#FFFFFF"));
            return paint;
        }
    }

    public static class MathUtil {
        public static float calculateDistance(float x1, float y1, float x2, float y2) {
            final float side1 = x2 - x1;
            final float side2 = y2 - y1;
            return (float) Math.sqrt(side1 * side1 + side2 * side2);
        }
    }

    public static class HandleUtil {
        public static Handle getPressedHandle(float x,
                                              float y,
                                              float left,
                                              float top,
                                              float right,
                                              float bottom,
                                              float targetRadius) {
            Handle closestHandle = null;
            float closestDistance = Float.POSITIVE_INFINITY;
            final float distanceToTopLeft = MathUtil.calculateDistance(x, y, left, top);
            if (distanceToTopLeft < closestDistance) {
                closestDistance = distanceToTopLeft;
                closestHandle = Handle.TOP_LEFT;
            }
            final float distanceToTopRight = MathUtil.calculateDistance(x, y, right, top);
            if (distanceToTopRight < closestDistance) {
                closestDistance = distanceToTopRight;
                closestHandle = Handle.TOP_RIGHT;
            }
            final float distanceToBottomLeft = MathUtil.calculateDistance(x, y, left, bottom);
            if (distanceToBottomLeft < closestDistance) {
                closestDistance = distanceToBottomLeft;
                closestHandle = Handle.BOTTOM_LEFT;
            }
            final float distanceToBottomRight = MathUtil.calculateDistance(x, y, right, bottom);
            if (distanceToBottomRight < closestDistance) {
                closestDistance = distanceToBottomRight;
                closestHandle = Handle.BOTTOM_RIGHT;
            }
            if (closestDistance <= targetRadius) {
                return closestHandle;
            }
            if (HandleUtil.isInHorizontalTargetZone(x, y, left, right, top, targetRadius)) {
                return Handle.TOP;
            } else if (HandleUtil.isInHorizontalTargetZone(x, y, left, right, bottom, targetRadius)) {
                return Handle.BOTTOM;
            } else if (HandleUtil.isInVerticalTargetZone(x, y, left, top, bottom, targetRadius)) {
                return Handle.LEFT;
            } else if (HandleUtil.isInVerticalTargetZone(x, y, right, top, bottom, targetRadius)) {
                return Handle.RIGHT;
            }
            if (isWithinBounds(x, y, left, top, right, bottom)) {
                return Handle.CENTER;
            }
            return null;
        }

        public static void getOffset(Handle handle,
                                     float x,
                                     float y,
                                     float left,
                                     float top,
                                     float right,
                                     float bottom,
                                     PointF touchOffsetOutput) {
            float touchOffsetX = 0;
            float touchOffsetY = 0;
            switch (handle) {
                case TOP_LEFT:
                    touchOffsetX = left - x;
                    touchOffsetY = top - y;
                    break;
                case TOP_RIGHT:
                    touchOffsetX = right - x;
                    touchOffsetY = top - y;
                    break;
                case BOTTOM_LEFT:
                    touchOffsetX = left - x;
                    touchOffsetY = bottom - y;
                    break;
                case BOTTOM_RIGHT:
                    touchOffsetX = right - x;
                    touchOffsetY = bottom - y;
                    break;
                case LEFT:
                    touchOffsetX = left - x;
                    touchOffsetY = 0;
                    break;
                case TOP:
                    touchOffsetX = 0;
                    touchOffsetY = top - y;
                    break;
                case RIGHT:
                    touchOffsetX = right - x;
                    touchOffsetY = 0;
                    break;
                case BOTTOM:
                    touchOffsetX = 0;
                    touchOffsetY = bottom - y;
                    break;
                case CENTER:
                    final float centerX = (right + left) / 2;
                    final float centerY = (top + bottom) / 2;
                    touchOffsetX = centerX - x;
                    touchOffsetY = centerY - y;
                    break;
            }
            touchOffsetOutput.x = touchOffsetX;
            touchOffsetOutput.y = touchOffsetY;
        }

        private static boolean isInHorizontalTargetZone(float x,
                                                        float y,
                                                        float handleXStart,
                                                        float handleXEnd,
                                                        float handleY,
                                                        float targetRadius) {
            return (x > handleXStart && x < handleXEnd && Math.abs(y - handleY) <= targetRadius);
        }

        private static boolean isInVerticalTargetZone(float x,
                                                      float y,
                                                      float handleX,
                                                      float handleYStart,
                                                      float handleYEnd,
                                                      float targetRadius) {
            return (Math.abs(x - handleX) <= targetRadius && y > handleYStart && y < handleYEnd);
        }

        private static boolean isWithinBounds(float x, float y, float left, float top, float right, float bottom) {
            return x >= left && x <= right && y >= top && y <= bottom;
        }
    }

    public static class AspectRatioUtil {
        public static float calculateAspectRatio(float left, float top, float right, float bottom) {
            final float width = right - left;
            final float height = bottom - top;
            return width / height;
        }

        public static float calculateAspectRatio(RectF rect) {
            return rect.width() / rect.height();
        }

        public static float calculateLeft(float top, float right, float bottom, float targetAspectRatio) {
            final float height = bottom - top;
            return right - (targetAspectRatio * height);
        }

        public static float calculateTop(float left, float right, float bottom, float targetAspectRatio) {
            final float width = right - left;
            return bottom - (width / targetAspectRatio);
        }

        public static float calculateRight(float left, float top, float bottom, float targetAspectRatio) {
            final float height = bottom - top;
            return (targetAspectRatio * height) + left;
        }

        public static float calculateBottom(float left, float top, float right, float targetAspectRatio) {
            final float width = right - left;
            return (width / targetAspectRatio) + top;
        }

        public static float calculateWidth(float height, float targetAspectRatio) {
            return targetAspectRatio * height;
        }

        public static float calculateHeight(float width, float targetAspectRatio) {
            return width / targetAspectRatio;
        }
    }

    static abstract class HandleHelper {
        private static final float UNFIXED_ASPECT_RATIO_CONSTANT = 1;
        private Edge mHorizontalEdge;
        private Edge mVerticalEdge;
        private EdgePair mActiveEdges;

        HandleHelper(Edge horizontalEdge, Edge verticalEdge) {
            mHorizontalEdge = horizontalEdge;
            mVerticalEdge = verticalEdge;
            mActiveEdges = new EdgePair(mHorizontalEdge, mVerticalEdge);
        }

        void updateCropWindow(float x,
                              float y,
                              RectF imageRect,
                              float snapRadius) {
            final EdgePair activeEdges = getActiveEdges();
            final Edge primaryEdge = activeEdges.primary;
            final Edge secondaryEdge = activeEdges.secondary;
            if (primaryEdge != null)
                primaryEdge.adjustCoordinate(x, y, imageRect, snapRadius, UNFIXED_ASPECT_RATIO_CONSTANT);
            if (secondaryEdge != null)
                secondaryEdge.adjustCoordinate(x, y, imageRect, snapRadius, UNFIXED_ASPECT_RATIO_CONSTANT);
        }

        abstract void updateCropWindow(float x,
                                       float y,
                                       float targetAspectRatio,
                                       RectF imageRect,
                                       float snapRadius);

        EdgePair getActiveEdges() {
            return mActiveEdges;
        }

        EdgePair getActiveEdges(float x, float y, float targetAspectRatio) {
            final float potentialAspectRatio = getAspectRatio(x, y);
            if (potentialAspectRatio > targetAspectRatio) {
                mActiveEdges.primary = mVerticalEdge;
                mActiveEdges.secondary = mHorizontalEdge;
            } else {
                mActiveEdges.primary = mHorizontalEdge;
                mActiveEdges.secondary = mVerticalEdge;
            }
            return mActiveEdges;
        }

        private float getAspectRatio(float x, float y) {
            final float left = (mVerticalEdge == Edge.LEFT) ? x : Edge.LEFT.getCoordinate();
            final float top = (mHorizontalEdge == Edge.TOP) ? y : Edge.TOP.getCoordinate();
            final float right = (mVerticalEdge == Edge.RIGHT) ? x : Edge.RIGHT.getCoordinate();
            final float bottom = (mHorizontalEdge == Edge.BOTTOM) ? y : Edge.BOTTOM.getCoordinate();
            return AspectRatioUtil.calculateAspectRatio(left, top, right, bottom);
        }
    }

    static class HorizontalHandleHelper extends HandleHelper {
        private Edge mEdge;

        HorizontalHandleHelper(Edge edge) {
            super(edge, null);
            mEdge = edge;
        }

        @Override
        void updateCropWindow(float x,
                              float y,
                              float targetAspectRatio,
                              RectF imageRect,
                              float snapRadius) {
            mEdge.adjustCoordinate(x, y, imageRect, snapRadius, targetAspectRatio);
            float left = Edge.LEFT.getCoordinate();
            float right = Edge.RIGHT.getCoordinate();
            final float targetWidth = AspectRatioUtil.calculateWidth(Edge.getHeight(), targetAspectRatio);
            final float difference = targetWidth - Edge.getWidth();
            final float halfDifference = difference / 2;
            left -= halfDifference;
            right += halfDifference;
            Edge.LEFT.setCoordinate(left);
            Edge.RIGHT.setCoordinate(right);
            if (Edge.LEFT.isOutsideMargin(imageRect, snapRadius)
                    && !mEdge.isNewRectangleOutOfBounds(Edge.LEFT, imageRect, targetAspectRatio)) {
                final float offset = Edge.LEFT.snapToRect(imageRect);
                Edge.RIGHT.offset(-offset);
                mEdge.adjustCoordinate(targetAspectRatio);
            }
            if (Edge.RIGHT.isOutsideMargin(imageRect, snapRadius)
                    && !mEdge.isNewRectangleOutOfBounds(Edge.RIGHT, imageRect, targetAspectRatio)) {
                final float offset = Edge.RIGHT.snapToRect(imageRect);
                Edge.LEFT.offset(-offset);
                mEdge.adjustCoordinate(targetAspectRatio);
            }
        }
    }

    static class VerticalHandleHelper extends HandleHelper {
        private Edge mEdge;

        VerticalHandleHelper(Edge edge) {
            super(null, edge);
            mEdge = edge;
        }

        @Override
        void updateCropWindow(float x,
                              float y,
                              float targetAspectRatio,
                              RectF imageRect,
                              float snapRadius) {
            mEdge.adjustCoordinate(x, y, imageRect, snapRadius, targetAspectRatio);
            float top = Edge.TOP.getCoordinate();
            float bottom = Edge.BOTTOM.getCoordinate();
            final float targetHeight = AspectRatioUtil.calculateHeight(Edge.getWidth(), targetAspectRatio);
            final float difference = targetHeight - Edge.getHeight();
            final float halfDifference = difference / 2;
            top -= halfDifference;
            bottom += halfDifference;
            Edge.TOP.setCoordinate(top);
            Edge.BOTTOM.setCoordinate(bottom);
            if (Edge.TOP.isOutsideMargin(imageRect, snapRadius)
                    && !mEdge.isNewRectangleOutOfBounds(Edge.TOP, imageRect, targetAspectRatio)) {
                final float offset = Edge.TOP.snapToRect(imageRect);
                Edge.BOTTOM.offset(-offset);
                mEdge.adjustCoordinate(targetAspectRatio);
            }
            if (Edge.BOTTOM.isOutsideMargin(imageRect, snapRadius)
                    && !mEdge.isNewRectangleOutOfBounds(Edge.BOTTOM, imageRect, targetAspectRatio)) {
                final float offset = Edge.BOTTOM.snapToRect(imageRect);
                Edge.TOP.offset(-offset);
                mEdge.adjustCoordinate(targetAspectRatio);
            }
        }
    }

    static class CenterHandleHelper extends HandleHelper {
        CenterHandleHelper() {
            super(null, null);
        }

        @Override
        void updateCropWindow(float x,
                              float y,
                              RectF imageRect,
                              float snapRadius) {
            float left = Edge.LEFT.getCoordinate();
            float top = Edge.TOP.getCoordinate();
            float right = Edge.RIGHT.getCoordinate();
            float bottom = Edge.BOTTOM.getCoordinate();
            final float currentCenterX = (left + right) / 2;
            final float currentCenterY = (top + bottom) / 2;
            final float offsetX = x - currentCenterX;
            final float offsetY = y - currentCenterY;
            Edge.LEFT.offset(offsetX);
            Edge.TOP.offset(offsetY);
            Edge.RIGHT.offset(offsetX);
            Edge.BOTTOM.offset(offsetY);
            if (Edge.LEFT.isOutsideMargin(imageRect, snapRadius)) {
                final float offset = Edge.LEFT.snapToRect(imageRect);
                Edge.RIGHT.offset(offset);
            } else if (Edge.RIGHT.isOutsideMargin(imageRect, snapRadius)) {
                final float offset = Edge.RIGHT.snapToRect(imageRect);
                Edge.LEFT.offset(offset);
            }
            if (Edge.TOP.isOutsideMargin(imageRect, snapRadius)) {
                final float offset = Edge.TOP.snapToRect(imageRect);
                Edge.BOTTOM.offset(offset);
            } else if (Edge.BOTTOM.isOutsideMargin(imageRect, snapRadius)) {
                final float offset = Edge.BOTTOM.snapToRect(imageRect);
                Edge.TOP.offset(offset);
            }
        }

        @Override
        void updateCropWindow(float x,
                              float y,
                              float targetAspectRatio,
                              RectF imageRect,
                              float snapRadius) {

            updateCropWindow(x, y, imageRect, snapRadius);
        }
    }

    static class CornerHandleHelper extends HandleHelper {
        CornerHandleHelper(Edge horizontalEdge, Edge verticalEdge) {
            super(horizontalEdge, verticalEdge);
        }

        @Override
        void updateCropWindow(float x,
                              float y,
                              float targetAspectRatio,
                              RectF imageRect,
                              float snapRadius) {
            final EdgePair activeEdges = getActiveEdges(x, y, targetAspectRatio);
            final Edge primaryEdge = activeEdges.primary;
            final Edge secondaryEdge = activeEdges.secondary;
            primaryEdge.adjustCoordinate(x, y, imageRect, snapRadius, targetAspectRatio);
            secondaryEdge.adjustCoordinate(targetAspectRatio);
            if (secondaryEdge.isOutsideMargin(imageRect, snapRadius)) {
                secondaryEdge.snapToRect(imageRect);
                primaryEdge.adjustCoordinate(targetAspectRatio);
            }
        }
    }

    public static class EdgePair {
        public Edge primary;
        public Edge secondary;

        public EdgePair(Edge edge1, Edge edge2) {
            primary = edge1;
            secondary = edge2;
        }
    }

}
