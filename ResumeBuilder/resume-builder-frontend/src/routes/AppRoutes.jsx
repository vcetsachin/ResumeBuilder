import { Routes, Route } from "react-router-dom";

import Login from "../pages/Login";
import Register from "../pages/Register";
import Dashboard from "../pages/Dashboard";
import ResumeList from "../pages/ResumeList";
import CreateResume from "../pages/CreateResume";
import EditResume from "../pages/EditResume";

import ProtectedRoute from "../components/ProtectedRoute";

function AppRoutes() {
  return (
    <Routes>

      <Route path="/" element={<Login />} />

      <Route
        path="/register"
        element={<Register />}
      />

      <Route
        path="/dashboard"
        element={
          <ProtectedRoute>
            <Dashboard />
          </ProtectedRoute>
        }
      />

      <Route
        path="/resumes"
        element={
          <ProtectedRoute>
            <ResumeList />
          </ProtectedRoute>
        }
      />

      <Route
        path="/create-resume"
        element={
          <ProtectedRoute>
            <CreateResume />
          </ProtectedRoute>
        }
      />

      <Route
        path="/edit-resume/:id"
        element={
          <ProtectedRoute>
            <EditResume />
          </ProtectedRoute>
        }
      />

    </Routes>
  );
}

export default AppRoutes;