package de.simagdo.engine;

public class Game {

    protected CoreEngine engine;

    public Game() {
        this.engine = new CoreEngine();
    }

    public void launch() {
        this.engine.start();
    }

    public void init() {
        this.engine.init();
    }

    public CoreEngine getEngine() {
        return engine;
    }

    public void setEngine(CoreEngine engine) {
        this.engine = engine;
    }

    @Override
    public String toString() {
        return "Game{" +
                "engine=" + engine +
                '}';
    }
}
