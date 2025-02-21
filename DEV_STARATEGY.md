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