#!/bin/bash
for i in {0..5}
#hostPortList
do
	cd 700$i
	./../redis-server redis.conf&
	cd ..
	hostPortList=$hostPortList" "172.16.137.70:700$i
done
./../src/redis-trib.rb create --replicas 1 $hostportList