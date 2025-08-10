export const API_URL = "http://localhost:8080/api/hash";

export async function insertKeyValue(key, value) {
  const params = new URLSearchParams();
  params.set("key", key);
  params.set("value", value);
  await fetch(`${API_URL}/insert?${params.toString()}`, {
    method: "POST"
  });
}

export async function getState() {
  const res = await fetch(`${API_URL}/state`);
  return res.json();
}
