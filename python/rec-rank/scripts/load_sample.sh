#!/usr/bin/env bash

source ./parameter.sh

sample_path="/home/operation/xiadingxin/Workspace/rec-sys/data/rec-rank/sample/index"

if [[ ! -d ${sample_path} ]]; then
    echo "create ${sample_path} directory"
    mkdir -p ${sample_path}
fi

${hadoop} fs -text /user/xiadinxin/rec-sys/rec-rank/sample/index/xgb.v1.train.libsvm/20200815/* > ${sample_path}/xgb.v1.train.libsvm
${hadoop} fs -text /user/xiadinxin/rec-sys/rec-rank/sample/index/xgb.v1.test.libsvm/20200816/* > ${sample_path}/xgb.v1.test.libsvm
