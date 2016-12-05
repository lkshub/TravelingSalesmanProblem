#!/bin/bash

#inputFiles=`ls ./data/ | grep .tsp`

cd src

mainPath="edu/gatech/cse6140/Driver.java"
mainPackage="edu.gatech.cse6140/Driver"

#declare -A codePath
#codePath+=(["BnB"]="" ["MSTApprox"]="" ["Heur1"]="" ["Heur2"]="" ["Heur3"]="edu/gatech/cse6140/tsp/solvers/heuristic/FarthestInsertionSolver.java" ["LS1"]="edu/gatech/cse6140/tsp/solvers/SimulatedAnnealingSolver.java" ["LS2"]="edu/gatech/cse6140/tsp/solvers/local_search/DeterministicHillClimbingSolver.java" ["LS3"]="edu/gatech/cse6140/tsp/solvers/local_search/RandomizedHillClimbingSolver.java")
#declare -A package
#package+=(["BnB"]="" ["MSTApprox"]="" ["Heur1"]="" ["Heur2"]="" ["Heur3"]="edu.gatech.cse6140.tsp.solvers.heuristic.FarthestInsertionSolver" ["LS1"]="edu.gatech.cse6140.tsp.solvers.SimulatedAnnealingSolver" ["LS2"]="edu.gatech.cse6140.tsp.solvers.local_search.DeterministicHillClimbingSolver" ["LS3"]="edu.gatech.cse6140.tsp.solvers.local_search.RandomizedHillClimbingSolver")

fileName=$2

method=$4

cutOff=$6

seed=$8

repeatTime=1

javac -cp '.:../lib/*' $mainPath
		#number=`echo $files | cut -d'.' -f1`
#echo  reading $fileName
for (( i=1; i<=$repeatTime; i++ ))
do
	echo $i
	java  -cp '.:../lib/*' $mainPackage -i $fileName -a $method -t $cutOff # -s $seed
done
#echo  problem solved

