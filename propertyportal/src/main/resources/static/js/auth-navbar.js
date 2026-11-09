
document.addEventListener("DOMContentLoaded", function () {

    const loginNav = document.getElementById("loginNav");
    const registerNav = document.getElementById("registerNav");
    const userNav = document.getElementById("userNav");

    const navbarUserName = document.getElementById("navbarUserName");
    const navbarUserEmail = document.getElementById("navbarUserEmail");
    const logoutButton = document.getElementById("logoutButton");

    // Get logged-in user details
    const loggedInUser = sessionStorage.getItem("loggedInUser");

    if (loggedInUser) {

        try {

            const user = JSON.parse(loggedInUser);

            // Hide Login and Register
            loginNav.style.display = "none";
            registerNav.style.display = "none";

            // Show User dropdown
            userNav.style.display = "block";

            // Display user information
            navbarUserName.textContent = user.fullName || user.name || "User";
            navbarUserEmail.textContent = user.email || "";

        } catch (error) {

            console.error("Error reading user details:", error);

            sessionStorage.removeItem("loggedInUser");

            loginNav.style.display = "block";
            registerNav.style.display = "block";
            userNav.style.display = "none";
        }

    } else {

        // User is not logged in
        loginNav.style.display = "block";
        registerNav.style.display = "block";
        userNav.style.display = "none";

    }

    // Logout functionality
    logoutButton.addEventListener("click", function () {

        sessionStorage.removeItem("loggedInUser");

        alert("You have been logged out successfully!");

        window.location.href = "login.html";

    });

});
