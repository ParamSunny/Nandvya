import { useState } from "react";
import API from "../api"; // your axios instance
import { useNavigate } from "react-router-dom";

function AddFertilizer() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    name: "",
    type: "",
    quantity: "",
    price: "",
    company: "",
    location: "",
    imageUrl: ""
  });

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await API.post("/fertilizer", form);
      alert("Fertilizer Added ✅");
      navigate("/dashboard/fertilizer");
    } catch (err) {
      console.error(err);
      alert("Error adding fertilizer");
    }
  };

  return (
    <div>
      <h2>Add Fertilizer</h2>

      <form onSubmit={handleSubmit}>
        <input name="name" placeholder="Name" onChange={handleChange} required />
        <input name="type" placeholder="Type" onChange={handleChange} />
        <input name="quantity" placeholder="Quantity" onChange={handleChange} />
        <input name="price" placeholder="Price" onChange={handleChange} />
        <input name="company" placeholder="Company" onChange={handleChange} />
        <input name="location" placeholder="Location" onChange={handleChange} />
        <input name="imageUrl" placeholder="Image URL" onChange={handleChange} />

        <button type="submit">Add</button>
      </form>
    </div>
  );
}

export default AddFertilizer;