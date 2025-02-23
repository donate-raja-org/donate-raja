#!/bin/bash

API_URL="http://localhost:8080/auth/register"

USERS=(
  '{"username": "algot1", "email": "algot1@example.com", "phone_number": "123451", "password": "algot1", "first_name": "John",  "last_name": "Doe", "pincode": "672281"}'
  '{"username": "algot2", "email": "algot2@example.com", "phone_number": "123422", "password": "algot2", "first_name": "Jane",  "last_name": "Smith", "pincode": "672282"}'
  '{"username": "algot3", "email": "algot3@example.com", "phone_number": "123453", "password": "algot3", "first_name": "Alice", "last_name": "Brown", "pincode": "672283"}'
  '{"username": "algot4", "email": "algot4@example.com", "phone_number": "123454", "password": "algot4", "first_name": "Bob",   "last_name": "Johnson", "pincode": "672284"}'
  '{"username": "algot5", "email": "algot5@example.com", "phone_number": "123455", "password": "algot5", "first_name": "Eve",   "last_name": "Davis", "pincode": "672285"}'
)




for USER in "${USERS[@]}"; do
  echo "Creating user:"
  curl -X 'POST' "$API_URL" \
    -H 'accept: application/json' \
    -H 'Content-Type: application/json' \
    -d "$USER"
  echo -e "\n"
done
