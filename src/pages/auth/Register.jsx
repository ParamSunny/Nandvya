import { useState } from "react";
import API from "../../api/axios";

export default function Register() {
  const [form, setForm] = useState({
    name: "",
    email: "",
    password: "",
    roles: []
  });

  const handleRoleChange = (role) => {
    if (form.roles.includes(role)) {
      setForm({
        ...form,
        roles: form.roles.filter(r => r !== role)
      });
    } else {
      setForm({
        ...form,
        roles: [...form.roles, role]
      });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (form.roles.length === 0) {
      alert("Select at least one role ❌");
      return;
    }

    try {
      const res = await API.post("/auth/register", form);
      alert("Registered Successfully ✅");
      console.log(res.data);
    } catch (err) {
      console.log(err.response?.data || err);
      alert("Registration Failed ❌");
    }
  };

  return (
    <div style={{ padding: "40px" }}>
      <h2>Register</h2>

      <form onSubmit={handleSubmit}>
        <input
          placeholder="Name"
          onChange={(e) => setForm({ ...form, name: e.target.value })}
        />

        <input
          placeholder="Email"
          onChange={(e) => setForm({ ...form, email: e.target.value })}
        />

        <input
          type="password"
          placeholder="Password"
          onChange={(e) => setForm({ ...form, password: e.target.value })}
        />

        <h4>Select Roles:</h4>

        {["FARMER", "SUPPLIER", "BIOGAS", "SERVICE"].map(role => (
          <label key={role} style={{ display: "block" }}>
            <input
              type="checkbox"
              onChange={() => handleRoleChange(role)}
            />
            {role}
          </label>
        ))}

        <button type="submit">Register</button>
      </form>
    </div>
  );
}