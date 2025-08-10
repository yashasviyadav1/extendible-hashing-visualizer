import React, { useState } from "react";

export default function InsertForm({ onInsert }) {
  const [key, setKey] = useState("");
  const [value, setValue] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (key && value) {
      onInsert(parseInt(key), value);
      setKey("");
      setValue("");
    }
  };

  return (
    <form onSubmit={handleSubmit} style={{ marginBottom: "20px" }}>
      <input
        type="number"
        placeholder="Key"
        value={key}
        onChange={(e) => setKey(e.target.value)}
        style={{ padding: "9px", marginRight: "10px"}}
      />
      <input
        type="text"
        placeholder="Value"
        value={value}
        onChange={(e) => setValue(e.target.value)}
        style={{ padding: "9px", marginRight: "10px" }}
      />
      <button type="submit"
      style={{
                padding: "5px 10px",
                margin: "10px",
                backgroundColor: "#EDAFB8",
                color: "black",
                border: "none",
                cursor: "pointer",
              }}
      >Insert</button>
    </form>
  );
}
