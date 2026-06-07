import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Sidebar from "../components/Sidebar";
import {
  getUserResumes,
  deleteResume
} from "../services/resumeService";

function ResumeList() {

  const [resumes, setResumes] = useState([]);

  useEffect(() => {
    loadResumes();
  }, []);

  const loadResumes = async () => {
    try {
      const data = await getUserResumes();
      setResumes(data);
    } catch (error) {
      console.error(error);
    }
  };

  const handleDelete = async (id) => {

    const confirmDelete =
      window.confirm(
        "Are you sure you want to delete this resume?"
      );

    if (!confirmDelete) {
      return;
    }

    try {

      await deleteResume(id);

      alert("Resume Deleted");

      loadResumes();

    } catch (error) {

      console.error(error);

      alert("Delete Failed");
    }
  };

  return (
    <div className="container-fluid">

      <div className="row">

        <div className="col-md-2 p-0">
          <Sidebar />
        </div>

        <div className="col-md-10 p-4">

          <div className="d-flex justify-content-between align-items-center mb-4">

            <h2>My Resumes</h2>

            <Link
              to="/create-resume"
              className="btn btn-primary"
            >
              Create New Resume
            </Link>

          </div>

          <table className="table table-bordered table-striped">

            <thead>
              <tr>
                <th>Title</th>
                <th>Actions</th>
              </tr>
            </thead>

            <tbody>

              {resumes.length === 0 ? (
                <tr>
                  <td colSpan="2">
                    No Resume Found
                  </td>
                </tr>
              ) : (
                resumes.map((resume) => (
                  <tr key={resume._id}>

                    <td>
                      {resume.title}
                    </td>

                    <td>

                      <Link
                        to={`/edit-resume/${resume._id}`}
                        className="btn btn-warning btn-sm me-2"
                      >
                        Edit
                      </Link>

                      <button
                        className="btn btn-danger btn-sm"
                        onClick={() =>
                          handleDelete(
                            resume._id
                          )
                        }
                      >
                        Delete
                      </button>

<Link
  to={`/resume-preview/${resume._id}`}
  className="btn btn-info btn-sm me-2"
>
  Preview
</Link>
                    </td>

                  </tr>
                ))
              )}

            </tbody>

          </table>

        </div>

      </div>

    </div>
  );
}

export default ResumeList;