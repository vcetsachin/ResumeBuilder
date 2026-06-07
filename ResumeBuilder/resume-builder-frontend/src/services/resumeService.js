import api from "./api";

// Get all resumes
export const getUserResumes = async () => {
const response = await api.get("/auth/resume");
return response.data;
};

// Create resume
export const createResume = async (data) => {
const response = await api.post("/auth/resume", data);
return response.data;
};

// Get single resume
export const getResumeById = async (id) => {
const response = await api.get(`/auth/resume/${id}`);
return response.data;
};

// Update resume
export const updateResume = async (id, data) => {
const response = await api.put(`/auth/resume/${id}`, data);
return response.data;
};

// Delete resume
export const deleteResume = async (id) => {
const response = await api.delete(`/auth/resume/${id}`);
return response.data;
};

// Upload thumbnail + profile image
export const uploadResumeImages = async (
resumeId,
thumbnail,
profileImage
) => {

const formData = new FormData();

if (thumbnail) {
formData.append("thumbnail", thumbnail);
}

if (profileImage) {
formData.append("profileImage", profileImage);
}

const response = await api.put(
`/auth/resume/${resumeId}/upload-image`,
formData,
{
headers: {
"Content-Type":
"multipart/form-data",
},
}
);

return response.data;
};

// Get templates
export const getTemplates = async () => {
const response = await api.get("/auth/template");
return response.data;
};
