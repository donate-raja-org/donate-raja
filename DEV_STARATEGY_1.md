# Donate Raja API Documentation

## Base URL
`https://api.donate-raja.com/v1`

---

## **Authentication & Authorization**
| Endpoint | Method | Description | Access | Parameters | Status |
|----------|--------|-------------|--------|------------|--------|
| `/auth/register` | POST | Register new user | Public | `UserRegistrationDTO` | DONE |
| `/auth/login` | POST | User login | Public | `LoginRequest` | DONE |
| `/auth/refresh` | POST | Refresh JWT token | User+Admin | `RefreshTokenRequest` | DONE |
| `/auth/logout` | POST | Invalidate token | User+Admin | - | DONE |

---

## **User Management**
| Endpoint | Method | Description | Access | Parameters | Status |
|----------|--------|-------------|--------|------------|--------|
| `/users/me` | GET | Get current user profile | User+Admin | - | DONE |
| `/users/me` | PUT | Update profile | User+Admin | `UserUpdateDTO` | DONE |
| `/users/me/password` | PUT | Change password | User+Admin | `PasswordChangeDTO` | DONE |
| `/users/me/avatar` | PATCH | Update profile picture | User+Admin | `MultipartFile` | DONE |
| `/users/{userId}` | GET | Get public user profile | Public | - | DONE |
| `/users/{userId}/verify-email` | POST | Resend verification email | User+Admin | - | PENDING |

---

## **Item Management**
**Base Path:** `/items`

| Endpoint | Method | Description | Access | Parameters | Status |
|----------|--------|-------------|--------|------------|--------|
| `/items` | POST | Create new item | User+Admin | `ItemCreateDTO` | DONE |
| `/items/{itemId}` | GET | Get item details | Public | - | DONE |
| `/items/{itemId}` | PUT | Update item | Owner/Admin | `ItemUpdateDTO` | DONE |
| `/items/{itemId}` | DELETE | Delete item | Owner/Admin | - | DONE |
| `/items/{itemId}/status` | PATCH | Update status | Owner/Admin | `ItemStatusDTO` | DONE |
| `/items` | GET | List all items | Public | `type=[donation\|rental]`, `category`, `status` | DONE |
| `/users/{userId}/items` | GET | Get user's items | Public | `type=[donation\|rental]` | DONE |
| `/items/donations` | GET | Get all donation items | Public | - | PENDING |
| `/items/rentals` | GET | Get all rental items | Public | - | PENDING |

---

## **Item Requests**
**Base Path:** `/requests`

| Endpoint | Method | Description | Access | Parameters | Status |
|----------|--------|-------------|--------|------------|--------|
| `/requests` | POST | Create request | User+Admin | `RequestCreateDTO` | DONE |
| `/requests/{requestId}` | GET | Get request | Requester/Owner/Admin | - | DONE |
| `/requests/{requestId}` | PATCH | Update request | Admin/Owner | `RequestUpdateDTO` | DONE |
| `/requests` | GET | List requests | User+Admin | `type=[sent\|received]`, `status` | DONE |

---

## **Transactions**
**Base Path:** `/transactions`

| Endpoint | Method | Description | Access | Parameters | Status |
|----------|--------|-------------|--------|------------|--------|
| `/transactions` | GET | List transactions | User+Admin | `type=[donation\|rental]`, `status` | PENDING |
| `/transactions/{transactionId}` | GET | Get details | Owner/Admin | - | PENDING |
| `/transactions/{transactionId}/payment` | POST | Process payment | Owner | `PaymentRequest` | PENDING |
| `/transactions/{transactionId}/status` | PATCH | Update status | Owner/Admin | `TransactionStatusDTO` | PENDING |

---

## **Admin Endpoints**
**Base Path:** `/admin`

| Endpoint | Method | Description | Access | Parameters | Status |
|----------|--------|-------------|--------|------------|--------|
| `/admin/users` | GET | List all users | Admin | `status`, `role` | PENDING |
| `/admin/users/{userId}/status` | PATCH | Block/unblock user | Admin | `UserStatusDTO` | PENDING |
| `/admin/items` | GET | List all items | Admin | `status`, `type` | PENDING |
| `/admin/items/{itemId}/status` | PATCH | Approve/reject item | Admin | `ItemApprovalDTO` | PENDING |

---

## **Search & Discovery**
| Endpoint | Method | Description | Access | Parameters | Status |
|----------|--------|-------------|--------|------------|--------|
| `/search/items` | GET | Search items | Public | `q`, `category`, `radius`, `sort` | DONE |
| `/search/users` | GET | Search users | Public | `q`, `location` | PENDING |

---

## **Implementation Notes:**
1. **DONE** = Implemented in provided code.
2. **PENDING** = Endpoint defined but not implemented.
3. **Transactions & Admin features** need backend implementation.
4. **User verification flow** requires email integration.
5. **Search users endpoint** needs geolocation implementation.

## **Contributing**
Feel free to contribute new endpoints or suggest improvements via GitHub issues or pull requests.

## **License**
MIT License. See `LICENSE` file for more details.

