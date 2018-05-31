package com.example.sahand.homework3;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;

public class MatchesFragment extends Fragment {

    private FirebaseDataModel firebaseDataModel = new FirebaseDataModel();
    private ArrayList<Match> matches = new ArrayList<>();

    private RecyclerView recyclerView;
    private ContentAdapter adapter;

    private double currentLat, currentLong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);


        adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return recyclerView;
    }


    public void updateMatches(ArrayList<Match> matches) {
        this.matches = matches;

        adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void updateLocation(double currentLat, double currentLong) {
        this.currentLat = currentLat;
        this.currentLong = currentLong;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;
        public ImageButton likeButton;
        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));
            picture = itemView.findViewById(R.id.card_image);
            name = itemView.findViewById(R.id.card_title);
            description = itemView.findViewById(R.id.card_text);
            likeButton = itemView.findViewById(R.id.like_button);
        }
    }

    /**
     * Adapter to display recycler view.
     */
    class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 18;


        ContentAdapter(Context context) { }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            int size = matches.size();

            System.out.println(currentLat);
            if(currentLat != 0) {
                Location locationA = new Location("point A");
                Location locationB = new Location("point B");
                locationA.setLatitude(currentLat);
                locationA.setLongitude(currentLong);


                for (Iterator<Match> iterator = matches.iterator(); iterator.hasNext(); ) {
                    Match match = iterator.next();
                    locationB.setLatitude(Double.parseDouble(match.getLat()));
                    locationB.setLongitude(Double.parseDouble(match.getLongitude()));
                    double distance = locationA.distanceTo(locationB) * 0.000621371;

                    System.out.println(distance);
                    if (distance > 10) {
                        iterator.remove();
                    }
                }
            }
            size = matches.size();

            if(size > 0){

                int index = position % matches.size();
                String url = matches.get(index).getImageUrl();
                Picasso.get().load(url).into(holder.picture);
                holder.name.setText(matches.get(index).getName());
                holder.name.setTag(matches.get(index).getUid());
                holder.description.setText(matches.get(index).getDescription());

                if(matches.get(index).getLiked()) {
                    holder.likeButton.setImageResource(R.drawable.ic_liked);
                    holder.likeButton.setTag(true);
                }
                else {
                    holder.likeButton.setImageResource(R.drawable.ic_unliked);
                    holder.likeButton.setTag(false);
                }

                holder.likeButton.setOnClickListener(v -> {
                    String toastText;

                    if(holder.likeButton.getTag().equals(true)) {
                        firebaseDataModel.unlikeMatch(holder.name.getTag().toString());
                        toastText = "You unliked " + holder.name.getText();
                    }
                    else {
                        firebaseDataModel.likeMatch(holder.name.getTag().toString());
                        toastText = "You liked " + holder.name.getText();
                    }

                    Toast.makeText(v.getContext(), toastText, Toast.LENGTH_SHORT).show();
                });
            }
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}