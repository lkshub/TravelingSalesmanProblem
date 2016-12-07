#!/bin/bash

cd ..

n=10

city=Roanoke
alg=LS1

t=300

for (( i=1; i<=$n; i++ )); do
    ./runCode.sh -i $city -a $alg -t $t -d

    echo finished $i/$n $city $a
done
