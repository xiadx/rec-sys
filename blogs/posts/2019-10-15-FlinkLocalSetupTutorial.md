---
title: FlinkLocalSetupTutorial
date: 2019-10-15 17:00:05
tags: Flink
categories: Flink
type: "tags"
---
Flink Local Setup Tutorial

## Download and Start Flink

```
java -version
/* Output:
java version "1.8.0_71"
Java(TM) SE Runtime Environment (build 1.8.0_71-b15)
Java HotSpot(TM) 64-Bit Server VM (build 25.71-b15, mixed mode)
*///:~
mkdir ~/Workspace/Softwares
cd ~/Workspace/Softwares
wget http://mirrors.tuna.tsinghua.edu.cn/apache/flink/flink-1.9.0/flink-1.9.0-bin-scala_2.11.tgz
tar -xzvf flink-1.9.0-bin-scala_2.11.tgz
cd flink-1.9.0
```

## Start a Local Flink Cluster

```
./bin/start-cluster.sh
http://localhost:8081/#/overview
tail log/flink-*-standalonesession-*.log
/* Output:
...
2019-10-15 17:38:10,670 INFO  org.apache.flink.runtime.dispatcher.DispatcherRestEndpoint    - Web frontend listening at http://localhost:8081.
...
*///:~
```

## Run the Example

```
nc -l 9000
cd flink-1.9.0
./bin/flink run examples/streaming/SocketWindowWordCount.jar --port 9000
/* Input:
lorem ipsum
ipsum ipsum ipsum
bye
*///:~
tail -f log/flink-*-taskexecutor-*.out
/* Output:
lorem : 1
bye : 1
ipsum : 4
*///:~
./bin/stop-cluster.sh
/* Output:
Stopping taskexecutor daemon (pid: 28517) on host xdxdeMacBook-Pro.local.
No standalonesession daemon (pid: 28100) is running anymore on xdxdeMacBook-Pro.local.
*///:~
```