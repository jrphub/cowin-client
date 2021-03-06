#!/bin/bash
echo "Stopping all Process for Cowin-Client"
mpid=`pgrep '[m]slots'`
bpid=`pgrep '[b]slots'`
hpid=`pgrep '[h]slots'`

kpid18=`pgrep '[k]slots18'`
kpid45=`pgrep '[k]slots45'`

cpid18=`pgrep '[c]slots18'`
cpid45=`pgrep '[c]slots45'`

klhpid=`pgrep '[k]lhslots'`

vznpid=`pgrep '[v]znslots'`

kill $mpid
kill $bpid
kill $hpid

kill $kpid18
kill $kpid45

kill $cpid18
kill $cpid45

kill $klhpid

kill $vznpid

sleep 2
echo "All Process stopped"