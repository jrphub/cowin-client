#!/bin/bash

mpid=`pgrep '[m]slots'`
bpid=`pgrep '[b]slots'`
hpid=`pgrep '[h]slots'`

kpid18=`pgrep '[k]slots18'`
kpid45=`pgrep '[k]slots45'`

cpid18=`pgrep '[c]slots18'`
cpid45=`pgrep '[c]slots45'`

kill $mpid
kill $bpid
kill $hpid

kill $kpid18
kill $kpid45

kill $cpid18
kill $cpid45

