import { useEffect, useState } from "react";
import API from "../../api/axios";

export default function ServicePanel() {
  const [services, setServices] = useState([]);

  useEffect(() => {
    API.get("/service").then(res => setServices(res.data));
  }, []);

  const update = async (id, action) => {
    await API.put(`/service/${action}/${id}`);
    window.location.reload();
  };

  return (
    <div className="container">
      <h2>Service Panel</h2>
      {services.map(s => (
        <div key={s.id} className="card">
          <p>{s.description}</p>
          <p>{s.status}</p>

          <button onClick={() => update(s.id, "accept")}>Accept</button>
          <button onClick={() => update(s.id, "start")}>Start</button>
          <button onClick={() => update(s.id, "complete")}>Complete</button>
        </div>
      ))}
    </div>
  );
}