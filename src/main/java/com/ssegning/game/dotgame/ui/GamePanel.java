package com.ssegning.game.dotgame.ui;

import com.ssegning.game.dotgame.model.Loadable;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
@Loadable("gamePanel")
public class GamePanel extends JPanel {
    public GamePanel() {
        super(new FlowLayout());
        log.info("Created game panel");
    }
}
