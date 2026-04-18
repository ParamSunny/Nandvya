import { Link } from "react-router-dom";

export default function PublicNavbar() {
  return (
    <nav style={{ padding: "10px", background: "#4CAF50", color: "#fff" }}>
      <Link to="/">Home</Link>
      <Link to="/waste">Waste</Link>
      <Link to="/fertilizer">Fertilizer</Link>
      <Link to="/chara">Chara</Link>

      <span style={{ float: "right" }}>
        <Link to="/login">Login</Link>
        <Link to="/register">Register</Link>
      </span>
    </nav>
  );
}