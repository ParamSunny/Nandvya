# 🚀 Nandvya – Waste to Resource Ecosystem Platform

## 📌 Project Overview
Nandvya is a full-stack ecosystem platform designed to connect multiple stakeholders like Suppliers, Farmers, Biogas Plants, and Admins to streamline the waste-to-resource conversion process.

The platform enables users to list, manage, and utilize organic waste efficiently, reducing manual coordination and improving accessibility of reusable resources.

---

## 🎯 Key Features

### 🔐 Authentication & Authorization
- JWT-based secure login & registration
- Role-based access control:
  - Supplier
  - Biogas
  - Farmer
  - Admin

---

### ♻️ Waste Management System
- Add waste with image upload 📸
- View only your own waste (multi-user isolation)
- Update and delete waste securely
- Dynamic image storage and rendering

---

### 👥 Multi-User System (Real-World Logic)
- Each user has separate data space
- Users cannot access or modify others' data
- Secure CRUD operations per user

---

### 🖼️ Image Upload System
- Upload images via backend (Spring Boot)
- Store images in server directory
- Save image path in database
- Display dynamically in frontend

---

### 🧠 Smart Backend Logic
- User-based filtering using JWT
- Ownership validation for update/delete
- Secure API endpoints using Spring Security

---

## 🛠️ Tech Stack

### 💻 Frontend
- React + Vite
- React Router
- Axios
- CSS

### ⚙️ Backend
- Spring Boot
- Spring Security
- JWT Authentication
- REST APIs

### 🗄️ Database
- MySQL

### 📦 Tools & Others
- Postman (API Testing)
- Git & GitHub
- Maven

---

## 📂 Project Structure

backend/
 ├── controller/
 ├── service/
 ├── repository/
 ├── entity/
 ├── config/
 ├── security/

frontend/
 ├── components/
 ├── pages/
 ├── api/
 ├── routes/

---

## 🔄 Workflow

1. User registers & logs in
2. JWT token generated
3. User performs operations (Add/View/Update/Delete waste)
4. Backend validates user via token
5. Data stored & filtered per user

---

## 🔐 Security Features

- JWT-based authentication
- Role-based authorization
- User ownership validation
- Protected API endpoints

---

## 📸 Screenshots (Add Yours)

- Login Page
- Dashboard
- Add Waste Form
- Waste Listing

---

## 🚀 How to Run

### Backend
cd backend  
mvn spring-boot:run  

### Frontend
cd frontend  
npm install  
npm run dev  

---

## 🌟 Future Enhancements

- Marketplace view for Biogas users
- Admin dashboard with analytics
- Pagination & search filters
- Cloud image storage (AWS S3)
- Real-time notifications

---

## 💡 Use Case

This platform solves real-world problems like:
- Waste management inefficiency
- Lack of resource accessibility
- Poor coordination between stakeholders

---

## 👨‍💻 Author

Paramveer Singh  
B.Tech CSE (AI & ML)  
Full Stack Developer | Java | Spring Boot | React  

---

## ⭐ Support

If you like this project:
- Star this repo
- Fork it
- Contribute
