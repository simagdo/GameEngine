package de.simagdo;

import de.simagdo.engine.Game;

import java.io.*;

public class GameEngine {

    public static void main(String[] args) throws IOException {
        Game game = new Game();
        game.getEngine().createWindow(1280, 720);
        game.init();
        game.launch();

        /*ClassLoader loader = new GameEngine().getClass().getClassLoader();
        BufferedReader reader = new BufferedReader(new InputStreamReader(loader.getResourceAsStream("models/dome/dome.obj")));
        String line;
        while ((line = reader.readLine()) != null)
            System.out.println(line);*/

    }

}
