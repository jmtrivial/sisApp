package com.cathythome.sis.Ambiants;

import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cathythome.sis.MainActivity;
import com.cathythome.sis.R;

import java.util.ArrayList;

public class Ambiants implements CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener, View.OnDragListener, View.OnClickListener {

    private TextView volumeData, sons;
    private Switch onOff;
    private SeekBar volume;
    private MainActivity mainActivity;
    private LinearLayout layout;
    private String state;
    private Button delete;
    private int volumeAmbiant;


    public Ambiants(MainActivity mainActivity){
        this.mainActivity = mainActivity;

        this.state = "off";
        this.volumeAmbiant = 15;

        volumeData = (TextView) this.mainActivity.findViewById(R.id.volumeData);
        sons = (TextView) this.mainActivity.findViewById(R.id.AmbiantSounds);
        delete = this.mainActivity.findViewById(R.id.delete);
        delete.setVisibility(View.GONE);
        delete.setOnClickListener(this);

        onOff = (Switch) this.mainActivity.findViewById(R.id.onOff);
        onOff.setOnCheckedChangeListener(this);

        volume = (SeekBar) this.mainActivity.findViewById(R.id.volume);
        volume.setOnSeekBarChangeListener(this);

        this.layout = this.mainActivity.findViewById(R.id.ambiants);
        this.layout.setOnDragListener(this);
    }

    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                //no action necessary
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                v.setBackgroundColor(Color.parseColor("#808080"));
                break;
            case DragEvent.ACTION_DRAG_EXITED:
            case DragEvent.ACTION_DRAG_ENDED:
                v.setBackgroundColor(Color.parseColor("#C7E3FF"));
                break;
            case DragEvent.ACTION_DROP:
                v.setBackgroundColor(Color.parseColor("#C7E3FF"));
                //handle the dragged view being dropped over a drop view
                String string = (String) event.getLocalState();
                //view being dragged and dropped
                String dropped = (String) string;
                //update the text in the target view to reflect the data being dropped
                if(sons.getText().equals("Glisser un son ici")) {
                    sons.setText(dropped);
                    delete.setVisibility(View.VISIBLE);
                    mainActivity.send("ambiant", 0, "son", dropped);
                }
                break;
            default:
                break;
        }
        return true;
    }

    private int progress;
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        this.progress = progress;
        volumeData.setText(String.valueOf(this.progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        this.volumeAmbiant = this.progress;
        volumeData.setText(String.valueOf(this.progress));
        mainActivity.send("ambiant", 0, "volume", String.valueOf(this.volumeAmbiant));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            this.state = "on";
        }
        else {
            this.state = "off";
        }
        mainActivity.send("ambiant", 0, "state", this.state);
    }

    @Override
    public void onClick(View view) {
        sons.setText("Glisser un son ici");
        delete.setVisibility(View.GONE);
        mainActivity.send("ambiant", 0, "son", "clear");
    }
}