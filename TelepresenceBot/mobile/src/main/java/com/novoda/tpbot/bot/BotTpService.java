package com.novoda.tpbot.bot;

import com.novoda.support.Observable;
import com.novoda.support.Result;
import com.novoda.tpbot.human.controller.Direction;

public interface BotTpService {

    Observable<Result> connect();

    Observable<Direction> listen();

    void disconnect();

}
