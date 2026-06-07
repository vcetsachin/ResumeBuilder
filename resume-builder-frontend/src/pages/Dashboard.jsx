import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import Sidebar from "../components/Sidebar";

function Dashboard() {

  const { user } = useContext(AuthContext);

  return (
    <div className="container-fluid">

      <div className="row">

        <div className="col-md-2 p-0">
          <Sidebar />
        </div>

        <div className="col-md-10 p-4">

          <h2>Dashboard</h2>

          <hr />

          <div className="card p-4">

            <h4>Welcome {user?.name}</h4>

            <p>Email: {user?.email}</p>

            <p>
              Subscription:
              {" "}
              {user?.subscriptionPlan}
            </p>

          </div>

        </div>

      </div>

    </div>
  );
}

export default Dashboard;