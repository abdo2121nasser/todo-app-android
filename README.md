# 📝 Todo App – Android (Kotlin)

An Android application that allows users to manage their tasks efficiently. Built using native Android with Kotlin, the app supports full offline capabilities using Room and provides real-time UI updates with LiveData.

---

## 🔐 Authentication

- Includes **Sign In** and **Sign Up** screens.
- Both screens have **input validation** for required fields and proper formatting.
- Uses a **refresh token system** to keep the user authenticated and automatically request new access tokens.

---

## 🏠 Home Screen

- Displays a list of Todo items stored in the local **Room database**.
- A filter bar allows users to view tasks based on status:
  - `All`
  - `In Progress`
  - `Waiting`
  - `Finished`
- Clicking on a task navigates to a detailed view of that Todo item.

---

## ➕ Add Todo

- A **Floating Action Button (FAB)** lets users add a new task.
- Users must complete all required fields, including uploading an image.
- Form has full validation before submission.
- On submission:
  - Data is added to **Room database**
  - Then sent to the backend via **Retrofit API**

---

## ✏️ Edit & 🗑️ Delete Todo

- Each task has a **"More" icon** with options to:
  - **Edit** the item – prefilled with existing values.
  - **Delete** the item – removes it from Room and backend.

---

## 👤 Profile Screen

- Accessible from the **toolbar profile icon**.
- Displays user profile information.
- Includes a **Logout** button that signs out the user and returns to the login screen.

---

## 🛠️ Tech Stack

- **Language:** Kotlin
- **UI Design:** XML
- **Networking:** Retrofit
- **Asynchronous Operations:** Kotlin Coroutines
- **State Management:** ViewModel + LiveData
- **Local Storage:** Room Database
- **Authentication:** Refresh & Access token flow

---

## 🧠 Data Behavior

- All tasks are **stored locally in Room**, and the app performs all operations (add/edit/delete) on Room first.
- The app then synchronizes the changes with the backend API.
- Thanks to **LiveData**, the UI reflects changes in the database in real-time.

---
## 📽️ Demo Video

 https://github.com/user-attachments/assets/1941bc9b-6387-421f-999c-67099f84620f


---


## 📦 Notes

- App works primarily on local database (Room) to ensure smooth offline performance.
- Refresh token mechanism ensures valid access tokens are always used without requiring re-login.

