 package hamiltonpaths;
 
 import java.io.FileNotFoundException;
 import java.io.PrintStream;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 
 
 
 
 
 
 
 
 
 public class GenerateFullyConnectedGraphs
 {
   int vertex;
   String filename;
   
   public GenerateFullyConnectedGraphs(int vertex)
   {
     this.vertex = vertex;
     this.filename = ("" + vertex + "_Connected_Graph.txt");
     generate();
   }
   
   public GenerateFullyConnectedGraphs(int vertex, String filename) {
     this.vertex = vertex;
     this.filename = filename;
     generate();
   }
   
   public int getVertex() {
     return this.vertex;
   }
   
   public void setVertex(int vertex) {
     this.vertex = vertex;
   }
   
   public String getFilename() {
     return this.filename;
   }
   
 
  public void setFilename(String filename) { this.filename = filename; }
   
   public void generate() {
     try {
       PrintStream out = new PrintStream(this.filename);Throwable localThrowable3 = null;
       try { out.println("" + this.vertex + "_Connected_Graph");
         out.println(this.vertex);
         for (int i = 1; i <= this.vertex; i++) {
           out.print("" + -i);
           for (int j = i + 1; j <= this.vertex; j++) {
             out.print(" " + j);
           }
           out.print("\n");
         }
         out.println(0);
         out.close();
       }
       catch (Throwable localThrowable1)
       {
         localThrowable3 = localThrowable1;throw localThrowable1;
 
 
 
 
       }
       finally
       {
 
 
 
 
         if (out != null) if (localThrowable3 != null) try { out.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else out.close();
       } } catch (FileNotFoundException ex) { Logger.getLogger(GenerateFullyConnectedGraphs.class.getName()).log(Level.SEVERE, null, ex);
     }
   }
   
   public static void main(String[] args) {
     new GenerateFullyConnectedGraphs(1024);
   }
 }
