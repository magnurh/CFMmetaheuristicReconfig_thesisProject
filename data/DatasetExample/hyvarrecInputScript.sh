#!/bin/bash

start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0001_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0002_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0003_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0004_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0005_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo "progress: 5/50"
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0006_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0007_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0008_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0009_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0010_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo "progress: 10/50"
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0011_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0012_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0013_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0014_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0015_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo "progress: 15/50"
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0016_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0017_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0018_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0019_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0020_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo "progress: 20/50"
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0021_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0022_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0023_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0024_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0025_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo "progress: 25/50"
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0026_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0027_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0028_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0029_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0030_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo "progress: 30/50"
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0031_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0032_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0033_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0034_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0035_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo "progress: 35/50"
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0036_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0037_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0038_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0039_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0040_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo "progress: 40/50"
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0041_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0042_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0043_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0044_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0045_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo "progress: 45/50"
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0046_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0047_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0048_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0049_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo >> hyvarrecResult.txt
start=`date +%s%N`
curl -H "Content-Type: application/json" -X POST -d @./CFM/0050_aFMwC.json http://localhost:4000/process >> hyvarrecResult.txt
echo $(($(expr `date +%s%N` - $start)/1000000)) >> hyvarrecTime.txt
echo "progress: 50/50"
