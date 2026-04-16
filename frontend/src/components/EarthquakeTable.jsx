import React, { useState, useEffect } from 'react';
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/earthquakes';

function EarthquakeTable() {
    const [earthquakes, setEarthquakes] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchEarthquakes = async () => {
        try {
            setLoading(true);
            setError(null);
            const response = await axios.get(API_URL);
            setEarthquakes(response.data);
        } catch (err) {
            setError('Failed to load earthquakes.');
        } finally {
            setLoading(false);
        }
    };

    const fetchAndStore = async () => {
        try {
            setLoading(true);
            setError(null);
            await axios.post(`${API_URL}/fetch`);
            await fetchEarthquakes();
        } catch (err) {
            setError('Failed to fetch from USGS API.');
        } finally {
            setLoading(false);
        }
    };

    const deleteEarthquake = async (id) => {
        try {
            await axios.delete(`${API_URL}/${id}`);
            setEarthquakes(earthquakes.filter(e => e.id !== id));
        } catch (err) {
            setError('Failed to delete earthquake.');
        }
    };

    useEffect(() => {
        fetchEarthquakes();
    }, []);

    const getMagnitudeColor = (mag) => {
        if (mag >= 5.0) return 'danger';
        if (mag >= 3.0) return 'warning';
        return 'success';
    };

    return (
        <div>
            <div className="d-flex flex-column align-items-center mb-5 mt-5">
                <button
                    className="btn btn-secondary mb-2"
                    onClick={fetchAndStore}
                    disabled={loading}
                    >
                        {loading ? (
                            <>
                                <span className="spinner-border spinner-border-sm me-2"/>
                                Fetching...
                            </>
                        ) : (
                            'Fetch Latest Earthquakes'
                        )}
                </button>
                <span className="text-muted mt-4">
                    {earthquakes.length} earthquakes found
                </span>
            </div>

            {error && (
                <div className="alert alert-danger">{error}</div>
            )}

            {earthquakes.length === 0 && !loading ? (
                <div className="alert alert-info">
                    No earthquakes found. Click fetch to load data.
                </div>
            ) : (
                <div className="table-responsive mx-auto" style={{maxWidth: '80%'}}>
                    <table className="table table-sm table-striped table-hover shadow-sm">
                        <thead className="table-dark">
                        <tr>
                            <th>Magnitude</th>
                            <th>Mag Type</th>
                            <th>Place</th>
                            <th>Title</th>
                            <th>Time</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {earthquakes.map(eq => (
                            <tr key={eq.id}>
                                <td>
                    <span className={`badge bg-${getMagnitudeColor(eq.magnitude)}`}>
                      {eq.magnitude}
                    </span>
                                </td>
                                <td>{eq.magType}</td>
                                <td>{eq.place}</td>
                                <td>{eq.title}</td>
                                <td>{new Date(eq.timeOfEvent).toLocaleString()}</td>
                                <td>
                                    <button
                                        className="btn btn-danger btn-sm"
                                        onClick={() => deleteEarthquake(eq.id)}
                                    >
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
}

export default EarthquakeTable;