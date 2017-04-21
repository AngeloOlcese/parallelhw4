#!/usr/bin/env python3
import sys

prev = []
for line in sys.stdin:
    line = line.strip().split()
    if line == prev:
        prev = []
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
    else:
        prev = line
