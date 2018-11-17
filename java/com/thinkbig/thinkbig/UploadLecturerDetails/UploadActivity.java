package com.thinkbig.thinkbig.UploadLecturerDetails;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.thinkbig.thinkbig.Objects.Upload;
import com.thinkbig.thinkbig.R;

public class UploadActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button btn_chooseimage, btn_upload;
    private EditText et_filename, et_description;
    private ImageView img_upload;
    private ProgressBar progress;
    private Uri mUriImage;
    private StorageReference mStorageRef;
    private DatabaseReference mDataRef;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_main_layout);
        getSupportActionBar().setTitle("Add New Lecturer");
        getSupportActionBar().setHomeButtonEnabled(true);


        btn_chooseimage = findViewById(R.id.btn_chooseimage);
        btn_upload = findViewById(R.id.btn_upload);
        et_filename = findViewById(R.id.et_filename);
        et_description = findViewById(R.id.et_description);
        img_upload =  findViewById(R.id.img_upload);
        progress =  findViewById(R.id.progress);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDataRef = FirebaseDatabase.getInstance().getReference("uploads");

        btn_chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mUploadTask != null && mUploadTask.isInProgress() || et_filename.equals(null) || et_description.equals(null)) {
                    Toast.makeText(UploadActivity.this, "Upload In Progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

    }


    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadFile() {
        if (mUriImage != null) {
            StorageReference storageReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mUriImage));

            mUploadTask = storageReference.putFile(mUriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progress.setProgress(0);

                        }
                    }, 500);

                    Toast.makeText(UploadActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();

                    Upload upload = new Upload(et_filename.getText().toString().trim(), taskSnapshot.getDownloadUrl().toString(),
                            et_description.getText().toString().trim());
                    String uploadID = mDataRef.push().getKey();
                    mDataRef.child(uploadID).setValue(upload);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progress1 = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progress.setProgress((int) progress1);
                }
            });

        } else {
            Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
        }

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mUriImage = data.getData();

            Picasso.with(this).load(mUriImage).into(img_upload);

        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
