package com.example.sahand.homework3;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.function.Consumer;

public class FirebaseDataModel {

    private DatabaseReference mDatabase;
    private HashMap<DatabaseReference, ValueEventListener> listeners;

    public FirebaseDataModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listeners = new HashMap<>();
    }
//
//    public void addTodoItem(TodoItem item) {
//        DatabaseReference todoItemsRef = mDatabase.child("todoItems");
//        todoItemsRef.push().setValue(item);
//    }

    public void likeMatch(Match match) {
        DatabaseReference matchesRef = mDatabase.child("matches");
        matchesRef.child(match.getUid()).child("liked").setValue(true);
    }

    public void unlikeMatch(Match match) {
        DatabaseReference matchesRef = mDatabase.child("matches");
        matchesRef.child(match.getUid()).child("liked").setValue(false);
    }

    public void getMatches(Consumer<DataSnapshot> dataChangedCallback, Consumer<DatabaseError> dataErrorCallback) {
        DatabaseReference matchesRef = mDatabase.child("matches");
        ValueEventListener matchesListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataChangedCallback.accept(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dataErrorCallback.accept(databaseError);
            }
        };
        matchesRef.addValueEventListener(matchesListener);
        listeners.put(matchesRef, matchesListener);
    }
//
//    public void updateTodoItemById(TodoItem item) {
//        DatabaseReference todoItemsRef = mDatabase.child("todoItems");
//        todoItemsRef.child(item.uid).setValue(item);
//    }

    public void clear() {
        // Clear all the listeners onPause
        listeners.forEach(Query::removeEventListener);
    }
}
