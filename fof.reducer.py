#!/usr/bin/env python3
import sys

sortedTrios = {}
for line in sys.stdin:
    line = line.strip().split()
    hashable = line[0] + line[1] + line[2]
    if hashable not in sortedTrios:
        sortedTrios[hashable] =  1
    elif (sortedTrios[hashable] == 1):
        sortedTrios[hashable] = 0
        #Output statements to output triangle once it is verified
        #Print statements ensure sorting of second and third value
        if line[1] < line[2]:
            print(line[0] + " " + line[1] + " " + line[2])
        else:
            print(line[0] + " " + line[2] + " " + line[1])
    
        if line[0] < line[2]:
            print(line[1] + " " + line[0] + " " + line[2])
        else:
            print(line[1] + " " + line[2] + " " + line[0])

        if line[0] < line[1]:
            print(line[2] + " " + line[0] + " " + line[1])
        else:
            print(line[2] + " " + line[1] + " " + line[0])
