package com.ssegning.game.dotgame.ui;

import com.ssegning.game.dotgame.model.InitMethod;
import com.ssegning.game.dotgame.model.Loadable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.net.URL;
import java.util.Objects;

@Getter
@Slf4j
@Loadable("gameWindow")
public class GameWindow extends JFrame {

    private final GamePanel panel;

    public GameWindow(GamePanel panel) {
        super("Dot Game");
        this.panel = panel;
        this.setVisible(true);
        log.info("Created game window");
    }

    @InitMethod
    public void setup() {
        this.add(panel);
        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @InitMethod
    public void setupIconImage() {
        URL resource = GameWindow.class.getResource("/static/casino.gif");
        ImageIcon img = new ImageIcon(Objects.requireNonNull(resource));
        this.setIconImage(img.getImage());
    }
}
