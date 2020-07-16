#!/usr/bin/env bash

source ./parameter.sh

master=yarn
queue=root.spark
spark_default_parallelism=600
driver_memory=1G
executor_memory=10G
num_executors=60
executor_cores=5

/usr/local/datacenter/spark2/bin/spark-submit \
    --master ${master} \
    --queue ${queue} \
    --conf spark.default.parallelism=${spark_default_parallelism} \
    --driver-memory ${driver_memory} \
    --executor-memory ${executor_memory} \
    --num-executors ${num_executors} \
    --executor-cores ${executor_cores} \
    ${sample_path}/dump_sample.py
