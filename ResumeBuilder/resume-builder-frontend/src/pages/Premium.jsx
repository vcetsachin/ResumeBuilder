import { createPremiumOrder }
from "../services/paymentService";

function Premium() {

const upgrade =
async () => {

```
  try {

    const order =
      await createPremiumOrder();

    alert(
      "Order Created: " +
      order.orderId
    );

  } catch (error) {

    alert(
      "Premium Upgrade Failed"
    );
  }
```

};

return ( <div className="container mt-5">

```
  <h2>
    Premium Plan
  </h2>

  <p>
    Unlock all templates
  </p>

  <button
    className="btn btn-warning"
    onClick={upgrade}
  >
    Upgrade ₹999
  </button>

</div>
```

);
}

export default Premium;
