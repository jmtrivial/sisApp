package com.cathythome.sis;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WaitingFragment extends Fragment {

    private boolean active;
    private TextView waiting;

    public WaitingFragment(boolean active) {
        // Required empty public constructor
        this.active = active;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_waiting, container, false);
        this.waiting = view.findViewById(R.id.waitingText);
        return view;
    }

    public void setAffichage() {
        this.waiting.setText("En attente d'instructions de l'ordinateur...");
    }
}