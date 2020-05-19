package de.simagdo.engine.scene;

import de.simagdo.math.Transform;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private Node parent;
    private List<Node> children;
    private Transform worldTransform;
    private Transform localTransform;

    public Node() {
        this.setWorldTransform(new Transform());
        this.setLocalTransform(new Transform());
        this.setChildren(new ArrayList<>());
    }

    public void addChild(Node child) {
        child.setParent(this);
        this.children.add(child);
    }

    public void update() {
        this.children.forEach(Node::update);
    }

    public void input() {
        this.children.forEach(Node::input);
    }

    public void render() {
        this.children.forEach(Node::render);
    }

    public void shutdown() {
        this.children.forEach(Node::shutdown);
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Transform getWorldTransform() {
        return worldTransform;
    }

    public void setWorldTransform(Transform worldTransform) {
        this.worldTransform = worldTransform;
    }

    public Transform getLocalTransform() {
        return localTransform;
    }

    public void setLocalTransform(Transform localTransform) {
        this.localTransform = localTransform;
    }
}
