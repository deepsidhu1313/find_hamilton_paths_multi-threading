package base;

public class JEdge {

    int id;

    private String label;

    private int sourceId;

    private int sinkId;

    private JNode sourceNode;

    private JNode sinkNode;

    public JEdge(int id) {
        this.id = id;
    }

    public JEdge(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public JEdge(int sourceId, int sinkId) {
         this.sourceId = sourceId;
         this.sinkId = sinkId;
    }

    public JEdge(int id, String label, JNode sourceNode, JNode sinkNode) {
         this.id = id;
         this.label = label;
         this.sourceNode = sourceNode;
        this.sinkNode = sinkNode;
        this.sourceId = sourceNode.getId();
         this.sinkId = sinkNode.getId();
    }

    public JEdge(int id, String label, int sourceId, int sinkId) {
         this.sourceId = sourceId;
         this.sinkId = sinkId;
         this.id = id;
        this.label = label;
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

    public int getSourceId() {
         return this.sourceId;
    }

    public void setSourceId(int sourceId) {
         this.sourceId = sourceId;
    }

    public int getSinkId() {
         return this.sinkId;
    }

    public void setSinkId(int sinkId) {
         this.sinkId = sinkId;
    }

    public JNode getSourceNode() {
         return this.sourceNode;
    }

    public void setSourceNode(JNode sourceNode) {
         this.sourceNode = sourceNode;
         this.sourceId = sourceNode.getId();
    }

    public JNode getSinkNode() {
         return this.sinkNode;
    }

    public void setSinkNode(JNode sinkNode) {
         this.sinkNode = sinkNode;
         this.sinkId = sinkNode.getId();
    }

    public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append("[ID = " + this.id + " from " + this.sourceId + " to " + this.sinkId + " ]");
         return sb.toString();
    }
}
