#!/usr/bin/env bash

hive="/usr/local/datacenter/hive/bin/hive"
presto="/usr/local/datacenter/presto/bin/presto-cli.pl"
hadoop="/usr/local/datacenter/hadoop/bin/hadoop"

job_path=$(dirname "$(dirname "$PWD")")
jar_path=$(dirname "$job_path")/jars
module_name="rec-rank"

frame_path=${job_path}/${module_name}/frame
sample_path=${job_path}/${module_name}/sample

echo "job_path:${job_path}"
echo "jar_path:${jar_path}"
echo "frame_path:${frame_path}"
echo "sample_path:${sample_path}"
