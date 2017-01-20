package com.novoda.peepz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.firebase.database.DataSnapshot;

class Converter {

    private static final String IMAGE_PREFIX = "data:image/webp";

    public Peep convert(DataSnapshot dataSnapshot) {
        ApiPeep value = dataSnapshot.getValue(ApiPeep.class);

        byte[] decodedString = Base64.decode(value.image, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, IMAGE_PREFIX.length(), decodedString.length - IMAGE_PREFIX.length());

        return new Peep(value.uid, new OptionalName(value.name), bitmap, new OptionalTimestamp(value.timestamp));
    }
}
