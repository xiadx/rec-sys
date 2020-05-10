#!/usr/bin/env bash

job_path=$(dirname "$(dirname "$PWD")")
module_name="rec-recall"
jar_path="${job_path}/${module_name}/target/${module_name}-1.0-SNAPSHOT.jar"
