import React, { useState } from "react";
import {BASE_BACKEND_URL} from "../config";

export default function SearchKeyForm() {
  const [searchKey, setSearchKey] = useState("");
  const [result, setResult] = useState("");

  const handleSearch = async () => {
    if (!searchKey) return;

    try {
      const res = await fetch(`${BASE_BACKEND_URL}/api/hash/search?key=${searchKey}`);
      const data = await res.text();
      setResult(data);
    } catch (err) {
      setResult("Error: Unable to fetch data");
    }
  };

  return (
    <div style={{ marginBottom: "20px" }}>
      <input
        type="number"
        placeholder="Search key"
        value={searchKey}
        onChange={(e) => setSearchKey(e.target.value)}
        style={{ padding: "9px", marginRight: "10px" }}
      />
      <button
        onClick={handleSearch}
        style={{
          padding: "5px 10px",
          backgroundColor: "#B0C4B1",
          color: "black",
          border: "none",
          cursor: "pointer",
        }}
      >
        Search the Value
      </button>

      {result && (
        <div style={{ marginTop: "10px", fontWeight: "bold", color: "pink", fontSize: "20px" }}>
          Value : {result}
        </div>
      )}
    </div>
  );
}
