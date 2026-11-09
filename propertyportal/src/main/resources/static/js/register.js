
document.getElementById("registerForm").addEventListener("submit", async function (event) {

    event.preventDefault();

    const userData = {
        fullName: document.getElementById("fullName").value.trim(),
        email: document.getElementById("email").value.trim(),
        password: document.getElementById("password").value,
        phoneNumber: document.getElementById("phoneNumber").value.trim(),
        role: document.getElementById("role").value
    };

    const message = document.getElementById("message");

    try {
        const response = await fetch("/api/users/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(userData)
        });

        const result = await response.json();

        if (response.ok) {
            message.style.color = "green";
            message.textContent = "Registration successful! Redirecting to login...";

            document.getElementById("registerForm").reset();

            setTimeout(() => {
                window.location.href = "login.html";
            }, 1500);
        } else {
            message.style.color = "red";
            message.textContent =
                typeof result === "string"
                    ? result
                    : Object.values(result).join(", ");
        }

    } catch (error) {
        message.style.color = "red";
        message.textContent = "Something went wrong. Please try again.";
    }
});
