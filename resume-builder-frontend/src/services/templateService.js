import api from "./api";

export const getTemplates = async () => {
const response =
await api.get("/auth/template");

return response.data;
};
