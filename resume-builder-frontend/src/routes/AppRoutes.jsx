import { Routes, Route } from "react-router-dom";

import ResumePreview from "../pages/ResumePreview";
import Login from "../pages/Login";
import Register from "../pages/Register";
import Dashboard from "../pages/Dashboard";
import ResumeList from "../pages/ResumeList";
import CreateResume from "../pages/CreateResume";
import EditResume from "../pages/EditResume";

import Templates from "../pages/Templates";
import Premium from "../pages/Premium";
import PaymentHistory from "../pages/PaymentHistory";
import SendResume from "../pages/SendResume";

import ProtectedRoute from "../components/ProtectedRoute";

//import ResumePreview from "../pages/ResumePreview";

function AppRoutes() {
  return (
    <Routes>

      {/* Public Routes */}

      <Route path="/" element={<Login />} />

      <Route path="/register" element={<Register />} />

      {/* Protected Routes */}

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

      <Route
        path="/templates"
        element={
          <ProtectedRoute>
            <Templates />
          </ProtectedRoute>
        }
      />

      <Route
        path="/premium"
        element={
          <ProtectedRoute>
            <Premium />
          </ProtectedRoute>
        }
      />

      <Route
        path="/payments"
        element={
          <ProtectedRoute>
            <PaymentHistory />
          </ProtectedRoute>
        }
      />

      <Route
        path="/send-resume"
        element={
          <ProtectedRoute>
            <SendResume />
          </ProtectedRoute>
        }
      />

     <Route
  path="/resume-preview/:id"
  element={
    <ProtectedRoute>
      <ResumePreview />
    </ProtectedRoute>
  }
/>
    </Routes>
  );
}

export default AppRoutes;