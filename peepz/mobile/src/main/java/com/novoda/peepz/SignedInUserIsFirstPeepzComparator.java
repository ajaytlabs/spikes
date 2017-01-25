package com.novoda.peepz;

import java.util.Comparator;

class SignedInUserIsFirstPeepzComparator implements Comparator<Peep> {

    private final String signedInUserUid;

    SignedInUserIsFirstPeepzComparator(String signedInUserUid) {
        this.signedInUserUid = signedInUserUid;
    }

    @Override
    public int compare(Peep peep1, Peep peep2) {
        if (peep1.id().equals(signedInUserUid)) {
            return 1;
        } else if (peep2.id().equals(signedInUserUid)) {
            return -1;
        } else {
            return 0;
        }
    }

}
