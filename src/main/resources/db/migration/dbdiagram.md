-- generate relation ships from dbdiagram.io
Table users {
  user_id BIGINT [pk, increment]
  username VARCHAR(255) [unique]
  email VARCHAR(255) [unique]
  password VARCHAR(255)
  first_name VARCHAR(255)
  last_name VARCHAR(255)
  phone_number VARCHAR(20)
  profile_picture VARCHAR(255)
  address TEXT
  created_at TIMESTAMP
  updated_at TIMESTAMP
}

Table items {
  item_id BIGINT [pk, increment]
  user_id BIGINT
  title VARCHAR(255)
  description TEXT
  condition VARCHAR(100)
  category VARCHAR(100)
  image_url VARCHAR(255)
  item_type VARCHAR(50)
  price_per_day DECIMAL(10, 2)
  available_from TIMESTAMP
  available_to TIMESTAMP
  status VARCHAR(50)
  created_at TIMESTAMP
  updated_at TIMESTAMP
}

Table item_reviews {
  review_id BIGINT [pk, increment]
  item_id BIGINT
  user_id BIGINT
  rating INT
  comment TEXT
  created_at TIMESTAMP
}

Table rental_transactions {
  transaction_id BIGINT [pk, increment]
  item_id BIGINT
  renter_id BIGINT
  rental_price DECIMAL(10, 2)
  rental_start_date TIMESTAMP
  rental_end_date TIMESTAMP
  status VARCHAR(50)
  created_at TIMESTAMP
}

Table donation_requests {
  request_id BIGINT [pk, increment]
  user_id BIGINT
  item_id BIGINT
  request_date TIMESTAMP
  status VARCHAR(50)
}

Table item_history {
  item_history_id BIGINT [pk, increment]
  item_id BIGINT
  user_id BIGINT
  title VARCHAR(255)
  description TEXT
  condition VARCHAR(100)
  category VARCHAR(100)
  image_url VARCHAR(255)
  item_type VARCHAR(50)
  price_per_day DECIMAL(10, 2)
  available_from TIMESTAMP
  available_to TIMESTAMP
  status VARCHAR(50)
  created_at TIMESTAMP
  updated_at TIMESTAMP
}

Table user_preferences {
  user_id BIGINT [pk]
  receive_notifications BOOLEAN
  show_profile_publicly BOOLEAN
  preferred_language VARCHAR(50)
}

Table favorites {
  favorite_id BIGINT [pk, increment]
  user_id BIGINT
  item_id BIGINT
  created_at TIMESTAMP
}

Ref: items.user_id > users.user_id
Ref: item_reviews.item_id > items.item_id
Ref: item_reviews.user_id > users.user_id
Ref: rental_transactions.item_id > items.item_id
Ref: rental_transactions.renter_id > users.user_id
Ref: donation_requests.item_id > items.item_id
Ref: donation_requests.user_id > users.user_id
Ref: item_history.item_id > items.item_id
Ref: item_history.user_id > users.user_id
Ref: user_preferences.user_id > users.user_id
Ref: favorites.user_id > users.user_id
Ref: favorites.item_id > items.item_id
