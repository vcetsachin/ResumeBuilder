import { useState, useContext } from "react";
import { useNavigate, Link } from "react-router-dom";
import { loginUser } from "../services/authService";
import { AuthContext } from "../context/AuthContext";
import "../css/auth.css";

function Login() {
  const navigate = useNavigate();
  const { login } = useContext(AuthContext);

  const [formData, setFormData] = useState({
    email: "",
    password: ""
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await loginUser(formData);

      login(response);

      alert("Login Successful");

      navigate("/dashboard");
    } catch (error) {
      console.error(error);

      alert(
        error?.response?.data?.message ||
        "Invalid Email or Password"
      );
    }
  };

return (
  <div className="auth-card">
    <h2 className="text-center mb-4">
      Resume Builder Login
    </h2>

    <form onSubmit={handleSubmit}>
      <input
        type="email"
        name="email"
        placeholder="Enter Email"
        className="form-control mb-3"
        value={formData.email}
        onChange={handleChange}
        required
      />

      <input
        type="password"
        name="password"
        placeholder="Enter Password"
        className="form-control mb-3"
        value={formData.password}
        onChange={handleChange}
        required
      />

      <button
        type="submit"
        className="btn btn-primary w-100"
      >
        Login
      </button>
    </form>

    <p className="text-center mt-3">
      Don't have an account?
      <Link to="/register"> Register</Link>
    </p>
  </div>
);
}

export default Login;