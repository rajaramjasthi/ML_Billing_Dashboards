import React, { useState } from 'react'
import Sidenav from "../components/Sidenav";
import Navbar from "../components/Navbar";
import { Box } from "@mui/material";
import { Outlet } from 'react-router-dom';
const Index = () => {
    const [sidenavOpen, setSidenavOpen] = useState(false);

    const bodyStyle = {
        backgroundColor: "#f0f0f0",
        minHeight: "100vh",
        padding: "20px",
        overflowX: "hidden",
    };

    const contentStyle = {
        transition: "margin-left 0.5s",
        marginLeft: sidenavOpen ? 250 : 0,
        width: "100%",
        overflowX: 'hidden'
    };
    const toggleSidenav = () => {
        setSidenavOpen(!sidenavOpen);
    };
    return (
        <>
            <div style={bodyStyle}>
                <Navbar toggleSidenav={toggleSidenav} />
                <Box height={50} />
                <Box sx={{ display: "flex" }}>
                    <Sidenav open={sidenavOpen} onClose={toggleSidenav} />

                    <Box
                        component="main"
                        sx={{ ...contentStyle }} id='billing-main-container'>
                        <Outlet />

                    </Box>

                </Box>
            </div>
        </>
    )
}

export default Index