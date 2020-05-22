package de.simagdo;

import de.simagdo.engine.Game;

public class GameEngine {

    public static void main(String[] args) {
        Game game = new Game();
        game.getEngine().createWindow(1920, 1080);
        game.init();
        game.launch();

    }

}
