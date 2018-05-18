package com.example.sahand.homework3;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
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

import java.util.ArrayList;
import java.util.Arrays;


public class MatchesFragment extends Fragment {

    private static final String ARG_DATA_SET = "matches";

    private ArrayList<Match> matches;

    public static String matchNames[];
    public static String matchPics[];
    public static String matchDescs[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(getArguments() != null) {
            matches = getArguments().getParcelableArrayList(ARG_DATA_SET);
        }

        return recyclerView;
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
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 18;
        private final String[] mMatches;
        private final String[] mMatchDesc;
        private final Drawable[] mMatchPictures;
        ContentAdapter(Context context) {
            Resources resources = context.getResources();
            mMatches = resources.getStringArray(R.array.places);
            mMatchDesc = resources.getStringArray(R.array.place_desc);
            TypedArray a = resources.obtainTypedArray(R.array.places_picture);
            mMatchPictures = new Drawable[a.length()];
            for (int i = 0; i < mMatchPictures.length; i++) {
                mMatchPictures[i] = a.getDrawable(i);
            }
            a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.picture.setImageDrawable(mMatchPictures[position % mMatchPictures.length]);
            holder.name.setText(mMatches[position % mMatches.length]);
            holder.description.setText(mMatchDesc[position % mMatchDesc.length]);
            holder.likeButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String toastText = "You liked " + holder.name.getText();
                    Toast.makeText(v.getContext(), toastText, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}