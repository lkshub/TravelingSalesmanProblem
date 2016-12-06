#!/bin/bash

cd src

mainPath="edu/gatech/cse6140/Driver.java"
mainPackage="edu.gatech.cse6140/Driver"

javac -cp '.:../lib/*' $mainPath

java  -cp '.:../lib/*' $mainPackage $1 $2 $3 $4 $5 $6 $7 $8 $9

