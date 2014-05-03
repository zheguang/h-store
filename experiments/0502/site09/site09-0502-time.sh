#!/bin/sh
ant clean-java build-java
ant hstore-prepare -Dproject=microwintimehstorefull -Dhosts="localhost:0:0"
ant hstore-prepare -Dproject=microwintimehstorecleanup -Dhosts="localhost:0:0"
ant hstore-prepare -Dproject=microwintimehstoresomecleanup -Dhosts="localhost:0:0"
ant hstore-prepare -Dproject=microwintimehstorenocleanup -Dhosts="localhost:0:0"
ant hstore-prepare -Dproject=microwintimesstore -Dhosts="localhost:0:0"
python ./tools/runexperiments.py --tmin 1 --tmax 1 --tstep 1 --rmin 1000 --rmax 25000 --rstep 2000 --stop --warmup 40000 -p microwintimehstorefull -o "experiments/0502/site09/microwintimehstorefull-1c-10w1s1000t-0502.txt"
python ./tools/runexperiments.py --tmin 1 --tmax 1 --tstep 1 --rmin 1000 --rmax 25000 --rstep 2000 --stop --warmup 40000 -p microwintimehstorecleanup -o "experiments/0502/site09/microwintimehstorecleanup-1c-10w1s1000t-0502.txt"
python ./tools/runexperiments.py --tmin 1 --tmax 1 --tstep 1 --rmin 1000 --rmax 25000 --rstep 2000 --stop --warmup 40000 -p microwintimehstoresomecleanup -o "experiments/0502/site09/microwintimehstoresomecleanup-1c-10w1s1000t-0502.txt"
python ./tools/runexperiments.py --tmin 1 --tmax 1 --tstep 1 --rmin 1000 --rmax 25000 --rstep 2000 --stop --warmup 40000 -p microwintimehstorenocleanup -o "experiments/0502/site09/microwintimehstorenocleanup-1c-10w1s1000t-0502.txt"
python ./tools/runexperiments.py --tmin 1 --tmax 1 --tstep 1 --rmin 1000 --rmax 25000 --rstep 2000 --stop --warmup 40000 -p microwintimesstore -o "experiments/0502/site09/microwintimesstore-1c-10w1s1000t-0502.txt"
python ./tools/runexperiments.py --tmin 10 --tmax 10 --tstep 1 --rmin 100 --rmax 2500 --rstep 200 --stop --warmup 40000 -p microwintimehstorefull -o "experiments/0502/site09/microwintimehstorefull-10c-10w1s1000t-0502.txt"
python ./tools/runexperiments.py --tmin 10 --tmax 10 --tstep 1 --rmin 100 --rmax 2500 --rstep 200 --stop --warmup 40000 -p microwintimehstorecleanup -o "experiments/0502/site09/microwintimehstorecleanup-10c-10w1s1000t-0502.txt"
python ./tools/runexperiments.py --tmin 10 --tmax 10 --tstep 1 --rmin 100 --rmax 2500 --rstep 200 --stop --warmup 40000 -p microwintimehstoresomecleanup -o "experiments/0502/site09/microwintimehstoresomecleanup-10c-10w1s1000t-0502.txt"
python ./tools/runexperiments.py --tmin 10 --tmax 10 --tstep 1 --rmin 100 --rmax 2500 --rstep 200 --stop --warmup 40000 -p microwintimehstorenocleanup -o "experiments/0502/site09/microwintimehstorenocleanup-10c-10w1s1000t-0502.txt"
python ./tools/runexperiments.py --tmin 10 --tmax 10 --tstep 1 --rmin 100 --rmax 2500 --rstep 200 --stop --warmup 40000 -p microwintimesstore -o "experiments/0502/site09/microwintimesstore-10c-10w1s1000t-0502.txt"
