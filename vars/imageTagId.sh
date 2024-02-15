#!/bin/bash

COUNT_FILE="../vars/tagId.txt"

COUNT=$(cat "$COUNT_FILE" || echo "1.0")
NEW_COUNT=$(echo "$COUNT + 0.1" | bc)
echo "$NEW_COUNT"

echo "$NEW_COUNT" > "$COUNT_FILE"

export $NEW_COUNT
