import { useState } from "react";
import { registerUser } from "../services/authService";
import { useNavigate, Link } from "react-router-dom";
import "../css/auth.css";

function Register() {

  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    profileImageUrl: ""
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

      await registerUser(formData);

      alert(
        "Registration Successful. Check Email."
      );

      navigate("/");

    } catch (error) {

      alert(
        error.response?.data?.message ||
        "Registration Failed"
      );
    }
  };

  return (
    <div className="auth-card">

      <h2 className="text-center mb-4">
        Create Account
      </h2>

      <form onSubmit={handleSubmit}>

        <input
          className="form-control mb-3"
          name="name"
          placeholder="Name"
          onChange={handleChange}
        />

        <input
          className="form-control mb-3"
          name="email"
          placeholder="Email"
          onChange={handleChange}
        />

        <input
          className="form-control mb-3"
          name="password"
          type="password"
          placeholder="Password"
          onChange={handleChange}
        />

        <button
          className="btn btn-success w-100"
          type="submit"
        >
          Register
        </button>

      </form>

      <p className="text-center mt-3">
        Already Have Account?
        <Link to="/"> Login</Link>
      </p>

    </div>
  );
}

export default Register;