package com.ssegning.game.dotgame.model;

public interface Content {
    String getId();

    ContentType getType();

    String getName();

    boolean isPlayable();
}
