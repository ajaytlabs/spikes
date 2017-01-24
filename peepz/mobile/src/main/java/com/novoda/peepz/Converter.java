package com.novoda.peepz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;

class Converter {

    private static final String IMAGE_PREFIX = "data:image/webp";

    public Peep convert(DataSnapshot dataSnapshot) {
        ApiPeep value = dataSnapshot.getValue(ApiPeep.class);

        Bitmap bitmap = null;
        try {
            byte[] decodedString = Base64.decode(value.image, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(decodedString, IMAGE_PREFIX.length(), decodedString.length - IMAGE_PREFIX.length());
        } catch (IllegalArgumentException e) {
            Log.e("!!!", "gross, the bitmap was not in a good state");
        }

        return new Peep(value.uid, new OptionalString(value.name), bitmap, new OptionalTimestamp(value.timestamp));
    }
}
