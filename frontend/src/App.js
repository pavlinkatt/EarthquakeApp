import React from 'react';
import EarthquakeTable from './components/EarthquakeTable';
import './App.css';

function App() {
  return (
      <div className="App mt-5">
        <h1>Latest Earthquakes</h1>
        <EarthquakeTable />
      </div>
  );
}

export default App;
