#!/usr/bin/env bash

exist_check() {
    echo "path ($1)"

    path=$1
    max_sleep_count=2
    sleep_time=60
    sleep_count=0
    find=0
    capacity_message=""
    while [[ -z ${capacity_message} ]] && [[ ${sleep_count} -le ${max_sleep_count} ]]
    do
        capacity_message=`${hadoop} fs -du -s ${path}`
        if [[ -z ${capacity_message} ]]; then
            sleep ${sleep_time}
            let sleep_count+=1
            echo "sleep "${sleep_count}
        else
            find=1
        fi
    done
    if [[ ${find} -eq 1 ]]; then
        echo "${path} query capacity message success"
    else
        echo "${path} query capacity message failure"
        exit -1
    fi
    capacity=`echo ${capacity_message} | awk -F "[ ]" '{print $1}'`
    echo "${path} capacity (${capacity})"

    if (( $(awk 'BEGIN{print ('${capacity}' > 0)}') )); then
        echo "${path} capacity (${capacity}>0)"
        echo "${path} capacity normal"
    else
        echo "${path} capacity (${capacity}=0)"
        exit -1
    fi
}
