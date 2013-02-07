1. What do you expect to be the maximum size of the input files that your project can handle ? 
Since the contents of each input file are loaded in memory, the maximum size is related to the available Heap Size.

2. What would you do to increase this limit ?
Firstly i would increase the heap size according to the input files size. 
In case the system resources were not enough to load files in memory, i would refactor the program in a way to decrease the memory footprint.
I could read each file in chunks with size enough to be loaded in memory and store the results in temporary files on disk.   

3. Is your program efficient ?
Yes, since all comparisons are performed in memory using appropriate collections (TreeSet to retain ordering of the output list) 

4. What would you do to increase its performance ?
I would refactor the reading process, in a multi-thread way, reducing the time required to load the file contents in memory.