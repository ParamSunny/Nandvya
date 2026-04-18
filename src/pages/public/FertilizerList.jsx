import { useEffect, useState } from "react";
import API from "../api";

function FertilizerList() {
  const [data, setData] = useState([]);

  useEffect(() => {
  const fetchData = async () => {
    const res = await API.get("/fertilizer");
    setData(res.data);
  };

  fetchData();
}, []);

  return (
    <div>
      <h2>All Fertilizers</h2>

      {data.map((item) => (
        <div key={item.id} style={{ border: "1px solid gray", margin: "10px" }}>
          <img src={item.imageUrl} width="150" alt="" />
          <h3>{item.name}</h3>
          <p>Type: {item.type}</p>
          <p>Qty: {item.quantity}</p>
          <p>Price: ₹{item.price}</p>
          <p>Company: {item.company}</p>
          <p>Location: {item.location}</p>
        </div>
      ))}
    </div>
  );
}

export default FertilizerList;