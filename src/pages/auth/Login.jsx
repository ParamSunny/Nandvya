import { useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../../api/axios";
import { setToken } from "../../utils/authUtils";

export default function Login() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    email: "",
    password: ""
  });

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const res = await API.post("/auth/login", form);

      console.log("LOGIN RESPONSE:", res.data);

      const token = res.data;

      if (!token) {
        alert("Token not received from backend ❌");
        return;
      }

      setToken(token);

      // decode safely
      const payload = JSON.parse(atob(token.split(".")[1]));

      // console.log("TOKEN PAYLOAD:", payload); // 🔍 DEBUG

      // ✅ Extract roles safely
      let roles = payload.roles || payload.authorities || [];

      // ✅ Remove ROLE_ prefix
      roles = roles.map(r => {
        if (typeof r === "string") {
          return r.replace("ROLE_", "");
        } else {
          return r.authority.replace("ROLE_", "");
        }
      });

      // ✅ Save roles
      localStorage.setItem("roles", JSON.stringify(roles));

      console.log("SAVED ROLES:", roles); // 🔍 DEBUG

      // ✅ Proper redirect (NO reload)
      navigate("/dashboard");

    } catch (err) {
      console.log(err.response?.data || err);
      alert("Login Failed ❌");
    }
  };

  return (
    <div style={{ padding: "40px" }}>
      <h2>Login</h2>

      <form onSubmit={handleLogin}>
        <input
          placeholder="Email"
          onChange={(e) => setForm({ ...form, email: e.target.value })}
        />

        <input
          type="password"
          placeholder="Password"
          onChange={(e) => setForm({ ...form, password: e.target.value })}
        />

        <button type="submit">Login</button>
      </form>
    </div>
  );
}