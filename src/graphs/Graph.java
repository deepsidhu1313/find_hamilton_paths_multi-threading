 package graphs;
 
 import base.JEdge;
 import base.JNode;
 import datastructure.MyArray;
 import java.io.PrintStream;
 

 
 public class Graph
 {
   int id;
   String label;
   JNode root;
   MyArray<JNode> vertices = new MyArray();
   MyArray<JEdge> edges = new MyArray();
   
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
   
   public Graph() {}
   
   public Graph(int id)
   {
     this.id = id;
   }
   
   public Graph(int id, String label) {
     this.id = id;
     this.label = label;
   }
   
   public Graph(int id, String label, JNode root) {
     this.id = id;
     this.label = label;
     this.root = root;
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
   
   public JNode getRoot() {
     return this.root;
   }
   
   public void setRoot(JNode root) {
     this.root = root;
   }
   
   public void addVertex(JNode j) {
     boolean exist = false;
     for (int i = 0; i < this.vertices.size(); i++) {
       if (((JNode)this.vertices.get(i)).getId() == j.getId()) {
         exist = true;
         System.err.println("Vertex with Duplicate ID " + j.getId());
       }
     }
     
 
     if (!exist) {
       this.vertices.add(j);
     }
   }
   
   public JNode getVertex(int id) {
     for (int i = 0; i < this.vertices.size(); i++) {
       if (((JNode)this.vertices.get(i)).getId() == id) {
        return (JNode)this.vertices.get(i);
       }
     }
     
     return null;
   }
   
   public boolean vertexExist(int id) {
     for (int i = 0; i < this.vertices.size(); i++) {
       if (((JNode)this.vertices.get(i)).getId() == id) {
         return true;
       }
     }
      return false;
   }
   
   public void removeVertex(int id) {
     for (int i = 0; i < this.vertices.size(); i++) {
       if (((JNode)this.vertices.get(i)).getId() == id) {
         this.vertices.remove(i);
       }
     }
   }
   
   public void addEdge(JEdge j)
   {
     boolean exist = false;
     for (int i = 0; i < this.edges.size(); i++) {
       if (((JEdge)this.edges.get(i)).getId() == j.getId()) {
         exist = true;
         System.err.println("Edge with Duplicate ID " + j.getId());
       }
     }
     
      if (!exist) {
      this.edges.add(j);
     }
   }
   
   public JEdge getEdge(int id) {
     for (int i = 0; i < this.edges.size(); i++) {
       if (((JEdge)this.edges.get(i)).getId() == id) {
         return (JEdge)this.edges.get(i);
       }
     }
     
     return null;
   }
   
   public boolean edgeExist(int id)
   {
     for (int i = 0; i < this.edges.size(); i++) {
       if (((JEdge)this.edges.get(i)).getId() == id) {
         return true;
       }
     }
     
     return false;
   }
   
   public void removeEdge(int id) {
    for (int i = 0; i < this.edges.size(); i++) {
       if (((JEdge)this.edges.get(i)).getId() == id) {
         this.edges.remove(i);
       }
     }
   }
 }

