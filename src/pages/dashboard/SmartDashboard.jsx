import PrivateNavbar from "../../components/navbar/PrivateNavbar";
import { getRoles } from "../../utils/roleUtils";

export default function SmartDashboard() {
  const roles = getRoles();

  return (
    <div>
      <PrivateNavbar /> {/* ✅ CONNECTED */}

      <div style={{ padding: "20px" }}>
        <h1>Dashboard</h1>
        <p>Your Roles: {roles.join(", ")}</p>
      </div>
    </div>
  );
}