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

word2vec_conf="${resources_path}/word2vec.conf"
#vector_hdfs=`sed '/^vector-path=/!d;s/vector-path=//;s/${dt}/'${date}'/;s/"//g' ${word2vec_conf}`
vector_hdfs=`cat ${word2vec_conf} | grep -E 'vector-path=".*"' | awk -F "\"" '{print $2}' | sed 's/${dt}/'${date}'/'`
vector_path="${data_path}$(echo ${vector_hdfs} | awk -F "${module_name}" '{print $2}')"

echo "word2vec_conf:${word2vec_conf}"
echo "vector_hdfs:${vector_hdfs}"
echo "vector_path:${vector_path}"

if [[ ! -d ${vector_path} ]]; then
    echo "create ${vector_path} data directory"
    mkdir -p ${vector_path}
fi

echo "load vector dep"

echo "load vector dep normal"

${hadoop} fs -text ${vector_hdfs}/* > ${vector_path}/vector.dat

echo "load vector mon"

echo "load vector mon normal"

exit 0
