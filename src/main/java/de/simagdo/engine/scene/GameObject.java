package de.simagdo.engine.scene;

import java.util.HashMap;

public class GameObject extends Node {

    private HashMap<String, Component> components;

    public GameObject() {
        this.components = new HashMap<>();
    }

    public void input() {
        this.components.keySet().forEach(key -> this.components.get(key).input());

        super.input();
    }

    public void update() {
        this.components.keySet().forEach(key -> this.components.get(key).update());

        super.update();
    }

    public void render() {
        this.components.keySet().forEach(key -> this.components.get(key).render());

        super.render();
    }

    public void addComponent(String key, Component component) {
        component.setParent(this);
        this.components.put(key, component);
    }

    public HashMap<String, Component> getComponents() {
        return components;
    }

    public void setComponents(HashMap<String, Component> components) {
        this.components = components;
    }

}
