    import { useState }
    from "react";

    import { sendResumeEmail }
    from "../services/emailService";

    function SendResume() {

    const [email,
    setEmail] = useState("");

    const [file,
    setFile] = useState(null);

    const submit =
    async (e) => {

    ```
      e.preventDefault();

      try {

        await sendResumeEmail(
          email,
          "Resume Application",
          "Please find attached",
          file
        );

        alert(
          "Resume Sent Successfully"
        );

      } catch (error) {

        alert(
          "Failed to Send"
        );
      }
    ```

    };

    return ( <div className="container">

    ```
      <h2>
        Send Resume
      </h2>

      <form
        onSubmit={submit}
      >

        <input
          className="form-control mb-3"
          placeholder="Recipient Email"
          value={email}
          onChange={(e)=>
            setEmail(
              e.target.value
            )
          }
        />

        <input
          type="file"
          className="form-control mb-3"
          onChange={(e)=>
            setFile(
              e.target.files[0]
            )
          }
        />

        <button
          className="btn btn-primary"
        >
          Send Resume
        </button>

      </form>

    </div>
    ```

    );
    }

    export default SendResume;
