 package base;
 
 import datastructure.MyArray;
 import java.util.ArrayList;
 
 
 
 
 
 
 
 
 
 
 public class Path
 {
   MyArray<JNode> vertices = new MyArray();
   MyArray<JEdge> edges = new MyArray();
   int id;
   String Label;
   boolean isHamilton = false;
   boolean isHamiltonCycle = false;
   boolean hasNoCycles = false;
   
   public boolean isHasNoCycles() {
     return this.hasNoCycles;
   }
   
   public void setHasNoCycles(boolean hasNoCycles) {
     this.hasNoCycles = hasNoCycles;
   }
   
 
   public Path() {}
   
   public Path(int id)
   {
     this.id = id;
   }
   
   public Path(int id, String Label) {
     this.id = id;
     this.Label = Label;
   }
   
   public int getId() {
     return this.id;
   }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public String getLabel() {
     return this.Label;
   }
   
   public void setLabel(String Label) {
     this.Label = Label;
   }
   
   public boolean IsHamilton() {
     return this.isHamilton;
   }
   
   public void IsHamilton(boolean isHamilton) {
     this.isHamilton = isHamilton;
   }
   
   public void addEdge(JEdge e) {
     if ((this.isHamilton) || (this.hasNoCycles)) {
       if (!this.vertices.contains(e.getSourceNode())) {
         this.vertices.add(e.getSourceNode());
       }
       if (!this.vertices.contains(e.getSinkNode())) {
         this.vertices.add(e.getSinkNode());
       }
       
       if (!this.edges.contains(e)) {
         this.edges.add(e);
       }
     } else {
       this.vertices.add(e.getSourceNode());
       this.vertices.add(e.getSinkNode());
       this.edges.add(e);
     }
   }
   
   public void removeEdge(JEdge e)
   {
     this.edges.remove(e);
     removeOrphanVertices();
   }
   
   public void removeOrphanVertices() {
     this.vertices.clear();
     for (int i = 0; i < this.edges.size(); i++) {
       JEdge e = (JEdge)this.edges.get(i);
       if ((this.isHamilton) || (this.hasNoCycles)) {
         if (!this.vertices.contains(e.getSourceNode())) {
           this.vertices.add(e.getSourceNode());
         }
         if (!this.vertices.contains(e.getSinkNode())) {
           this.vertices.add(e.getSinkNode());
         }
       }
       else {
         this.vertices.add(e.getSourceNode());
         this.vertices.add(e.getSinkNode());
       }
     }
   }
   
 
   public synchronized void addVertex(JNode jn)
   {
     if ((this.isHamilton) || (this.hasNoCycles)) {
       if (!this.vertices.contains(jn)) {
         this.vertices.add(jn);
       }
     } else {
       this.vertices.add(jn);
     }
   }
   
   public synchronized ArrayList<JNode> addVertex(MyArray<JNode> jn, int id) {
     boolean flag = false;
     ArrayList<JNode> ret = new ArrayList();
     for (int i = 0; i < jn.size(); i++) {
       if (((JNode)jn.get(i)).getId() == id) {
         flag = true;
       }
       if (flag) {
        ret.add(jn.get(i));
         break;
       }
       addVertex((JNode)jn.get(i));
     }
     
     return ret;
   }
   
   public synchronized void addVertex(MyArray<JNode> jn) {
     for (int i = 0; i < jn.size(); i++) {
       addVertex((JNode)jn.get(i));
     }
   }
   
   public boolean isHamiltonCycle()
   {
     return this.isHamiltonCycle;
   }
   
   public void isHamiltonCycle(boolean isHamiltonCycle) {
     this.isHamiltonCycle = isHamiltonCycle;
   }
   
   public MyArray<JNode> getVertices() {
     return this.vertices;
   }
   
   public void setVertices(MyArray<JNode> vertices) {
     this.vertices = vertices;
   }
   
   public MyArray<JEdge> getEdges() {
    return this.edges;
   }
   
   public void setEdges(MyArray<JEdge> edges) {
     this.edges = edges;
   }
   
   public String toStringVertexId() {
     StringBuilder sb = new StringBuilder("");
     
     if (this.vertices.size() > 1) {
       sb.append(((JNode)this.vertices.get(0)).getId());
       
       for (int i = 1; i < this.vertices.size(); i++) {
         sb.append("->").append(((JNode)this.vertices.get(i)).getId());
       }
       
       if (this.isHamiltonCycle) {
         sb.append("->" + ((JNode)this.vertices.get(0)).getId());
       }
     }
     else {
       for (int i = 1; i < this.vertices.size(); i++) {
         sb.append("->").append(((JNode)this.vertices.get(i)).getId());
       }
     }
     
 
     return sb.toString();
   }
   
   public String toStringVertexLabel() {
     StringBuilder sb = new StringBuilder("");
     
     if (this.vertices.size() > 1) {
       sb.append(((JNode)this.vertices.get(0)).getLabel());
       
       for (int i = 1; i < this.vertices.size(); i++) {
         sb.append("->").append(((JNode)this.vertices.get(i)).getLabel());
       }
       
       if (this.isHamiltonCycle) {
         sb.append("->").append(((JNode)this.vertices.get(0)).getLabel());
       }
     }
     
     return sb.toString();
   }
   
   public String toString()
   {
     return toStringVertexId();
   }
 }
