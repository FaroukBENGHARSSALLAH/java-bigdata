java-bigdata-hadoop
==========================

An example for MapReduce in Haddop framework.

-  'Map' , map a set of input in a tuple of a key and value
-  'Reduce' , reduce the tuples by keys

Apache Hadoop is an open-source software framework for distributed storage and distributed processing of very large data sets on computer clusters built from commodity hardware. All the modules in Hadoop are designed with a fundamental assumption that hardware failures are common and should be automatically handled by the framework. The core of Apache Hadoop consists of a storage part, known as Hadoop Distributed File System (HDFS), and a processing part called MapReduce. Hadoop splits files into large blocks and distributes them across nodes in a cluster. To process data, Hadoop transfers packaged code for nodes to process in parallel based on the data that needs to be processed. This approach takes advantage of data locality – nodes manipulating the data they have access to – to allow the dataset to be processed faster and more efficiently than it would be in a more conventional supercomputer architecture that relies on a parallel file system where computation and data are distributed via high-speed networking.


The base Apache Hadoop framework is composed of the following modules:

- 'Hadoop Common'    contains libraries and utilities needed by other Hadoop modules
- 'Hadoop Distributed File System (HDFS)'   a distributed file-system that stores data on commodity machines, providing very high aggregate bandwidth across the cluster
- 'Hadoop YARN'  a resource-management platform responsible for managing computing resources in clusters and using them for scheduling of users' applications
- 'Hadoop MapReduce'  an implementation of the MapReduce programming model for large scale data processing

![alt text](https://github.com/FaroukBENGHARSSALLAH/java-bigdata/blob/master/java-bigdata-hadoop/hadoop-components.jpg "Hadoop Components")

