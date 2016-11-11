package com.novoda.tpbot.human;

import com.novoda.support.Observable;
import com.novoda.support.Result;
import com.novoda.tpbot.human.socket.io.Move;

public interface HumanTpService {

    Observable<Result> connect();

    void move(Move move);

    void disconnect();

}
