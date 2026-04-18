import { useEffect, useState } from "react";
import API from "../../api/axios";
import PublicNavbar from "../../components/navbar/PublicNavbar";

export default function WasteList() {
  const [waste, setWaste] = useState([]);

  useEffect(() => {
    const fetchWaste = async () => {
      try {
        const res = await API.get("/waste");
        setWaste(res.data); // ✅ THIS IS PERFECTLY FINE
      } catch (err) {
        console.log(err);
      }
    };

    fetchWaste();
  }, []);

  return (
    <div>
      <PublicNavbar />

      <div style={{ padding: "20px" }}>
        <h2>Waste Marketplace</h2>

        <div style={{ display: "flex", gap: "20px", flexWrap: "wrap" }}>
          {waste.map((item) => (
            <div key={item.id} style={{
              border: "1px solid #ccc",
              padding: "15px",
              width: "250px"
            }}>
              <img
                src={`http://localhost:8082/${item.imageUrl}`}
                alt=""
                style={{ width: "100%", height: "150px" }}
              />

              <h3>{item.name}</h3>
              <p>Type: {item.type}</p>
              <p>Qty: {item.quantity}</p>
              <p>₹ {item.price}</p>
              <p>{item.location}</p>

              <button>Buy</button>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}