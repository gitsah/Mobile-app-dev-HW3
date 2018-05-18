package com.example.sahand.homework3;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.util.DiffUtil;
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
import java.util.List;


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
        private final List<Match> mMatchesList = new ArrayList<>();
        private String[] mMatches;
        private String[] mMatchDescs;
        private Drawable[] mMatchPictures;
        ContentAdapter(Context context) {
            Resources resources = context.getResources();
//            mMatches = resources.getStringArray(R.array.places);
//            mMatchDescs = resources.getStringArray(R.array.place_desc);

            updateLists();
            TypedArray a = resources.obtainTypedArray(R.array.places_picture);
            mMatchPictures = new Drawable[a.length()];
            for (int i = 0; i < mMatchPictures.length; i++) {
                mMatchPictures[i] = a.getDrawable(i);
            }
            a.recycle();
        }

        private void updateLists () {
            String[] tempMatches = new String[mMatchesList.size()];
            String[] tempDescs = new String[mMatchesList.size()];
            String[] tempPictures = new String[mMatchesList.size()];
            for(int i = 0; i < mMatchesList.size(); i++) {
                tempMatches[i] = mMatchesList.get(i).getName();
                tempDescs[i] = mMatchesList.get(i).getDescription();
                tempPictures[i] = mMatchesList.get(i).getImageUrl();
            }

            mMatches = tempMatches;
            mMatchDescs = tempDescs;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position, List<Object> payloads) {
            holder.picture.setImageDrawable(mMatchPictures[position % mMatchPictures.length]);
            holder.name.setText(mMatches[position % mMatches.length]);
            holder.description.setText(mMatchDescs[position % mMatchDescs.length]);
            holder.likeButton.setOnClickListener(v -> {
                String toastText = "You liked " + holder.name.getText();
                Toast.makeText(v.getContext(), toastText, Toast.LENGTH_SHORT).show();
            });
        }

        public void updateMatchesListItems(List<Match> matches) {
            final MatchesDiffCallback diffCallback = new MatchesDiffCallback(this.mMatchesList, matches);
            final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

            this.mMatchesList.clear();
            this.mMatchesList.addAll(matches);
            diffResult.dispatchUpdatesTo(this);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}