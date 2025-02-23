## **Donate Raja API Endpoints**

This document outlines all the essential API endpoints for the **Donate Raja** platform, detailing methods for user management, item management, request handling, transactions, and admin functionalities.

### **User Management**

| Endpoint | HTTP Method | Description | Developer Name | Status | Expected Finish Date |
|----------|-------------|-------------|----------------|--------|----------------------|
| `/api/users/register` | `POST` | Register a new user (includes first name, last name, email, phone number, password, and pincode). |  |  |  |
| `/api/users/login` | `POST` | Login a user using username/email/phone and password. |  |  |  |
| `/api/users/logout` | `POST` | Logout the user from the session. |  |  |  |
| `/api/users/{user_id}` | `GET` | Fetch user profile details by user ID. |  |  |  |
| `/api/users/{user_id}` | `PUT` | Update user profile information (name, address, profile picture, etc.). |  |  |  |
| `/api/users/{user_id}/change-password` | `PUT` | Change user password. |  |  |  |
| `/api/users/{user_id}/reset-password` | `POST` | Send a password reset link via email/phone. |  |  |  |

### **Item Management**

#### **User-specific Views (Own Posts)**

| Endpoint | HTTP Method | Description | Developer Name | Status | Expected Finish Date |
|----------|-------------|-------------|----------------|--------|----------------------|
| `/api/users/{user_id}/posted-rentals` | `GET` | Get rentals posted by the user. |  |  |  |
| `/api/users/{user_id}/asked-rentals` | `GET` | Get rental requests by the user. |  |  |  |
| `/api/users/{user_id}/donated-items` | `GET` | Get items donated by the user. |  |  |  |
| `/api/users/{user_id}/asked-donations` | `GET` | Get donation requests by the user. |  |  |  |

#### **User-specific Views (All Posts by Others)**

| Endpoint                         | HTTP Method | Description | Developer Name | Status | Expected Finish Date |
|----------------------------------|-------------|-------------|----------------|--------|----------------------|
| `/api/items/rentals`             | `GET` | Get all posted rentals from all users. |  |  |  |
| `/api/items/requested/rentals`   | `GET` | Get all rental requests from all users. |  |  |  |
| `/api/items/donations`           | `GET` | Get all donated items from all users. |  |  |  |
| `/api/items/requested/donations` | `GET` | Get all donation requests from all users. |  |  |  |

#### **Donated Items (User-specific)**

| Endpoint | HTTP Method | Description | Developer Name | Status | Expected Finish Date |
|----------|-------------|-------------|----------------|--------|----------------------|
| `/api/items/donate` | `POST` | Post a new donated item (includes description, condition, etc.). |  |  |  |
| `/api/items/{item_id}/donated` | `PUT` | Update donated item details (condition, category, etc.). |  |  |  |
| `/api/items/{item_id}/remove-donation` | `DELETE` | Remove a donated item. |  |  |  |

#### **Rental Items (User-specific)**

| Endpoint | HTTP Method | Description | Developer Name | Status | Expected Finish Date |
|----------|-------------|-------------|----------------|--------|----------------------|
| `/api/items/rent` | `POST` | Post an item for rent (includes description, price, duration, etc.). |  |  |  |
| `/api/items/{item_id}/rental` | `PUT` | Update rental item details (price, duration, etc.). |  |  |  |
| `/api/items/{item_id}/remove-rental` | `DELETE` | Remove a rental item from the platform. |  |  |  |

#### **Item Requests (Donations, Rentals)**

| Endpoint | HTTP Method | Description | Developer Name | Status | Expected Finish Date |
|----------|-------------|-------------|----------------|--------|----------------------|
| `/api/items/{item_id}/send-request` | `POST` | Send a request for an item (donation or rental). |  |  |  |
| `/api/items/{item_id}/requests` | `GET` | View all requests for a specific item. |  |  |  |
| `/api/items/{item_id}/requests/{request_id}/approve` | `POST` | Approve a request for the item (admin or owner). |  |  |  |
| `/api/items/{item_id}/requests/{request_id}/reject` | `POST` | Reject a request for the item (admin or owner). |  |  |  |
| `/api/items/{item_id}/requests/{request_id}/cancel` | `POST` | Cancel a request for an item (user or admin). |  |  |  |

### **Transaction Management**

| Endpoint | HTTP Method | Description | Developer Name | Status | Expected Finish Date |
|----------|-------------|-------------|----------------|--------|----------------------|
| `/api/transactions` | `GET` | Get all transactions (filter by user, type, status). |  |  |  |
| `/api/transactions/{transaction_id}` | `GET` | Get details of a specific transaction. |  |  |  |
| `/api/transactions/{transaction_id}/payment` | `POST` | Process payment for a donation or rental. |  |  |  |
| `/api/transactions/{transaction_id}/complete` | `POST` | Mark a transaction as completed (finalize donation/rental). |  |  |  |

### **Admin Management**

| Endpoint | HTTP Method | Description | Developer Name | Status | Expected Finish Date |
|----------|-------------|-------------|----------------|--------|----------------------|
| `/api/admin/users` | `GET` | View all users (admin only). |  |  |  |
| `/api/admin/users/{user_id}/block` | `POST` | Block a user (admin only). |  |  |  |
| `/api/admin/items/{item_id}/approve` | `POST` | Approve an item for donation/rental (admin only). |  |  |  |
| `/api/admin/items/{item_id}/reject` | `POST` | Reject an item for donation/rental (admin only). |  |  |  |

### **Search & Filters**

| Endpoint | HTTP Method | Description | Developer Name | Status | Expected Finish Date |
|----------|-------------|-------------|----------------|--------|----------------------|
| `/api/search/items` | `GET` | Search items by keyword, category, or tags. |  |  |  |
| `/api/search/transactions` | `GET` | Search transactions with filters (e.g., status, date). |  |  |  |
| `/api/search/users` | `GET` | Search users by name, email, or phone. |  |  |  |

---

### **Core Features**

#### 1. **User Management**
- **Registration/Login**: Users can register using email, phone, or userid (jpa generated) accounts. Login options include user ID/password and password reset.
- **Profile Management**: Users can manage their profile, including name, address, and profile picture.
- **Security**: Includes password change and reset functionalities.

#### 2. **Item Management**
- **Donations and Rentals**: Users can post, update, and remove items they want to donate or rent.
- **Request Handling**: Users can send, view, approve, or reject requests for donations or rentals.

#### 3. **Transaction Management**
- **Payments**: Payment processing for donation and rental transactions.
- **Transaction Completion**: Finalization of transactions for both donations and rentals.

#### 4. **Admin Features**
- **User and Item Management**: Admins can block users, approve/reject posted items, and manage transactions.
- **Moderation**: Admins can view and manage all platform activities, ensuring fair use and safety.

#### 5. **Search and Filter**
- **Efficient Search**: Search for items, users, and transactions based on multiple filters like category, status, and date.

---

### **User Flow**

1. **Registration/Login**:
   - User logs in or registers via email/phone/customer_id accounts.
   - Post-login, user is redirected to the dashboard.

2. **Dashboard**:
   - Display options to post a donation/rental, view personal posts, and manage account settings.

3. **Item Management**:
   - Browse available items for donation/rent and send requests.
   - Manage own posts (update/remove).

4. **Transaction Handling**:
   - Process payments for rentals.
   - Finalize donations or rentals upon completion.

5. **Post-Transaction**:
   - Review and rate items or users after transactions.

---

### **Conclusion**

This API structure supports the core operations of the Donate Raja platform, ensuring smooth interaction for users, admins, and the platform’s core features. If you need any more refinements

or have specific implementation questions, feel free to ask!

Let me clarify the difference between **`item_transactions`** and **`item_requests`**, along with their typical use cases and database schemas:

---

### **1. `item_requests` Table**
**Purpose**:
- Tracks **requests** from users to donate/rent an item (pre-transaction phase).
- Manages the **request lifecycle** (pending → approved/rejected).

**Schema Example**:
```sql  
CREATE TABLE item_requests (  
    request_id BIGSERIAL PRIMARY KEY,  
    item_id BIGINT NOT NULL REFERENCES items(item_id),  
    requester_id BIGINT NOT NULL REFERENCES users(user_id),  
    request_type VARCHAR(20) CHECK (request_type IN ('DONATION', 'RENTAL')),  
    status VARCHAR(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED')),  
    message TEXT,  
    requested_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  
    resolved_at TIMESTAMP  
);  
```

**Use Cases**:
- User A requests to rent User B’s item.
- User C requests to donate an item to User D.

---

### **2. `item_transactions` Table**
**Purpose**:
- Records **completed exchanges** of items (post-request approval).
- Tracks financials (for rentals) or ownership transfers (for donations).

**Schema Example**:
```sql  
CREATE TABLE item_transactions (  
    transaction_id BIGSERIAL PRIMARY KEY,  
    request_id BIGINT REFERENCES item_requests(request_id), -- Optional link to original request  
    item_id BIGINT NOT NULL REFERENCES items(item_id),  
    from_user_id BIGINT NOT NULL REFERENCES users(user_id),  
    to_user_id BIGINT NOT NULL REFERENCES users(user_id),  
    transaction_type VARCHAR(20) CHECK (transaction_type IN ('DONATION', 'RENTAL')),  
    amount DECIMAL(10,2), -- Rental fee (if applicable)  
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  
    status VARCHAR(20) CHECK (status IN ('COMPLETED', 'CANCELLED', 'REFUNDED'))  
);  
```

**Use Cases**:
- Finalized rental of an item (payment processed).
- Completed donation transfer (ownership updated).

---

### **Key Differences**
| Feature               | `item_requests`                          | `item_transactions`                     |  
|-----------------------|------------------------------------------|-----------------------------------------|  
| **Stage**             | Pre-transaction (negotiation phase)      | Post-approval (execution phase)         |  
| **Statuses**          | Pending, Approved, Rejected              | Completed, Cancelled, Refunded          |  
| **Financials**        | No monetary exchange                     | Tracks payments (for rentals)           |  
| **Ownership**         | No ownership change                      | Updates item ownership                  |  
| **Relationship**      | May lead to a transaction                | May reference a request (optional)      |  

---

### **Workflow Example**
1. **Request Phase**:
   - User creates an `item_requests` entry (e.g., "Request to rent Item X").
   - Owner approves/rejects the request.

2. **Transaction Phase** (if approved):
   - System creates an `item_transactions` entry.
   - For rentals: Processes payment via the `wallet_transactions` table.
   - Updates item ownership (if donation).

---

### **When to Use Both**
1. **Complex Workflows** (e.g., rentals requiring approval):
   ```  
   item_requests → item_transactions → wallet_transactions  
   ```  

2. **Audit Trail**:
   - Keep requests for dispute resolution.
   - Use transactions for financial reporting.

3. **Cancel/Refund Scenarios**:
   - Update `item_transactions.status` to `CANCELLED`/`REFUNDED` without deleting the request.

---

### **Simplified Implementation (If Needed)**
If your use case is simple (e.g., instant donations with no approval step), you can merge both into a single `item_transactions` table with a `status` field. However, separate tables are better for:
- Multi-step workflows
- Tracking negotiations
- Maintaining audit trails

Let me know if you need help implementing either/both!