#!/usr/bin/env python3
import sys

#Take in all input from standard in
for line in sys.stdin:
    #Create a string array from each line of input
    line = line.strip().split()
    #Iterate through each value except for the first
    #Output triplets created using first value and every permutation of possible
    #second and third values. Sort this triplet and output.
    for i in range(1,(len(line))):
        for j in range(i+1, len(line)):
            key = sorted([line[0], line[i], line[j]])
            print(key[0] + " " + key[1] + " " + key[2])

