import { useEffect, useState } from "react";
import { getProfile } from "../services/authService";
import { getUserResumes } from "../services/resumeService";
import Sidebar from "../components/Sidebar";

function Dashboard() {
  const [profile, setProfile] = useState(null);
  const [resumeCount, setResumeCount] = useState(0);

  useEffect(() => {
    loadDashboard();
  }, []);

  const loadDashboard = async () => {
    try {
      const profileData = await getProfile();
      setProfile(profileData);

      const resumes = await getUserResumes();
      setResumeCount(resumes.length);
    } catch (error) {
      console.error("Dashboard Error:", error);
    }
  };

  return (
    <div className="container-fluid">
      <div className="row">

        {/* Sidebar */}
        <div className="col-md-2 p-0">
          <Sidebar />
        </div>

        {/* Main Content */}
        <div className="col-md-10 p-4">
          <h1 className="mb-4">Resume Builder Dashboard</h1>

          <div className="row g-4">

            {/* Name Card */}
            <div className="col-md-3">
              <div className="card shadow-sm border-0">
                <div className="card-body">
                  <h5>Name</h5>
                  <p className="mb-0">
                    {profile?.name || "Loading..."}
                  </p>
                </div>
              </div>
            </div>

            {/* Email Card */}
            <div className="col-md-3">
              <div className="card shadow-sm border-0">
                <div className="card-body">
                  <h5>Email</h5>
                  <p className="mb-0">
                    {profile?.email || "Loading..."}
                  </p>
                </div>
              </div>
            </div>

            {/* Plan Card */}
            <div className="col-md-3">
              <div className="card shadow-sm border-0">
                <div className="card-body">
                  <h5>Plan</h5>
                  <p className="mb-0">
                    {profile?.subscriptionPlan || "Basic"}
                  </p>
                </div>
              </div>
            </div>

            {/* Resume Count Card */}
            <div className="col-md-3">
              <div className="card shadow-sm border-0">
                <div className="card-body">
                  <h5>Total Resumes</h5>
                  <p className="mb-0">
                    {resumeCount}
                  </p>
                </div>
              </div>
            </div>

          </div>

          {/* Welcome Section */}
          <div className="card mt-5 shadow-sm border-0">
            <div className="card-body">
              <h3>
                Welcome {profile?.name}
              </h3>

              <p className="text-muted">
                Create professional resumes, upload images,
                choose templates, upgrade to premium and
                send resumes directly via email.
              </p>
            </div>
          </div>

        </div>
      </div>
    </div>
  );
}

export default Dashboard;