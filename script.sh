#!/bin/bash

# Check if COUNT variable is set, if not, initialize it to 1.0
: ${COUNT:=1.0}

# Increment the count by 0.1
COUNT=$(echo "$COUNT + 0.1" | bc)

# Print the new count
echo "Current count: $COUNT"

# Export the updated count as an environment variable
export COUNT

