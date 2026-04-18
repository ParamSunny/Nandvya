import { useEffect, useState } from "react";
import API from "../../api/axios";

export default function CharaList() {
  const [data, setData] = useState([]);

  useEffect(() => {
    API.get("/chara").then(res => setData(res.data));
  }, []);

  return (
    <div className="container">
      <h2>Chara Market</h2>

      <div className="grid">
        {data.map(item => (
          <div className="card" key={item.id}>
            <img src={`http://localhost:8082/${item.imageUrl}`} />
            <h3>{item.name}</h3>
            <p>₹ {item.price}</p>
          </div>
        ))}
      </div>
    </div>
  );
}