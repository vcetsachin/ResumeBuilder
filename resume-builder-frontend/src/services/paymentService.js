import api from "./api";

export const createPremiumOrder =
async () => {

const response =
  await api.post(
    "/api/payment/create-order",
    {
      planType: "premium"
    }
  );

return response.data;


};

export const getPaymentHistory =
async () => {

const response =
  await api.get(
    "/api/payment/history"
  );

return response.data;

};
