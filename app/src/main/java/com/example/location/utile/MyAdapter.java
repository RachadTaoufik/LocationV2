package com.example.location.utile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.location.R;
import com.example.location.model.Offre;

import java.util.LinkedList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private LinkedList<Offre> offres;
    private Context context;
    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(LinkedList<Offre> offres, Context context) {
        this. offres = new LinkedList<Offre>() ;
        this. offres.addAll( offres );
        this.context=context;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
// create a new view
        View itemLayoutView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.offre_item_layout,
                        parent, false);
        MyViewHolder vh = new MyViewHolder(itemLayoutView);
        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
// - get element from your dataset at this position
// - replace the contents of the view with that element
        holder.titre.setText(offres.get(position).getTitre());


// Reference to an image file in Cloud Storage



    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return offres.size();
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        public TextView titre;
        public ImageView photo;
        //……….. la suite des attributs
        // Context is a reference to the activity that contain the the recycler view
        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            titre =itemLayoutView.findViewById(R.id.offre_titre);
            photo= itemLayoutView.findViewById(R.id.offre_photo);
            itemLayoutView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

        }
    }
}
