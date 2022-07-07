package com.cathythome.sis.Ponctuels;

import static com.cathythome.sis.R.*;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.cathythome.sis.MainActivity;

public class GroupeAdapter extends RecyclerView.Adapter<GroupeAdapter.ViewHolder> {
    private Integer groupes, max;
    private Long debut, fin;
    private ImageButton bouton1, bouton2, bouton3, bouton4, bouton5, bouton6, bouton7, bouton8, bouton9, bouton10, bouton11, bouton12, bouton13, bouton14, boutonStop;
    private LayoutInflater inflater;
    private LinearLayout linearLayout;
    private Context context;
    private TextView textVolume, cpt1, cpt2, cpt3, cpt4, cpt5, cpt6, cpt7, cpt8, cpt9, cpt10, cpt11, cpt12, cpt13, cpt14;
    private View view;
    private ViewHolder viewHolder;
    public Ponctuels main;
    private SeekBar volume;
    private MainActivity mainActivity;
    private Switch onOff;
    private ImageButton[] buttons;
    private Integer[] colours;
    private Boolean[] trajectoires;
    private TextView[] cpts;
    private MyView myView;

    // data is passed into the constructor
    public GroupeAdapter(Context context, MainActivity mainActivity, Ponctuels main, Integer groupes) {
        this.inflater = LayoutInflater.from(context);
        this.groupes = groupes;
        this.context = context;
        this.main = main;
        this.mainActivity = mainActivity;
        this.debut = Long.valueOf(0);
        this.fin = Long.valueOf(0);
        this.trajectoires = new Boolean[]{false, false, false, false, false, false, false, false, false, false, false, false};
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(this.groupes == 2){
            view = inflater.inflate(layout.carrefour, parent, false);
            linearLayout = view.findViewById(id.image);
            myView = new MyView(mainActivity, 0);
        }
        else {
            view = inflater.inflate(layout.marche, parent, false);
            linearLayout = view.findViewById(id.image);
            myView = new MyView(mainActivity, 1);
        }
        linearLayout.addView(myView);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(this.groupes == 2) {
            this.max = 12;
            this.buttons = new ImageButton[]{bouton1, bouton2, bouton3, bouton4, bouton5, bouton6, bouton7, bouton8, bouton9, bouton10, bouton11, bouton12};
            this.colours = new Integer[]{Color.rgb(158, 67, 230), Color.rgb(112, 173, 71), Color.rgb(237, 125, 49), Color.rgb(237, 125, 49), Color.rgb(68, 114, 196), Color.rgb(230, 125, 183), Color.rgb(230, 125, 183), Color.rgb(112, 173, 71), Color.rgb(255, 192, 2), Color.rgb(255, 192, 2), Color.rgb(68, 114, 196), Color.rgb(158, 67, 230)};
        }
        else {
            this.max = 2;
            this.buttons = new ImageButton[]{bouton13, bouton14};
            this.colours = new Integer[]{Color.rgb(112, 173, 71), Color.rgb(112, 173, 71)};
        }
        for(int i=0; i<this.max; i++){
            int finalI = i;
            ImageButton[] finalButtons = this.buttons;
            this.buttons[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_DOWN) {
                        debut = System.currentTimeMillis();
                        finalButtons[finalI].setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                    }
                    return false;
                }
            });
            finalButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fin = System.currentTimeMillis();
                    if(trajectoires[finalI] == true){
                        finalButtons[finalI].setBackgroundTintList(ColorStateList.valueOf(colours[finalI]));
                        mainActivity.send("groupe", 0, "automatique", String.valueOf(finalI + 1));
                        trajectoires[finalI] = false;
                        Log.d("fin", "fin");
                    }
                    else {
                        if (fin - debut > 300) {
                            finalButtons[finalI].setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                            mainActivity.send("groupe", 1, "automatique", String.valueOf(finalI + 1));
                            trajectoires[finalI] = true;
                            Log.d("click", "long click");
                        } else {
                            finalButtons[finalI].setBackgroundTintList(ColorStateList.valueOf(colours[finalI]));
                            mainActivity.send("groupe", 0, "trajectoire", String.valueOf(finalI + 1));
                        }
                    }
                }
            });
        }

        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            private int progress;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                this.progress = progress;
                textVolume.setText(String.valueOf(this.progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textVolume.setText(String.valueOf(this.progress));
                mainActivity.send("groupe", 0, "volume", String.valueOf(this.progress));
            }
        });

        boutonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.send("groupe", 0, "stop", "stop");
                textVolume.setText(String.valueOf(0));
                volume.setProgress(0);

            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return groupes;
    }

    public void setCompteurs(int[] compteurs){
        this.cpts = new TextView[]{cpt1, cpt2, cpt3, cpt4, cpt5, cpt6, cpt7, cpt8, cpt9, cpt10, cpt11, cpt12, cpt13, cpt14};
        for(int i=0; i<12; i++){
            if(i == 0 && this.cpts[12] != null){
                this.cpts[12].setText(String.valueOf(compteurs[i]));
            }
            else if(i == 1 && this.cpts[13] != null){
                this.cpts[13].setText(String.valueOf(compteurs[i]));
            }
            else if(this.cpts[i] != null) {
                System.out.println(compteurs[i]);
                this.cpts[i].setText(String.valueOf(compteurs[i]));
            }
        }
        if(view != null){ mainActivity.setAlpha(compteurs);
        }
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
            if(groupes == 2){
                textVolume = itemView.findViewById(id.generalVolumeText2);
            }
            else {
                textVolume = itemView.findViewById(id.generalVolumeText1);
            }
            //textVolume = itemView.findViewById(id.generalVolumeText);
            volume = itemView.findViewById(id.generalVolume);
            bouton1 = itemView.findViewById(id.btn1);
            bouton2 = itemView.findViewById(id.btn2);
            bouton3 = itemView.findViewById(id.btn3);
            bouton4 = itemView.findViewById(id.btn4);
            bouton5 = itemView.findViewById(id.btn5);
            bouton6 = itemView.findViewById(id.btn6);
            bouton7 = itemView.findViewById(id.btn7);
            bouton8 = itemView.findViewById(id.btn8);
            bouton9 = itemView.findViewById(id.btn9);
            bouton10 = itemView.findViewById(id.btn10);
            bouton11 = itemView.findViewById(id.btn11);
            bouton12 = itemView.findViewById(id.btn12);
            bouton13 = itemView.findViewById(id.btn13);
            bouton14 = itemView.findViewById(id.btn14);
            boutonStop = itemView.findViewById(id.stop);
            cpt1 = itemView.findViewById(id.traj1);
            cpt2 = itemView.findViewById(id.traj2);
            cpt3 = itemView.findViewById(id.traj3);
            cpt4 = itemView.findViewById(id.traj4);
            cpt5 = itemView.findViewById(id.traj5);
            cpt6 = itemView.findViewById(id.traj6);
            cpt7 = itemView.findViewById(id.traj7);
            cpt8 = itemView.findViewById(id.traj8);
            cpt9 = itemView.findViewById(id.traj9);
            cpt10 = itemView.findViewById(id.traj10);
            cpt11 = itemView.findViewById(id.traj11);
            cpt12 = itemView.findViewById(id.traj12);
            cpt13 = itemView.findViewById(id.traj13);
            cpt14 = itemView.findViewById(id.traj14);
        }
    }
}
