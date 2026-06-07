import { useNavigate } from "react-router-dom";
import Sidebar from "../components/Sidebar";

import professionalImg from "../assets/templates/professional.png";
import modernImg from "../assets/templates/modern.png";
import creativeImg from "../assets/templates/creative.png";
//import atsImg from "../assets/templates/ats.png";
//import minimalImg from "../assets/templates/minimal.png";
//import executiveImg from "../assets/templates/executive.png";

function Templates() {

const navigate = useNavigate();

const useTemplate = (template) => {


localStorage.setItem(
  "selectedTemplate",
  template
);

alert(`${template} Template Selected`);

// Future:
// navigate("/resume-preview/RESUME_ID");


};

const templates = [
  {
    name: "Professional",
    image: professionalImg,
    description: "Best for corporate jobs"
  },
  {
    name: "Modern",
    image: modernImg,
    description: "Clean modern design"
  },
  {
    name: "Creative",
    image: creativeImg,
    description: "Best for designers"
  }
];

return ( <div className="container-fluid">


  <div className="row">

    <div className="col-md-2 p-0">
      <Sidebar />
    </div>

    <div className="col-md-10 p-4">

      <h2 className="mb-4">
        Resume Templates
      </h2>

      <div className="row">

        {templates.map((template, index) => (

          <div
            className="col-md-4 mb-4"
            key={index}
          >

            <div className="card shadow h-100">

              <img
                src={template.image}
                alt={template.name}
                className="card-img-top"
                style={{
                  height: "350px",
                  objectFit: "cover"
                }}
              />

              <div className="card-body">

                <h5 className="card-title">
                  {template.name}
                </h5>

                <p className="card-text">
                  {template.description}
                </p>

                <button
                  className="btn btn-primary w-100"
                  onClick={() =>
                    useTemplate(template.name)
                  }
                >
                  Use Template
                </button>

              </div>

            </div>

          </div>

        ))}

      </div>

    </div>

  </div>

</div>

);
}

export default Templates;
