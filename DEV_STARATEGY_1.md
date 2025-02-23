Here’s your **updated README** with:  
✅ **All API endpoints**  
✅ **Status (✅ DONE / ⏳ PENDING)**  
✅ **Wallet system integration**

---

# **Donate Raja API Documentation**
🚀 **Backend: Kotlin (Spring Boot)**  
📡 **Frontend: React**  
🔒 **Authentication: JWT**  
💰 **Payment: Razorpay (Planned)**  
📨 **Notifications: REST API Polling**

---

## **📌 API Status Overview**
| Feature                 | Status  |
|-------------------------|---------|
| **Authentication**      | ✅ DONE |
| **User Management**     | ✅ DONE |
| **Items & Listings**    | ✅ DONE |
| **Item Requests**       | ✅ DONE |
| **Reviews & Ratings**   | ✅ DONE |
| **Favorites**           | ✅ DONE |
| **Messaging**           | ✅ DONE |
| **Wallet System**       | ⏳ PENDING |
| **Payments (Razorpay)** | ⏳ PENDING |
| **Banners (Weekly Targets)** | ⏳ PENDING |
| **Admin Features**      | ✅ DONE |

---

## **🔹 Authentication & User Management**
| Endpoint                     | Method | Description                          | Status  |
|------------------------------|--------|--------------------------------------|---------|
| `/auth/register`             | POST   | Register a new user                 | ✅ DONE |
| `/auth/login`                | POST   | User login                          | ✅ DONE |
| `/auth/logout`               | POST   | User logout                         | ✅ DONE |
| `/auth/refresh-token`        | POST   | Refresh access token                | ✅ DONE |
| `/users/{userId}`            | GET    | Get user profile                    | ✅ DONE |
| `/users/update`              | PATCH  | Update user profile                 | ✅ DONE |

---

## **🔹 Items & Listings**
| Endpoint                     | Method | Description                          | Status  |
|------------------------------|--------|--------------------------------------|---------|
| `/items`                     | GET    | List items with filters             | ✅ DONE |
| `/items/{itemId}`            | GET    | Get item details                    | ✅ DONE |
| `/items`                     | POST   | Create a new item                   | ✅ DONE |
| `/items/{itemId}`            | PATCH  | Update item details                 | ✅ DONE |
| `/items/{itemId}`            | DELETE | Delete item                          | ✅ DONE |

---

## **🔹 Item Requests (Donations/Rentals)**
| Endpoint                      | Method | Description                          | Status  |
|--------------------------------|--------|--------------------------------------|---------|
| `/item-requests`              | GET    | List item requests                  | ✅ DONE |
| `/item-requests`              | POST   | Create an item request              | ✅ DONE |
| `/item-requests/{requestId}`  | PATCH  | Update request status               | ✅ DONE |
| `/item-requests/{requestId}`  | DELETE | Cancel item request                 | ✅ DONE |

---

## **🔹 Reviews & Ratings**
| Endpoint                       | Method | Description                          | Status  |
|---------------------------------|--------|--------------------------------------|---------|
| `/item-reviews/{itemId}`        | GET    | Get item reviews                    | ✅ DONE |
| `/item-reviews/{itemId}`        | POST   | Submit a review                     | ✅ DONE |
| `/item-reviews/{reviewId}`      | DELETE | Delete a review                     | ✅ DONE |

---

## **🔹 Favorites & Wishlist**
| Endpoint                     | Method | Description                          | Status  |
|------------------------------|--------|--------------------------------------|---------|
| `/favorites`                 | GET    | List favorite items                 | ✅ DONE |
| `/favorites`                 | POST   | Add an item to favorites            | ✅ DONE |
| `/favorites/{favoriteId}`    | DELETE | Remove from favorites               | ✅ DONE |

---

## **🔹 Messaging (Chat System)**
| Endpoint                     | Method | Description                          | Status  |
|------------------------------|--------|--------------------------------------|---------|
| `/messages`                  | GET    | Get user messages                   | ✅ DONE |
| `/messages/send`             | POST   | Send a message                      | ✅ DONE |
| `/messages/{messageId}`      | DELETE | Delete a message                    | ✅ DONE |

---

## **🔹 Wallet System (Planned Feature)**
| Endpoint                      | Method | Description                          | Status  |
|--------------------------------|--------|--------------------------------------|---------|
| `/wallet/balance`             | GET    | Get user wallet balance             | ⏳ PENDING |
| `/wallet/transactions`        | GET    | Get wallet transaction history      | ⏳ PENDING |
| `/wallet/redeem`              | POST   | Redeem points for features          | ⏳ PENDING |
| `/wallet/add-points`          | POST   | Add points (Admin or Payment)       | ⏳ PENDING |

### **💰 Wallet System Features**
- Users **earn points** for donations, referrals, and platform activity.
- Points can be **spent on premium features** (chatting, boosting items).
- Users can **purchase points via Razorpay** (optional).

---

## **🔹 Razorpay & Payment Transactions (Planned)**
| Endpoint                      | Method | Description                          | Status  |
|--------------------------------|--------|--------------------------------------|---------|
| `/payments/initiate`          | POST   | Initiate a new payment              | ⏳ PENDING |
| `/payments/verify`            | POST   | Verify payment status               | ⏳ PENDING |
| `/payments/history`           | GET    | Get user payment history            | ⏳ PENDING |

### **🔹 Payment Flow**
- Users donate to **weekly banners** (featured donation events).
- Payments processed via **Razorpay (or alternate payment providers).**

---

## **🔹 Weekly Banners (Planned Feature)**
| Endpoint                       | Method | Description                          | Status  |
|---------------------------------|--------|--------------------------------------|---------|
| `/banners`                     | GET    | List active banners                 | ⏳ PENDING |
| `/banners/{bannerId}`          | GET    | Get banner details                  | ⏳ PENDING |
| `/banners`                     | POST   | Create a new banner                 | ⏳ PENDING |
| `/banners/{bannerId}`          | PATCH  | Update banner details               | ⏳ PENDING |
| `/banners/{bannerId}`          | DELETE | Delete a banner                     | ⏳ PENDING |

---

## **🔹 Admin Endpoints**
| Endpoint                      | Method | Description                          | Status  |
|--------------------------------|--------|--------------------------------------|---------|
| `/admin/users`                | GET    | List all users                      | ✅ DONE |
| `/admin/users/{userId}/status` | PATCH  | Block/unblock user                  | ✅ DONE |
| `/admin/items`                | GET    | List all items                      | ✅ DONE |
| `/admin/items/{itemId}/status` | PATCH  | Approve/reject item                 | ✅ DONE |
| `/admin/reports`              | GET    | Get user reports                    | ✅ DONE |
| `/admin/reports/{reportId}`   | PATCH  | Resolve a report                    | ✅ DONE |

---

### **📌 Next Steps**
✅ **Would you like me to generate the API controllers for Wallet & Razorpay?**  
✅ **Any other API enhancements you need?**

🚀 Let me know how you’d like to proceed!