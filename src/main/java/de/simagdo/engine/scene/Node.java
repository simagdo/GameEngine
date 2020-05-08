package de.simagdo.engine.scene;

import de.simagdo.math.Transform;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private Node parent;
    private List<Node> children;
    private Transform worldTransformation;
    private Transform localTransformation;

    public Node() {
        this.setWorldTransformation(new Transform());
        this.setLocalTransformation(new Transform());
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

    public void cleanUp() {
        this.children.forEach(Node::cleanUp);
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

    public Transform getWorldTransformation() {
        return worldTransformation;
    }

    public void setWorldTransformation(Transform worldTransformation) {
        this.worldTransformation = worldTransformation;
    }

    public Transform getLocalTransformation() {
        return localTransformation;
    }

    public void setLocalTransformation(Transform localTransformation) {
        this.localTransformation = localTransformation;
    }

    @Override
    public String toString() {
        return "Node{" +
                "parent=" + parent +
                ", children=" + children +
                ", worldTransformation=" + worldTransformation +
                ", localTransformation=" + localTransformation +
                '}';
    }
}
