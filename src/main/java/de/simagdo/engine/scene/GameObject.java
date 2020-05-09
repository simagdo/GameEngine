package de.simagdo.engine.scene;

import de.simagdo.math.Transform;

import java.util.HashMap;

public class GameObject {

    private HashMap<String, Component> components;
    private Transform transform;

    public GameObject() {
        this.components = new HashMap<>();
        this.transform = new Transform();
    }

    public void input() {
        this.components.keySet().forEach(key -> this.components.get(key).input());
    }

    public void update() {
        for (String key : this.components.keySet()) this.components.get(key).update();
    }

    public void render() {
        for (String key : this.components.keySet()) this.components.get(key).render();
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

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }
}
