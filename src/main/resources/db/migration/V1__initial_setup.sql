-- ==========================
-- Migration Version: V1
-- Description: Full schema with indexes for optimized performance
-- ==========================

-- CREATE USERS TABLE
CREATE TABLE IF NOT EXISTS users (
    user_id BIGSERIAL PRIMARY KEY,                       -- Unique ID for each user
    username VARCHAR(50) NOT NULL UNIQUE,                -- Unique username for login
    email VARCHAR(100) NOT NULL UNIQUE,                  -- Unique email for communication/login
    phone_number VARCHAR(15) UNIQUE,                     -- Unique phone number (optional)
    password VARCHAR(255) NOT NULL,                      -- Hashed password
    first_name VARCHAR(100),                             -- First name of the user
    last_name VARCHAR(100),                              -- Last name of the user
    profile_picture TEXT,                                -- URL or path to profile picture
    address TEXT,                                        -- Full address of the user
    pin_code VARCHAR(20),                                -- ZIP or postal code
    role VARCHAR(20) DEFAULT 'USER',                     -- Role for user access levels (e.g., USER, ADMIN)
    status VARCHAR(20) DEFAULT 'ACTIVE',                 -- Account status (ACTIVE, INACTIVE, BLOCKED)
    is_email_verified BOOLEAN DEFAULT FALSE,             -- Whether email is verified
    is_phone_verified BOOLEAN DEFAULT FALSE,             -- Whether phone number is verified
    verification_code VARCHAR(6),                        -- Temporary verification code (email/phone)
    verification_expires TIMESTAMP,                      -- Expiry for verification code
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,      -- When the user was created
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,      -- Last update timestamp
    last_login_at TIMESTAMP                              -- Last login timestamp
);

--CREATE TABLE user_roles (
--    user_id BIGINT NOT NULL,
--    role VARCHAR(50) NOT NULL,
--    PRIMARY KEY (user_id, role),
--    FOREIGN KEY (user_id) REFERENCES users(id)
--);

-- CREATE INDEXES FOR USERS TABLE
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

-- CREATE CONDITIONS TABLE
CREATE TABLE IF NOT EXISTS conditions (
    condition_id BIGSERIAL PRIMARY KEY,  -- Unique ID for each condition
    name VARCHAR(100) NOT NULL UNIQUE,   -- Name of the condition (e.g., New, Used, Refurbished)
    description TEXT                     -- Description of the condition
);

-- CREATE CATEGORY TABLE
CREATE TABLE IF NOT EXISTS categories (
    category_id BIGSERIAL PRIMARY KEY,  -- Unique ID for each category
    name VARCHAR(100) NOT NULL UNIQUE,  -- Name of the category
    description TEXT                    -- Description of the category
);


-- CREATE ITEMS TABLE (Merged Donation and Rental Items with unified status)
CREATE TABLE IF NOT EXISTS items (
    item_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    condition_id BIGINT,  -- Condition ID from the conditions table
    category_id BIGINT,  -- Category ID from the categories table
    image_url VARCHAR(255),
    item_type VARCHAR(50) CHECK (item_type IN ('DONATE', 'DONATE_REQUESTED', 'RENTAL', 'RENTAL_REQUESTED')),
    price_per_day DECIMAL(10, 2), -- Only for rentals
    available_from TIMESTAMP, -- Only for rentals
    available_to TIMESTAMP, -- Only for rentals
    status VARCHAR(50) CHECK (status IN (
        'DONATED',
        'DONATE_REQUESTED_PROVIDED',
        'DONATE_REQUESTED',
        'RENTAL_RECEIVED',
        'RENTAL_REQUESTED_COMPLETED',
        'RENTAL_REQUESTED',
        'AVAILABLE',
        'COMPLETED'
    )),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (condition_id) REFERENCES conditions(condition_id) ON DELETE SET NULL,
    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE SET NULL

);

-- CREATE ITEM IMAGES TABLE (New Table)
CREATE TABLE IF NOT EXISTS item_images (
    image_id BIGSERIAL PRIMARY KEY,  -- Unique ID for each image
    item_id BIGINT NOT NULL,  -- Item ID to which the image belongs
    image_url VARCHAR(255) NOT NULL,  -- URL of the image
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Image upload timestamp
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE  -- Foreign key to items table
);

-- CREATE ITEM TAGS TABLE (New Table)
CREATE TABLE IF NOT EXISTS item_tags (
    tag_id BIGSERIAL PRIMARY KEY,  -- Unique ID for each tag
    item_id BIGINT NOT NULL,  -- Item ID to which the tag belongs
    tag VARCHAR(100) NOT NULL,  -- Tag associated with the item
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Tag creation timestamp
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE  -- Foreign key to items table
);

-- CREATE INDEXES FOR ITEMS TABLE
CREATE INDEX IF NOT EXISTS idx_items_user_id ON items(user_id);
CREATE INDEX IF NOT EXISTS idx_items_status ON items(status);
CREATE INDEX IF NOT EXISTS idx_items_item_type ON items(item_type);
CREATE INDEX IF NOT EXISTS idx_items_category ON items(category_id);
CREATE INDEX IF NOT EXISTS idx_items_available_dates ON items(available_from, available_to); -- Composite index for availability date range

-- CREATE ITEM REVIEWS TABLE
CREATE TABLE IF NOT EXISTS item_reviews (
    review_id BIGSERIAL PRIMARY KEY,
    item_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- CREATE INDEXES FOR ITEM REVIEWS TABLE
CREATE INDEX IF NOT EXISTS idx_item_reviews_item_id ON item_reviews(item_id);
CREATE INDEX IF NOT EXISTS idx_item_reviews_user_id ON item_reviews(user_id);

-- CREATE RENTAL TRANSACTIONS TABLE
CREATE TABLE IF NOT EXISTS rental_transactions (
    transaction_id BIGSERIAL PRIMARY KEY,
    item_id BIGINT NOT NULL,
    renter_id BIGINT NOT NULL,
    rental_price DECIMAL(10, 2) NOT NULL,
    rental_start_date TIMESTAMP,
    rental_end_date TIMESTAMP,
    status VARCHAR(50) CHECK (status IN ('PENDING', 'ACTIVE', 'COMPLETED', 'CANCELLED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE,
    FOREIGN KEY (renter_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- CREATE INDEXES FOR RENTAL TRANSACTIONS TABLE
CREATE INDEX IF NOT EXISTS idx_rental_transactions_item_id ON rental_transactions(item_id);
CREATE INDEX IF NOT EXISTS idx_rental_transactions_renter_id ON rental_transactions(renter_id);
CREATE INDEX IF NOT EXISTS idx_rental_transactions_status ON rental_transactions(status);

-- CREATE ITEM DONATION REQUEST TABLE
CREATE TABLE IF NOT EXISTS donation_requests (
    request_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) CHECK (status IN ('PENDING', 'FULFILLED', 'CANCELLED')),
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- CREATE INDEXES FOR DONATION REQUESTS TABLE
CREATE INDEX IF NOT EXISTS idx_donation_requests_item_id ON donation_requests(item_id);
CREATE INDEX IF NOT EXISTS idx_donation_requests_user_id ON donation_requests(user_id);
CREATE INDEX IF NOT EXISTS idx_donation_requests_status ON donation_requests(status);

-- CREATE ITEM HISTORY TABLE (Exact replica of ITEMS TABLE)
CREATE TABLE IF NOT EXISTS item_history (
    item_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    condition VARCHAR(100),
    category VARCHAR(100),
    image_url VARCHAR(255),
    item_type VARCHAR(50) CHECK (item_type IN ('DONATE', 'DONATE_REQUESTED', 'RENTAL', 'RENTAL_REQUESTED')),
    price_per_day DECIMAL(10, 2),
    available_from TIMESTAMP,
    available_to TIMESTAMP,
    status VARCHAR(50) CHECK (status IN (
        'DONATED',
        'DONATE_REQUESTED_PROVIDED',
        'DONATE_REQUESTED',
        'RENTAL_RECEIVED',
        'RENTAL_REQUESTED_COMPLETED',
        'RENTAL_REQUESTED',
        'AVAILABLE',
        'COMPLETED'
    )),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- CREATE INDEXES FOR ITEM HISTORY TABLE
CREATE INDEX IF NOT EXISTS idx_item_history_user_id ON item_history(user_id);
CREATE INDEX IF NOT EXISTS idx_item_history_status ON item_history(status);
CREATE INDEX IF NOT EXISTS idx_item_history_item_type ON item_history(item_type);
CREATE INDEX IF NOT EXISTS idx_item_history_category ON item_history(category);

-- CREATE USER PREFERENCES TABLE
CREATE TABLE IF NOT EXISTS user_preferences (
    user_id BIGINT PRIMARY KEY,
    receive_notifications BOOLEAN DEFAULT TRUE,
    show_profile_publicly BOOLEAN DEFAULT TRUE,
    preferred_language VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- CREATE FAVORITES TABLE
CREATE TABLE IF NOT EXISTS favorites (
    favorite_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE
);

-- CREATE INDEXES FOR FAVORITES TABLE
CREATE INDEX IF NOT EXISTS idx_favorites_user_id ON favorites(user_id);
CREATE INDEX IF NOT EXISTS idx_favorites_item_id ON favorites(item_id);

-- CREATE USER REPORTS TABLE
CREATE TABLE IF NOT EXISTS user_reports (
    report_id BIGSERIAL PRIMARY KEY,
    reported_user_id BIGINT NOT NULL,
    reported_by_user_id BIGINT NOT NULL,
    reason TEXT NOT NULL,
    status VARCHAR(50) CHECK (status IN ('PENDING', 'RESOLVED', 'DISMISSED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reported_user FOREIGN KEY (reported_user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_reported_by_user FOREIGN KEY (reported_by_user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- CREATE INDEXES FOR USER REPORTS TABLE
CREATE INDEX IF NOT EXISTS idx_user_reports_reported_user_id ON user_reports(reported_user_id);
CREATE INDEX IF NOT EXISTS idx_user_reports_reported_by_user_id ON user_reports(reported_by_user_id);

-- CREATE NOTIFICATIONS TABLE
CREATE TABLE IF NOT EXISTS notifications (
    notification_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    message TEXT NOT NULL,
    type VARCHAR(50),
    status VARCHAR(50) CHECK (status IN ('UNREAD', 'READ', 'ARCHIVED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_notifications_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- CREATE MESSAGES TABLE
CREATE TABLE IF NOT EXISTS messages (
    message_id BIGSERIAL PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_message_sender FOREIGN KEY (sender_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_message_receiver FOREIGN KEY (receiver_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- CREATE PAYMENT TRANSACTIONS TABLE
CREATE TABLE IF NOT EXISTS payment_transactions (
    payment_id BIGSERIAL PRIMARY KEY,  -- Unique ID for each payment transaction
    user_id BIGINT NOT NULL,  -- User ID making the payment
    item_id BIGINT NOT NULL,  -- Item being paid for
    amount DECIMAL(10, 2) NOT NULL,  -- Amount paid
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Payment date
    status VARCHAR(50) CHECK (status IN ('PENDING', 'COMPLETED', 'FAILED')),  -- Payment status
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE,  -- Foreign key to items table
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE  -- Foreign key to users table
);

-- ==========================
-- End of Migration V1
-- ==========================
