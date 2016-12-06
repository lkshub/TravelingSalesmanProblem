#!/bin/bash

cd ..

cd data

cities=$(ls)

cd ..

for city in $cities; do
    for a in Heur1 Heur2 Heur3; do
        ./runCode.sh -i $city -a $a -t 60 -d
        echo Ran $a on $city
    done
done