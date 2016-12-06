#!/bin/bash

cd ..

n=10

for city in Roanoke Toronto; do
    for a in LS1 LS2; do
        for (( i=1; i<=$n; i++ )); do
            ./runCode.sh -i $city -a $a -t 60 -d

            echo finished $i/$n $city $a
        done
    done
done
