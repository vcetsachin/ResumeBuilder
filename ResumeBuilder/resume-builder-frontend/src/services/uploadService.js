import api from "./api";

export const uploadResumeImages = async (
resumeId,
thumbnail,
profileImage
) => {

const formData = new FormData();

if (thumbnail) {
formData.append(
"thumbnail",
thumbnail
);
}

if (profileImage) {
formData.append(
"profileImage",
profileImage
);
}

const response = await api.put(
`/auth/resume/${resumeId}/upload-image`,
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
