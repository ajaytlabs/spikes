package com.novoda.peepz;

import com.google.firebase.database.DataSnapshot;

class Converter {

    public Peep convert(DataSnapshot dataSnapshot) {
        ApiPeep value = dataSnapshot.getValue(ApiPeep.class);
        ApiImage apiImage = dataSnapshot.child("image").getValue(ApiImage.class);

        return new Peep(
                value.uid,
                value.name,
                convert(apiImage),
                value.lastSeen
        );
    }

    private Image convert(ApiImage apiImage) {
        return new Image(apiImage.payload, apiImage.timestamp);
    }

}
