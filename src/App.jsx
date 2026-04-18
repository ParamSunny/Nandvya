import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/auth/Login";
import Register from "./pages/auth/Register";
import Home from "./pages/public/Home";
import SmartDashboard from "./pages/dashboard/SmartDashboard";
import AddWaste from "./pages/product/AddWaste";
import WasteList from "./pages/public/WasteList";
import MyWaste from "./pages/product/MyWaste";
import WasteMarket from "./pages/dashboard/WasteMarket";
import EditWaste from "./pages/dashboard/EditWaste";


export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/dashboard" element={<SmartDashboard />} />
        <Route path="/add-waste" element={<AddWaste />} />
        <Route path="/waste" element={<WasteList />} />
        <Route path="/my-waste" element={<MyWaste />} />
        <Route path="/dashboard/waste" element={<WasteMarket />} />
        <Route path="/dashboard/edit-waste/:id" element={<EditWaste />} />
      </Routes>
    </BrowserRouter>
  );
}