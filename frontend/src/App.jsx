import React, { useState, useEffect } from "react";
import HashVisualizer from "./components/HashVisualizer";
import InsertForm from "./components/InsertForm";
import SearchKeyForm from "./components/SearchKeyForm";
import {BASE_BACKEND_URL} from "./config";

export default function App() {
  const [globalDepth, setGlobalDepth] = useState(0);
  const [directory, setDirectory] = useState([]);

console.log(BASE_BACKEND_URL);
  const fetchData = async () => {
    const res = await fetch(`${BASE_BACKEND_URL}/api/hash/state`);
    const data = await res.json();
    setGlobalDepth(data.globalDepth);
    setDirectory(data.directory);
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleInsert = async (key, value) => {
    await fetch(`${BASE_BACKEND_URL}/api/hash/insert`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ key, value }),
    });
    console.log(`Inserted: ${key} → ${value}`);
    fetchData(); // Refresh UI after insert
  };

  return (
    <div style={{ padding: "20px", height: "100vh", width: "100vw", backgroundColor: "#4A5759", color: "#F7E1D7"}}>
      <p style={{fontSize: "30px", fontWeight: "bold"}}>Extendible Hashing Visualizer</p>
      <InsertForm onInsert={handleInsert} />
      <SearchKeyForm />
      <HashVisualizer globalDepth={globalDepth} directory={directory} />
      <Footer/>
    </div>
  );
}

const Footer = () => {
    return (
        <div style={{ marginTop: "0px", textAlign: "left", color: "#F7E1D7", fontSize: "12px"}}>
            <p>© Yashasvi Yadav - Find Source Code at <a style={{color: "#EDAFB8", fontSize: "14px" ,fontWeight: "bold"
                }}
             href="https://github.com/yashasviyadav1/extendible-hashing-visualizer">Github</a> </p>
        </div>
    );
}

