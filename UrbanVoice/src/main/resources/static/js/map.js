// Lastly added
function openMap() {
	document.getElementById('mapModal').style.display = 'block';
	initializeLeafletMap();
}

function closeMap() {
	document.getElementById('mapModal').style.display = 'none';
}

function initializeLeafletMap() {
	const app = document.getElementById('app');
	const setLocationButton = document.getElementById('setLocationButton');

	// Get current user location
	if ('geolocation' in navigator) {
		navigator.geolocation.getCurrentPosition(
			async (position) => {
				const userLocation = {
					latitude: position.coords.latitude,
					longitude: position.coords.longitude,
				};
				console.log('User Location:', userLocation);

				const map = L.map(app).setView([userLocation.latitude, userLocation.longitude], 13);

				L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
					attribution: '&copy; OpenStreetMap contributors'
				}).addTo(map);

				let markerposition = L.marker([userLocation.latitude, userLocation.longitude], {
					draggable: true,
					icon: L.icon({
						iconUrl: "https://cdn-icons-png.flaticon.com/128/3082/3082383.png",
						iconSize: [48, 48],
						iconAnchor: [24, 48], // Adjusted anchor point
					}),
				}).addTo(map);

				markerposition.on('dragend', function(event) {
					const marker = event.target;
					const position = marker.getLatLng();
					console.log('New marker position: ' + position.lat + ', ' + position.lng);
					console.log(`https://www.google.com/maps?q=${position.lat},${position.lng}`);
				});

				setLocationButton.addEventListener('click', function(event) {
					// Prevent the default form submission behavior
					event.preventDefault();

					const position = markerposition.getLatLng();
					console.log('Set location: ' + position.lat + ', ' + position.lng);

					// Send location to the server
					fetch('/setLocation', {
						method: 'POST',
						headers: {
							'Content-Type': 'application/json',
						},
						body: JSON.stringify({ latitude: position.lat, longitude: position.lng }),
					})
						.then(response => response.text())
						.then(data => console.log(data))
						.catch(error => console.error('Error setting location:', error));
				});


				map.on('click', function(event) {
					const { lat, lng } = event.latlng;
					map.removeLayer(markerposition);
					markerposition = L.marker([lat, lng], {
						draggable: true,
						icon: L.icon({
							iconUrl: "https://cdn-icons-png.flaticon.com/128/3082/3082383.png",
							iconSize: [48, 48],
							iconAnchor: [24, 48], // Adjusted anchor point
						}),
					}).addTo(map);
					markerposition.openPopup();
					console.log(`https://www.google.com/maps?q=${lat},${lng}`);
				});
			},
			(error) => {
				console.error('Error getting user location:', error);
			},
			{ enableHighAccuracy: true, timeout: 5000, maximumAge: 0 }
		);
	} else {
		console.error('Geolocation is not supported by this browser.');
	}
}

