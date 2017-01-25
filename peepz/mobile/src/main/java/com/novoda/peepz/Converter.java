package com.novoda.peepz;

import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;

import java.util.concurrent.TimeUnit;

class Converter {

    private static final int MAX_AGE_MINUTES_FRESH = 6;
    private static final int MAX_AGE_MINUTES_STALE = 15;

    public Peep convert(DataSnapshot dataSnapshot) {
        ApiPeep value = dataSnapshot.getValue(ApiPeep.class);

        String payload = (String) value.image.get(ApiPeep.KEY_IMAGE_PAYLOAD);
        long timestamp = (long) value.image.get(ApiPeep.KEY_IMAGE_TIMESTAMP);

        return new Peep(
                value.uid,
                value.name,
                new Image(payload, timestamp),
                value.lastSeen,
                getOnlineStatusFor(value)
        );
    }

    private Peep.OnlineStatus getOnlineStatusFor(ApiPeep value) {
        long diffMillis = System.currentTimeMillis() - value.lastSeen;
        long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis);

        if (diffMinutes < MAX_AGE_MINUTES_FRESH) {
            return Peep.OnlineStatus.FRESH;
        } else if (diffMinutes < MAX_AGE_MINUTES_STALE) {
            return Peep.OnlineStatus.STALE;
        } else {
            return Peep.OnlineStatus.OFFLINE;
        }
    }

}
