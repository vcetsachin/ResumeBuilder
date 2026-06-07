import { useEffect, useState } from "react";
import { getTemplates } from "../services/templateService";

function Templates() {

  const [templates,
    setTemplates] = useState([]);

  useEffect(() => {
    loadTemplates();
  }, []);

  const loadTemplates =
    async () => {

      const response =
        await getTemplates();

      setTemplates(
        response.availableTemplates
      );
  };

  return (
    <div className="container">

      <h2>
        Available Templates
      </h2>

      <ul>

        {
          templates.map(
            (template) => (

              <li
                key={template}
              >
                Template {template}
              </li>

            )
          )
        }

      </ul>

    </div>
  );
}

export default Templates;