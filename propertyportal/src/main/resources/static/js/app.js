
// Backend API URLs
const API_URL = "http://localhost:8080/api/properties/approved";
const FILTER_API_URL = "http://localhost:8080/api/properties/filter";
const PROPERTY_DETAILS_API = "http://localhost:8080/api/properties";

// Load properties when homepage opens
document.addEventListener("DOMContentLoaded", function () {

    loadProperties();

    // Search form
    document.getElementById("propertySearchForm")
        .addEventListener("submit", function (event) {
            event.preventDefault();
            applySearchAndFilters();
        });

    // Apply filters button
    document.getElementById("applyFilters")
        .addEventListener("click", function () {
            applySearchAndFilters();
        });

    // Clear filters
    document.getElementById("clearFilters")
        .addEventListener("click", function () {

            document.getElementById("citySearch").value = "";
            document.getElementById("localitySearch").value = "";
            document.getElementById("listingTypeFilter").value = "";
            document.getElementById("propertyTypeFilter").value = "";
            document.getElementById("minPriceFilter").value = "";
            document.getElementById("maxPriceFilter").value = "";

            loadProperties();
        });
});

// Load all approved properties
async function loadProperties() {

    try {
        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Unable to fetch properties");
        }

        const properties = await response.json();
        displayProperties(properties);

    } catch (error) {
        console.error("Error:", error);
        showMessage("Unable to load properties. Please try again.");
    }
}

// Apply search and filters together
async function applySearchAndFilters() {

    const city = document.getElementById("citySearch").value.trim();
    const locality = document.getElementById("localitySearch").value.trim();

    const listingType = document.getElementById("listingTypeFilter").value;
    const propertyType = document.getElementById("propertyTypeFilter").value;

    const minPrice = document.getElementById("minPriceFilter").value;
    const maxPrice = document.getElementById("maxPriceFilter").value;

    const propertyList = document.getElementById("propertyList");

    try {

        propertyList.innerHTML = `
            <div class="col-12 text-center">
                <p>Searching properties...</p>
            </div>
        `;

        const searchURL = new URL(FILTER_API_URL);

        if (city !== "") {
            searchURL.searchParams.append("city", city);
        }

        if (locality !== "") {
            searchURL.searchParams.append("locality", locality);
        }

        if (listingType !== "") {
            searchURL.searchParams.append("listingType", listingType);
        }

        if (propertyType !== "") {
            searchURL.searchParams.append("propertyType", propertyType);
        }

        if (minPrice !== "") {
            searchURL.searchParams.append("minPrice", minPrice);
        }

        if (maxPrice !== "") {
            searchURL.searchParams.append("maxPrice", maxPrice);
        }

        const response = await fetch(searchURL);

        if (!response.ok) {
            throw new Error("Unable to search properties");
        }

        const properties = await response.json();

        displayProperties(properties);

    } catch (error) {

        console.error("Search Error:", error);
        showMessage("Unable to search properties. Please try again.");
    }
}

// Display property cards
function displayProperties(properties) {

    const propertyList = document.getElementById("propertyList");
    propertyList.innerHTML = "";

    if (properties.length === 0) {

        propertyList.innerHTML = `
            <div class="col-12 text-center">
                <h5>No properties found.</h5>
            </div>
        `;

        return;
    }

    properties.forEach(function (property) {

        const imageUrl = property.imageUrl
            ? property.imageUrl
            : "https://placehold.co/600x400?text=No+Image";

        const propertyCard = `
            <div class="col-lg-4 col-md-6">
                <div class="property-card">

                    <img src="${escapeHTML(imageUrl)}"
                         class="property-image"
                         alt="Property Image"
                         onerror="this.onerror=null;this.src='https://placehold.co/600x400?text=No+Image'">

                    <div class="card-body">

                        <div>
                            <span class="property-badge ${
                                property.listingType === "SALE" ? "sale-badge" : "rent-badge"
                            }">
                                ${escapeHTML(property.listingType)}
                            </span>

                            <span class="property-badge type-badge">
                                ${escapeHTML(property.propertyType)}
                            </span>
                        </div>

                        <h5 class="property-title">
                            ${escapeHTML(property.title)}
                        </h5>

                        <p class="property-location">
                            ${escapeHTML(property.locality)},
                            ${escapeHTML(property.city)}
                        </p>

                        <h5 class="property-price">
                            ₹ ${Number(property.price || 0).toLocaleString("en-IN")}
                        </h5>

                        <div class="property-details">
                            <span>${property.bedrooms ?? "-"} Beds</span>
                            <span>${property.bathrooms ?? "-"} Baths</span>
                            <span>${property.area ?? "-"} Sq.ft</span>
                        </div>

                        <div class="mt-3">
                            <button class="btn btn-primary w-100"
                                onclick="viewProperty(${property.id})">
                                View Details
                            </button>
                        </div>

                    </div>
                </div>
            </div>
        `;

        propertyList.innerHTML += propertyCard;
    });
}

// Display error or information message
function showMessage(message) {

    const propertyList = document.getElementById("propertyList");

    propertyList.innerHTML = `
        <div class="col-12 text-center">
            <p class="text-danger">${escapeHTML(message)}</p>
        </div>
    `;
}

// Prevent HTML injection
function escapeHTML(value) {

    const element = document.createElement("div");

    element.textContent = value ?? "";

    return element.innerHTML;
}

// View complete property details
async function viewProperty(id) {

    try {

        const response = await fetch(`${PROPERTY_DETAILS_API}/${id}`);

        if (!response.ok) {
            throw new Error("Unable to fetch property details");
        }

        const property = await response.json();

        const imageUrl = property.imageUrl
            ? property.imageUrl
            : "https://placehold.co/800x500?text=No+Image";

        const detailsContent = document.getElementById(
            "propertyDetailsContent"
        );

        // Seller contact button
        const contactSeller = property.ownerPhone
            ? `
                <a href="tel:${escapeHTML(property.ownerPhone)}"
                   class="btn btn-success">
                    <i class="bi bi-telephone-fill"></i>
                    Call Seller
                </a>
              `
            : `
                <span class="text-muted">
                    Seller contact number unavailable.
                </span>
              `;

        detailsContent.innerHTML = `

            <img src="${escapeHTML(imageUrl)}"
                 class="img-fluid rounded mb-4 w-100"
                 style="max-height: 350px; object-fit: cover;"
                 alt="Property Image"
                 onerror="this.onerror=null;this.src='https://placehold.co/800x500?text=No+Image'">

            <h3>${escapeHTML(property.title)}</h3>

            <h4 class="text-primary mt-2">
                ₹ ${Number(property.price || 0).toLocaleString("en-IN")}
            </h4>

            <p class="text-muted">
                ${escapeHTML(property.locality)},
                ${escapeHTML(property.city)}
            </p>

            <hr>

            <h5>Property Description</h5>
            <p>${escapeHTML(property.description || "No description available.")}</p>

            <div class="row mt-3">

                <div class="col-md-4 mb-3">
                    <strong>Property Type</strong>
                    <p>${escapeHTML(property.propertyType)}</p>
                </div>

                <div class="col-md-4 mb-3">
                    <strong>Listing Type</strong>
                    <p>${escapeHTML(property.listingType)}</p>
                </div>

                <div class="col-md-4 mb-3">
                    <strong>Area</strong>
                    <p>${property.area ?? "-"} Sq.ft</p>
                </div>

                <div class="col-md-4 mb-3">
                    <strong>Bedrooms</strong>
                    <p>${property.bedrooms ?? "-"}</p>
                </div>

                <div class="col-md-4 mb-3">
                    <strong>Bathrooms</strong>
                    <p>${property.bathrooms ?? "-"}</p>
                </div>

                <div class="col-md-4 mb-3">
                    <strong>Seller Name</strong>
                    <p>${escapeHTML(property.ownerName || "N/A")}</p>
                </div>

            </div>

            <hr>

            <div class="mt-3">
                <h5>Interested in this property?</h5>
                <p class="text-muted">
                    Contact the seller directly for more information.
                </p>

                ${contactSeller}
            </div>
        `;

        const modalElement = document.getElementById(
            "propertyDetailsModal"
        );

        const modal = bootstrap.Modal.getOrCreateInstance(modalElement);

        modal.show();

    } catch (error) {

        console.error("Property Details Error:", error);

        alert("Unable to load property details. Please try again.");
    }
}
