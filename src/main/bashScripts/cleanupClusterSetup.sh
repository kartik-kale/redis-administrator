#!/bin/bash
for i in {0..5}
do
	cd 700$i
	rm *.rdb
	rm nodes.conf
	cd ..
done
