# Tourist_Records_EndLabExam

> **End Semester Lab Exam Project: Service-Oriented Architecture (SOA)**  
> **Topic:** Tourist Records Management with JWT Token Validation, Hotel Check-In System (Updating Visited Destination & Budget), Preloaded In-Memory Data Store (Zero Database Setup), and Swagger UI Testing.

---

## 📌 Project Overview

This project implements a lightweight, secure RESTful **Hotel Check-In System** designed specifically for lab examinations:
1. **Zero Database Configuration:** Uses an in-memory thread-safe `ConcurrentHashMap` preloaded with initial tourist records (IDs: 1, 2, 3, 4). No MySQL/PostgreSQL/H2 database setup is required.
2. **Request Token Validation:** Stateless authentication using JSON Web Tokens (JWT HMAC-SHA256) via Spring Security.
3. **Hotel Check-In Update API:** Protected endpoint to update a tourist's `visitedDestination` and `budget` along with hotel details. When you execute the update in Swagger UI, the record is immediately updated in memory.
4. **Interactive Swagger UI (OpenAPI 3):** Full OpenAPI documentation with built-in Bearer Token authorization dialog to test endpoints directly in the browser.

---

## 🎯 Preloaded Tourist Records (Initial State)

When the application starts, the following records are automatically loaded into memory:

| Tourist ID | Name | Initial Destination | Initial Budget | Hotel Name | Status | Room Type |
| :---: | :--- | :--- | :---: | :--- | :---: | :--- |
| **1** | Rahul Sharma | **Mumbai** | **20,000.00** | Hotel Sea Breeze | NOT_CHECKED_IN | Standard Room |
| **2** | Emily Watson | **London** | **50,000.00** | Grand Palace Hotel | NOT_CHECKED_IN | Deluxe Suite |
| **3** | Kenji Sato | **Tokyo** | **35,000.00** | City View Inn | NOT_CHECKED_IN | Single Room |
| **4** | Sophia Miller | **New York** | **40,000.00** | Manhattan Lodge | NOT_CHECKED_IN | Executive Suite |

---

## 🏗️ Architecture & Component Flow

```
+-----------------------------------------------------------------------------------+
|                                  Swagger UI                                       |
|                    (http://localhost:8080/swagger-ui/index.html)                   |
+-----------------------------------------------------------------------------------+
                                   |
                +------------------+------------------+
                |                                     |
    [1] POST /api/auth/login              [2] PUT /api/tourists/{id}/checkin
   (Generate JWT Bearer Token)             (Authorization: Bearer <token>)
                |                                     |
                v                                     v
       +-----------------+                  +--------------------+
       | AuthController  |                  | JwtAuthFilter      |
       |  (JwtService)   |                  | (Validate Token)   |
       +-----------------+                  +--------------------+
                                                      |
                                             Token Valid?
                                            /            \
                                          YES             NO
                                          /                \
                                         v                  v
                             +-------------------+    +----------------------+
                             | TouristController |    | 401 Unauthorized     |
                             +-------------------+    | (Missing/Bad Token)  |
                                       |              +----------------------+
                                       v
                             +-------------------+
                             |  TouristService   |
                             +-------------------+
                                       |
                                       v
                             +-------------------+
                             | TouristRepository |
                             | (In-Memory Map)   |
                             +-------------------+
                                       |
                   Destination & Budget Updated Immediately!
```

---

## 📂 Project Structure

```
Tourist_Records_EndLabExam/
│
├── .mvn/wrapper/ & mvnw.cmd               # Maven wrapper (Java 21 ready)
├── .project & .classpath                  # STS / Eclipse project configuration
├── pom.xml                                # Web, Validation, Security, JWT, Swagger UI
├── README.md                              # Complete lab exam documentation
├── run-app.bat                            # Quick launch script
└── src/main/java/com/klu/tourist/
    ├── TouristRecordsEndLabExamApplication.java  # Main application starter
    │
    ├── config/
    │   ├── SecurityConfig.java            # Spring Security: Secures /api/tourists/**
    │   └── OpenApiConfig.java             # Configures Swagger UI Bearer Authorization modal
    │
    ├── controller/
    │   ├── AuthController.java            # POST /api/auth/login (JWT generation)
    │   └── TouristController.java         # PUT /api/tourists/{id}/checkin (Update API)
    │
    ├── dto/
    │   ├── ApiResponse.java               # Standard JSON response format
    │   ├── HotelCheckInRequest.java       # Update payload (visitedDestination, budget, hotelName)
    │   ├── LoginRequest.java              # Credentials request
    │   └── LoginResponse.java             # Token response
    │
    ├── entity/
    │   └── Tourist.java                   # Tourist Model (Pure POJO, no database annotations)
    │
    ├── exception/
    │   ├── ResourceNotFoundException.java # 404 handler for missing tourist ID
    │   └── GlobalExceptionHandler.java    # Handles validation and system errors cleanly
    │
    ├── repository/
    │   └── TouristRepository.java         # In-Memory ConcurrentHashMap with preloaded records
    │
    ├── security/
    │   ├── JwtService.java                # Token generation (HS256) and validation logic
    │   ├── JwtAuthenticationFilter.java   # Request token interceptor & validator
    │   └── JwtAuthenticationEntryPoint.java# Custom 401 Unauthorized JSON response
    │
    └── service/
        ├── TouristService.java            # Service Interface
        └── impl/TouristServiceImpl.java   # Updates visitedDestination & budget in memory
```

---

## 🚀 How to Run the Application

### Option A: In Spring Tool Suite (STS) / Eclipse
1. Open **Spring Tool Suite (STS)**.
2. In the Package Explorer, click **File ➔ Import... ➔ Maven ➔ Existing Maven Projects**.
3. Select `E:\3-1\SOA Workspace\Tourist_Records_EndLabExam` and click **Finish**.
4. Right-click `TouristRecordsEndLabExamApplication.java` ➔ **Run As ➔ Spring Boot App**.
5. The console displays:
   ```
   ==========================================================================
    Tourist_Records_EndLabExam Application Started Successfully!             
    In-Memory Store : Preloaded with 4 tourist records (IDs: 1, 2, 3, 4)     
    Swagger UI URL  : http://localhost:8080/swagger-ui/index.html            
    Alternative URL : http://localhost:8080/swagger-ui.html                  
    Demo User/Pass  : admin / admin123                                       
   ==========================================================================
   ```

---

## 🧪 Step-by-Step Swagger UI Testing Guide

### Step 1: Open Swagger UI
Open your browser and navigate to:
👉 **`http://localhost:8080/swagger-ui/index.html`**

---

### Step 2: Check Preloaded Records (Initial State)
1. In Swagger UI, expand **`2. Tourist Records & Hotel Check-In API`**.
2. Click **`GET /api/tourists/{id}`** ➔ Click **Try it out**.
3. Set `id`: `1`.
4. *Notice before authorizing, calling this endpoint returns `401 Unauthorized` proving token protection!*

---

### Step 3: Generate JWT Token (Public Authentication)
1. Expand **`1. Authentication & Token Generation`** ➔ Click **`POST /api/auth/login`**.
2. Click **Try it out** and enter credentials:
   ```json
   {
     "username": "admin",
     "password": "admin123"
   }
   ```
3. Click **Execute**.
4. **Response (200 OK):**
   ```json
   {
     "message": "Login successful! Copy this token and click 'Authorize' in Swagger UI.",
     "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJIT1RFTF9TVEFGRiIsImlhdCI6MTczMTgzMTM0NSwiZXhwIjoxNzMxODM0OTQ1fQ...",
     "tokenType": "Bearer",
     "username": "admin",
     "expiresInMs": 3600000
   }
   ```
5. Copy the value of the `token` string (without quotes).

---

### Step 4: Authorize in Swagger UI
1. Scroll to the top right of Swagger UI and click the green **Authorize 🔓** button.
2. In the **Value** box, paste the token.
3. Click **Authorize** ➔ **Close**.
4. The lock icon changes to locked 🔒.

---

### Step 5: Execute the Update API (Hotel Check-In System)
1. Go to **`PUT /api/tourists/{id}/checkin`**.
2. Click **Try it out**.
3. Set `id`: `1`.
4. Fill the request body with updated visited destination and budget:
   ```json
   {
     "visitedDestination": "Switzerland",
     "budget": 45000.0,
     "hotelName": "The Chedi Andermatt",
     "roomType": "Deluxe Alpine Suite",
     "checkInDate": "2026-11-17",
     "checkInStatus": "CHECKED_IN"
   }
   ```
5. Click **Execute**.
6. **Response (200 OK):**
   ```json
   {
     "success": true,
     "message": "Tourist visited destination and budget successfully updated in Hotel Check-In system!",
     "data": {
       "id": 1,
       "touristName": "Rahul Sharma",
       "email": "rahul.sharma@example.com",
       "passportNumber": "IND-987654",
       "visitedDestination": "Switzerland",
       "budget": 45000.0,
       "hotelName": "The Chedi Andermatt",
       "checkInStatus": "CHECKED_IN",
       "roomType": "Deluxe Alpine Suite",
       "checkInDate": "2026-11-17"
     },
     "timestamp": "2026-11-17T15:45:00.123456"
   }
   ```
   *Notice `visitedDestination` updated from `Mumbai` to `Switzerland`, and `budget` updated from `20000.0` to `45000.0`!*

---

### Step 6: Verify the Update Immediately
1. Expand **`GET /api/tourists/1`**.
2. Click **Try it out** ➔ **Execute**.
3. You will see that Tourist ID 1 now permanently reflects `visitedDestination: Switzerland` and `budget: 45000.0`!
4. Expand **`GET /api/tourists`** ➔ Click **Execute** to view all updated preloaded records.

---

### Step 7: Negative / Validation Testing
1. **Token Validation Error (401 Unauthorized):**
   - Click the green **Authorize 🔒** button at the top ➔ click **Logout** ➔ click **Close**.
   - Re-execute `PUT /api/tourists/1/checkin`.
   - **Result:** `401 Unauthorized` (Token Validation Failed).
2. **Tourist Not Found (404 Not Found):**
   - Authorize with token again.
   - Execute `PUT /api/tourists/999/checkin`.
   - **Result:** `404 Not Found` (`"Tourist record not found with ID: 999"`).
3. **Invalid Budget (400 Bad Request):**
   - Execute `PUT /api/tourists/1/checkin` with `"budget": -500.0`.
   - **Result:** `400 Bad Request` (`"Budget must be non-negative"`).

---

## 🎓 Lab Viva Questions & Answers

**Q1: How does the application store records without an external database?**  
> *Ans:* The application uses an in-memory repository (`TouristRepository`) backed by a thread-safe `ConcurrentHashMap<Long, Tourist>`. Initial records are preloaded via the `@PostConstruct` lifecycle hook. When an update request arrives, the record is updated in the map and immediately accessible by subsequent queries.

**Q2: How does Token Validation protect the Update API?**  
> *Ans:* `SecurityConfig` permits only `/api/auth/**` and Swagger UI endpoints without authentication. For `/api/tourists/**`, `JwtAuthenticationFilter` intercepts the request and validates the `Authorization: Bearer <token>` header using `JwtService.validateToken(token)`. Missing or invalid tokens trigger `JwtAuthenticationEntryPoint` returning HTTP 401.

**Q3: Why is Swagger UI useful for SOA testing?**  
> *Ans:* It provides OpenAPI 3.0 specification with interactive UI, enabling clients to authenticate with JWT Bearer tokens, test payload validation, execute REST calls (GET, POST, PUT, DELETE), and inspect response status codes directly in the browser.
