---
title: 剖析spark-shell
date: 2019-09-14 11:22:44
tags: Spark
categories: Spark
type: "tags"
---
剖析spark-shell

## spark-shell

* spark-shell -> spark-submit -> spark-class -> SparkSubmit

## VisualVM

* [VisualVM](http://visualvm.github.io/)

```
cd /usr/local/spark/spark-1.2.0-bin-hadoop1/conf
cp spark-defaults.conf.template spark-defaults.conf
vim spark-defaults.conf
/* Input:
# Set jmx
# Driver
spark.driver.extraJavaOptions=-XX:+UnlockCommercialFeatures -XX:+FlightRecorder -Djava.rmi.server.hostname=192.168.1.107 -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=10207 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
# Executor
# spark.executor.extraJavaOptions=-XX:+UnlockCommercialFeatures -XX:+FlightRecorder -Djava.rmi.server.hostname=192.168.1.107 -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=0 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false
*///:~
cd /usr/local/spark/spark-1.2.0-bin-hadoop1/bin
spark-shell
```

* lsof

```
yum install -y lsof
lsof -i:10207
/* Output:
COMMAND  PID USER   FD   TYPE DEVICE SIZE/OFF NODE NAME
java    5442 root   17u  IPv6  34715      0t0  TCP *:10207 (LISTEN)
*///:~
```

## Homebrew

* [Homebrew](https://brew.sh/)

```
/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
```

* telnet

```
brew install telnet
telnet 192.168.1.107 10207
/* Output:
Trying 192.168.1.107...
telnet: connect to address 192.168.1.107: Connection refused
telnet: Unable to connect to remote host
*///:~
telnet 192.168.1.107 22
/* Output:
Trying 192.168.1.107...
Connected to 192.168.1.107.
Escape character is '^]'.
SSH-2.0-OpenSSH_7.4
*///:~
```

* firewalld

```
systemctl status firewalld
/* Output:
...
Active: active (running)
...
*///:~
systemctl stop firewalld
systemctl disable firewalld
systemctl status firewalld
/* Output:
...
Active: inactive (dead)
...
*///:~
telnet 192.168.1.107 10207
/* Output:
Trying 192.168.1.107...
Connected to 192.168.1.107.
Escape character is '^]'.
*///:~
```

* VisualVM -> Remote -> Host name -> Add JMX Connection -> Connection(192.168.1.107:10207) -> Threads -> main -> Select thread -> Thread Dump

```
/* Output:
"main" - Thread t@1
   java.lang.Thread.State: RUNNABLE
        at java.io.FileInputStream.read0(Native Method)
        at java.io.FileInputStream.read(FileInputStream.java:210)
        at scala.tools.jline.TerminalSupport.readCharacter(TerminalSupport.java:152)
        at scala.tools.jline.UnixTerminal.readVirtualKey(UnixTerminal.java:125)
        at scala.tools.jline.console.ConsoleReader.readVirtualKey(ConsoleReader.java:933)
        at scala.tools.jline.console.ConsoleReader.readBinding(ConsoleReader.java:1136)
        at scala.tools.jline.console.ConsoleReader.readLine(ConsoleReader.java:1218)
        at scala.tools.jline.console.ConsoleReader.readLine(ConsoleReader.java:1170)
        at org.apache.spark.repl.SparkJLineReader.readOneLine(SparkJLineReader.scala:80)
        at scala.tools.nsc.interpreter.InteractiveReader$class.readLine(InteractiveReader.scala:43)
        at org.apache.spark.repl.SparkJLineReader.readLine(SparkJLineReader.scala:25)
        at org.apache.spark.repl.SparkILoop.readOneLine$1(SparkILoop.scala:619)
        at org.apache.spark.repl.SparkILoop.innerLoop$1(SparkILoop.scala:636)
        at org.apache.spark.repl.SparkILoop.loop(SparkILoop.scala:641)
        at org.apache.spark.repl.SparkILoop$$anonfun$process$1.apply$mcZ$sp(SparkILoop.scala:968)
        at org.apache.spark.repl.SparkILoop$$anonfun$process$1.apply(SparkILoop.scala:916)
        at org.apache.spark.repl.SparkILoop$$anonfun$process$1.apply(SparkILoop.scala:916)
        at scala.tools.nsc.util.ScalaClassLoader$.savingContextLoader(ScalaClassLoader.scala:135)
        at org.apache.spark.repl.SparkILoop.process(SparkILoop.scala:916)
        at org.apache.spark.repl.SparkILoop.process(SparkILoop.scala:1011)
        at org.apache.spark.repl.Main$.main(Main.scala:31)
        at org.apache.spark.repl.Main.main(Main.scala)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:606)
        at org.apache.spark.deploy.SparkSubmit$.launch(SparkSubmit.scala:358)
        at org.apache.spark.deploy.SparkSubmit$.main(SparkSubmit.scala:75)
        at org.apache.spark.deploy.SparkSubmit.main(SparkSubmit.scala)

   Locked ownable synchronizers:
        - None
*///:~
```

* SparkSubmit.main -> repl.Main -> SparkILoop.process -> initializeSpark -> createSparkContext