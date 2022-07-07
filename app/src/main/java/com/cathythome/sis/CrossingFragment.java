package com.cathythome.sis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cathythome.sis.Ambiants.Ambiants;
import com.cathythome.sis.Sons.Sons;

import java.util.ArrayList;

public class CrossingFragment extends Fragment {

    private String data;
    private ArrayList[] donnees;
    private Sons sons;
    private Ambiants ambiants;
    private MainActivity mainActivity;

    public CrossingFragment() {
        // Required empty public constructor
    }
    public CrossingFragment(MainActivity activity, ArrayList[] donnees){
        this.donnees = donnees;
        this.mainActivity = activity;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrossingFragment newInstance() {
        CrossingFragment fragment = new CrossingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sons = new Sons(this.mainActivity, this.donnees);
        ambiants = new Ambiants(this.mainActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.carrefour, container, false);
    }
}