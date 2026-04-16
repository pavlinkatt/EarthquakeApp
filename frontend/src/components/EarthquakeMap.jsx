import React from 'react';
import { MapContainer, TileLayer, CircleMarker, Popup } from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';

delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
    iconUrl: require('leaflet/dist/images/marker-icon.png'),
    shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
});

function EarthquakeMap({ earthquakes }) {

    const getColor = (mag) => {
        if (mag >= 5.0) return '#dc3545';
        if (mag >= 3.0) return '#fd7e14';
        return '#28a745';
    };

    const validEarthquakes = earthquakes.filter(
        eq => eq.latitude != null && eq.longitude != null
    );

    return (
        <div className="mt-4 mb-5">
            <h4 className="text-center mt-5 mb-3"> 📍 Earthquake Map</h4>
            <MapContainer
                center={[45, 0]}
                zoom={2}
                minZoom={2}
                maxBounds={[[-90, -200], [90, 200]]}
                maxBoundsViscosity={1.0}
                style={{
                    height: '600px',
                    width: '70%',
                    margin: '0 auto',
                    borderRadius: '12px',
                    border: '1px solid #dee2e6',
                    zIndex: 0
                }}
            >
                <TileLayer
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    attribution='&copy; OpenStreetMap contributors'
                    noWrap={true}
                />
                {validEarthquakes.map(eq => (
                    <CircleMarker
                        key={eq.id}
                        center={[eq.latitude, eq.longitude]}
                        radius={Math.max(eq.magnitude * 4, 6)}
                        pathOptions={{
                            color: getColor(eq.magnitude),
                            fillColor: getColor(eq.magnitude),
                            fillOpacity: 0.7,
                            weight: 1.5
                        }}
                    >
                        <Popup>
                            <div style={{ minWidth: '180px' }}>
                                <strong>{eq.title}</strong>
                                <hr style={{ margin: '6px 0' }}/>
                                <div>📍 {eq.place}</div>
                                <div>📊 Magnitude: <strong>{eq.magnitude}</strong></div>
                                <div>🔽 Depth: {eq.depth} km</div>
                                <div>🕐 {new Date(eq.timeOfEvent).toLocaleString()}</div>
                            </div>
                        </Popup>
                    </CircleMarker>
                ))}
            </MapContainer>

            <div className="d-flex justify-content-center gap-4 mt-3">
                <span>
                    <span style={{
                        display: 'inline-block',
                        width: '12px',
                        height: '12px',
                        borderRadius: '50%',
                        backgroundColor: '#28a745',
                        marginRight: '5px'
                    }}/>
                    Below 3.0
                </span>
                <span>
                    <span style={{
                        display: 'inline-block',
                        width: '12px',
                        height: '12px',
                        borderRadius: '50%',
                        backgroundColor: '#fd7e14',
                        marginRight: '5px'
                    }}/>
                    3.0 - 5.0
                </span>
                <span>
                    <span style={{
                        display: 'inline-block',
                        width: '12px',
                        height: '12px',
                        borderRadius: '50%',
                        backgroundColor: '#dc3545',
                        marginRight: '5px'
                    }}/>
                    Above 5.0
                </span>
            </div>
        </div>
    );
}

export default EarthquakeMap;