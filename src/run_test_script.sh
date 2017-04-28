#!/bin/bash

sysctl -w kernel.sched_latency_ns=$1
sysctl -n kernel.sched_latency_ns

sysctl -w kernel.sched_min_granularity_ns=$2
sysctl -n kernel.sched_min_granularity_ns 

sysctl -w kernel.sched_rr_timeslice_ms=$3
sysctl -n kernel.sched_rr_timeslice_ms

sysctl -w vm.swappiness=$4
sysctl -a | grep vm.swappiness

phoronix-test-suite benchmark pts/sample-program 
