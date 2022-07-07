package com.cathythome.sis.Sons;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cathythome.sis.MainActivity;
import com.cathythome.sis.R;

import java.util.ArrayList;

public class Sons {

    private RecyclerView listeSonsAmbiants, listeSonsPonctuels;
    private ArrayList<String> sonsAmbiantsList, sonsPonctuelsList;
    private ListSoundAdapter listeSonsAmbiantsAdapter, listeSonsPonctuelsAdapter;
    private ArrayList[] donnees;
    private MainActivity main;

    public Sons(MainActivity mainActivity, ArrayList[] donnees) {
        sonsAmbiantsList = new ArrayList<String>();
        sonsPonctuelsList = new ArrayList<String>();
        this.donnees = donnees;
        this.main = mainActivity;

        setting(1, this.sonsAmbiantsList, this.listeSonsAmbiants, this.listeSonsAmbiantsAdapter, R.id.listeSonsAmbiants);
        setting(2, this.sonsPonctuelsList, this.listeSonsPonctuels, this.listeSonsPonctuelsAdapter, R.id.listeSonsPonctuels);
    }

    private void setting(int id, ArrayList list, RecyclerView recycler, ListSoundAdapter adapter, int view){
        for(int i=0; i<this.donnees[id].size(); i++){
            list.add(String.valueOf(this.donnees[id].get(i)));
        }

        recycler = (RecyclerView) this.main.findViewById(view);
        adapter = new ListSoundAdapter(this.main, list);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this.main));
    }
}
