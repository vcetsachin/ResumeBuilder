import api from "./api";

export const sendResumeEmail =
  async (
    recipientEmail,
    subject,
    message,
    pdfFile
  ) => {

    const formData =
      new FormData();

    formData.append(
      "recipientEmail",
      recipientEmail
    );

    formData.append(
      "subject",
      subject
    );

    formData.append(
      "message",
      message
    );

    formData.append(
      "pdfFile",
      pdfFile
    );

    const response =
      await api.post(
        "/api/email/send-resume",
        formData,
        {
          headers: {
            "Content-Type":
              "multipart/form-data"
          }
        }
      );

    return response.data;
};