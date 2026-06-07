import { Link, useNavigate } from "react-router-dom";

function Sidebar() {

const navigate = useNavigate();

const logout = () => {

```
localStorage.removeItem("token");

navigate("/");
```

};

return (
<div
className="bg-dark text-white p-3 vh-100"
style={{
width: "100%"
}}
>

```
  <h4 className="mb-3">
    Resume Builder
  </h4>

  <hr />

  <ul className="nav flex-column">

    <li className="nav-item mb-3">
      <Link
        className="text-white text-decoration-none"
        to="/dashboard"
      >
        Dashboard
      </Link>
    </li>

    <li className="nav-item mb-3">
      <Link
        className="text-white text-decoration-none"
        to="/resumes"
      >
        My Resumes
      </Link>
    </li>

    <li className="nav-item mb-3">
      <Link
        className="text-white text-decoration-none"
        to="/create-resume"
      >
        Create Resume
      </Link>
    </li>

    <li className="nav-item mb-3">
      <Link
        className="text-white text-decoration-none"
        to="/templates"
      >
        Templates
      </Link>
    </li>

    <li className="nav-item mb-3">
      <Link
        className="text-white text-decoration-none"
        to="/premium"
      >
        Premium Plan
      </Link>
    </li>

    <li className="nav-item mb-3">
      <Link
        className="text-white text-decoration-none"
        to="/payments"
      >
        Payment History
      </Link>
    </li>

    <li className="nav-item mb-3">
      <Link
        className="text-white text-decoration-none"
        to="/send-resume"
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
```

);
}

export default Sidebar;
