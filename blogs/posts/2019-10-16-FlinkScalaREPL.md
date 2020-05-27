---
title: FlinkScalaREPL
date: 2019-10-16 17:08:56
tags: Flink
categories: Flink
type: "tags"
---
Flink Scala REPL

## Run Flink Scala REPL

```
cd /Users/xdx/Workspace/Softwares/flink-1.9.0
./bin/start-scala-shell.sh local
scala> :paste
// Entering paste mode (ctrl-D to finish)
val text = benv.fromElements(
  "To be, or not to be,--that is the question:--",
  "Whether 'tis nobler in the mind to suffer",
  "The slings and arrows of outrageous fortune",
  "Or to take arms against a sea of troubles,")
/* Output:
text: org.apache.flink.api.scala.DataSet[String] = org.apache.flink.api.scala.DataSet@1007954a
*///:~
scala> :paste
// Entering paste mode (ctrl-D to finish)

val counts = text
  .flatMap { _.toLowerCase.split("\\W+") }
  .map { (_, 1) }.groupBy(0).sum(1)

// Exiting paste mode, now interpreting.
/* Output:
counts: org.apache.flink.api.scala.AggregateDataSet[(String, Int)] = org.apache.flink.api.scala.AggregateDataSet@472c13d
*///:~
scala> counts.print()
/* Output:
(a,1)
(against,1)
(and,1)
(arms,1)
(arrows,1)
(be,2)
(fortune,1)
(in,1)
(is,1)
(mind,1)
(nobler,1)
(not,1)
(of,2)
(or,2)
(outrageous,1)
(question,1)
(sea,1)
(slings,1)
(suffer,1)
(take,1)
(that,1)
(the,3)
(tis,1)
(to,4)
(troubles,1)
(whether,1)
*///:~
scala> :paste
// Entering paste mode (ctrl-D to finish)

val textStreaming = senv.fromElements(
  "To be, or not to be,--that is the question:--",
  "Whether 'tis nobler in the mind to suffer",
  "The slings and arrows of outrageous fortune",
  "Or to take arms against a sea of troubles,")

// Exiting paste mode, now interpreting.
/* Output:
textStreaming: org.apache.flink.streaming.api.scala.DataStream[String] = org.apache.flink.streaming.api.scala.DataStream@5ab30bb4
*///:~
scala> :paste
// Entering paste mode (ctrl-D to finish)

val countsStreaming = textStreaming
    .flatMap { _.toLowerCase.split("\\W+") }
    .map { (_, 1) }.keyBy(0).sum(1)

// Exiting paste mode, now interpreting.
/* Output:
countsStreaming: org.apache.flink.streaming.api.scala.DataStream[(String, Int)] = org.apache.flink.streaming.api.scala.DataStream@63c5b63a
*///:~
scala> countsStreaming.print()
/* Output:
res2: org.apache.flink.streaming.api.datastream.DataStreamSink[(String, Int)] = org.apache.flink.streaming.api.datastream.DataStreamSink@1f6e7714
*///:~
scala> senv.execute("Streaming Wordcount")
/* Output:
(to,1)
(be,1)
(or,1)
(not,1)
(to,2)
(be,2)
(that,1)
(is,1)
(the,1)
(question,1)
(whether,1)
(tis,1)
(nobler,1)
(in,1)
(the,2)
(mind,1)
(to,3)
(suffer,1)
(the,3)
(slings,1)
(and,1)
(arrows,1)
(of,1)
(outrageous,1)
(fortune,1)
(or,2)
(to,4)
(take,1)
(arms,1)
(against,1)
(a,1)
(sea,1)
(of,2)
(troubles,1)
res3: org.apache.flink.api.common.JobExecutionResult = org.apache.flink.api.common.JobExecutionResult@6da69c15
*///:~
scala> :q
```