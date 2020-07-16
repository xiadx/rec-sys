#!/usr/bin/env bash

source ./parameter.sh

sample_path="/home/operation/xiadingxin/Workspace/rec-sys/data/rec-rank/sample/"

if [[ ! -d ${sample_path}/sample.rec.wdl.v1.tfrecord.train ]]; then
    echo "create ${sample_path}/sample.rec.wdl.v1.tfrecord.train directory"
    mkdir -p ${sample_path}/sample.rec.wdl.v1.tfrecord.train
fi

if [[ ! -d ${sample_path}/sample.rec.wdl.v1.tfrecord.test ]]; then
    echo "create ${sample_path}/sample.rec.wdl.v1.tfrecord.test directory"
    mkdir -p ${sample_path}/sample.rec.wdl.v1.tfrecord.test
fi

${hadoop} fs -get /user/xiadinxin/rec-sys/python/rec-rank/sample/sample.rec.wdl.v1.tfrecord.train/20200627 ${sample_path}/sample.rec.wdl.v1.tfrecord.train
${hadoop} fs -get /user/xiadinxin/rec-sys/python/rec-rank/sample/sample.rec.wdl.v1.tfrecord.test/20200628 ${sample_path}/sample.rec.wdl.v1.tfrecord.test
