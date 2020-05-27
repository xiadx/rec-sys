#!/usr/bin/env bash

hive="/usr/local/datacenter/hive/bin/hive"
presto="/usr/local/datacenter/presto/bin/presto-cli.pl"
hadoop="/usr/local/datacenter/hadoop/bin/hadoop"

job_path=$(dirname "$(dirname "$PWD")")
module_name="rec-recall"
jar_path="${job_path}/${module_name}/target/${module_name}-1.0-SNAPSHOT.jar"
resources_path="${job_path}/${module_name}/src/main/resources"
dependency_jar_path="${job_path}/jars/${module_name}"
log_path="${job_path}/logs/${module_name}"
data_path="${job_path}/data/${module_name}"

# dependency jar
dependency_jar=""
for file in `ls ${dependency_jar_path}`
do
    if [[ ${file##*.} == "jar" ]]; then
        dependency_jar=${dependency_jar}${dependency_jar_path}/${file},
    fi
done
dependency_jar=${dependency_jar%?}

echo "job_path:${job_path}"
echo "module_name:${module_name}"
echo "jar_path:${jar_path}"
echo "resources_path:${resources_path}"
echo "dependency_jar_path:${dependency_jar_path}"
echo "log_path:${log_path}"
echo "data_path:${data_path}"
echo "dependency_jar:${dependency_jar}"
