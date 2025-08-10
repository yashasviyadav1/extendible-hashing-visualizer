import React from "react";
import "../css/HashVisualizer.css";

export default function HashVisualizer({ globalDepth, directory }) {
  return (
    <div className="hash-visualizer">
      <h2>Global Depth: {globalDepth}</h2>

      {/* Scrollable Directory Table */}
      <div className="directory-table-container">
        <table className="directory-table">
          <thead>
            <tr>
              <th>Dir Index</th>
              <th>Points To</th>
            </tr>
          </thead>
          <tbody>
            {directory.map((bucket, index) => (
              <tr key={index}>
                <td>{index}</td>
                <td>
                  <div className="bucket">
                    <div className="bucket-header">
                      Local Depth: {bucket.localDepth}
                    </div>
                    <div className="bucket-entries">
                      {bucket.entries.map((entry) => (
                        <div key={entry.key} className="entry">
                          <strong>{entry.key}</strong> → {entry.value}
                        </div>
                      ))}
                    </div>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
