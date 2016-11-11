package com.novoda.tpbot.bot;

import com.novoda.support.Observable;
import com.novoda.support.Result;
import com.novoda.tpbot.human.socket.io.Move;

public interface BotTpService {

    Observable<Result> connect();

    Observable<Move> listen();

    void disconnect();

}
