import React from "react";
import "../css/HashVisualizer.css";
import {get_X_LBS_from_number} from '../utils/utilityFunctions.jsx';

export default function HashVisualizer({ globalDepth, directory }) {
  return (
    <div className="hash-visualizer">
      <h3 style={{ marginBottom: "0px"}}>Global Depth: {globalDepth}</h3>
        <i><p style={{ fontSize: "0.8em", opacity: 0.6, margin: 0, marginBottom: "3px" }}>
            Note: bucket size is fixed at 2 and binary representation of index is shown as per the global globalDepth
        </p></i>

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
                <td>
                    {index} ~ ({get_X_LBS_from_number(index, globalDepth)})<sub>2</sub>
                </td>
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
