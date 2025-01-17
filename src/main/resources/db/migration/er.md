Overview
This application allows users to donate items, request donations, rent items, and track their activity. Admins have full control over content and can manage users, approve donations, monitor donation campaigns, and generate reports.

Entity-Relationship (ER) Diagram
Below is the Entity-Relationship (ER) Diagram for the Donation & Rental App. The diagram represents key entities, their attributes, and relationships.


+--------------------+         +------------------------+
|      User          |         |      Admin             |
+--------------------+         +------------------------+
| user_id (PK)       |         | admin_id (PK)          |
| username           |         | user_id (FK)           |
| email              |         | role                   |
| password           |         | last_login             |
| first_name         |         +------------------------+
| last_name          |         
| phone_number       |         
| profile_picture    |         
| address            |         
+--------------------+
|  
| 1
|  
| has
v  
+--------------------+        +--------------------+        +--------------------+
|  Donation_Item     |        |   Rental_Item      |        |   Donation_Request |
+--------------------+        +--------------------+        +--------------------+
| item_id (PK)       |        | rental_id (PK)     |        | request_id (PK)    |
| user_id (FK)       |        | user_id (FK)       |        | user_id (FK)       |
| title              |        | title              |        | item_id (FK)       |
| description        |        | description        |        | quantity           |
| condition          |        | condition          |        | date_requested     |
| category           |        | category           |        | status             |
| image_url          |        | price_per_day      |        +--------------------+
| donation_status    |        | rental_status      |
| created_at         |        | available_from     |
| updated_at         |        | available_to       |
+--------------------+        +--------------------+
|                             |
| 1                           | 1
| has                        | has
|                             |
v                             v
+--------------------+        +--------------------+
|    Report          |        |   Message          |
+--------------------+        +--------------------+
| report_id (PK)     |        | message_id (PK)    |
| user_id (FK)       |        | sender_id (FK)     |
| item_id (FK)       |        | receiver_id (FK)   |
| report_type        |        | message_text       |
| report_description |        | sent_at            |
| status             |        | is_read            |
| created_at         |        +--------------------+
+--------------------+
|
| 1
| has
|
v
+--------------------+        
|   Donation_Campaign|       
+--------------------+       
| campaign_id (PK)   |       
| name               |       
| description        |       
| target_amount      |       
| amount_raised      |       
| start_date         |       
| end_date           |       
| status             |       
| created_at         |       
+--------------------+
# ER Diagram - Donate Raja Platform

This document provides the Entity-Relationship (ER) diagram schema for the "Donate Raja" platform, outlining the key entities and their relationships.

## Tables

### Users Table
Stores information about the users.

| Column         | Data Type      | Description                                |
|----------------|----------------|--------------------------------------------|
| user_id        | BIGINT         | Primary key, unique user identifier        |
| username       | VARCHAR(255)   | User's unique username                     |
| email          | VARCHAR(255)   | User's email address, unique               |
| password       | VARCHAR(255)   | User's password                            |
| first_name     | VARCHAR(255)   | User's first name                          |
| last_name      | VARCHAR(255)   | User's last name                           |
| phone_number   | VARCHAR(20)    | User's phone number                        |
| profile_picture| VARCHAR(255)   | URL to user's profile picture              |
| address        | TEXT           | User's physical address                    |
| created_at     | TIMESTAMP      | Timestamp when the user was created        |
| updated_at     | TIMESTAMP      | Timestamp when the user was last updated   |

### Items Table
Stores information about items donated or rented by users.

| Column         | Data Type      | Description                                |
|----------------|----------------|--------------------------------------------|
| item_id        | BIGINT         | Primary key, unique item identifier        |
| user_id        | BIGINT         | Foreign key to Users table (user_id)       |
| title          | VARCHAR(255)   | Title of the item                          |
| description    | TEXT           | Description of the item                    |
| condition      | VARCHAR(100)   | Condition of the item                      |
| category       | VARCHAR(100)   | Category of the item                       |
| image_url      | VARCHAR(255)   | URL to the item's image                    |
| item_type      | VARCHAR(50)    | Type of item (DONATE, DONATE_REQUESTED, RENTAL, RENTAL_REQUESTED) |
| price_per_day  | DECIMAL(10, 2) | Price per day (only for rentals)           |
| available_from | TIMESTAMP      | Availability start date (only for rentals) |
| available_to   | TIMESTAMP      | Availability end date (only for rentals)   |
| status         | VARCHAR(50)    | Current status of the item                 |
| created_at     | TIMESTAMP      | Timestamp when the item was created        |
| updated_at     | TIMESTAMP      | Timestamp when the item was last updated   |

### Item Reviews Table
Stores user reviews for items.

| Column         | Data Type      | Description                                |
|----------------|----------------|--------------------------------------------|
| review_id      | BIGINT         | Primary key, unique review identifier      |
| item_id        | BIGINT         | Foreign key to Items table (item_id)       |
| user_id        | BIGINT         | Foreign key to Users table (user_id)       |
| rating         | INT            | Rating of the item (1 to 5)                |
| comment        | TEXT           | Comment provided by the user               |
| created_at     | TIMESTAMP      | Timestamp when the review was created      |

### Rental Transactions Table
Stores information about rental transactions.

| Column         | Data Type      | Description                                |
|----------------|----------------|--------------------------------------------|
| transaction_id | BIGINT         | Primary key, unique transaction identifier |
| item_id        | BIGINT         | Foreign key to Items table (item_id)       |
| renter_id      | BIGINT         | Foreign key to Users table (user_id)       |
| rental_price   | DECIMAL(10, 2) | Rental price per day                       |
| rental_start_date | TIMESTAMP    | Start date of the rental                   |
| rental_end_date   | TIMESTAMP    | End date of the rental                     |
| status         | VARCHAR(50)    | Status of the rental transaction (PENDING, ACTIVE, COMPLETED, CANCELLED) |
| created_at     | TIMESTAMP      | Timestamp when the transaction was created |

### Donation Requests Table
Stores information about donation requests made by users.

| Column         | Data Type      | Description                                |
|----------------|----------------|--------------------------------------------|
| request_id     | BIGINT         | Primary key, unique request identifier     |
| user_id        | BIGINT         | Foreign key to Users table (user_id)       |
| item_id        | BIGINT         | Foreign key to Items table (item_id)       |
| request_date   | TIMESTAMP      | Date the donation request was made         |
| status         | VARCHAR(50)    | Status of the donation request (PENDING, FULFILLED, CANCELLED) |

### Item History Table
Exact replica of the Items table for tracking item changes over time.

| Column         | Data Type      | Description                                |
|----------------|----------------|--------------------------------------------|
| item_id        | BIGINT         | Foreign key to Items table (item_id)       |
| user_id        | BIGINT         | Foreign key to Users table (user_id)       |
| title          | VARCHAR(255)   | Title of the item                          |
| description    | TEXT           | Description of the item                    |
| condition      | VARCHAR(100)   | Condition of the item                      |
| category       | VARCHAR(100)   | Category of the item                       |
| image_url      | VARCHAR(255)   | URL to the item's image                    |
| item_type      | VARCHAR(50)    | Type of item (DONATE, DONATE_REQUESTED, RENTAL, RENTAL_REQUESTED) |
| price_per_day  | DECIMAL(10, 2) | Price per day (only for rentals)           |
| available_from | TIMESTAMP      | Availability start date (only for rentals) |
| available_to   | TIMESTAMP      | Availability end date (only for rentals)   |
| status         | VARCHAR(50)    | Current status of the item                 |
| created_at     | TIMESTAMP      | Timestamp when the item was created        |
| updated_at     | TIMESTAMP      | Timestamp when the item was last updated   |

### User Preferences Table
Stores user-specific settings and preferences.

| Column                 | Data Type      | Description                               |
|------------------------|----------------|-------------------------------------------|
| user_id                | BIGINT         | Foreign key to Users table (user_id)      |
| receive_notifications  | BOOLEAN        | Whether the user receives notifications   |
| show_profile_publicly  | BOOLEAN        | Whether the user's profile is public      |
| preferred_language     | VARCHAR(50)    | User's preferred language                 |

### Favorites Table
Stores information about user's favorite items.

| Column         | Data Type      | Description                                |
|----------------|----------------|--------------------------------------------|
| favorite_id    | BIGINT         | Primary key, unique favorite identifier    |
| user_id        | BIGINT         | Foreign key to Users table (user_id)       |
| item_id        | BIGINT         | Foreign key to Items table (item_id)       |
| created_at     | TIMESTAMP      | Timestamp when the favorite was created    |

---

## Relationships

- **Users ↔ Items**: One-to-many relationship. A user can have multiple items listed (donated or rented).
- **Items ↔ Item Reviews**: One-to-many relationship. Each item can have multiple reviews from different users.
- **Users ↔ Rental Transactions**: One-to-many relationship. A user can rent multiple items.
- **Users ↔ Donation Requests**: One-to-many relationship. A user can make multiple donation requests.
- **Items ↔ Item History**: One-to-many relationship. Each change to an item is tracked in the item history table.
- **Users ↔ User Preferences**: One-to-one relationship. Each user can have only one preference entry.
- **Users ↔ Favorites**: One-to-many relationship. A user can save multiple favorite items.
