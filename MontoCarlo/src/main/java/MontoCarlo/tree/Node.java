package MontoCarlo.tree;

import MontoCarlo.NodeState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Node<State extends NodeState> {
    State state;
    Node parent;
    List<Node<State>> childArray;


    public Node() throws IllegalAccessException, InstantiationException {
        this.state = ((Class<State>) state.getClass()).newInstance();
        childArray = new ArrayList<>();
    }



    public Node(State state ,Node parent,List<Node<State>> childArray) {
        this.state = state;
        this.parent  = parent;
        this.childArray = childArray;
    }

    public Node(State state) {
//TODO
    }
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node<State>> getChildArray() {
        return childArray;
    }

    public void setChildArray(List<Node<State>> childArray) {
        this.childArray = childArray;
    }



    public Node getChildWithMaxScore() {
        return Collections.max(this.childArray, Comparator.comparing(c -> {
            return c.getState().getVisitCount();
        }));
    }

    public Node DeepCopy() throws InstantiationException, IllegalAccessException {
        //TODO
/*
        Node<> newNode = new Node();

        this.childArray = new ArrayList<>();
        this.state = State.DeepCopy(this.getState());
        if (node.getParent() != null)
            this.parent = node.getParent();
        List<Node> childArray = node.getChildArray();
        for (Node child : childArray) {
            this.childArray.add(new Node(child));
        }
*/
  //      return newNode;
        return null;
    }



}