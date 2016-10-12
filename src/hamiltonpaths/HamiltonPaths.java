package hamiltonpaths;

import base.JEdge;
import base.JNode;
import base.Path;
import datastructure.MyArray;
import graphs.Graph;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import threadpool.ThreadPool;

public class HamiltonPaths {

    MyArray<Graph> graphs = new MyArray();
    ArrayList<Path> paths = new ArrayList();
    ArrayList<LinkedList<Path>> hpaths = new ArrayList<>();

    //LinkedList<Path> hpaths = new LinkedList();
    Thread[] pt;
    int pathsCounter = 0;
    public static int threadLimit = 0;
    public static int threadCount = 0;
    public static long hpathcount = 0L;
    public static long hpathcount2[];
    public boolean printHPath = false;
    JNode startnode;
    ExecutorService executorService;
    ThreadPool tp;

    public int getThreadLimit() {
        return threadLimit;
    }

    public boolean isPrintHPath() {
        return this.printHPath;
    }

    public void setPrintHPath(boolean printHPath) {
        this.printHPath = printHPath;
    }

    public HamiltonPaths() {
        this.paths.add(new Path(this.pathsCounter));
    }

    public void initThreadPool(int Threads) {
        this.tp = new ThreadPool(Threads);
        threadLimit = Threads;
        this.pt = new Thread[Threads];

    }

    public void initCommonVars(int Threads) {
        //   pt = new Thread[Threads + 1];
        hpathcount2 = new long[Threads + 1];

        for (int i = 0; i < hpathcount2.length; i++) {
            hpathcount2[i] = 0;
            hpaths.add(new LinkedList<>());
        }

    }

    Graph readGraph(int gid, String filename) {
        File f = new File(filename);
        if (!f.exists()) {
            System.out.println("File Not Found");
            return null;
        }
        Graph g = null;
        String label = "";
        int edgeCounter = 1;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            Throwable localThrowable3 = null;
            try {
                String line = br.readLine();
                label = line.trim();
                g = new Graph(gid, label);
                g.addVertex(new JNode(-99, ""));
                g.addEdge(new JEdge(-11, "rw"));
                int counter = 0;
                int noofvertex = 3;
                while (line != null) {
                    if (counter == 1) {
                        noofvertex = Integer.parseInt(line.trim());
                    } else if (counter > 1) {
                        String[] in = line.split(" ");
                        JNode jn = null;
                        if (in[0].equalsIgnoreCase("0")) {
                            break;
                        }

                        for (int i = 0; i < in.length; i++) {
                            String string = in[i];
                            if (i == 0) {
                                int id = Integer.parseInt(string.trim());
                                id *= -1;

                                jn = new JNode(id, "" + id);
                                if (!g.vertexExist(id)) {
                                    g.addVertex(jn);
                                } else {
                                    jn = g.getVertex(id);
                                }
                            } else {
                                int id = Integer.parseInt(string.trim());
                                JNode jn2 = new JNode(id, string.trim());

                                if (!g.vertexExist(id)) {
                                    g.addVertex(jn2);
                                } else {
                                    jn2 = g.getVertex(id);
                                }

                                JEdge e1 = new JEdge(edgeCounter, "" + jn.getId() + "-" + id, jn, jn2);
                                if (g.edgeExist(edgeCounter)) {
                                    e1 = g.getEdge(edgeCounter);
                                } else {
                                    g.addEdge(e1);
                                    edgeCounter++;
                                }

                                jn.addEdge(e1);
                                jn2.addEdge(e1);
                            }
                        }
                    }

                    line = br.readLine();

                    counter++;
                }
                g.setRoot((JNode) g.getVertices().get(1));
                this.graphs.add(g);
                MyArray<JNode> jnd = g.getVertices();
                MyArray<JEdge> jed = g.getEdges();
                for (int i = 0; i < jnd.size(); i++) {
                }

                for (int i = 0; i < jed.size(); i++) {
                }
            } catch (IOException | NumberFormatException localThrowable1) {
                localThrowable3 = localThrowable1;
                throw localThrowable1;
            } finally {

                if (br != null) {
                    if (localThrowable3 != null) {
                        try {
                            br.close();
                        } catch (Throwable localThrowable2) {
                            localThrowable3.addSuppressed(localThrowable2);
                        }
                    } else {
                        br.close();
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println(e);
        }
        System.out.println("Read Graph " + g.getLabel());
        return g;
    }

    public JNode getStartnode() {
        return this.startnode;
    }

    public void setStartnode(JNode startnode) {
        this.startnode = startnode;
        ((Path) this.paths.get(0)).getVertices().clear();
        ((Path) this.paths.get(0)).setHasNoCycles(true);
        ((Path) this.paths.get(0)).addVertex(startnode);
    }

    public void serialfindHpath(Path P, JNode jn) {
        jn.findNeighbours();

        for (int i = 0; i < jn.getNeighbours().size(); i++) {
            JNode get = (JNode) jn.getNeighbours().get(i);
            if (!P.getVertices().contains(get)) {
                this.pathsCounter += 1;
                int fi = this.pathsCounter;
                Path cp = new Path(fi);
                cp.setHasNoCycles(true);
                cp.setVertices((MyArray) P.getVertices().clone());

                cp.addVertex(get);

                if (cp.getVertices().size() == ((Graph) this.graphs.get(0)).getVertices().size() - 1) {
                    incrementHPath(cp);
                }

                serialfindHpath(cp, get);
            }
        }
    }

    public void startInParallel(Path P, JNode jn) {
        spawnNewThread(new PTask(P, jn));
    }

    public void PPath(Path P, JNode jn) {
        jn.findNeighbours();
        
//            System.out.println("Path "+P.toString());
        //  int threadc = getThreadCount();

        for (int i = 0; i < jn.getNeighbours().size(); i++) {
            JNode get = (JNode) jn.getNeighbours().get(i);
            if (!P.getVertices().contains(get)) {
                this.pathsCounter += 1;
                int fi = this.pathsCounter;
                Path cp = new Path(fi);
                cp.setHasNoCycles(true);

                cp.setVertices((MyArray) P.getVertices().clone());

                cp.addVertex(get);

                if (cp.getVertices().size() == ((Graph) this.graphs.get(0)).getVertices().size() - 1) {
                    incrementHPath(cp);
                }

                if ((this.tp.getTaskQueueSize() < threadLimit) && (!ThreadPool.isPoolShutDownInitiated())) {
                    if (spawnNewThread(new PTask(cp, get))) {
                    } else {
                        PPath(cp, get);
                    }

                } else {
                    PPath(cp, get);
                }
            }
        }
    }

    synchronized boolean spawnNewThread(Runnable r) {
        try {
            boolean b = this.tp.execute(r);
            return b;
        } catch (Exception ex) {
            Logger.getLogger(HamiltonPaths.class.getName()).log(Level.SEVERE, null, ex);
            //r.run();
        }
        return false;
    }

    void shutdownExecutor() {
        while (this.tp.getTaskQueueSize() != 0) {
            try {
//                System.out.println(Thread.currentThread().getName() + " " + this.tp.getTaskQueueSize());
                Thread.sleep(1000);
                // if(LinkedBloc)
            } catch (InterruptedException ex) {
                Logger.getLogger(HamiltonPaths.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.tp.shutdown();
    }

    class PTask
            implements Runnable {

        Path P;

        JNode jn;

        public PTask(Path P, JNode jn) {
            this.P = P;
            this.jn = jn;
        }

        @Override
        public void run() {
            this.jn.findNeighbours();
//            System.out.println("Path "+P.toString());
            for (int i = 0; i < this.jn.getNeighbours().size(); i++) {
                JNode get = (JNode) this.jn.getNeighbours().get(i);
                if (!this.P.getVertices().contains(get)) {
                    HamiltonPaths.this.pathsCounter += 1;
                    int fi = HamiltonPaths.this.pathsCounter;
                    Path cp = new Path(fi);
                    cp.setHasNoCycles(true);
                    cp.setVertices((MyArray) this.P.getVertices().clone());
                    cp.addVertex(get);
                    if (cp.getVertices().size() == ((Graph) HamiltonPaths.this.graphs.get(0)).getVertices().size() - 1) {
                        HamiltonPaths.this.incrementHPath(cp);
                    }
                    if ((HamiltonPaths.this.tp.getTaskQueueSize() < HamiltonPaths.threadLimit) && (!ThreadPool.isPoolShutDownInitiated())) {
                        //   HamiltonPaths.this.tp.execute(new PTask( cp, get));
                        if (spawnNewThread(new PTask(cp, get))) {
                        } else {
                            PPath(cp, get);
                        }
                    } else {
                        HamiltonPaths.this.PPath(cp, get);
                    }
                }
            }
            // HamiltonPaths.this.decrementThreadCount();
        }
    }

    public void incrementThreadCount() {
        threadCount += 1;
    }

    public void decrementThreadCount() {
        threadCount -= 1;
    }

    public int getThreadCount() {
        return threadCount;
    }

    /*public synchronized void incrementHPath(Path P) {
     hpathcount += 1L;
     if (this.printHPath) {
       this.hpaths.add(P);
     }
   }*/
    public void incrementHPath(Path P) {

        String nm = Thread.currentThread().getName().trim();
        // System.out.println(nm);
        if (nm.contains("main")) {
            nm = "0";
        }
        int loc = Integer.parseInt(nm);
        hpathcount2[loc]++;
        //System.out.println("" + nm + "  " + loc + " " + hpathcount2[loc]);
        if(hpathcount2[loc]%10==0){
            System.out.println("Found "+hpathcount2[loc]+" Paths on Thread "+nm+" Path "+P.toString());
        }
        if (printHPath) {
            hpaths.get(loc).add(P);
        }
    }

    public static void main(String[] args) {
        ArrayList<String> arg = new ArrayList();
        String filename = "";
        if (args.length > 0) {
            arg.addAll(Arrays.asList(args));
        } else {
            System.err.println("No Input Defined");
        }
        HamiltonPaths asgn;
        if (arg.contains("--threads")) {
            int i = arg.indexOf("--threads");
            int th = Integer.parseInt((String) arg.get(i + 1));
            asgn = new HamiltonPaths();
            asgn.initThreadPool(th);
            asgn.initCommonVars(th);
            System.out.println("Total Threads  " + th);
        } else {
            asgn = new HamiltonPaths();
        }

        if (arg.contains("--graph")) {
            int i = arg.indexOf("--graph");
            filename = (String) arg.get(i + 1);
        }

        if (arg.contains("--print-paths")) {
            asgn.setPrintHPath(true);
        }

        if (arg.contains("--gen-graph")) {
            int i = arg.indexOf("--gen-graph");
            int th = Integer.parseInt((String) arg.get(i + 1));
            int j;
            if (arg.contains("--tofile")) {
                j = arg.indexOf("--tofile");
                String filename2 = (String) arg.get(j + 1);
                GenerateFullyConnectedGraphs localGenerateFullyConnectedGraphs = new GenerateFullyConnectedGraphs(th, filename2);
            } else {
                GenerateFullyConnectedGraphs localGenerateFullyConnectedGraphs = new GenerateFullyConnectedGraphs(th);
            }
        }

        if (arg.contains("--gen-KMgraph")) {
            int i = arg.indexOf("--gen-KMgraph");
            int rows = Integer.parseInt((String) arg.get(i + 1));
            int columns = Integer.parseInt((String) arg.get(i + 2));
            int j;
            if (arg.contains("--tofile")) {
                j = arg.indexOf("--tofile");
                String filename2 = (String) arg.get(j + 1);
                GenerateKnightsMoveGraphs gkm = new GenerateKnightsMoveGraphs(rows, columns, filename2);
            } else {
                GenerateKnightsMoveGraphs gkm = new GenerateKnightsMoveGraphs(rows, columns);
            }
        }

        Graph g2 = asgn.readGraph(0, filename);
        asgn.setStartnode(g2.getRoot());
        long start = 0L;
        if (arg.contains("--serial")) {
            start = System.currentTimeMillis();
            System.out.println("Executing in serial ");
            asgn.initCommonVars(1);
            asgn.serialfindHpath((Path) asgn.paths.get(0), g2.getRoot());
        }

        if (arg.contains("--parallel")) {
            start = System.currentTimeMillis();
            System.out.println("Executing in parallel ");

            // asgn.PPath((Path) asgn.paths.get(0), g2.getRoot());
            asgn.startInParallel((Path) asgn.paths.get(0), g2.getRoot());

            asgn.shutdownExecutor();
        }

        long stop = System.currentTimeMillis();

        System.out.println("Total Time : " + (stop - start) + "ms");
        // System.out.println("Total Hamilton Paths  " + hpathcount);
        long c = 0;
        for (int i = 0; i < asgn.hpathcount2.length; i++) {
            c += asgn.hpathcount2[i];
            System.out.println("Thread " + i + " " + asgn.hpathcount2[i]);

        }
        System.out.println("Total Hamilton Paths  " + c);

        if (asgn.isPrintHPath()) {
            System.out.println("Paths " + asgn.hpaths.toString());
        }

        System.out.println("************** Execution Completed ****************\n");
    }
}
