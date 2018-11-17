package com.thinkbig.thinkbig.NavigationDrawer;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.thinkbig.thinkbig.AppoinmentHandler.MakeAppointmentActivity;
import com.thinkbig.thinkbig.AppointmentListView.AppointmentActivity;
import com.thinkbig.thinkbig.R;



public class AppointmentFragment extends Fragment {
    private CardView card_list_appointment, card_make_appointment, card_appoint;
    private FirebaseAuth auth;
    TextView txt_email1;
    LinearLayout linearLayout;

    public AppointmentFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
        txt_email1 = view.findViewById(R.id.txt_user12);
        String user1 = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        txt_email1.setText("Welcome " +user1);
        card_list_appointment = view.findViewById(R.id.card_list_appointment);
        card_make_appointment = view.findViewById(R.id.card_make_appointment);
        linearLayout = view.findViewById(R.id.apointe);
        card_appoint = view.findViewById(R.id.card_appoint);
        card_appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(linearLayout, "Choose one of the functions below", Snackbar.LENGTH_SHORT).show();
            }
        });
        card_list_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AppointmentActivity.class);
                startActivity(intent);
            }
        });
        card_make_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MakeAppointmentActivity.class);
                startActivity(intent);
            }
        });




        return view;
    }

}
