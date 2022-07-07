package com.cathythome.sis.Ponctuels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.cathythome.sis.MainActivity;
import com.cathythome.sis.R;

import java.util.ArrayList;

public class IndividualAdapter extends RecyclerView.Adapter<IndividualAdapter.ViewHolder> {
    private ArrayList<String> enceintes, sons;
    private ArrayList<Integer> volumes;
    private ArrayList<Boolean> states;
    private Button bouttonSuppr;
    private LayoutInflater inflater;
    private Context context;
    private TextView textEnceinte, textSon, textVolume, textGeneralVolume;
    private View view;
    private ViewHolder viewHolder;
    public Ponctuels main;
    private SeekBar volume, generalVolume;
    private MainActivity mainActivity;
    private Switch onOff;

    // data is passed into the constructor
    public IndividualAdapter(Context context, MainActivity mainActivity, Ponctuels main, ArrayList<String> enceintes, ArrayList<String> sons, ArrayList<Integer> volumes, ArrayList<Boolean> states) {
        this.inflater = LayoutInflater.from(context);
        this.enceintes = enceintes;
        this.sons = sons;
        this.volumes = volumes;
        this.states = states;
        this.context = context;
        this.main = main;
        this.mainActivity = mainActivity;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.individuel, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        textEnceinte.setText(enceintes.get(position));
        textSon.setText(sons.get(position));
        if(sons.get(position) == "Glisser un son ici"){
            bouttonSuppr.setVisibility(View.INVISIBLE);
        }
        else {
            bouttonSuppr.setVisibility(View.VISIBLE);
        }

        textVolume.setText(String.valueOf(volumes.get(position)));
        bouttonSuppr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sons.set(position, "Glisser un son ici");
                main.update(1, sons);
                mainActivity.send("solo", Integer.valueOf(enceintes.get(position)), "son", "clear");
            }
        });

        volume.setProgress(volumes.get(position));
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            private int progress;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {this.progress = progress; }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                volumes.set(position, this.progress);
                main.update(2, volumes);
                mainActivity.send("solo", Integer.valueOf(enceintes.get(position)), "volume", String.valueOf(this.progress));
            }
        });

        textSon.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        //no action necessary
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        v.setBackgroundColor(Color.GRAY);
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                    case DragEvent.ACTION_DRAG_ENDED:
                        v.setBackgroundColor(Color.parseColor("#FFEDC7"));
                        break;
                    case DragEvent.ACTION_DROP:
                        v.setBackgroundColor(Color.parseColor("#FFEDC7"));
                        //handle the dragged view being dropped over a drop view
                        String string = (String) event.getLocalState();
                        //view being dragged and dropped
                        String dropped = (String) string;
                        //update the text in the target view to reflect the data being dropped
                        sons.set(position, dropped);
                        main.update(1, sons);
                        mainActivity.send("solo", Integer.valueOf(enceintes.get(position)), "son", dropped);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        onOff.setChecked(states.get(position));
        onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                states.set(position, isChecked);
                main.update(4, states);
                String state;
                if (isChecked){
                    state = "on";
                }
                else {
                    state = "off";
                }
                mainActivity.send("solo", Integer.valueOf(enceintes.get(position)), "state", state);
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return enceintes.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
            textEnceinte = itemView.findViewById(R.id.numero);
            textSon = itemView.findViewById(R.id.soundIndiv);
            textVolume = itemView.findViewById(R.id.volumeDataIndiv);
            bouttonSuppr = itemView.findViewById(R.id.deleteIndiv);
            volume = itemView.findViewById(R.id.volumeIndiv);
            onOff = itemView.findViewById(R.id.onOffIndiv);
        }
    }
}
