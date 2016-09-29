java-bigdata-spark
==========================

This folder contains a set of Spark libraries.

- [x] Spark RDD
- [x] Spark Machine learning
- [x] Spark Streaming

Apache Spark is an open source cluster computing framework, originally developed at the University of California, Berkeley's AMPLab, the Spark codebase was later donated to the Apache Software Foundation, which has maintained it since. Spark provides an interface for programming entire clusters with implicit data parallelism and fault-tolerance.

The base Apache Hadoop framework is composed of the following modules:

- 'Spark Core'   provides distributed task dispatching, scheduling, and basic I/O functionalities, exposed through an application programming interface nad centered on the RDD abstraction
- 'Spark SQL'  introduces a data abstraction called DataFrames, which provides support for structured and semi-structured data
- 'Spark Streaming'  enables fast scheduling capability to perform streaming analytics. It ingests data in mini-batches and performs RDD transformations on those mini-batches of data
- 'MLlib Machine Learning Library'  implementes many common machine learning and statistical algorithms and shippes with large scale pipelines
- 'GraphX'   a distributed graph processing framework on top of Apache Spark

![alt text](https://github.com/FaroukBENGHARSSALLAH/java-bigdata/blob/master/java-bigdata-spark/spark-components.jpg "Spark Components")

