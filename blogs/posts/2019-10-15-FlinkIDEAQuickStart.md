---
title: FlinkIDEAQuickStart
date: 2019-10-15 18:36:06
tags: Flink
categories: Flink
type: "tags"
---
Flink IDEA QuickStart

## Create Project

### Use Maven Archetypes

```
mkdir /Users/xdx/Workspace/FlinkProjects
vim quickstart.sh
/* Input:
mvn archetype:generate \
    -DarchetypeGroupId=org.apache.flink \
    -DarchetypeArtifactId=flink-quickstart-scala \
    -DarchetypeVersion=1.9.0
*///:~
sh quickstart.sh
/* Input:
Define value for property 'groupId': com.mafengwo.recommend.flink.quickstart
Define value for property 'artifactId': flink-quickstart-scala
*///:~
```

### Run the QuickStart Script

```
curl https://flink.apache.org/q/quickstart-scala.sh | bash -s 1.9.0
```

### Install Tree

```
brew install tree
tree flink-quickstart-scala
/* Output:
flink-quickstart-scala/
├── pom.xml
└── src
    └── main
        ├── resources
        │   └── log4j.properties
        └── scala
            └── com
                └── mafengwo
                    └── recommend
                        └── flink
                            └── quickstart
                                ├── BatchJob.scala
                                └── StreamingJob.scala

9 directories, 4 files
*///:~
```

## IDEA Open Project

```scala
//: WindowWordCount
package com.mafengwo.recommend.flink.quickstart

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

object WindowWordCount {
  def main(args: Array[String]) {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val text = env.socketTextStream("localhost", 9999)

    val counts = text.flatMap { _.toLowerCase.split("\\W+") filter { _.nonEmpty } }
      .map { (_, 1) }
      .keyBy(0)
      .timeWindow(Time.seconds(5))
      .sum(1)

    counts.print()

    env.execute("Window Stream WordCount")
  }
}
```

```
//: window-word-count.sh
#!/usr/bin/env bash

# parameter
PROJECT_PATH=/Users/xdx/Workspace/FlinkProjects/flink-quickstart-scala

/usr/local/flink/flink-1.9.0/bin/flink \
    run \
    ${PROJECT_PATH}/target/flink-quickstart-scala-1.0-SNAPSHOT.jar
```

```
//: pom.xml
<mainClass>com.mafengwo.recommend.flink.quickstart.WindowWordCount</mainClass>
```

```
nc -lk 9999
sh window-word-count.sh
```