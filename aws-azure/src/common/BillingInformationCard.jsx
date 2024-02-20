import React, { useEffect, useState } from 'react'
import { Card, Grid, Box } from "@mui/material";
import DurationSelector from "../components/DurationSelector";
import { FormControl, Select, MenuItem } from "@mui/material";
import axios from "axios";

// const azureSubscriptionTypes = [
//     {
//         id: 1,
//         name: "Azure Subscription 1",
//         value: "azure",
//     },
//     {
//         id: 2,
//         name: "Azure Subscription 2",
//         value: "AzureSubscriptionTwo",
//     },
//     {
//         id: 3,
//         name: "Azure Subscription 3",
//         value: "AzureSubscriptionThree",
//     },
//     {
//         id: 4,
//         name: "Azure Subscription 4",
//         value: "AzureSubscriptionFour",
//     }
// ]

const BillingInformationCard = (props) => {
    const { handleMonthChange, months, setDateRange, setCalling, calling, setAzureSubscriptionValue, azureSubscriptions = false, handleSubscriptionChange, azureSubscriptionValue = '' } = props;
    // console.log("props", props)
    const [subscriptionOptions, setSubscriptionOptions] = useState([]);

    useEffect(() => {
        const fetchServiceOptions = async () => {
            try {
                const token = localStorage.getItem("token");

                if (token) {
                    const config = {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    };

                    const response = await axios.get(`http://localhost:8080/azure/distinct-subscription-names`, config);
                    // console.log("subscriptions response", response)
                    if (response?.data && response?.data?.length > 0) {
                        setSubscriptionOptions(response?.data);
                        const getInitialSubscription = response?.data?.filter((subscription) => subscription === "Microsoft Azure Motivity");
                        console.log("getInitialSubscription", getInitialSubscription)
                        if (getInitialSubscription?.length > 0) {
                            setAzureSubscriptionValue(getInitialSubscription[0])
                        }
                        // handleSubscriptionChange(response?.data[0])
                    }
                    // setSubscriptionOptions(response?.data);
                    // setClicked(true);
                } else {
                    console.error("Token not found in localStorage or options already fetched");
                }
            } catch (error) {
                console.error("Error fetching service options:", error);
            }
        };

        fetchServiceOptions();
    }, []);
    const newPropsCss = {
        backgroundColor: "#FFFF",
        // width: "340px",
        width: "90%",
        textAlign: "center",
        ":hover": {
            backgroundColor: "#FFFF",
            color: "black",
        },
        "&.Mui-selected": {
            backgroundColor: "#FFFF !important",
            color: "black",
        },
    };
    return (
        <>
            <Card sx={{ px: 2, py: 4, m: 2 }}>
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
                        // spacing={3}
                        spacing={{ xs: 1, md: 2, lg: 3 }}
                        // justifyContent="center"
                        alignItems="center"
                    >
                        <Grid item xs={12} lg={azureSubscriptions ? 12 : 4} xl={azureSubscriptions ? 12 : 4}>
                            <div className={`h4 fw-bold ${azureSubscriptions ? '' : ''}`}>Billing Information</div>
                        </Grid>
                        {azureSubscriptions ? <Grid item xs={12} sm={12} md={4} lg={4} xl={4} className='d-flex justify-content-center'>
                            <div style={{ width: '100%' }}>
                                <p className="p-0 m-0">Subscriptions</p>
                                <FormControl sx={{ ...newPropsCss }} fullWidth>
                                    <Select
                                        fullWidth
                                        labelId="demo-simple-select-label"
                                        id="demo-simple-select"
                                        className='demo-simple-select'
                                        sx={{ ...newPropsCss, height: "2.4em" }}
                                        value={azureSubscriptionValue}
                                        onChange={handleSubscriptionChange}
                                        //   onFocus={handleFocus}
                                        displayEmpty
                                        inputProps={{ 'aria-label': 'Without label' }}
                                        MenuProps={{ PaperProps: { sx: { maxHeight: 200 } } }}
                                    >
                                        <MenuItem value="" disabled>
                                            Select Subscription
                                        </MenuItem>
                                        {subscriptionOptions?.map((option, index) => (
                                            <MenuItem key={index} value={option} sx={{ ...newPropsCss }}>
                                                {option}
                                            </MenuItem>
                                        ))}
                                    </Select>
                                </FormControl>

                            </div>
                        </Grid> : null}

                        <Grid item xs={12} sm={12} md={azureSubscriptions ? 4 : 6} lg={4} xl={4} className='d-flex justify-content-center'>

                            {props.children}
                        </Grid>

                        <Grid item xs={12} sm={12} md={azureSubscriptions ? 4 : 6} lg={4} xl={4} className='d-flex justify-content-center'>
                            <div style={{ width: '100%' }}>
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
            </Card>
        </>
    )
}

export default BillingInformationCard