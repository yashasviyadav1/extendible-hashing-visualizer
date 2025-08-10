import React, { useState } from "react";

export default function SearchKeyForm() {
  const [searchKey, setSearchKey] = useState("");
  const [result, setResult] = useState("");

  const handleSearch = async () => {
    if (!searchKey) return;

    try {
      const res = await fetch(`http://localhost:8080/api/hash/search?key=${searchKey}`);
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
