import { useEffect, useState } from "react";
import API from "../../api/axios";
import PrivateNavbar from "../../components/navbar/PrivateNavbar";
import { useNavigate } from "react-router-dom";

export default function MyFertilizer() {
  const [fertilizer, setFertilizer] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchMyFertilizer = async () => {
      try {
        const res = await API.get("/fertilizer");

        const token = localStorage.getItem("token");
        if (!token) return;

        const payload = JSON.parse(atob(token.split(".")[1]));
        const userEmail = payload.sub;

        // ⚠️ TEMP FILTER (since no user field in backend)
        const myData = res.data.filter(
          (item) => item.company === userEmail
        );

        setFertilizer(myData);

      } catch (err) {
        console.log("Fetch Error:", err);
      }
    };

    fetchMyFertilizer();
  }, []);

  // ✅ DELETE
  const handleDelete = async (id) => {
    if (!window.confirm("Delete this fertilizer?")) return;

    try {
      await API.delete(`/fertilizer/${id}`);
      alert("Deleted ✅");

      setFertilizer((prev) =>
        prev.filter((item) => item.id !== id)
      );

    } catch (err) {
      console.log("Delete Error:", err);
      alert("Delete Failed ❌");
    }
  };

  return (
    <div>
      <PrivateNavbar />

      <div style={{ padding: "20px" }}>
        <h2>My Fertilizer</h2>

        {fertilizer.length === 0 ? (
          <p>No fertilizer added yet</p>
        ) : (
          <div
            style={{
              display: "flex",
              gap: "20px",
              flexWrap: "wrap"
            }}
          >
            {fertilizer.map((item) => (
              <div
                key={item.id}
                style={{
                  border: "1px solid #ddd",
                  borderRadius: "10px",
                  padding: "15px",
                  width: "250px",
                  boxShadow: "0 2px 8px rgba(0,0,0,0.1)"
                }}
              >
                <img
                  src={item.imageUrl}
                  alt={item.name}
                  style={{
                    width: "100%",
                    height: "150px",
                    objectFit: "cover",
                    borderRadius: "8px"
                  }}
                />

                <h3>{item.name}</h3>
                <p><b>Type:</b> {item.type}</p>
                <p><b>Qty:</b> {item.quantity}</p>
                <p><b>Price:</b> ₹ {item.price}</p>
                <p><b>Company:</b> {item.company}</p>
                <p><b>Location:</b> {item.location}</p>

                {/* 🔥 ACTION BUTTONS */}
                <div
                  style={{
                    display: "flex",
                    justifyContent: "space-between",
                    marginTop: "10px"
                  }}
                >
                  <button
                    onClick={() =>
                      navigate(`/dashboard/edit-fertilizer/${item.id}`)
                    }
                    style={{
                      background: "#007bff",
                      color: "#fff",
                      border: "none",
                      padding: "5px 10px",
                      borderRadius: "5px",
                      cursor: "pointer"
                    }}
                  >
                    Edit
                  </button>

                  <button
                    onClick={() => handleDelete(item.id)}
                    style={{
                      background: "red",
                      color: "#fff",
                      border: "none",
                      padding: "5px 10px",
                      borderRadius: "5px",
                      cursor: "pointer"
                    }}
                  >
                    Delete
                  </button>
                </div>

              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}