package com.novoda.peepz;

import com.google.firebase.database.DataSnapshot;

class Converter {

    public Peep convert(DataSnapshot dataSnapshot) {
        ApiPeep value = dataSnapshot.getValue(ApiPeep.class);

        String payload = (String) value.image.get(ApiPeep.KEY_IMAGE_PAYLOAD);
        long timestamp = (long) value.image.get(ApiPeep.KEY_IMAGE_TIMESTAMP);

        return new Peep(
                value.uid,
                value.name,
                new Image(payload, timestamp),
                value.lastSeen
        );
    }

}
