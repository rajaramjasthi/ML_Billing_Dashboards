import React, { useEffect, useState } from "react";
// import { Card, Grid } from "@mui/material";
import { awsService } from "../services/Services";
// import DurationSelector from "../components/DurationSelector";
import AwsTable from "../tables/AwsTable";
import ServiceSelector from "../components/ServiceSelector";
// import Sidenav from "../components/Sidenav";
// import Navbar from "../components/Navbar";
// import { Box } from "@mui/material";
import Typography from "@mui/material/Typography";
// import CustomBarChart from "../components/CustomBarChart";
// import CustomPieChart from "../components/CustomPieChart";
import BillingInformationCard from "../common/BillingInformationCard";
import BillingDetailsChartsAndTable from "../common/BillingDetailsChartsAndTable";
export const AwsPage = () => {
  const [service, setService] = useState("");
  // const [sidenavOpen, setSidenavOpen] = useState(false);
  const [dateRange, setDateRange] = useState({
    startDate: null,
    endDate: null,
  });
  const [months, setMonths] = useState(3);
  const [display, setDisplay] = useState(false);
  const [data, setData] = useState([]);
  const [calling, setCalling] = useState(true);
  // console.log("dateRange", dateRange);

  useEffect(() => {
    forAwsGet();
  }, [calling]);


  const handleMonthChange = (selectedMonth) => {
    // console.log("selectedMjjhhdshfkhonth", selectedMonth);
    setMonths(selectedMonth);
    setDisplay(true);
    setCalling(!calling);
  };

  const handleServiceChange = (event) => {
    console.log("service", event.target.value);
    setService(event.target.value);
    setCalling(!calling);
  };

  // const toggleSidenav = () => {
  //   setSidenavOpen(!sidenavOpen);
  // };

  const forAwsGet = async () => {
    awsService(service, dateRange?.startDate, dateRange?.endDate, months)
      .then((res) => {
        console.log(res);
        setData(res);
      })
      .catch((error) => {
        console.log(error);
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

    if (savedService) setService(savedService);
  }, []);

  const monthdata = Array.isArray(data?.monthlyTotalAmounts)
    ? data?.monthlyTotalAmounts?.map((item) => ({
      name: Object.keys(item)[0],
      amount: Object.values(item)[0]?.toFixed(2),
    }))
    : [];

  const topFiveCustomers = data?.top5Services?.map((item) => {
    const { service, totalCost } = item;
    return {
      // name: `${service} - $${totalCost}`,
      name: `${service}`,
      value: totalCost,
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
             sx={{ ...contentStyle }} id='billing-main-container'> */}
      <Typography
        variant="h5"
        sx={{ marginBottom: 3, textAlign: "center", marginTop: 3 }}
        className="fw-bold"
      >
        AWS Billing-Details
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
                <Grid
                  container
                  spacing={3}
                  // justifyContent="center"
                  alignItems="center"
                >
                  <Grid item xs={12} sm={6} md={6} lg={4} xl={4}>
                    <div className="h3 fw-bold">Billing Information</div>
                  </Grid>
                  <Grid item xs={12} sm={6} md={6} lg={4} xl={4}>
                    <div>
                      <p className="p-0 m-0">Service</p>
                      <ServiceSelector
                        service={service}
                        handleServiceChange={handleServiceChange}
                      />
                    </div>
                  </Grid>

                  <Grid item xs={12} sm={6} md={6} lg={2} xl={2}>
                    <div>
                      <p className="p-0 m-0">Duration</p>
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
          <ServiceSelector
            service={service}
            handleServiceChange={handleServiceChange}
          />
        </div>
      </BillingInformationCard>
      <BillingDetailsChartsAndTable data={data} monthdata={monthdata} topFiveCustomers={topFiveCustomers} >
        <AwsTable data={data?.billingDetails} />
      </BillingDetailsChartsAndTable>
      {/* <Grid
              container
              spacing={3}
              style={{ marginLeft: "-10px", width: "100%" }}
            >
              <Grid item xs={11.2} md={6} lg={8}>

                <div className="card p-3">
                  <div className="fw-bold h5">Billing Summary</div>
                  {data?.monthlyTotalAmounts && data?.monthlyTotalAmounts?.length > 0 ? <CustomBarChart
                    data={data?.monthlyTotalAmounts && monthdata}
                    height={400}
                    barLineSize={60}
                    colors={["#10B981", "#FE6476", "#FEA37C", "#048DAD"]}
                  /> : <div className="h6 d-flex flex-column align-items-center justify-content-center" style={{ height: '340px' }}>No Data Available</div>}
                </div>
              </Grid>

              <Grid
                item xs={11.2} md={6} lg={4}
                style={{
                  padding: '24px 5px 0 20px',
                }}
              >
                <div className="card p-3">
                  <div className="mb-3 text-center">
                    <span className="h5 fw-bold " style={{ fontSize: '16px' }}>{'Billing Period '}</span>{" "}
                    <span className="h5 fw-bold" style={{ fontSize: '16px' }}>
                      ({data?.billingPeriod?.map((i) => i?.BillingPeriod)})
                    </span>
                  </div>
                  {data?.totalAmount ? <div className="d-flex justify-content-center">
                    <span style={{ fontSize: "15px" }}>Total Amount-</span>
                    <span
                      style={{
                        fontSize: "15px",
                        color: "#10B981",
                        paddingLeft: "4px",
                      }}
                    >
                      <span className="px-1 fw-bold">
                        {"$"}
                        {data?.totalAmount && data?.totalAmount?.toFixed(2)}
                      </span>
                    </span>
                  </div> : <div className="h6 d-flex flex-column align-items-center justify-content-center p-3" >No Data Available</div>}
                </div>

                <div className="card p-3 mt-2">
                  <div className="">
                    <div className="h5 fw-bold" style={{ fontSize: '17px' }}>Top 5 Consumers</div>
                    {data?.top5Services && data?.top5Services?.length > 0 ? <CustomPieChart
                      data={data?.top5Services && topFiveCustomers}
                      height={300}
                    /> : <div className="h6 d-flex flex-column align-items-center justify-content-center" style={{ height: '200px' }}>No Data Available</div>}
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
                    <AwsTable data={data?.billingDetails} />
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

export default AwsPage;
