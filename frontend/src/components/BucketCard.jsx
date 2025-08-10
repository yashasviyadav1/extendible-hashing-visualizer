export default function BucketCard({ index, bucket }) {
  return (
    <div className="bucket-card" style={{ backgroundColor: "#B0C4B1", color: "black", padding: "20px", borderRadius: "5px" }}>
      <div className="bucket-header">
        <div className="index">i[{index}]</div>
        <div>localDepth: {bucket.localDepth}</div>
      </div>
      <ul>
        {bucket.entries.length === 0 && <li className="empty">— empty —</li>}
        {bucket.entries.map((e, idx) => (
          <li key={idx}>
            <strong>{e.key}</strong> → {e.value}
          </li>
        ))}
      </ul>
    </div>
  );
}
