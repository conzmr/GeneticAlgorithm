#!/usr/bin/expect -f

set testName [lindex $argv 0];
set latency [lindex $argv 1];
set granularity [lindex $argv 2];
set timeslice [lindex $argv 3];
set swappiness [lindex $argv 4];

spawn rm pts*.csv

spawn \sudo -i

expect "\[sudo]\ password for :" //Add your username as appear when sudo -i 

send " \r" //Your password***

spawn sysctl -w kernel.sched_latency_ns=$latency

spawn sysctl -w kernel.sched_min_granularity_ns=$granularity

spawn sysctl -w kernel.sched_rr_timeslice_ms=$timeslice

spawn sysctl -w vm.swappiness=$swappiness

spawn phoronix-test-suite benchmark pts/sample-program

expect "Would you like to save these test results (Y/n):"

send "Y\r"

expect "Enter a name to save these results under:"

send "$testName\r"

expect "Enter a unique name to describe this test run / configuration:"

send "$testName\r"

expect "New Description:"

send "$testName\r"

expect "Do you want to view the results in your web browser (Y/n):"

send "n\r"

expect "Would you like to upload the results to OpenBenchmarking.org (Y/n):"

send "n\r"

interact

spawn phoronix-test-suite result-file-to-csv $testName

interact
