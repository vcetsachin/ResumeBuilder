import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Sidebar from "/components/Sidebar";
import {
getUserResumes,
deleteResume
} from "/services/resumeService";

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

const confirmDelete = window.confirm(
  "Are you sure you want to delete this resume?"
);

if (!confirmDelete) return;

try {
  await deleteResume(id);
  loadResumes();
} catch (error) {
  console.error(error);
  alert("Failed to delete resume");
}

};

return ( <div className="container-fluid"> <div className="row">

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
          Create Resume
        </Link>
      </div>

      <table className="table table-bordered table-hover">

        <thead className="table-dark">
          <tr>
            <th>Title</th>
            <th>Last Updated</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>

          {resumes.length > 0 ? (
            resumes.map((resume) => (

              <tr key={resume._id || resume.id}>

                <td>{resume.title}</td>

                <td>
                  {resume.updateAt
                    ? new Date(resume.updateAt).toLocaleString()
                    : "N/A"}
                </td>

                <td>

                  <Link
                    to={`/edit-resume/${resume._id || resume.id}`}
                    className="btn btn-warning btn-sm me-2"
                  >
                    Edit
                  </Link>

                  <button
                    className="btn btn-danger btn-sm"
                    onClick={() =>
                      handleDelete(resume._id || resume.id)
                    }
                  >
                    Delete
                  </button>

                </td>

              </tr>

            ))
          ) : (
            <tr>
              <td colSpan="3" className="text-center">
                No resumes found
              </td>
            </tr>
          )}

        </tbody>

      </table>

    </div>

  </div>
</div>

);
}

export default ResumeList;
