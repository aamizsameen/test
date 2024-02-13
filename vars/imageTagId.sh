#!/bin/bash

# Check if COUNT variable is set, if not, initialize it to 1.0
: ${COUNT:=1.0}

COUNT=$(echo "$COUNT + 0.1" | bc)
echo $COUNT
export COUNT