package com.ssegning.game.dotgame.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentType {
    VERTICAL_LINE("|", 0),
    HORIZONTAL_LINE("_", 1),
    CROSS("x", 2),
    EMPTY(" ", 3),
    ;

    private final String symbol;
    private final int order;
}
