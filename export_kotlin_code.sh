#!/bin/bash

# Define the output file
OUTPUT_FILE="all_kotlin_code.txt"

# Ensure the output file is empty before writing
> "$OUTPUT_FILE"

# Find all Kotlin files under the 'src' directory and concatenate them into the output file
find src -type f -name "*.kt" -exec cat {} \; >> "$OUTPUT_FILE"

# Print completion message
echo "All Kotlin files under 'src' have been copied to $OUTPUT_FILE"
