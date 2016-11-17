package com.novoda.tpbot.human;

import com.novoda.support.Observable;
import com.novoda.support.Result;
import com.novoda.tpbot.human.controller.Direction;

public interface HumanTpService {

    Observable<Result> connect();

    void moveIn(Direction direction);

    void disconnect();

}
