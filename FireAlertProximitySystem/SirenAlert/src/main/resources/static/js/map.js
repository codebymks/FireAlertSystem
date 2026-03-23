console.log("Map script loaded!");

//Create the map centered on Los Angeles
const map = L.map("map").setView([34.03, -118.15], 10);

//Add OpenStreetMap tiles
L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
    maxZoom: 19,
    attribution:
        '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
}).addTo(map);

//DivIcons for emojis
const sirenIcon = L.divIcon({
    html: "🚨",
    className: "emoji-marker",
    iconSize: [30, 30],
    iconAnchor: [15, 30]
});

const fireIcon = L.divIcon({
    html: "🔥",
    className: "emoji-marker",
    iconSize: [30, 30],
    iconAnchor: [15, 30]
});

//Add sirens to the map
async function addSirensToMap() {
    try {
        const res = await fetch(`${API_BASE_URL}/sirens`);
        const sirens = await res.json();

        sirens.forEach((s) => {
            L.marker([s.sirenLatitude, s.sirenLongitude], { icon: sirenIcon })
                .bindPopup(
                    `<b>Siren ${s.sirenId}</b><br>Status: ${s.status}<br>Out of Service: ${s.outOfServiceSince || "No"}`
                )
                .addTo(map);
        });
    } catch (err) {
        console.error("Error loading sirens for map:", err);
    }
}

//Add fires to the map
async function addFiresToMap() {
    try {
        const res = await fetch(`${API_BASE_URL}/fires`);
        const fires = await res.json();

        fires.forEach((f) => {
            L.marker([f.fireLatitude, f.fireLongitude], { icon: fireIcon })
                .bindPopup(
                    `<b>Fire ${f.fireId}</b><br>Time: ${new Date(f.timestamp).toLocaleString()}`
                )
                .addTo(map);
        });
    } catch (err) {
        console.error("Error loading fires for map:", err);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    addSirensToMap();
    addFiresToMap();
});
