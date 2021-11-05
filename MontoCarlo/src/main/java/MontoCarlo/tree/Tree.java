package MontoCarlo.tree;

import lombok.Getter;
import lombok.Setter;

public class Tree {
    @Getter @Setter
    Node root;

    public Tree(Node root) {
        this.root = root;
    }

    public void addChild(Node parent, Node child) {
        parent.getChildArray().add(child);
    }

}