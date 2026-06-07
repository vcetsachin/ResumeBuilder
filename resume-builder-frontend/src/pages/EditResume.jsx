import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
  getResumeById,
  updateResume
} from "../services/resumeService";
import Sidebar from "../components/Sidebar";
function EditResume() {

  const { id } = useParams();

  const [resume, setResume] = useState({
    title: "",

    profileInfo: {
      fullName: "",
      destination: "",
      summary: ""
    },

    contactInfo: {
      email: "",
      phone: "",
      location: "",
      linkIn: "",
      github: "",
      website: ""
    },

    skills: [],
    educations: [],
    workExperiences: [],
    projects: [],
    certifications: [],
    languages: [],
    interest: []
  });

  useEffect(() => {
    loadResume();
  }, []);

  const loadResume = async () => {
    try {
      const data = await getResumeById(id);
      setResume(data);
    } catch (error) {
      console.log(error);
    }
  };

  const saveResume = async () => {
    try {

      await updateResume(id, resume);

      alert("Resume Updated Successfully");

    } catch (error) {
      alert("Update Failed");
    }
  };

  return (
    <div className="container-fluid">
      <div className="row">

        <div className="col-md-2 p-0">
          <Sidebar />
        </div>

        <div className="col-md-10 p-4">

          <h2>Edit Resume</h2>

          <hr />

          <h4>Profile Information</h4>

          <input
            className="form-control mb-2"
            placeholder="Full Name"
            value={resume.profileInfo?.fullName || ""}
            onChange={(e) =>
              setResume({
                ...resume,
                profileInfo: {
                  ...resume.profileInfo,
                  fullName: e.target.value
                }
              })
            }
          />

          <input
            className="form-control mb-2"
            placeholder="Destination"
            value={resume.profileInfo?.destination || ""}
            onChange={(e) =>
              setResume({
                ...resume,
                profileInfo: {
                  ...resume.profileInfo,
                  destination: e.target.value
                }
              })
            }
          />

          <textarea
            className="form-control mb-4"
            placeholder="Summary"
            value={resume.profileInfo?.summary || ""}
            onChange={(e) =>
              setResume({
                ...resume,
                profileInfo: {
                  ...resume.profileInfo,
                  summary: e.target.value
                }
              })
            }
          />

          <h4>Contact Information</h4>

          <input
            className="form-control mb-2"
            placeholder="Email"
            value={resume.contactInfo?.email || ""}
            onChange={(e) =>
              setResume({
                ...resume,
                contactInfo: {
                  ...resume.contactInfo,
                  email: e.target.value
                }
              })
            }
          />

          <input
            className="form-control mb-2"
            placeholder="Phone"
            value={resume.contactInfo?.phone || ""}
            onChange={(e) =>
              setResume({
                ...resume,
                contactInfo: {
                  ...resume.contactInfo,
                  phone: e.target.value
                }
              })
            }
          />

          <input
            className="form-control mb-4"
            placeholder="Location"
            value={resume.contactInfo?.location || ""}
            onChange={(e) =>
              setResume({
                ...resume,
                contactInfo: {
                  ...resume.contactInfo,
                  location: e.target.value
                }
              })
            }
          />
          
        <h4>Skills</h4>
<input
  className="form-control mb-4"
  placeholder="Java, Spring Boot, React"
  value={
    resume.skills
      ?.map(skill => skill.name)
      .join(", ") || ""
  }
  onChange={(e) =>
    setResume({
      ...resume,
      skills: e.target.value
        .split(",")
        .filter(item => item.trim() !== "")
        .map(item => ({
          name: item.trim(),
          progress: 80
        }))
    })
  }
/>

<h4>Education</h4>

<input
  className="form-control mb-2"
  placeholder="Degree"
  value={resume.educations?.[0]?.degree || ""}
  onChange={(e) =>
    setResume({
      ...resume,
      educations: [{
        ...resume.educations?.[0],
        degree: e.target.value
      }]
    })
  }
/>

<input
  className="form-control mb-2"
  placeholder="Institution"
  value={resume.educations?.[0]?.institution || ""}
  onChange={(e) =>
    setResume({
      ...resume,
      educations: [{
        ...resume.educations?.[0],
        institution: e.target.value
      }]
    })
  }
/>


<h4>Work Experience</h4>

<input
  className="form-control mb-2"
  placeholder="Company"
  value={resume.workExperiences?.[0]?.company || ""}
  onChange={(e) =>
    setResume({
      ...resume,
      workExperiences: [{
        ...resume.workExperiences?.[0],
        company: e.target.value
      }]
    })
  }
/>

<input
  className="form-control mb-2"
  placeholder="Role"
  value={resume.workExperiences?.[0]?.role || ""}
  onChange={(e) =>
    setResume({
      ...resume,
      workExperiences: [{
        ...resume.workExperiences?.[0],
        role: e.target.value
      }]
    })
  }
/>


<h4>Projects</h4>

<input
  className="form-control mb-2"
  placeholder="Project Name"
  value={resume.projects?.[0]?.name || ""}
  onChange={(e) =>
    setResume({
      ...resume,
      projects: [{
        ...resume.projects?.[0],
        name: e.target.value
      }]
    })
  }
/>

<textarea
  className="form-control mb-3"
  placeholder="Project Description"
  value={resume.projects?.[0]?.description || ""}
  onChange={(e) =>
    setResume({
      ...resume,
      projects: [{
        ...resume.projects?.[0],
        description: e.target.value
      }]
    })
  }
/>

<h4>Languages</h4>

<input
  className="form-control mb-3"
  placeholder="English, Hindi, Marathi"
  value={
    resume.languages
      ?.map(lang => lang.name)
      .join(", ") || ""
  }
  onChange={(e) =>
    setResume({
      ...resume,
      languages: e.target.value
        .split(",")
        .filter(item => item.trim())
        .map(item => ({
          name: item.trim(),
          progress: "90%"
        }))
    })
  }
/>

<h4>Interests</h4>

<input
  className="form-control mb-4"
  placeholder="Coding, Cricket, Reading"
  value={resume.interest?.join(", ") || ""}
  onChange={(e) =>
    setResume({
      ...resume,
      interest: e.target.value
        .split(",")
        .map(item => item.trim())
    })
  }
/>
          <button
            className="btn btn-success"
            onClick={saveResume}
          >
            Save Resume
          </button>

        </div>

      </div>
    </div>
  );
}

export default EditResume;