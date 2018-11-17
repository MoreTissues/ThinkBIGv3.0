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

import com.thinkbig.thinkbig.Chat.ChatActivity;
import com.thinkbig.thinkbig.GoogleMap.GoogleMapActivity;
import com.thinkbig.thinkbig.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OthersFragment extends Fragment {
    CardView card_map, card_chat, card_other;
    LinearLayout linearLayout;

    public OthersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_others, container, false);
        card_map = view.findViewById(R.id.card_map);
        card_other = view.findViewById(R.id.card_other);
        linearLayout = view.findViewById(R.id.othere);
        card_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(linearLayout, "Choose one of the functions below", Snackbar.LENGTH_SHORT).show();
            }
        });
        card_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GoogleMapActivity.class);
                startActivity(intent);
            }
        });
        card_chat = view.findViewById(R.id.card_chat);
        card_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
