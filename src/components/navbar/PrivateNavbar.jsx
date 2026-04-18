import { Link } from "react-router-dom";
import { getRoles, hasRole, isAdmin } from "../../utils/roleUtils";

export default function PrivateNavbar() {
  const roles = getRoles();

  return (
    <nav style={{ padding: "10px", background: "#222", color: "#fff" }}>
        <p>User Roles: {roles.join(", ")}</p>
      <Link to="/dashboard">Dashboard</Link>

      {/* FARMER */}
      {(hasRole("FARMER") || isAdmin()) && (
        <>
          <Link to="/add-chara">Add Chara</Link>
          <Link to="/fertilizer">Buy Fertilizer</Link>
        </>
      )}

      {/* BIOGAS */}
      {(hasRole("BIOGAS") || isAdmin()) && (
        <>
          <Link to="/add-fertilizer">Add Fertilizer</Link>
          <Link to="/dashboard/waste">Buy Waste</Link>
        </>
      )}

      {/* SUPPLIER */}
      {(hasRole("SUPPLIER") || isAdmin()) && (
        <>
          <Link to="/add-waste">Add Waste</Link>
          <Link to="/my-waste">My Waste</Link>
          <Link to="/chara">Buy Chara</Link>
        </>
      )}

      {/* SERVICE */}
      {(hasRole("SERVICE") || isAdmin()) && (
        <>
          <Link to="/service-panel">Service Panel</Link>
        </>
      )}

      {/* COMMON */}
      <Link to="/orders">Orders</Link>
      <Link to="/my-services">My Services</Link>

      <button onClick={() => {
        localStorage.clear();
        window.location.href = "/login";
      }}>
        Logout
      </button>
    </nav>
  );
}