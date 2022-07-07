package com.cathythome.sis.Ponctuels;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cathythome.sis.MainActivity;
import com.cathythome.sis.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Ponctuels implements SeekBar.OnSeekBarChangeListener {

    private RecyclerView enceintesIndiv, enceintesGroupe;
    private ArrayList enceintesIndivList, sonsEnceintesIndivList, volumesEnceintesIndivList,stateIndivList;
    private IndividualAdapter individualAdapter;
    private GroupeAdapter groupeAdapter;
    private MainActivity mainActivity;
    private ArrayList[] donnees;
    private Integer typeGroupe;
    private TextView textGeneralVolume;
    private SeekBar generalVolume;
    private LinearLayout volume;


    public Ponctuels(MainActivity mainActivity, ArrayList[] donnees) {
        this.mainActivity = mainActivity;
        this.donnees = donnees;

        this.textGeneralVolume = (TextView) this.mainActivity.findViewById(R.id.volumeDataGen);
        this.generalVolume = (SeekBar) this.mainActivity.findViewById(R.id.volumeGen);
        this.generalVolume.setOnSeekBarChangeListener(this);

        this.volume = this.mainActivity.findViewById(R.id.volumeGenLayout);

        //enceintes individuelles
        enceintesIndivList = new ArrayList<String>();
        sonsEnceintesIndivList = new ArrayList<String>();
        volumesEnceintesIndivList = new ArrayList<Integer>();
        stateIndivList = new ArrayList<Boolean>();

        setView(this.donnees);
        if (this.typeGroupe != 0){
            this.volume.setVisibility(View.GONE);
        }

        enceintesIndiv = (RecyclerView) this.mainActivity.findViewById(R.id.RVsoundsIndiv);
        this.individualAdapter = new IndividualAdapter(this.mainActivity, this.mainActivity, this, enceintesIndivList, sonsEnceintesIndivList, volumesEnceintesIndivList, stateIndivList);
        this.enceintesIndiv.setAdapter(this.individualAdapter);
        this.enceintesIndiv.setLayoutManager(new LinearLayoutManager(this.mainActivity));

        enceintesGroupe = (RecyclerView) this.mainActivity.findViewById(R.id.RVsoundsGroupe);
        this.groupeAdapter = new GroupeAdapter(this.mainActivity, this.mainActivity, this, this.typeGroupe);
        this.enceintesGroupe.setAdapter(this.groupeAdapter);
        this.enceintesGroupe.setLayoutManager(new LinearLayoutManager(this.mainActivity));
    }

    public void setView(ArrayList[] donnees){
        //on me donne une liste de nombres, je décompose pour afficher mes enceintes individuelles
        String listeNombre = String.valueOf(donnees[0].get(1));
        if(listeNombre != ""){
            for(int i = 0; i < listeNombre.length(); i++) {
                enceintesIndivList.add(String.valueOf(listeNombre.charAt(i)));
                sonsEnceintesIndivList.add("Glisser un son ici");
                volumesEnceintesIndivList.add(15);
                stateIndivList.add(false);
            }
        }
        this.typeGroupe = Integer.parseInt((String) donnees[0].get(0));
    }

    public void update(int id, ArrayList liste) {
        switch (id) {
            case 1:
                this.sonsEnceintesIndivList = liste;
                break;
            case 2:
                this.volumesEnceintesIndivList = liste;
                break;
            case 4:
                this.stateIndivList = liste;
                break;
        }
        this.individualAdapter = new IndividualAdapter(this.mainActivity, this.mainActivity,this, enceintesIndivList, sonsEnceintesIndivList, volumesEnceintesIndivList, stateIndivList);
        this.enceintesIndiv.setAdapter(this.individualAdapter);
    }

    public void setCompteurs(int[] compteurs){
        this.groupeAdapter.setCompteurs(compteurs);

    }

    private int progress;
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        this.progress = progress;
        this.textGeneralVolume.setText(String.valueOf(this.progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        this.update(2, new ArrayList(Arrays.asList(this.progress, this.progress, this.progress, this.progress, this.progress, this.progress, this.progress, this.progress)));
        for(int i=1; i<9; i++){
            mainActivity.send("solo", i, "volume", String.valueOf(this.progress));
        }
    }
}