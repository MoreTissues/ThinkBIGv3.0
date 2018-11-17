package com.thinkbig.thinkbig.AppointmentListView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.podcopic.animationlib.library.AnimationType;
import com.podcopic.animationlib.library.StartSmartAnimation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.thinkbig.thinkbig.AppoinmentHandler.AppointmentAdapter;
import com.thinkbig.thinkbig.Objects.Appointment;
import com.thinkbig.thinkbig.R;

import java.util.ArrayList;
import java.util.List;

public class AppointmentActivity extends AppCompatActivity implements AppointmentAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private AppointmentAdapter mAdapter;

    private ProgressBar mProgressCircle;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
ConstraintLayout constraintLayout;
    private ValueEventListener mDBListener;
    private List<Appointment> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_list_appointments);
        getSupportActionBar().setTitle("List Of Appointments");
        getSupportActionBar().setHomeButtonEnabled(true);


        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        constraintLayout = findViewById(R.id.appoint_list);


        mProgressCircle = findViewById(R.id.progress1);

        mUploads = new ArrayList<>();
        mAdapter = new AppointmentAdapter(AppointmentActivity.this, mUploads);


        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(AppointmentActivity.this);

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("appointments");

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Appointment appointment = postSnapshot.getValue(Appointment.class);
                    appointment.setAppointID(postSnapshot.getKey());
                    mUploads.add(appointment);
                }
                mAdapter.notifyDataSetChanged();

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AppointmentActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Snackbar.make(constraintLayout, "Under Construction", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onWhatEverClick(int position) {
        Snackbar.make(constraintLayout, "Under Construction", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(final int position) {
        final Appointment appointment = mUploads.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(AppointmentActivity.this);
        builder.setTitle("Delete Appointment");
        builder.setMessage("Are you sure you want to delete it?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseReference dR = FirebaseDatabase.getInstance().getReference("appointments").child(appointment.getAppointID());
                dR.removeValue();
                Toast.makeText(AppointmentActivity.this, "Appointment Deleted", Toast.LENGTH_SHORT).show();

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
