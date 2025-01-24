package com.example.myapplication;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;


public class newsFragment extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);
        TextView fillTitle = view.findViewById(R.id.fill_me1);
        TextView fillDescription = view.findViewById(R.id.fill_me2);
        TextView fillLink = view.findViewById(R.id.fill_me3);
        TextView fillDate = view.findViewById(R.id.fill_me4);

        fillTitle.setText(getArguments().getString("title"));
        fillDescription.setText(getArguments().getString("description"));
        fillLink.setText(getArguments().getString("link"));
        fillDate.setText(getArguments().getString("date"));


        return view ;
    }
}