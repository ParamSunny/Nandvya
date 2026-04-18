import { useState } from "react";
import API from "../../api/axios";
import { toast } from "react-toastify";

export default function RaiseService() {
  const [form, setForm] = useState({
    description: "",
    location: "",
    type: "TRANSPORT"
  });

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await API.post("/service", form);
      toast.success("Service Requested 🛠");
    } catch {
      toast.error("Error ❌");
    }
  };

  return (
    <form className="container" onSubmit={handleSubmit}>
      <h2>Raise Service</h2>

      <input placeholder="Description" onChange={e => setForm({...form, description: e.target.value})} />
      <input placeholder="Location" onChange={e => setForm({...form, location: e.target.value})} />

      <select onChange={e => setForm({...form, type: e.target.value})}>
        <option>TRANSPORT</option>
        <option>MAINTENANCE</option>
        <option>REPAIR</option>
      </select>

      <button>Submit</button>
    </form>
  );
}