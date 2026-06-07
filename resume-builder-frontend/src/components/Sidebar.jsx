import { Link, useNavigate } from "react-router-dom";

function Sidebar() {

  const navigate = useNavigate();

  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  return (
    <div
      className="bg-dark text-white p-3"
      style={{
        minHeight: "100vh"
      }}
    >

      <h4 className="mb-4">
        Resume Builder
      </h4>

      <ul className="nav flex-column">

        <li className="nav-item mb-3">
          <Link
            to="/dashboard"
            className="text-white text-decoration-none"
          >
            Dashboard
          </Link>
        </li>

        <li className="nav-item mb-3">
          <Link
            to="/resumes"
            className="text-white text-decoration-none"
          >
            My Resumes
          </Link>
        </li>

        <li className="nav-item mb-3">
          <Link
            to="/create-resume"
            className="text-white text-decoration-none"
          >
            Create Resume
          </Link>
        </li>

        <li className="nav-item mb-3">
          <Link
            to="/templates"
            className="text-white text-decoration-none"
          >
            Templates
          </Link>
        </li>

        <li className="nav-item mb-3">
          <Link
            to="/premium"
            className="text-white text-decoration-none"
          >
            Premium Plan
          </Link>
        </li>

        <li className="nav-item mb-3">
          <Link
            to="/payments"
            className="text-white text-decoration-none"
          >
            Payment History
          </Link>
        </li>

        <li className="nav-item mb-3">
          <Link
            to="/send-resume"
            className="text-white text-decoration-none"
          >
            Send Resume
          </Link>
        </li>

        <li className="nav-item mt-4">
          <button
            className="btn btn-danger w-100"
            onClick={logout}
          >
            Logout
          </button>
        </li>

      </ul>

    </div>
  );
}

export default Sidebar;