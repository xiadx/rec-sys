#!/usr/bin/env bash

# parameter.sh
source ./parameter.sh

# check.sh
source ./check.sh

if [[ $# -gt 1 ]]; then
    echo "date parameter error"
    exit -1
elif [[ $# -eq 1 ]]; then
    date=$1
else
    date=`date -d "yesterday" +%Y%m%d`
fi

echo "${date}"

echo "isf job dep"

echo "isf job dep normal"

# log director
job_name="ISFJob"
if [[ ! -d ${log_path}/${job_name}/$(date +%Y%m%d) ]]; then
    echo "create ${log_path}/${job_name}/$(date +%Y%m%d) log director"
    mkdir -p ${log_path}/${job_name}/$(date +%Y%m%d)
fi
if [[ -d ${log_path}/${job_name}/$(date -d "7 days ago" +%Y%m%d) ]]; then
    echo "delete ${log_path}/${job_name}/$(date -d "7 days ago" +%Y%m%d) log director"
    rm -rf ${log_path}/${job_name}/$(date -d "7 days ago" +%Y%m%d)
fi

echo "///:~${job_name}///:~${date}"

# isf job
master=yarn
queue=root.spark
class=feature.ISFJob
spark_default_parallelism=600
driver_memory=6G
executor_memory=10G
num_executors=60
executor_cores=5

/usr/local/datacenter/spark2/bin/spark-submit \
    --master ${master} \
    --queue ${queue} \
    --class ${class} \
    --conf spark.default.parallelism=${spark_default_parallelism} \
    --driver-memory ${driver_memory} \
    --executor-memory ${executor_memory} \
    --num-executors ${num_executors} \
    --executor-cores ${executor_cores} \
    --jars ${dependency_jar} \
    ${jar_path} ${date} 2>&1 | tee ${log_path}/${job_name}/$(date +%Y%m%d)/${job_name}_$(date +%Y%m%d-%H%M).log

echo "isf job mon"

echo "isf job mon normal"

exit 0
