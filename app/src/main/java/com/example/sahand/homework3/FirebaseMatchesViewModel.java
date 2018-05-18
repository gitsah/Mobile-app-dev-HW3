package com.example.sahand.homework3;

import com.google.firebase.database.DataSnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FirebaseMatchesViewModel {

    private FirebaseDataModel dataModel;
    private ArrayList<Match> matches;

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public FirebaseMatchesViewModel() {
        dataModel = new FirebaseDataModel();
    }

    public void likeMatch(String uid) {
        dataModel.likeMatch(uid);
    }

    public void unlikeMatch(String uid) {
        dataModel.unlikeMatch(uid);
    }

    public void getMatches(Consumer<ArrayList<Match>> responseCallback) {
        dataModel.getMatches(
                (DataSnapshot dataSnapshot) -> {
                    ArrayList<Match> matches = new ArrayList<>();
                    for (DataSnapshot matchSnapshot : dataSnapshot.getChildren()) {
                        Match match = matchSnapshot.getValue(Match.class);
                        assert match != null;
                        matches.add(match);
                    }
                    responseCallback.accept(matches);
                },
                (databaseError -> System.out.println("Error reading Matches: " + databaseError))
        );
    }

    public void clear() {
        dataModel.clear();
    }
}
