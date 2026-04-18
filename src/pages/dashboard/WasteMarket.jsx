import { useEffect, useState } from "react";
import API from "../../api/axios";
import PrivateNavbar from "../../components/navbar/PrivateNavbar";

export default function WasteMarket() {
  const [waste, setWaste] = useState([]);

  useEffect(() => {
    const fetchWaste = async () => {
      const res = await API.get("/waste");
      setWaste(res.data);
    };

    fetchWaste();
  }, []);

  const handleBuy = async (item) => {
    try {
      const token = localStorage.getItem("token");
      const payload = JSON.parse(atob(token.split(".")[1]));

      const buyerEmail = payload.sub;

await API.post("/orders", {
  buyerEmail: buyerEmail, // if backend supports
  sellerId: item.user?.id,
  productId: item.id,
  quantity: 1,
  price: item.price,
  productType: "WASTE",
  orderType: "BUY"
});

      alert("Order Placed ✅");

    } catch (err) {
      console.log(err);
      alert("Order Failed ❌");
    }
  };

  return (
    <div>
      <PrivateNavbar />

      <div style={{ padding: "20px" }}>
        <h2>Buy Waste</h2>

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
              <p>₹ {item.price}</p>
              <p>{item.location}</p>

              <button onClick={() => handleBuy(item)}>
                Buy Now
              </button>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}