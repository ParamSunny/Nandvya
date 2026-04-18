import { useState } from "react";
import API from "../../api/axios";
import { toast } from "react-toastify";

export default function AddChara() {
  const [form, setForm] = useState({
    name: "",
    type: "",
    quantity: "",
    price: "",
    imageUrl: "",
    location: ""
  });

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await API.post("/chara", {
        ...form,
        quantity: Number(form.quantity),
        price: Number(form.price)
      });

      toast.success("Chara Added 🌾");
    } catch {
      toast.error("Error ❌");
    }
  };

  return (
    <form className="container" onSubmit={handleSubmit}>
      <h2>Add Chara</h2>

      <input placeholder="Name" onChange={e => setForm({...form, name: e.target.value})} />
      <input placeholder="Type" onChange={e => setForm({...form, type: e.target.value})} />
      <input placeholder="Quantity" type="number" onChange={e => setForm({...form, quantity: e.target.value})} />
      <input placeholder="Price" type="number" onChange={e => setForm({...form, price: e.target.value})} />
      <input placeholder="Image URL" onChange={e => setForm({...form, imageUrl: e.target.value})} />
      <input placeholder="Location" onChange={e => setForm({...form, location: e.target.value})} />

      <button>Add Chara</button>
    </form>
  );
}