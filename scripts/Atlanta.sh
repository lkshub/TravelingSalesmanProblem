#!/bin/bash
cd ..
n=3
t=300
for city in Atlanta; do
    for a in LS1 LS2 LS3 BnB; do
        for (( i=1; i<=$n; i++ )); do
            ./runCode.sh -i $city -a $a -t $t -d
            echo finished $i/$n $city $a
        done
    done
done