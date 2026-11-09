
const LOGIN_API_URL = "http://localhost:8080/api/auth/login";

document.getElementById("loginForm").addEventListener("submit", async function(event) {

    event.preventDefault();

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;

    const message = document.getElementById("message");
    const loginButton = document.getElementById("loginButton");

    try {

        loginButton.disabled = true;
        loginButton.textContent = "Logging in...";

        message.innerHTML = "";

        const response = await fetch(LOGIN_API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || data.error || "Invalid email or password.");
        }

        // Save logged-in user details
        sessionStorage.setItem("loggedInUser", JSON.stringify(data));

        message.innerHTML = `
            <p class="text-success text-center">
                Login successful! Redirecting...
            </p>
        `;

        // Redirect according to user role
        if (data.role === "ADMIN") {

            window.location.href = "admin-dashboard.html";

        } else if (data.role === "SELLER") {

            window.location.href = "seller-dashboard.html";

        } else if (data.role === "BUYER") {

            window.location.href = "index.html";

        } else {

            throw new Error("Invalid user role.");
        }

    } catch (error) {

        message.innerHTML = `
            <p class="text-danger text-center">
                ${error.message}
            </p>
        `;

    } finally {

        loginButton.disabled = false;
        loginButton.textContent = "Login";
    }

});
