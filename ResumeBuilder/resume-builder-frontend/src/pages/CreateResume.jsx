import { useState } from "react";
import { createResume } from "../services/resumeService";
import { useNavigate } from "react-router-dom";
import Sidebar from "../components/Sidebar";

function CreateResume() {
  const [title, setTitle] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await createResume({
        title: title
      });

      navigate("/resumes");
    } catch (error) {
      alert("Failed to create resume");
    }
  };

  return (
    <div className="container-fluid">
      <div className="row">

        <div className="col-md-2 p-0">
          <Sidebar />
        </div>

        <div className="col-md-10 p-4">

          <h2>Create Resume</h2>

          <form onSubmit={handleSubmit}>

            <div className="mb-3">
              <label className="form-label">
                Resume Title
              </label>

              <input
                type="text"
                className="form-control"
                placeholder="Java Developer Resume"
                value={title}
                onChange={(e) =>
                  setTitle(e.target.value)
                }
              />
            </div>

            <button
              className="btn btn-success"
              type="submit"
            >
              Create Resume
            </button>

          </form>

        </div>

      </div>
    </div>
  );
}

export default CreateResume;