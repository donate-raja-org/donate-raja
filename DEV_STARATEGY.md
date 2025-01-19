# Donate Raja API Endpoints

This document lists all the API endpoints for the **Donate Raja** platform. Each endpoint is described with the HTTP method, description, developer responsible for the implementation, the current status of the endpoint, and the expected date to finish.

---

## **User Management**

| Endpoint | HTTP Method | Description | Developer Name | Status | Expected Finish Date |
|----------|-------------|-------------|----------------|--------|----------------------|
| `/api/users/register` | `POST` | Register a new user (include username, email, phone number, password, etc.). |  |  |  |
| `/api/users/login` | `POST` | Login a user using username/password or social login (Google/Facebook). |  |  |  |
| `/api/users/logout` | `POST` | Logout the user from the session. |  |  |  |
| `/api/users/{user_id}` | `GET` | Get user profile information by user ID. |  |  |  |
| `/api/users/{user_id}` | `PUT` | Update user profile (first name, last name, profile picture, etc.). |  |  |  |
| `/api/users/{user_id}/change-password` | `PUT` | Change user password. |  |  |  |
| `/api/users/{user_id}/reset-password` | `POST` | Reset user password (send reset link to email/phone). |  |  |  |

---

## **Item Management**

### **User-specific Views (Own Posts)**

| Endpoint | HTTP Method | Description | Developer Name | Status | Expected Finish Date |
|----------|-------------|-------------|----------------|--------|----------------------|
| `/api/users/{user_id}/posted-rentals` | `GET` | Get all rentals posted by the user (user's own rental posts). |  |  |  |
| `/api/users/{user_id}/asked-rentals` | `GET` | Get all rentals asked for by the user (user's own rental requests). |  |  |  |
| `/api/users/{user_id}/donated-items` | `GET` | Get all donated items by the user (user's own donated items). |  |  |  |
| `/api/users/{user_id}/asked-donations` | `GET` | Get all donation requests asked by the user (user's own donation requests). |  |  |  |

### **User-specific Views (All Posts by Others)**

| Endpoint | HTTP Method | Description | Developer Name | Status | Expected Finish Date |
|----------|-------------|-------------|----------------|--------|----------------------|
| `/api/items/posted-rentals` | `GET` | Get all posted rentals (from all users). |  |  |  |
| `/api/items/asked-rentals` | `GET` | Get all asked rentals (from all users). |  |  |  |
| `/api/items/donated-items` | `GET` | Get all donated items (from all users). |  |  |  |
| `/api/items/asked-donations` | `GET` | Get all asked donations (from all users). |  |  |  |

### **Donated Items (User-specific)**

| Endpoint | HTTP Method | Description | Developer Name | Status | Expected Finish Date |
|----------|-------------|-------------|----------------|--------|----------------------|
| `/api/items/donate` | `POST` | Donating a new item (user submits item for donation). |  |  |  |
| `/api/items/{item_id}/donated` | `PUT` | Update donated item information (e.g., condition, category). |  |  |  |
| `/api/items/{item_id}/remove-donation` | `DELETE` | Remove a donated item from the platform. |  |  |  |

### **Rental Items (User-specific)**

| Endpoint | HTTP Method | Description | Developer Name | Status | Expected Finish Date |
|----------|-------------|-------------|----------------|--------|----------------------|
| `/api/items/rent` | `POST` | Post an item for rent (user submits item for rent). |  |  |  |
| `/api/items/{item_id}/rental` | `PUT` | Update rental item information (e.g., price, rental duration). |  |  |  |
| `/api/items/{item_id}/remove-rental` | `DELETE` | Remove a rental item from the platform. |  |  |  |

### **Item-specific Requests (Donations, Rentals)**

| Endpoint | HTTP Method | Description | Developer Name | Status | Expected Finish Date |
|----------|-------------|-------------|----------------|--------|----------------------|
| `/api/items/{item_id}/send-request` | `POST` | Send a request for the item (donated, asked donation, posted rental, asked rental). |  |  |  |
| `/api/items/{item_id}/requests` | `GET` | Get all requests for an item (donated, asked donation, posted rental, or asked rental). |  |  |  |
| `/api/items/{item_id}/requests/{request_id}/approve` | `POST` | Approve a request for the item (admin or owner can approve). |  |  |  |
| `/api/items/{item_id}/requests/{request_id}/reject` | `POST` | Reject a request for the item (admin or owner can reject). |  |  |  |
| `/api/items/{item_id}/requests/{request_id}/view` | `GET` | View details of a specific request for an item. |  |  |  |
| `/api/items/{item_id}/requests/{request_id}/cancel` | `POST` | Cancel a request for an item (user or admin). |  |  |  |

---

## **Transaction Management**

| Endpoint | HTTP Method | Description | Developer Name | Status | Expected Finish Date |
|----------|-------------|-------------|----------------|--------|----------------------|
| `/api/transactions` | `GET` | Get a list of all transactions (filter by user, type, status, etc.). |  |  |  |
| `/api/transactions/{transaction_id}` | `GET` | Get details of a specific transaction. |  |  |  |
| `/api/transactions` | `POST` | Create a new transaction (user initiates donation or rental request). |  |  |  |
| `/api/transactions/{transaction_id}/status` | `PUT` | Update transaction status (admin or user can approve/cancel, etc.). |  |  |  |
| `/api/transactions/{transaction_id}/payment` | `POST` | Make payment for a donation or rental transaction. |  |  |  |
| `/api/transactions/{transaction_id}/complete` | `POST` | Mark transaction as completed (finalize donation or rental). |  |  |  |

---

## **Payment Transactions**

| Endpoint | HTTP Method | Description | Developer Name | Status | Expected Finish Date |
|----------|-------------|-------------|----------------|--------|----------------------|
| `/api/payment/transactions` | `POST` | Process a payment for an item (for donations or rentals). |  |  |  |
| `/api/payment/transactions/{payment_id}` | `GET` | Get payment transaction details. |  |  |  |
| `/api/payment/transactions/{payment_id}` | `PUT` | Update payment transaction (status, amount adjustment, etc.). |  |  |  |

---

## **Admin Management**

| Endpoint | HTTP Method | Description | Developer Name | Status | Expected Finish Date |
|----------|-------------|-------------|----------------|--------|----------------------|
| `/api/admin/users` | `GET` | Get a list of all users (admin only). |  |  |  |
| `/api/admin/users/{user_id}` | `GET` | Get a specific user's details (admin only). |  |  |  |
| `/api/admin/users/{user_id}/block` | `POST` | Block a user (admin only). |  |  |  |
| `/api/admin/items` | `GET` | Get a list of all items (admin only). |  |  |  |
| `/api/admin/items/{item_id}/approve` | `POST` | Approve an item for donation or rental (admin only). |  |  |  |
| `/api/admin/items/{item_id}/reject` | `POST` | Reject an item for donation or rental (admin only). |  |  |  |
| `/api/admin/items/{item_id}/remove` | `DELETE` | Remove an item from the platform (admin only). |  |  |  |
| `/api/admin/transactions` | `GET` | View all transactions (admin only). |  |  |  |
| `/api/admin/transactions/{transaction_id}/approve` | `POST` | Approve a transaction (admin only). |  |  |  |
| `/api/admin/transactions/{transaction_id}/cancel` | `POST` | Cancel a transaction (admin only). |  |  |  |

---

## **Search & Filters**

| Endpoint | HTTP Method | Description | Developer Name | Status | Expected Finish Date |
|----------|-------------|-------------|----------------|--------|----------------------|
| `/api/search/items` | `GET` | Search items by keyword, category, or tags. |  |  |  |
| `/api/search/transactions` | `GET` | Search transaction history based on various filters. |  |  |  |
| `/api/search/users` | `GET` | Search users based on name, email, or phone. |  |  |  |

---

## **Categories and Conditions**

| Endpoint | HTTP Method | Description | Developer Name | Status | Expected Finish Date |
|----------|-------------|-------------|----------------|--------|----------------------|
| `/api/categories` | `GET` | Get all available categories. |  |  |  |
| `/api/categories` | `POST` | Create a new category (admin only). |  |  |  |
| `/api/categories/{category_id}` | `PUT` | Update category details (admin only). |  |  |  |
| `/api/categories/{category_id}` | `DELETE` | Delete a category (admin only). |  |  |  |
| `/api/conditions` | `GET` | Get all available conditions. |  |  |  |
| `/api/conditions` | `POST` | Create a new condition (admin only). |  |  |  |
| `/api/conditions/{condition_id}` | `PUT` | Update condition details (admin only). |  |  |  |
| `/api/conditions/{condition_id}` | `DELETE` | Delete a condition (admin only). |  |  |  |

---

This document covers:

- User management (profile, login/logout, reset password).
- Donation and rental item management (create, update, remove).
- Request management for donations and rentals (send request, approve/reject).
- Transaction management (creating, updating, payment, completion).
- Admin functionality for managing users, items, transactions, and categories.
- Search functionality for items, transactions, and users.

You can now copy-paste this comprehensive structure. Let me know if there are any further corrections needed.

### **Sample User Flow**

1. **Start**
    - User visits the platform.
    - Options to register or log in (email/phone number, Google/Facebook).
    - User provides necessary credentials.
    - User successfully logs in and is redirected to the Dashboard.

2. **Dashboard**
    - User is presented with a dashboard containing the following options:
        - View their own posts (Donated items, Rentals, etc.).
        - Post a new item (Donation/Rental).
        - View all available items (for donation/rental).
        - Manage account settings.

3. **View All Available Posts**
    - User can browse all available posts, including:
        - **Donated Items**
        - **Rental Items**
        - **Requested Donations**
        - **Requested Rentals**
    - User can filter/search items by category, condition, price, etc.

4. **Manage Own Posts**
    - User can view and manage their own posts:
        - **View Own Posts**: All donated, rental, and requested items.
        - **Update Own Posts**: Modify price, description, condition, and other details.
        - **Delete Own Posts**: Remove items from the platform.

5. **Send Request for Items**
    - User can send a request for donated or rental items.
    - If it’s a rental, the user may also make a payment.
    - If it’s a donation, the user can request to receive the item.

6. **Post Item (Donation/Rental)**
    - User posts a new item, including details such as description, price, category, condition, and images.
    - New post is available for others to view and interact with.

7. **User Post Owner (Post Owner Approval)**
    - Post owner reviews requests from other users.
    - **Approve or Reject** requests for donations or rentals.
    - Notification sent to the requesting user after decision.

8. **Transaction Completion**
    - **For Rentals**: Payment processed, item marked as rented.
    - **For Donations**: Item marked as donated to the requesting user.

9. **Post-Transaction (Feedback & Ratings)**
    - After the transaction completes, both parties can provide feedback or ratings.
    - User reviews the experience, giving stars or a written review.

---

### **Conclusion**
This flow illustrates the core user journey for the Donate Raja platform. It covers everything from registration to posting items, making requests, approval process, and feedback collection.

---

Let me know if this is more in line with what you were looking for, or if any further adjustments are needed!
