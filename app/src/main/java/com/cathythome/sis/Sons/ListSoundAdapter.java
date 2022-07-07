package com.cathythome.sis.Sons;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.cathythome.sis.R;

import java.util.ArrayList;

public class ListSoundAdapter extends RecyclerView.Adapter<ListSoundAdapter.ViewHolder> {
    private ArrayList<String> sounds;
    private LayoutInflater inflater;
    private Context context;
    TextView myTextView;
    private View view;

    // data is passed into the constructor
    public ListSoundAdapter(Context context, ArrayList<String> sounds) {
        this.inflater = LayoutInflater.from(context);
        this.sounds = sounds;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.soundlist, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = sounds.get(position);
        myTextView.setText(title);
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(holder.itemView);
                    //start dragging the item touched
                    holder.itemView.startDrag(data, shadowBuilder, sounds.get(position), 0);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return sounds.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.sound);
        }
    }
}
