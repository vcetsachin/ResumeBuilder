import { useEffect, useState, useRef } from "react";
import { useParams } from "react-router-dom";
import { getResumeById } from "../services/resumeService";
import Sidebar from "../components/Sidebar";

import jsPDF from "jspdf";
import html2canvas from "html2canvas";

function ResumePreview() {
  const { id } = useParams();

  const [resume, setResume] = useState(null);

  const resumeRef = useRef();

  useEffect(() => {
    loadResume();
  }, []);

  const loadResume = async () => {
    try {
      const data = await getResumeById(id);
      setResume(data);
    } catch (error) {
      console.error(error);
    }
  };

  const downloadPDF = async () => {
    const element = resumeRef.current;

    const canvas = await html2canvas(element, {
      scale: 2
    });

    const data = canvas.toDataURL("image/png");

    const pdf = new jsPDF("p", "mm", "a4");

    const imgWidth = 210;

    const imgHeight =
      (canvas.height * imgWidth) /
      canvas.width;

    pdf.addImage(
      data,
      "PNG",
      0,
      0,
      imgWidth,
      imgHeight
    );

    pdf.save("resume.pdf");
  };

  if (!resume) {
    return (
      <h3 className="p-4">
        Loading...
      </h3>
    );
  }

  return (
    <div className="container-fluid">

      <div className="row">

        <div className="col-md-2 p-0">
          <Sidebar />
        </div>

        <div className="col-md-10 p-4">

          <div className="text-end mb-3">

            <button
              className="btn btn-success"
              onClick={downloadPDF}
            >
              Download PDF
            </button>

          </div>

          <div
            ref={resumeRef}
            className="bg-white shadow p-5"
            style={{
              maxWidth: "900px",
              margin: "auto"
            }}
          >

            <h1>
              {resume.profileInfo?.fullName}
            </h1>

            <h4 className="text-muted">
              {resume.profileInfo?.destination}
            </h4>

            <hr />

            <p>
              <strong>Email:</strong>{" "}
              {resume.contactInfo?.email}
            </p>

            <p>
              <strong>Phone:</strong>{" "}
              {resume.contactInfo?.phone}
            </p>

            <p>
              <strong>Location:</strong>{" "}
              {resume.contactInfo?.location}
            </p>

            <p>
              <strong>LinkedIn:</strong>{" "}
              {resume.contactInfo?.linkIn}
            </p>

            <p>
              <strong>GitHub:</strong>{" "}
              {resume.contactInfo?.github}
            </p>

            <p>
              <strong>Website:</strong>{" "}
              {resume.contactInfo?.website}
            </p>

            <hr />

            <h3>Profile Summary</h3>

            <p>
              {resume.profileInfo?.summary}
            </p>

            <hr />

            <h3>Skills</h3>

            <ul>
              {resume.skills?.map(
                (skill, index) => (
                  <li key={index}>
                    {skill.name}
                  </li>
                )
              )}
            </ul>

            <hr />

            <h3>Education</h3>

            {resume.educations?.map(
              (edu, index) => (
                <div key={index}>

                  <h5>
                    {edu.degree}
                  </h5>

                  <p>
                    {edu.institution}
                  </p>

                  <p>
                    {edu.startDate}
                    {" - "}
                    {edu.endDate}
                  </p>

                  <hr />

                </div>
              )
            )}

            <h3>Work Experience</h3>

            {resume.workExperiences?.map(
              (exp, index) => (
                <div key={index}>

                  <h5>
                    {exp.company}
                  </h5>

                  <strong>
                    {exp.role}
                  </strong>

                  <p>
                    {exp.startDate}
                    {" - "}
                    {exp.endDate}
                  </p>

                  <p>
                    {exp.description}
                  </p>

                  <hr />

                </div>
              )
            )}

            <h3>Projects</h3>

            {resume.projects?.map(
              (project, index) => (
                <div key={index}>

                  <h5>
                    {project.name}
                  </h5>

                  <p>
                    {project.description}
                  </p>

                  <p>
                    GitHub:
                    {" "}
                    {project.github}
                  </p>

                  <p>
                    Live Demo:
                    {" "}
                    {project.liveDemo}
                  </p>

                  <hr />

                </div>
              )
            )}

            <h3>Certifications</h3>

            <ul>

              {resume.certifications?.map(
                (cert, index) => (
                  <li key={index}>
                    {cert.title}
                    {" - "}
                    {cert.issuer}
                    {" ("}
                    {cert.year}
                    {")"}
                  </li>
                )
              )}

            </ul>

            <hr />

            <h3>Languages</h3>

            <ul>

              {resume.languages?.map(
                (lang, index) => (
                  <li key={index}>
                    {lang.name}
                    {" - "}
                    {lang.progress}
                  </li>
                )
              )}

            </ul>

            <hr />

            <h3>Interests</h3>

            <ul>

              {resume.interest?.map(
                (item, index) => (
                  <li key={index}>
                    {item}
                  </li>
                )
              )}

            </ul>

          </div>

        </div>

      </div>

    </div>
  );
}

export default ResumePreview;