import React, { useEffect, useState } from "react";
// import { Card, Grid } from "@mui/material";
import { gcpService } from "../services/Services";
// import DurationSelector from "../components/DurationSelector";
// import Sidenav from "../components/Sidenav";
// import Navbar from "../components/Navbar";
// import { Box } from "@mui/material";
import Typography from "@mui/material/Typography";
import GcpTable from "../tables/GcpTable";
import GcpSelector from "../components/Gcp/GcpSelector";
// import CustomBarChart from "../components/CustomBarChart";
// import CustomPieChart from "../components/CustomPieChart";
// import toast from "react-hot-toast";
import BillingInformationCard from "../common/BillingInformationCard";
import BillingDetailsChartsAndTable from "../common/BillingDetailsChartsAndTable";

export const GcpPage = () => {
  const [serviceDescription, setServiceDescription] = useState("");
  // const [sidenavOpen, setSidenavOpen] = useState(false);
  const [dateRange, setDateRange] = useState({
    startDate: "",
    endDate: "",
  });
  const [months, setMonths] = useState(3);
  const [display, setDisplay] = useState(false);
  const [data, setData] = useState([]);
  const [calling, setCalling] = useState(true);
  // console.log("data", data);

  useEffect(() => {
    forGcpGet();
  }, [calling]);

  const handleMonthChange = (selectedMonth) => {
    // console.log("selectedMonthsss", selectedMonth);
    setMonths(selectedMonth);
    setDisplay(true);
    //setIsDateDisabled(event.target.value !== "0");
    setCalling(!calling);
  };

  const handleServiceChange = (event) => {
    setServiceDescription(event.target.value);
    setCalling(!calling);
  };

  // const toggleSidenav = () => {
  //   setSidenavOpen(!sidenavOpen);
  // };

  const forGcpGet = async () => {
    gcpService(
      serviceDescription,
      dateRange.startDate,
      dateRange.endDate,
      months
    )
      .then((res) => {
        console.log(res, "prudhvi");
        setData(res);
      })
      .catch((error) => {
        console.log(error, error?.response?.data?.error, "ghgg");
        // toast.error(error?.response?.data?.error);
      });
  };

  // const bodyStyle = {
  //   backgroundColor: "#f0f0f0",
  //   minHeight: "100vh",
  //   padding: "20px",
  //   overflowX: "hidden",
  // };

  // const contentStyle = {
  //   transition: "margin-left 0.5s",
  //   marginLeft: sidenavOpen ? 250 : 0,
  //   width: "100%",
  //   overflowX: 'hidden'

  // };

  useEffect(() => {
    setDisplay(true);
  }, [display]);

  useEffect(() => {
    const savedService = localStorage.getItem("service");

    if (savedService) setServiceDescription(savedService);
  }, []);

  const monthdata = Array.isArray(data?.monthlyTotalAmounts)
    ? data.monthlyTotalAmounts.map((item) => ({
      name: Object.keys(item)[0],
      value: Object.values(item)[0],
    }))
    : [];

  const topFiveCustomers = data?.top5Services?.map((item) => {
    const { serviceDescription, totalCost } = item;
    return {
      // name: `${serviceDescription} - $${totalCost}`,
      name: `${serviceDescription}`,
      value: totalCost && +totalCost?.toFixed(0),
      costType: 'Dollar'

    };
  });

  return (
    <>
      {/* <div style={bodyStyle}>
      <React.Fragment>
        <Navbar toggleSidenav={toggleSidenav} />
        <Box height={50} />
        <Box sx={{ display: "flex" }}>
          <Sidenav open={sidenavOpen} onClose={toggleSidenav} />

          <Box
            component="main"
            sx={{ ...contentStyle }}> */}
      <Typography
        variant="h5"
        sx={{ marginBottom: 3, textAlign: "center", marginTop: 3 }}
        className="fw-bold"
      >
        GCP Billing-Details
      </Typography>
      {/* <Card sx={{ px: 2, py: 4, m: 2 }}>
              <Box
                component={"div"}
                sx={{
                  display: "flex",
                  justifyContent: "space-around",
                  alignItems: "center",
                }}
              >
                <Grid container spacing={3} alignItems="center">
                  <Grid item xs={12} sm={6} md={6} lg={4} xl={4}>
                    <div className="h3 fw-bold">Billing Information</div>
                  </Grid>
                  <Grid item xs={12} sm={6} md={6} lg={4} xl={4}>
                    <div>
                      <h5>Service</h5>
                      <GcpSelector
                        serviceDescription={serviceDescription}
                        handleServiceChange={handleServiceChange}
                      />
                    </div>
                  </Grid>

                  <Grid item xs={12} sm={6} md={6} lg={2} xl={2}>
                    <div>
                      <h5>Duration</h5>
                      <DurationSelector
                        handleMonthChange={handleMonthChange}
                        months={months}
                        setDateRange={setDateRange}
                        setCalling={setCalling}
                        calling={calling}
                      />
                    </div>
                  </Grid>
                </Grid>
              </Box>
            </Card> */}
      <BillingInformationCard handleMonthChange={handleMonthChange}
        months={months}
        setDateRange={setDateRange}
        setCalling={setCalling}
        calling={calling}
      >
        <div style={{ width: '100%' }}>
          <p className="p-0 m-0">Service</p>
          <GcpSelector
            serviceDescription={serviceDescription}
            handleServiceChange={handleServiceChange}
          />
        </div>
      </BillingInformationCard>
      <BillingDetailsChartsAndTable data={data} monthdata={monthdata} topFiveCustomers={topFiveCustomers} >
        <GcpTable data={data?.billingDetails} />
      </BillingDetailsChartsAndTable>

      {/* <Grid
              container spacing={3}
              style={{
                marginLeft: '-10px',
                width: '100%'
              }}
            >
              <Grid item xs={11.2} md={6} lg={8}>
                <div className="card p-3">
                  <div className="fw-bold h5">Billing Summary</div>
                  <CustomBarChart
                    data={data && monthdata}
                    height={460}
                    barLineSize={60}
                    colors={["#10B981", "#FE6476", "#FEA37C", "#048DAD"]}
                  />
                </div>
              </Grid>

              <Grid
                item xs={11.2} md={6} lg={4}
                style={{
                  padding: '24px 5px 0 20px',
                }}
              >
                <div className="card p-3">
                  <div className="p-3">
                    <span className="h5 fw-bold">{'Billing Period '}</span>{" "}
                    <span className="h5 fw-bold">
                      ({data?.billingPeriod?.map((i) => i?.BillingPeriod)})
                    </span>
                  </div>
                  <div className="d-flex justify-content-center">
                    <span style={{ fontSize: "20px" }}>Total Amount-</span>
                    <span
                      style={{
                        fontSize: "20px",
                        color: "#10B981",
                        paddingLeft: "4px",
                      }}
                    >
                      <span className="px-1 fw-bold">{"$"} {data?.totalAmount && data?.totalAmount?.toFixed(2)}</span>

                    </span>
                  </div>
                </div>
                <div className="card p-3 mt-2">
                  <div className="p-3">
                    <div className="h5 fw-bold">Top 5 Consumers</div>
                    <CustomPieChart
                      data={data?.top5Services && topFiveCustomers}
                      height={300}
                    />
                  </div>
                </div>
              </Grid>
            </Grid>

            <Card sx={{ px: 2, py: 4, m: 2 }}>
              <Box
                component={"div"}
                sx={{
                  display: "flex",
                  justifyContent: "space-around",
                  alignItems: "center",
                }}
              >
                <Grid container spacing={2} className="mb-3">
                  <Grid
                    item
                    xs={11}
                    sm={11}
                    lg={12}
                    className="mx-auto mx-sm-0"
                  >
                    <GcpTable data={data?.billingDetails} />
                  </Grid>
                </Grid>
              </Box>
            </Card> */}
      {/* </Box>
        </Box>
      </React.Fragment>
    </div> */}
    </>
  );
};

export default GcpPage;
