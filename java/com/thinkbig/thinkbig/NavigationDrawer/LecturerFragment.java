package com.thinkbig.thinkbig.NavigationDrawer;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.thinkbig.thinkbig.LecturerList.LecturerList;
import com.thinkbig.thinkbig.R;
import com.thinkbig.thinkbig.UploadLecturerDetails.UploadActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LecturerFragment extends Fragment {
    CardView card_list_lecturer, card_add_lecturer, card_lect;
    LinearLayout linearLayout;

    public LecturerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lecturer, container, false);
        card_list_lecturer = view.findViewById(R.id.card_list_lecturer);
        card_lect = view.findViewById(R.id.card_lect);
        linearLayout = view.findViewById(R.id.lecte);
        card_lect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(linearLayout, "Choose one of the functions below", Snackbar.LENGTH_SHORT).show();
            }
        });
        card_list_lecturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LecturerList.class);
                startActivity(intent);
            }
        });
        card_add_lecturer = view.findViewById(R.id.card_add_lecturer);
        card_add_lecturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadActivity.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
