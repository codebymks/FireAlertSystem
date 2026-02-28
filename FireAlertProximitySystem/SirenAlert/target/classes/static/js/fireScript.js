console.log("fire script loaded!");

//create fire
async function createFire() {
    const fire = {
        fireLatitude: document.getElementById("newLatitude").value,
        fireLongitude: document.getElementById("newLongitude").value,
        timestamp: new Date().toISOString(),
        isFireActive: true,
    };

    try {
        const res = await fetch(`${API_BASE_URL}/fires`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(fire),
        });
        if (res.ok) {
            alert("fire reported");
            loadFires();
        } else {
            const error = await res.text();
            console.error(error);
            alert("error occurred trying to create fire.");
        }
    } catch (error) {
        console.error(error);
        alert("error occurred trying to create fire.");
    }
}

// read fires
async function loadFires() {
    try {
        const rest = await fetch(`${API_BASE_URL}/fires`,);

        if (!rest.ok) {
            throw new Error("HTTP " + rest.status);
        }
        const data = await rest.json();
        console.log("fire ", data);

        const tbody = document.querySelector("#fireTable tbody");
        tbody.innerHTML = "";

        data.forEach(element => {
            const row = document.createElement("tr");
            row.innerHTML = `
<td>${element.fireId}</td>
<td>${element.fireLatitude}</td>
<td>${element.fireLongitude}</td>
<td>${element.timestamp}</td>
<td>
<button onclick="showUpdateFireForm(${element.fireId}, ${element.fireLatitude}, ${element.fireLongitude})">Update</button>
<button onclick="deleteFire(${element.fireId})">Delete</button>
</td>
`;
            console.log("Deleting fire ID:", element.fireId);

            tbody.appendChild(row);
        });
    } catch (error) {
        console.log("load error " + error);
        alert("error occurred trying to load fire.");
    }
}

//Show the form for updating fire
function showUpdateFireForm(id, lat, lon) {
    document.getElementById('updateFireFormContainer').style.display = 'block'
    document.getElementById('updateFireId').value = id;
    document.getElementById('updateFireLatitude').value = lat;
    document.getElementById('updateFireLongitude').value = lon;
}

//update fire
async function updateFire(event) {
    event.preventDefault();

    const id = document.getElementById('updateFireId').value;
    const updatedFire = {
        fireLatitude: document.getElementById("updateFireLatitude").value,
        fireLongitude: document.getElementById("updateFireLongitude").value,
    };

    console.log("fire updated: ", updatedFire);

    const res = await fetch(`${API_BASE_URL}/fires/${id}`, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
            body: JSON.stringify(updatedFire),

    });

    if (res.ok) {
        console.log("fire updated successfully");
        document.getElementById("updateFireForm").reset();
        document.getElementById('updateFireFormContainer').style.display = 'none';
        loadFires();
    } else {
        const error = await res.text();
        console.error(error);
        alert("error occurred trying to update fire.");
    }
}

async function deleteFire(id) {
    console.log("deleting fire with ID: ", id);
    if (!confirm("Are you sure you want to delete this Fire?")) return;

    try {
        const res = await fetch(`${API_BASE_URL}/fires/${id}`, {method: "DELETE"});
        if (res.ok) {
            alert("fire deleted successfully");
            loadFires();
        } else {
            const error = await res.text();
            console.error(error);
            alert("error occurred trying to delete fire.");
        }
    } catch (error) {
        console.error(error);
        alert("error occurred trying to delete fire.");
    }
}

// Wait for page to load
document.addEventListener("DOMContentLoaded", () => {
    loadFires();
    // attach the fire creation function to the button
    document.getElementById("createFireBtn").addEventListener("click", createFire);

    document.getElementById("updateFireForm").addEventListener("submit", updateFire);
});
