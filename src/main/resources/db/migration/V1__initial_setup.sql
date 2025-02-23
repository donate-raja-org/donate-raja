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
    user_bio VARCHAR(255) ,                               -- Unique username for login
    gender VARCHAR(20) CHECK (gender IN ('MALE', 'FEMALE', 'OTHER', 'PREFER_NOT_TO_SAY')),
    dob DATE CHECK (dob <= CURRENT_DATE - INTERVAL '13 years'),  -- NEW: Date of birth with age validation
    profile_picture TEXT,                                -- URL or path to profile picture
    status VARCHAR(20) DEFAULT 'ACTIVE',                 -- Account status (ACTIVE, INACTIVE, BLOCKED)
    is_email_verified BOOLEAN DEFAULT FALSE,             -- Whether email is verified
    is_phone_verified BOOLEAN DEFAULT FALSE,             -- Whether phone number is verified
    verification_code VARCHAR(6),                        -- Temporary verification code (email/phone)
    verification_expires TIMESTAMP,                      -- Expiry for verification code
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,      -- When the user was created
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,      -- Last update timestamp
    last_login_at TIMESTAMP,                              -- Last login timestamp
    failed_login_attempts INT DEFAULT 0,          -- NEW: Brute-force protection
    account_locked_until TIMESTAMP                -- NEW: Account lock duration
);


CREATE TABLE user_roles (
    role_id BIGSERIAL PRIMARY KEY,                             -- Unique ID for each role assignment
    user_id BIGINT NOT NULL,                              -- Foreign key reference to the user
    role VARCHAR(50) NOT NULL,                             -- Role name (e.g., USER, ADMIN)
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE -- Relate to the users table
);


-- CREATE INDEXES FOR USERS TABLE
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_phone ON users(phone_number);


-- CREATE ADDRESSES TABLE
CREATE TABLE IF NOT EXISTS addresses (
    address_id BIGSERIAL PRIMARY KEY,                   -- Unique ID for each address
    user_id BIGINT NOT NULL,                             -- Foreign key reference to the user
    street VARCHAR(255) NOT NULL,                        -- Street address
    city VARCHAR(100) NOT NULL,                          -- City
    state VARCHAR(100) NOT NULL,                         -- State
    pincode VARCHAR(20) NOT NULL,                       -- ZIP or postal code
    country VARCHAR(100),                                -- Optional country field
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,      -- When the address was created
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,      -- Last update timestamp
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE    -- Relate to the users table
);

-- Weekly Banner Table (For Featured Causes/Events)
CREATE TABLE banners (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    image_url TEXT,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE login_sessions (
    session_id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    token TEXT NOT NULL UNIQUE,
    user_agent TEXT,
    ip_address INET NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    expires_at TIMESTAMPTZ NOT NULL
);
-- Index for user_id (improves query performance for user-based lookups)
CREATE INDEX idx_login_sessions_user_id ON login_sessions(user_id);

-- Index for token (improves performance for token validation)
CREATE INDEX idx_login_sessions_token ON login_sessions(token);

-- Index for expiration (optimizes session cleanup)
CREATE INDEX idx_login_sessions_expires ON login_sessions(expires_at);

---- CREATE USER ROLES TABLE
--CREATE TABLE user_roles (
--    user_id BIGINT NOT NULL,                             -- Foreign key reference to the user
--    role VARCHAR(50) NOT NULL,                            -- Role name (e.g., USER, ADMIN)
--    PRIMARY KEY (user_id, role),                         -- Composite primary key
--    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE -- Relate to the users table
--);
-- CREATE USER ROLES TABLE

---- CREATE CONDITIONS TABLE
--CREATE TABLE IF NOT EXISTS conditions (
--    condition_id BIGSERIAL PRIMARY KEY,  -- Unique ID for each condition
--    name VARCHAR(100) NOT NULL UNIQUE,   -- Name of the condition (e.g., New, Used, Refurbished)
--    description TEXT                     -- Description of the condition
--);
--
---- CREATE CATEGORY TABLE
--CREATE TABLE IF NOT EXISTS categories (
--    category_id BIGSERIAL PRIMARY KEY,  -- Unique ID for each category
--    name VARCHAR(100) NOT NULL UNIQUE,  -- Name of the category
--    description TEXT                    -- Description of the category
--);


CREATE TABLE IF NOT EXISTS items (
    item_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    condition VARCHAR(50) CHECK (condition IN ('NEW', 'USED', 'REFURBISHED')),
    category VARCHAR(50) CHECK (category IN (
        'ELECTRONICS', 'FURNITURE', 'BOOKS', 'CLOTHING', 'HOME_APPLIANCES',
        'SPORTS', 'TOYS', 'VEHICLES', 'MUSIC_INSTRUMENTS', 'HEALTH_CARE',
        'OFFICE_SUPPLIES', 'ART_CRAFTS', 'PET_SUPPLIES', 'BABY_PRODUCTS',
        'JEWELRY', 'FOOTWEAR', 'GARDENING', 'BEAUTY_CARE', 'KITCHENWARE',
        'EDUCATIONAL', 'MISCELLANEOUS'
    )),
    donation_or_rent VARCHAR(20) CHECK (donation_or_rent IN ('DONATE', 'RENTAL')),
    available_from TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    available_to TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    price DECIMAL(10,2) DEFAULT 0.0,
    location VARCHAR(255) NOT NULL,
    pincode VARCHAR(10) NOT NULL,
    status VARCHAR(50) CHECK (status IN ('AVAILABLE', 'RESERVED', 'TAKEN')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);



CREATE TABLE IF NOT EXISTS item_requests (
    request_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    request_type VARCHAR(20) CHECK (request_type IN ('DONATION', 'RENTAL')),  -- NEW: Specifies the type of request
    message TEXT,
    status VARCHAR(50) CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED', 'CANCELLED')),
    rental_start_date TIMESTAMP,  -- NEW: Required only for rentals
    rental_end_date TIMESTAMP,  -- NEW: Required only for rentals
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE
);

-- CREATE INDEXES FOR ITEM REQUESTS TABLE
CREATE INDEX IF NOT EXISTS idx_item_requests_user_id ON item_requests(user_id);
CREATE INDEX IF NOT EXISTS idx_item_requests_item_id ON item_requests(item_id);
CREATE INDEX IF NOT EXISTS idx_item_requests_status ON item_requests(status);
CREATE INDEX IF NOT EXISTS idx_item_requests_request_type ON item_requests(request_type);


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
--CREATE INDEX IF NOT EXISTS idx_items_item_type ON items(item_type);
--CREATE INDEX IF NOT EXISTS idx_items_category ON items(category_id);
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
    deleted_by_sender BOOLEAN DEFAULT FALSE,      -- NEW: Soft delete tracking
    deleted_by_receiver BOOLEAN DEFAULT FALSE,     -- NEW: Soft delete tracking
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
    payment_method VARCHAR(50) CHECK (payment_method IN ('CREDIT_CARD', 'PAYPAL', 'BANK_TRANSFER')),  -- NEW
    transaction_reference VARCHAR(255) UNIQUE,     -- NEW: Prevent duplicate payments
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE,  -- Foreign key to items table
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE  -- Foreign key to users table
);
-- NEW INDEXES (Performance critical)
CREATE INDEX IF NOT EXISTS idx_login_sessions_user ON login_sessions(user_id, expires_at);  -- NEW: Session cleanup
CREATE INDEX IF NOT EXISTS idx_messages_sender_receiver ON messages(sender_id, receiver_id);  -- NEW: Message lookup
-- ==========================
-- End of Migration V1
-- ==========================
