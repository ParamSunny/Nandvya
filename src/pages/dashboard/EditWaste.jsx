import { useState, useEffect } from "react";
import API from "../../api/axios";
import { useParams, useNavigate } from "react-router-dom";

export default function EditWaste() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [form, setForm] = useState({
    name: "",
    type: "",
    quantity: "",
    price: "",
    location: "",
    imageUrl: ""
  });

  const [image, setImage] = useState(null);
  const [preview, setPreview] = useState(null);

  // 🔥 FETCH DATA
  useEffect(() => {
    const fetchWaste = async () => {
      const res = await API.get(`/waste/${id}`);
      setForm(res.data);

      // show old image
      if (res.data.imageUrl) {
        setPreview(`http://localhost:8082/${res.data.imageUrl}`);
      }
    };

    fetchWaste();
  }, [id]);

  // 🔥 UPDATE
  const handleUpdate = async (e) => {
    e.preventDefault();

    try {
      let imagePath = form.imageUrl; // keep old image

      // 🔥 if new image selected → upload
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

      // 🔥 update payload
      const payload = {
        ...form,
        quantity: Number(form.quantity),
        price: Number(form.price),
        imageUrl: imagePath
      };

      await API.put(`/waste/${id}`, payload);

      alert("Updated Successfully ✅");
      navigate("/my-waste");

    } catch (err) {
      console.log(err);
      alert("Update Failed ❌");
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Edit Waste</h2>

      <form onSubmit={handleUpdate}>

        <input
          placeholder="Name"
          value={form.name}
          onChange={(e) => setForm({...form, name: e.target.value})}
        />

        <input
          placeholder="Type"
          value={form.type}
          onChange={(e) => setForm({...form, type: e.target.value})}
        />

        <input
          placeholder="Quantity"
          type="number"
          value={form.quantity}
          onChange={(e) => setForm({...form, quantity: e.target.value})}
        />

        <input
          placeholder="Price"
          type="number"
          value={form.price}
          onChange={(e) => setForm({...form, price: e.target.value})}
        />

        <input
          placeholder="Location"
          value={form.location}
          onChange={(e) => setForm({...form, location: e.target.value})}
        />

        {/* 🔥 IMAGE INPUT */}
        <input
          type="file"
          accept="image/*"
          onChange={(e) => {
            const file = e.target.files[0];
            if (!file) return;

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
          </div>
        )}

        <br />

        <button type="submit">Update</button>

      </form>
    </div>
  );
}