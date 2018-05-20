package com.example.sahand.homework3;

import android.content.Context;
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


public class MatchesFragment extends Fragment {

    private FirebaseDataModel firebaseDataModel = new FirebaseDataModel();
    private ArrayList<Match> matches = new ArrayList<>();

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);

        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return recyclerView;
    }

    public void updateMatches(ArrayList<Match> matches) {
        this.matches = matches;

        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        ContentAdapter(Context context) {}

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            int size = matches.size();
            if(size > 0){
                String url = matches.get(position % size).getImageUrl();
                Picasso.get().load(url).into(holder.picture);
                holder.name.setText(matches.get(position % size).getName());
                holder.name.setTag(matches.get(position % size).getUid());
                holder.description.setText(matches.get(position % size).getDescription());
                holder.likeButton.setOnClickListener(v -> {
                    String toastText = "You liked " + holder.name.getText();
                    Toast.makeText(v.getContext(), toastText, Toast.LENGTH_SHORT).show();
                    firebaseDataModel.likeMatch(holder.name.getTag().toString());
                });
            }
        }


        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}