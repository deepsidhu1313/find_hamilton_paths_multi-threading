package base;

import datastructure.MyArray;
import java.util.Comparator;

public class JNode {

    private MyArray<JNode> parents = new MyArray();
    private MyArray<JNode> children = new MyArray();
    private MyArray<JNode> neighbours = new MyArray();
    private MyArray<JEdge> edges = new MyArray();

    int id;

    String label;

    public JNode() {
    }

    public JNode(int id) {
        this.id = id;
    }

    public JNode(int id, String label) {
        this.id = id;
        this.label = label;
    }
    public static final Comparator<JNode> IdComparator = new Comparator<JNode>() {

        @Override
        public int compare(JNode o1, JNode o2) {
            return o1.id - o2.id;  // This will work because age is positive integer
        }

    };

    public MyArray<JNode> getParents() {
        return this.parents;
    }

    public void setParents(MyArray<JNode> parents) {
        this.parents = parents;
    }

    public MyArray<JNode> getChildren() {
        return this.children;
    }

    public void setChildren(MyArray<JNode> children) {
        this.children = children;
    }

    public MyArray<JEdge> getEdges() {
        return this.edges;
    }

    public void setEdges(MyArray<JEdge> edges) {
        this.edges = edges;
    }

    public boolean removeEdges(JEdge edge) {
        boolean b = this.edges.remove(edge);
        if (b) {
            findNeighbours();
        }
        return b;
    }

    public boolean removeEdges(int id) {
        int i = 0;
        if (i < this.edges.size()) {
            if (id == ((JEdge) this.edges.get(i)).getId()) {
            }

            boolean b = this.edges.removeByIndex(id);
            findNeighbours();
            return b;
        }

        return false;
    }

    public void addEdge(JEdge edge) {
        boolean exist = false;
        for (int i = 0; i < this.edges.size(); i++) {
            if (edge.getId() == ((JEdge) this.edges.get(i)).getId()) {

                exist = true;
            }
        }

        if (!exist) {
            this.edges.add(edge);
            findNeighbours();
        }
    }

    public boolean removeNeighbour(JNode node) {
        return this.neighbours.remove(node);
    }

    public boolean removeNeighbour(int id) {
        int i = 0;
        if (i < this.neighbours.size()) {
            if (id == ((JNode) this.neighbours.get(i)).getId()) {
            }

            return this.neighbours.removeByIndex(id);
        }

        return false;
    }

    public void addNeighbour(JNode node) {
        boolean exist = false;
        for (int i = 0; i < this.neighbours.size(); i++) {
            if (node.getId() == ((JNode) this.neighbours.get(i)).getId()) {

                exist = true;
            }
        }

        if (!exist) {
            this.neighbours.add(node);
        }
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public MyArray<JNode> getSibling() {
        MyArray<JNode> ret = new MyArray();
        for (int i = 0; i < this.parents.size(); i++) {
            MyArray<JNode> get = ((JNode) this.parents.get(i)).getChildren();
            for (int j = 0; j < get.size(); j++) {
                JNode get1 = (JNode) get.get(j);
                if (get1.getId() != this.id) {
                    ret.add(get1);
                }
            }
        }

        return ret;
    }

    public MyArray<JNode> getNeighbours() {
        return this.neighbours;
    }

    public void setNeighbours(MyArray<JNode> neighbours) {
        this.neighbours = neighbours;
    }

    public void findNeighbours() {
        for (int i = 0; i < this.edges.size(); i++) {
            JEdge get = (JEdge) this.edges.get(i);
            if (equals(get.getSourceNode())) {
                addNeighbour(get.getSinkNode());
            } else {
                addNeighbour(get.getSourceNode());
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Id =").append(this.id).append(" ");
        for (int i = 0; i < this.edges.size(); i++) {
            sb.append(((JEdge) this.edges.get(i)).getLabel()).append(",");
        }

        sb.append("]");

        return sb.toString();
    }
}
