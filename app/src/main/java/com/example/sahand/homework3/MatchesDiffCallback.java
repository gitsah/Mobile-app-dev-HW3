package com.example.sahand.homework3;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

public class MatchesDiffCallback extends DiffUtil.Callback {

    private final List<Match> mOldMatchesList;
    private final List<Match> mNewMatchesList;

    public MatchesDiffCallback(List<Match> oldmatchesList, List<Match> newMatchesList) {
        this.mOldMatchesList = oldmatchesList;
        this.mNewMatchesList = newMatchesList;
    }

    @Override
    public int getOldListSize() {
        return mOldMatchesList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewMatchesList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        final Match oldMatch = mOldMatchesList.get(oldItemPosition);
        final Match newMatch = mNewMatchesList.get(newItemPosition);

        return oldMatch.getUid().equals(newMatch.getUid());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Match oldMatch = mOldMatchesList.get(oldItemPosition);
        final Match newMatch = mNewMatchesList.get(newItemPosition);

        return oldMatch.getName().equals(newMatch.getName()) &&
                oldMatch.getDescription().equals(newMatch.getDescription()) &&
                oldMatch.getImageUrl().equals(newMatch.getImageUrl());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}