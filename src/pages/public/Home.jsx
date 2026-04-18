import PublicNavbar from "../../components/navbar/PublicNavbar";

export default function Home() {
  return (
    <div>
      <PublicNavbar /> {/* ✅ CONNECTED */}

      <div style={{ padding: "40px" }}>
        <h1>🌱 Nandvya Ecosystem</h1>
        <p>Buy & Sell Waste, Fertilizer, Chara</p>
      </div>
    </div>
  );
}