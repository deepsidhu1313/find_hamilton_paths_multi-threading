package hamiltonpaths;

import base.JEdge;
import base.JNode;
import datastructure.MyArray;
import graphs.Graph;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenerateKnightsMoveGraphs {

    int row, column;
    String filename;
    JNode vertices[][];
    //MyArray<JNode> nodelist = new MyArray<>();
    ArrayList<JNode> nodelist = new ArrayList<>();
    int vertexCounter = 1;
    int edgeCounter = 1;
    Graph g = new Graph(0);

    public GenerateKnightsMoveGraphs(int row, int column) {
        this.row = row;
        this.column = column;
        vertices = new JNode[row][column];
        this.filename = ("" + row + "x" + column + "_Knight_Graph.txt");
//        compute(0, 0, null);
        compute2();
        addVtoAl();
        generate();
    }

    public GenerateKnightsMoveGraphs(int row, int column, String filename) {

        this.row = row;
        this.column = column;

        vertices = new JNode[row][column];
        this.filename = filename;
        //  compute(0, 0, null);
        compute2();
        addVtoAl();
        generate();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    void addVtoAl() {
        for (int i = 0; i < vertices.length; i++) {
            JNode[] vertice = vertices[i];
            for (int j = 0; j < vertice.length; j++) {
                JNode jNode = vertice[j];
                if (jNode != null) {
                    nodelist.add(jNode);
                    System.out.println(i + " " + j + " " + jNode.getId());
                }
            }
        }
        Collections.sort((ArrayList<JNode>) nodelist, JNode.IdComparator);
    }

    public void compute(int sr, int sc, JNode jn) {
        System.out.println("Row " + sr + ", Column: " + sc);
        if (vertices[sr][sc] == null) {
            vertices[sr][sc] = new JNode(vertexCounter);
            vertexCounter++;
            //}
            if (jn != null) {
                jn.findNeighbours();
                if (!jn.getNeighbours().contains(vertices[sr][sc])) {
                    jn.addEdge(new JEdge(edgeCounter, "" + edgeCounter, jn, vertices[sr][sc]));
                    edgeCounter++;
                }
            }

            for (int i = 0; i < 8; i++) {
                switch (i) {
                    case 0:
                        if ((sr - 1 >= 0 && sr - 1 < row) && (sc - 2 >= 0 && sc - 2 < column)) {
                            compute(sr - 1, sc - 1, vertices[sr][sc]);
                        }
                        break;
                    case 1:
                        if ((sr + 1 >= 0 && sr + 1 < row) && (sc - 2 >= 0 && sc - 2 < column)) {
                            compute(sr + 1, sc - 2, vertices[sr][sc]);
                        }
                        break;

                    case 2:
                        if ((sr - 1 >= 0 && sr - 1 < row) && (sc + 2 >= 0 && sc + 2 < column)) {
                            compute(sr - 1, sc + 2, vertices[sr][sc]);
                        }
                        break;

                    case 3:
                        if ((sr + 1 >= 0 && sr + 1 < row) && (sc + 2 >= 0 && sc + 2 < column)) {
                            compute(sr + 1, sc + 2, vertices[sr][sc]);
                        }
                        break;

                    case 4:
                        if ((sr - 2 >= 0 && sr - 2 < row) && (sc - 1 >= 0 && sc - 1 < column)) {
                            compute(sr - 2, sc - 1, vertices[sr][sc]);
                        }
                        break;

                    case 5:
                        if ((sr - 2 >= 0 && sr - 2 < row) && (sc + 1 >= 0 && sc + 1 < column)) {
                            compute(sr - 2, sc + 1, vertices[sr][sc]);

                        }
                        break;

                    case 6:
                        if ((sr + 2 >= 0 && sr + 2 < row) && (sc - 1 >= 0 && sc - 1 < column)) {
                            compute(sr + 2, sc - 1, vertices[sr][sc]);

                        }
                        break;
                    case 7:
                        if ((sr + 2 >= 0 && sr + 2 < row) && (sc + 1 >= 0 && sc + 1 < column)) {
                            compute(sr + 2, sc + 1, vertices[sr][sc]);

                        }
                        break;

                }
            }
        }
    }

    public void compute2() {
//        System.out.println("Row " + sr + ", Column: " + j);
//        if (vertices[sr][sc] == null) {
//            vertices[sr][sc] = new JNode(vertexCounter);
//            vertexCounter++;
//        //}
//        if (jn != null) {
//            jn.findNeighbours();
//            if (!jn.getNeighbours().contains(vertices[sr][sc])) {
//                jn.addEdge(new JEdge(edgeCounter, "" + edgeCounter, jn, vertices[sr][sc]));
//                edgeCounter++;
//            }
//        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (vertices[i][j] == null) {
                    vertices[i][j] = new JNode(vertexCounter);
                    vertexCounter++;
                }
                vertices[i][j].findNeighbours();
                for (int k = 0; k < 8; k++) {
                    switch (k) {
                        case 0:
                            if ((i - 1 >= 0 && i - 1 < row) && (j - 2 >= 0 && j - 2 < column)) {
                                if (vertices[i - 1][j - 1] == null) {
                                    vertices[i - 1][j - 1] = new JNode(vertexCounter);
                                    vertexCounter++;
                                }

                                if (!vertices[i][j].getNeighbours().contains(vertices[i - 1][j - 1])) {
                                    vertices[i][j].addEdge(new JEdge(edgeCounter, "" + edgeCounter, vertices[i][j], vertices[i - 1][j - 1]));
                                    edgeCounter++;
                                    vertices[i][j].addNeighbour(vertices[i - 1][j - 1]);
                                }

//                                compute(i - 1, j - 1, vertices[i][j]);
                            }
                            break;
                        case 1:
                            if ((i + 1 >= 0 && i + 1 < row) && (j - 2 >= 0 && j - 2 < column)) {
                                if (vertices[i + 1][j - 2] == null) {
                                    vertices[i + 1][j - 2] = new JNode(vertexCounter);
                                    vertexCounter++;
                                }

                                if (!vertices[i][j].getNeighbours().contains(vertices[i + 1][j - 2])) {
                                    vertices[i][j].addEdge(new JEdge(edgeCounter, "" + edgeCounter, vertices[i][j], vertices[i + 1][j - 2]));
                                    edgeCounter++;
                                    vertices[i][j].addNeighbour(vertices[i + 1][j - 2]);
                                }

//                                compute(i + 1, j - 2, vertices[i][j]);
                            }
                            break;

                        case 2:
                            if ((i - 1 >= 0 && i - 1 < row) && (j + 2 >= 0 && j + 2 < column)) {

                                if (vertices[i - 1][j + 2] == null) {
                                    vertices[i - 1][j + 2] = new JNode(vertexCounter);
                                    vertexCounter++;
                                }

                                if (!vertices[i][j].getNeighbours().contains(vertices[i - 1][j + 2])) {
                                    vertices[i][j].addEdge(new JEdge(edgeCounter, "" + edgeCounter, vertices[i][j], vertices[i - 1][j + 2]));
                                    edgeCounter++;
                                    vertices[i][j].addNeighbour(vertices[i - 1][j + 2]);
                                }

//                                compute(i - 1, j + 2, vertices[i][j]);
                            }
                            break;

                        case 3:
                            if ((i + 1 >= 0 && i + 1 < row) && (j + 2 >= 0 && j + 2 < column)) {
                                if (vertices[i + 1][j + 2] == null) {
                                    vertices[i + 1][j + 2] = new JNode(vertexCounter);
                                    vertexCounter++;
                                }

                                if (!vertices[i][j].getNeighbours().contains(vertices[i + 1][j + 2])) {
                                    vertices[i][j].addEdge(new JEdge(edgeCounter, "" + edgeCounter, vertices[i][j], vertices[i + 1][j + 2]));
                                    edgeCounter++;
                                    vertices[i][j].addNeighbour(vertices[i + 1][j + 2]);
                                }

//                                compute(i + 1, j + 2, vertices[i][j]);
                            }
                            break;

                        case 4:
                            if ((i - 2 >= 0 && i - 2 < row) && (j - 1 >= 0 && j - 1 < column)) {

                                if (vertices[i - 2][j - 1] == null) {
                                    vertices[i - 2][j - 1] = new JNode(vertexCounter);
                                    vertexCounter++;
                                }

                                if (!vertices[i][j].getNeighbours().contains(vertices[i - 2][j - 1])) {
                                    vertices[i][j].addEdge(new JEdge(edgeCounter, "" + edgeCounter, vertices[i][j], vertices[i - 2][j - 1]));
                                    edgeCounter++;
                                    vertices[i][j].addNeighbour(vertices[i - 2][j - 1]);
                                }

//                                compute(i - 2, j - 1, vertices[i][j]);
                            }
                            break;

                        case 5:
                            if ((i - 2 >= 0 && i - 2 < row) && (j + 1 >= 0 && j + 1 < column)) {
                                if (vertices[i - 2][j + 1] == null) {
                                    vertices[i - 2][j + 1] = new JNode(vertexCounter);
                                    vertexCounter++;
                                }

                                if (!vertices[i][j].getNeighbours().contains(vertices[i - 2][j + 1])) {
                                    vertices[i][j].addEdge(new JEdge(edgeCounter, "" + edgeCounter, vertices[i][j], vertices[i - 2][j + 1]));
                                    edgeCounter++;
                                    vertices[i][j].addNeighbour(vertices[i - 2][j + 1]);
                                }

//                                compute(i - 2, j + 1, vertices[i][j]);
                            }
                            break;

                        case 6:
                            if ((i + 2 >= 0 && i + 2 < row) && (j - 1 >= 0 && j - 1 < column)) {
                                if (vertices[i + 2][j - 1] == null) {
                                    vertices[i + 2][j - 1] = new JNode(vertexCounter);
                                    vertexCounter++;
                                }

                                if (!vertices[i][j].getNeighbours().contains(vertices[i + 2][j - 1])) {
                                    vertices[i][j].addEdge(new JEdge(edgeCounter, "" + edgeCounter, vertices[i][j], vertices[i + 2][j - 1]));
                                    edgeCounter++;
                                    vertices[i][j].addNeighbour(vertices[i + 2][j - 1]);
                                }

//                                compute(i + 2, j - 1, vertices[i][j]);
                            }
                            break;
                        case 7:
                            if ((i + 2 >= 0 && i + 2 < row) && (j + 1 >= 0 && j + 1 < column)) {
                                if (vertices[i + 2][j + 1] == null) {
                                    vertices[i + 2][j + 1] = new JNode(vertexCounter);
                                    vertexCounter++;
                                }

                                if (!vertices[i][j].getNeighbours().contains(vertices[i + 2][j + 1])) {
                                    vertices[i][j].addEdge(new JEdge(edgeCounter, "" + edgeCounter, vertices[i][j], vertices[i + 2][j + 1]));
                                    edgeCounter++;
                                    vertices[i][j].addNeighbour(vertices[i + 2][j + 1]);
                                }

//                                compute(i + 2, j + 1, vertices[i][j]);
                            }
                            break;

                    }
                }
            }
        }

    }

    public void generate() {
        try {
            PrintStream out = new PrintStream(this.filename);
            Throwable localThrowable3 = null;
            try {
                out.println("" + row + "x" + column + "_Knight_Graph");
                out.println(nodelist.size());
                for (int i = 0; i < nodelist.size(); i++) {
                    int oID = nodelist.get(i).getId();
                    out.print("" + -(oID));
                    for (int j = 0; j < nodelist.get(i).getNeighbours().size(); j++) {
                        int nID = nodelist.get(i).getNeighbours().get(j).getId();
                        if (nID > oID) {
                            out.print(" " + nID);
                        }
                    }
                    out.print("\n");
                }
                out.println(0);
                out.close();
            } catch (Throwable localThrowable1) {
                localThrowable3 = localThrowable1;
                throw localThrowable1;

            } finally {

                if (out != null) {
                    if (localThrowable3 != null) {
                        try {
                            out.close();
                        } catch (Throwable localThrowable2) {
                            localThrowable3.addSuppressed(localThrowable2);
                        }
                    } else {
                        out.close();
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenerateKnightsMoveGraphs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        new GenerateKnightsMoveGraphs(6, 6);
    }
}
