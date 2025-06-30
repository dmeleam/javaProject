import React, { useState } from 'react';

function CsvUpload() {
  const [summary, setSummary] = useState(null);
  const [file, setFile] = useState(null);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleUpload = async () => {
    if (!file) return;

    const formData = new FormData();
    formData.append('file', file);

    const response = await fetch('http://localhost:8080/api/csv/upload', {
      method: 'POST',
      body: formData,
    });

    const data = await response.json();
    setSummary(data);
  };

  return (
    <div className="container mt-5">
      <h2>Upload CSV File</h2>
      <input type="file" className="form-control" accept=".csv" onChange={handleFileChange} />
      <button className="btn btn-primary mt-3" onClick={handleUpload}>
        Upload
      </button>

      {summary && (
        <div className="mt-4">
          <h4>Summary</h4>
          <p><strong>Row Count:</strong> {summary.rowCount}</p>
          <p><strong>Headers:</strong> {Array.from(summary.columnHeaders).join(', ')}</p>
        </div>
      )}
    </div>
  );
}

export default CsvUpload;
