# find_hamilton_paths_multi-threading
A simple project to exploit parallelism in the techniques which are used to find Hamilton (Hamiltonian) paths
 

There were some restrictions while coding this project (so that i can learn more)
* Design and implement your own data structure
* try to avoid use of inbuilt/third-party libraries

That is why you may see some implementations of the classes like ArrayList and ThreadPool. However, you can modify code
and use inbuilt libs.

Thanks !!!


# Usage
Usage of The Program:
 java -jar <Filename>.jar <options> --graph <graphfilename.txt>
 options:
 --serial : finds the Hamilton paths using recursion (no multi-threading)
 --parallel : finds the Hamilton paths using multiple threads
 --threads : set the no of threads to be used, by default it is no of cores on your processor
 --gen-graph <no of vertices> : generates <no of vertices>-fully connected graph
 --gen-KMgraph <Rows> <Columns>: Generates Knight's Move Graph on a Row*Column Board
 --tofile <outputfilename> : saves the generated graph to the given filename
 --sleepbeforeTPoff <time in milliseconds>: put main thread to sleep for <time> before shutting down the
thread pool
 --print-paths: prints all the Hamilton paths
