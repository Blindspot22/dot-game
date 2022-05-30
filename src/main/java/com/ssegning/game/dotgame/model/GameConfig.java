package com.ssegning.game.dotgame.model;

import java.util.Collection;

public interface GameConfig {

    String getId();

    Collection<Player> getPlayers();

    Content[][] getContents();

    void setContent(Content content, int x, int y);

    void makeAction(Player player, Content content);

}
