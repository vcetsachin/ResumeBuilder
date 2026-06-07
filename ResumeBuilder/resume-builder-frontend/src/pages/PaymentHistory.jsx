import { useEffect, useState } from "react";
import Sidebar from "../components/Sidebar";
import { getPaymentHistory } from "../services/paymentService";

function PaymentHistory() {

const [payments, setPayments] =
useState([]);

useEffect(() => {
loadPayments();
}, []);

const loadPayments =
async () => {

```
  try {

    const data =
      await getPaymentHistory();

    setPayments(data);

  } catch (error) {
    console.log(error);
  }
```

};

return ( <div className="container-fluid"> <div className="row">

```
    <div className="col-md-2 p-0">
      <Sidebar />
    </div>

    <div className="col-md-10 p-4">

      <h2>Payment History</h2>

      <table className="table">

        <thead>
          <tr>
            <th>Order ID</th>
            <th>Amount</th>
            <th>Status</th>
          </tr>
        </thead>

        <tbody>

          {payments.map(
            (payment) => (

              <tr key={payment.id}>
                <td>
                  {payment.razorpayOrderId}
                </td>

                <td>
                  ₹{payment.amount / 100}
                </td>

                <td>
                  {payment.status}
                </td>
              </tr>

            )
          )}

        </tbody>

      </table>

    </div>

  </div>
</div>
```

);
}

export default PaymentHistory;
