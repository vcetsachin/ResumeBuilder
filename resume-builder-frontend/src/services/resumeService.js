import api from "./api";

export const getUserResumes = async () => {
  const response = await api.get("/auth/resume");
  return response.data;
};

export const createResume = async (data) => {
  const response = await api.post("/auth/resume", data);
  return response.data;
};

export const deleteResume = async (id) => {
  const response = await api.delete(`/auth/resume/${id}`);
  return response.data;
};

export const getResumeById = async (id) => {
  const response = await api.get(`/auth/resume/${id}`);
  return response.data;
};

export const updateResume = async (id, data) => {
  const response = await api.put(`/auth/resume/${id}`, data);
  return response.data;
};