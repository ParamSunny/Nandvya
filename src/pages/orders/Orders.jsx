import { useEffect, useState } from "react";
import API from "../../api/axios";

export default function Orders() {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    API.get("/orders/my").then(res => setOrders(res.data));
  }, []);

  return (
    <div className="container">
      <h2>Orders</h2>
      {orders.map(o => (
        <div key={o.id} className="card">
          <p>{o.productType}</p>
          <p>{o.quantity}</p>
          <p>{o.status}</p>
        </div>
      ))}
    </div>
  );
}