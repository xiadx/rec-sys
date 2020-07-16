#!/usr/bin/env bash

sample_path="/home/operation/xiadingxin/Workspace/rec-sys/data/rec-rank/sample/"

train_file="${sample_path}/sample.rec.lr.v1.train"
test_file="${sample_path}/sample.rec.lr.v1.train"

shuf -n20000 ${train_file} > ${sample_path}/sample.rec.lr.v1.train.shuf
shuf -n20000 ${test_file} > ${sample_path}/sample.rec.lr.v1.test.shuf
