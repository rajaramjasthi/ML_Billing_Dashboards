import React from 'react'
import { Card, Grid, Box } from "@mui/material";
import CustomBarChart from '../components/CustomBarChart';
// import AwsTable from '../tables/AwsTable';
import CustomPieChart from '../components/CustomPieChart';
const BillingDetailsChartsAndTable = (props) => {
    const { data, monthdata, topFiveCustomers, costType  } = props;
    return (
        <>
            <Grid
                container
                spacing={3}
                style={{ marginLeft: "-10px", width: "100%" }}
            >
                {/* Barchart */}
                <Grid item xs={12} md={6} lg={8}>

                    <div className="card p-3">
                        <div className="fw-bold h5">Billing Summary</div>
                        {data?.monthlyTotalAmounts && data?.monthlyTotalAmounts?.length > 0 ? <CustomBarChart
                            data={data?.monthlyTotalAmounts && monthdata}
                            height={404}
                            costType={costType}
                            barLineSize={40}
                            colors={["#10B981", "#FE6476", "#FEA37C", "#048DAD"]}
                        /> : <div className="h6 d-flex flex-column align-items-center justify-content-center" style={{ height: '404px' }}>No Data Available</div>}
                    </div>
                </Grid>

                {/* Totalamount */}
                <Grid
                    item xs={12} md={6} lg={4}
                    style={{
                        padding: '24px 5px 0 20px',
                    }}
                >
                    <div className="card p-3">
                        <div className="mb-3 text-center">
                            <div className="h5 fw-bold billing-period" style={{ fontSize: '16px' }} title={`${data?.billingPeriod?.map((i) => i?.BillingPeriod)}`}>{`Billing Period (${data?.billingPeriod?.map((i) => i?.BillingPeriod)})`}</div>
                            {/* <span className="h5 fw-bold" style={{ fontSize: '16px' }}>
                                ({data?.billingPeriod?.map((i) => i?.BillingPeriod)})
                            </span> */}
                            {/* {data?.billingPeriod && data?.billingPeriod?.length > 0 ? <span className="h5 fw-bold">
                      ({data?.billingPeriod?.map((i) => i?.BillingPeriod)})
                    </span> : <span className="h5 fw-bold">{`${dateRange?.startDate ? `(${dateRange?.startDate})` : ''} ${(dateRange?.startDate && dateRange?.endDate) ? `to` : ''} ${(dateRange?.endDate) ? `(${dateRange?.endDate})` : ''}`}</span>} */}
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
                                    {costType == 'INR' ? '₹' : "$"}
                                    {data?.totalAmount && data?.totalAmount?.toFixed(2)}
                                </span>
                            </span>
                        </div> : <div className="h6 d-flex flex-column align-items-center justify-content-center" >No Data Available</div>}
                    </div>

                    <div className="card p-3 mt-2">
                        <div className="">
                            <div className="h5 fw-bold" style={{ fontSize: '17px' }}>Top 5 Consumers</div>
                            {data?.top5Services && data?.top5Services?.length > 0 ? <CustomPieChart
                                data={data?.top5Services && topFiveCustomers}
                                height={300}
                            /> : <div className="h6 d-flex flex-column align-items-center justify-content-center" style={{ height: '300px' }}>No Data Available</div>}
                        </div>
                    </div>
                </Grid>

                {/* <Grid item xs={11.2} md={6} lg={4}>
                <div className="card p-3">
                  <div className="p-3">
                    <span className="h5 fw-bold">Billing Period</span>{" "}
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
                  <div className="h5 fw-bold">Top 5 Consumers</div>
                  <CustomPieChart
                    data={data?.top10Services && topFiveCustomers}
                    height={300}
                  />
                </div>
              </Grid> */}

                {/* ServicesPieChart*/}
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
                            xs={12}
                            sm={12}
                            lg={12}
                            className="mx-auto mx-sm-0"
                        >
                            {props.children}
                        </Grid>
                    </Grid>
                </Box>
            </Card>
        </>
    )
}

export default BillingDetailsChartsAndTable