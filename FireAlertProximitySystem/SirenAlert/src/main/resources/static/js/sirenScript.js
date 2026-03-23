console.log("Siren script loaded!");

//Create siren
async function createSirens() {

    const siren = {
        sirenLatitude: document.getElementById("newLat").value,
        sirenLongitude: document.getElementById("newLon").value,
        status: "PEACE",
        outOfServiceSince: null
    };

    const res = await fetch(`${API_BASE_URL}/sirens`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(siren),
    });

    if (res.ok) {
        alert("Siren created!");
        loadSirens();
    } else {
        const err = await res.text();
        console.error(err);
        alert("Error creating siren");
    }
}

//read sirens
async function loadSirens() {
    try {
        const res = await fetch(`${API_BASE_URL}/sirens`);

        if (!res.ok) {
            throw new Error("HTTP " + res.status);
        }

        const data = await res.json();

        console.log("Sirens:", data);

        const tbody = document.querySelector("#sirenTable tbody");
        tbody.innerHTML = "";

        data.forEach(s => {

            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${s.sirenId}</td>
                <td>${s.sirenLatitude}</td>
                <td>${s.sirenLongitude}</td>
                <td>${s.status}</td>
                <td>${s.outOfServiceSince}</td>
                <td>
                <button onclick="showUpdateForm(${s.sirenId}, ${s.sirenLatitude}, ${s.sirenLongitude}, '${s.status}', '${s.outOfServiceSince || ''}')">Update</button>
                <button onclick="deleteSiren(${s.sirenId})">Delete</button>
                </td>

            `;

            tbody.appendChild(row);
        });

    } catch (err) {
        console.error("Load error:", err);
        alert("Cannot load sirens");
    }
}

function showUpdateForm(id, lat, lon, status, outOfService) {
    document.getElementById('updateSirenFormContainer').style.display = 'block';
    document.getElementById('updateSirenId').value = id;
    document.getElementById('updateLatitude').value = lat;
    document.getElementById('updateLongitude').value = lon;


    document.getElementById('updateOutOfServiceCheckbox').checked = !!outOfService;
    document.getElementById('updateSirenForm').dataset.originalOutOfService = outOfService || '';
}

//Update siren
async function updateSiren(event) {
    event.preventDefault();

    const id = document.getElementById('updateSirenId').value;

    const isOutOfService = document.getElementById('updateOutOfServiceCheckbox').checked;
    const originalTimestamp = document.getElementById('updateSirenForm').dataset.originalOutOfService;

    //Determine the correct timestamp
    let outOfServiceSince;
    if (!isOutOfService) {
        outOfServiceSince = null;
    } else {
        //Set new timestamp only if original was empty
        outOfServiceSince = originalTimestamp || new Date().toISOString();
    }

    const updatedSiren = {
        sirenLatitude: parseFloat(document.getElementById('updateLatitude').value),
        sirenLongitude: parseFloat(document.getElementById('updateLongitude').value),
        outOfServiceSince: outOfServiceSince,
    };

    console.log("Updating siren:", updatedSiren);

    const res = await fetch(`${API_BASE_URL}/sirens/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updatedSiren)
    });

    if (res.ok) {
        document.getElementById('updateSirenForm').reset();
        document.getElementById('updateSirenFormContainer').style.display = 'none';
        loadSirens();
    } else {
        const err = await res.text();
        console.error(err);
        alert('Siren update error');
    }
}

//Delete siren
async function deleteSiren(id) {
    if (!confirm('Are you sure you want to delete this siren?')) return;

    const res = await fetch(`${API_BASE_URL}/sirens/${id}`, {method: 'DELETE'});

    if (res.ok) {
        alert('Siren deleted');
        loadSirens();
    } else {
        const err = await res.text();
        console.error(err);
        alert('Could not delete the siren');
    }
}

document.addEventListener("DOMContentLoaded", () => {
    loadSirens();
    document
        .getElementById("createSirenBtn")
        .addEventListener("click", createSirens);
    document
        .getElementById("updateSirenForm")
        .addEventListener("submit", updateSiren);
});
