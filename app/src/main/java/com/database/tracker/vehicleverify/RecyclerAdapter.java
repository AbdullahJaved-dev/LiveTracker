package com.database.tracker.vehicleverify;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<AppsViewHolder> {
    List<notifData> list;

    public RecyclerAdapter(List<notifData> list) {
        this.list = list;
    }

    @Override
    public AppsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notif_layout, viewGroup, false);

        AppsViewHolder AppsViewHolder = new AppsViewHolder(view);

        return AppsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AppsViewHolder viewHolder, final int i) {

        viewHolder.title.setText(list.get(i).getTitle());
        viewHolder.body.setText(list.get(i).getBody());


        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(i).link));
                view.getContext().startActivity(browserIntent);
            }
        });




    }

    @Override
    public int getItemCount() {

        return list.size();
    }
}

class AppsViewHolder extends RecyclerView.ViewHolder {

    TextView title, body, date;
    Button remove;
    RelativeLayout layout;

    public AppsViewHolder(@NonNull final View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.titleNotifId);
        body = itemView.findViewById(R.id.bodyNotifId);
        layout = itemView.findViewById(R.id.notifLay);
    }

}
