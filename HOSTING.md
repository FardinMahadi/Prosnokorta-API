# Hosting & Setup Guide (Online Quiz Management System)

This guide explains how to set up the backend locally using environment variables and how to host it for free on the cloud.

---

## 💻 Local Setup (Environment Variables)

We now use a `.env` file to manage sensitive information like database credentials. This keeps your secrets safe from being committed to GitHub.

### Steps:
1.  **Duplicate the Template**: Copy `.env.example` and rename it to `.env`.
2.  **Configure Credentials**: Open `.env` and update it with your local MySQL details:
    ```env
    DB_URL=jdbc:mysql://localhost:3306/quiz_db?createDatabaseIfNotExist=true
    DB_USERNAME=root
    DB_PASSWORD=your_password_here
    ```
3.  **Run**: The application will automatically load these variables using the `dotenv-java` dependency.

---

## 🚀 Deployment & Hosting (Free Platforms)

### 1. Database: Aiven (Free MySQL)
[Aiven](https://aiven.io/) provides a reliable free tier for MySQL.

1.  **Sign Up**: Create an account at [aiven.io](https://aiven.io/).
2.  **Create Service**:
    *   Select **MySQL**.
    *   Choose the **Free Plan**.
3.  **Connection**:
    *   Once running, copy the **Host**, **Port**, **User**, and **Password**.
    *   Your `DB_URL` should look like: `jdbc:mysql://<HOST>:<PORT>/defaultdb?sslmode=require`

### 2. Server: Render (Web Service)
[Render](https://render.com/) requires **Docker** to run Java/Spring Boot.

1.  **Connect Repo**: Connect your GitHub account to Render.
2.  **Create Web Service**:
    *   **Language**: Select **Docker** (since Java is not natively listed).
    *   **Dashboard View**: Render will automatically use the `Dockerfile` in your repository.
3.  **Environment Variables**:
    *   In the **Environment** tab on Render, add these keys:
        *   `DB_URL`: (Your Aiven JDBC URL)
        *   `DB_USERNAME`: (Aiven User)
        *   `DB_PASSWORD`: (Aiven Password)

---

## 🔄 CI/CD Pipeline (GitHub Actions)

We have set up a GitHub Actions workflow to automate your workflow.

### How it works:
1.  **Continuous Integration**: Every time you create a Pull Request or Push to `main`, GitHub will automatically build your project to ensure there are no compilation errors.
2.  **Continuous Deployment**: Once code is pushed/merged into `main`, GitHub will trigger a deployment to Render.

### Final Step Required:
To enable automatic deployment, you must add your **Deploy Hook** to GitHub:
1.  Go to your **Render Dashboard** -> Your Web Service -> **Settings**.
2.  Scroll down to **Deploy Hook** and copy the URL.
3.  Go to your **GitHub Repository** -> **Settings** -> **Secrets and variables** -> **Actions**.
4.  Click **New repository secret**.
5.  Name: `RENDER_DEPLOY_HOOK_URL`
6.  Value: (Paste the URL you copied from Render)

---

## ⚠️ Important Notes
*   **CORS**: Remember to update your backend or use an environment variable to allow your hosted frontend URL.
*   **Spin Down**: Render's free tier sleeps after 15 mins of inactivity. The first request after a break will be slow as it "wakes up".
