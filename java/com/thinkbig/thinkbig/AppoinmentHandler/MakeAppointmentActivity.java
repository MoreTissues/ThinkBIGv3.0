package com.thinkbig.thinkbig.AppoinmentHandler;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thinkbig.thinkbig.AppointmentListView.AppointmentActivity;
import com.thinkbig.thinkbig.NavigationDrawer.NewNavigationActivity;
import com.thinkbig.thinkbig.Objects.Appointment;
import com.thinkbig.thinkbig.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MakeAppointmentActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    Button btn_add, btn_cancel, btn_timepicker, btn_datepicker;
    TextInputEditText et_title;
    EditText et_desc;
    Spinner spinner_lect;
    TextView txt_time, txt_date;
    ArrayAdapter<String> adapter;
    String CHANNEL_ID = "1";
    private NotificationHelper MnotificationHelper;

    List<Appointment> appoint_list;
    DatabaseReference mLecturer;

    private ArrayAdapter<String> spineerArrayAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_new_appointment);
        getSupportActionBar().setTitle("Appointment");
        getSupportActionBar().setHomeButtonEnabled(true);


        et_desc = findViewById(R.id.et_desc);
        spinner_lect = findViewById(R.id.spinner_lecturer);
        et_title = findViewById(R.id.et_title);
        btn_add = findViewById(R.id.btn_add);
        btn_cancel = findViewById(R.id.btn_cancel);
        txt_time = findViewById(R.id.txt_time1);
        txt_date = findViewById(R.id.txt_date1);
        btn_timepicker = findViewById(R.id.btn_timepicker);
        btn_datepicker = findViewById(R.id.btn_datepicker);

        MnotificationHelper = new NotificationHelper(this);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });
        btn_datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        btn_timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker = new TimePickerFragment();
                timepicker.show(getSupportFragmentManager(), "time picker");

            }
        });

        String[] lecturers = new String[]{"Select Lecturer", "Dr Lam Meng Chun", "Puan Faridatul", "Puan Masura", "Dr Zainal"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lecturers);
        spinner_lect.setAdapter(adapter);

        appoint_list = new ArrayList<>();

        mLecturer = FirebaseDatabase.getInstance().getReference("appointments");
        mLecturer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                appoint_list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Appointment appointment = snapshot.getValue(Appointment.class);
                    appoint_list.add(appointment);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_title.equals(null) || et_desc.equals(null) || txt_date.equals("Date") || txt_time.equals("Time") || spinner_lect.getSelectedItem().equals("Select Lecturer")) {
                    Toast.makeText(getApplicationContext(), "Please Fill In The Required Details", Toast.LENGTH_SHORT).show();
                } else {
                    makeAppointment();
                    createnotification();
                }


            }
        });


    }

    private void makeAppointment() {
        String title = et_title.getText().toString().trim();
        String date = txt_date.getText().toString();
        String time = txt_time.getText().toString();
        String desc = et_desc.getText().toString().trim();
        String spinner1 = spinner_lect.getSelectedItem().toString();

        if (!TextUtils.isEmpty(title) || !TextUtils.isEmpty(desc) || !TextUtils.isEmpty(date) || !TextUtils.isEmpty(time) || !TextUtils.isEmpty(spinner1)) {
            String id = mLecturer.push().getKey();
            Appointment appointment = new Appointment(id, title, date, time, desc, spinner1);
            mLecturer.child(id).setValue(appointment);

            et_title.setText("");
            txt_date.setText("Date");
            txt_time.setText("Time");
            et_desc.setText("");


            Toast.makeText(getApplicationContext(), "Appointment Succefully Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Please Fill in the Details", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        txt_time.setText(hourOfDay + ":" + minute);

        startAlarm(c);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        txt_date.setText(currentDate);
    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }

    private void cancelAlarm() {
        Intent intent = new Intent(this, NewNavigationActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Appointment Cancelled", Toast.LENGTH_SHORT).show();
    }

    private void createnotification() {
        createNotificationChannel();

        Intent activityIntent = new Intent(this, NewNavigationActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        Intent broadcastIntent = new Intent(this, AppointmentActivity.class);
        PendingIntent actionIntent = PendingIntent.getActivity(this, 0, broadcastIntent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_none_black_24dp)
                .setContentTitle("Appointment")
                .setContentText("Appointment Have Been Set")
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.drawable.ic_notifications_none_black_24dp, "Appointment List", actionIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Appointment Have Been Set"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, mBuilder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel1";
            String description = "lets have fun";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
