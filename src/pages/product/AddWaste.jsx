import { useState, useEffect } from "react";
import API from "../../api/axios";
import { useNavigate } from "react-router-dom";

export default function AddWaste() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    name: "",
    type: "",
    quantity: "",
    price: "",
    location: ""
  });

  const [image, setImage] = useState(null);
  const [preview, setPreview] = useState(null);

  // ✅ cleanup preview memory
  useEffect(() => {
    return () => {
      if (preview) URL.revokeObjectURL(preview);
    };
  }, [preview]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    // ✅ basic validation
    if (!form.name || !form.type || !form.quantity || !form.price || !form.location) {
      alert("Please fill all fields ❌");
      return;
    }

    try {
      let imagePath = "images/waste/sample.jpg";

      // 🔥 STEP 1: Upload Image
      if (image) {
        const formData = new FormData();
        formData.append("image", image);

        const res = await API.post(
          "/image/upload/waste",
          formData,
          {
            headers: {
              "Content-Type": "multipart/form-data"
            }
          }
        );

        imagePath = res.data;
      }

      // 🔥 STEP 2: Save Waste
      const payload = {
        ...form,
        quantity: Number(form.quantity),
        price: Number(form.price),
        imageUrl: imagePath
      };

      await API.post("/waste", payload);

      alert("Waste Added ✅");
      navigate("/my-waste");

      // ✅ reset form
      setForm({
        name: "",
        type: "",
        quantity: "",
        price: "",
        location: ""
      });
      setImage(null);
      setPreview(null);

    } catch (err) {
      console.log("ERROR:", err.response?.data || err.message);
      alert("Error ❌");
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Add Waste</h2>

      <form onSubmit={handleSubmit}>
        <input
          placeholder="Name"
          value={form.name}
          onChange={(e)=>setForm({...form,name:e.target.value})}
        />

        <input
          placeholder="Type"
          value={form.type}
          onChange={(e)=>setForm({...form,type:e.target.value})}
        />

        <input
          placeholder="Quantity"
          type="number"
          value={form.quantity}
          onChange={(e)=>setForm({...form,quantity:e.target.value})}
        />

        <input
          placeholder="Price"
          type="number"
          value={form.price}
          onChange={(e)=>setForm({...form,price:e.target.value})}
        />

        <input
          placeholder="Location"
          value={form.location}
          onChange={(e)=>setForm({...form,location:e.target.value})}
        />

        {/* 🔥 IMAGE INPUT */}
        <input
          type="file"
          accept="image/*"
          onChange={(e) => {
            const file = e.target.files[0];
            if (!file) return;

            // ✅ validate type
            if (!file.type.startsWith("image/")) {
              alert("Only image allowed ❌");
              return;
            }

            // ✅ validate size (2MB)
            if (file.size > 2 * 1024 * 1024) {
              alert("Max 2MB allowed ❌");
              return;
            }

            setImage(file);
            setPreview(URL.createObjectURL(file));
          }}
        />

        {/* 🔥 PREVIEW */}
        {preview && (
          <div style={{ marginTop: "10px" }}>
            <img
              src={preview}
              alt="preview"
              width="150"
              style={{ borderRadius: "10px" }}
            />

            <br />

            <button
              type="button"
              onClick={() => {
                setImage(null);
                setPreview(null);
              }}
              style={{
                marginTop: "5px",
                background: "red",
                color: "#fff",
                border: "none",
                padding: "5px 10px",
                borderRadius: "5px"
              }}
            >
              Remove
            </button>
          </div>
        )}

        <br />

        <button type="submit" style={{ marginTop: "10px" }}>
          Add Waste
        </button>
      </form>
    </div>
  );
}