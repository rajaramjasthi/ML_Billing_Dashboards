import React, { useState, useEffect } from "react";
import { FormControl, Select, MenuItem } from "@mui/material";
import axios from "axios";

const ServiceSelector = ({ service, handleServiceChange }) => {
  const [serviceOptions, setServiceOptions] = useState([]);
  const [clicked, setClicked] = useState(false);

  useEffect(() => {
    const fetchServiceOptions = async () => {
      try {
        const token = localStorage.getItem("token");
        if (token && !clicked) {
          const config = {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          };

          const response = await axios.get("http://localhost:8080/aws/distinct-services", config);
          setServiceOptions(response.data);
          setClicked(true);
        } else {
          console.error("Token not found in localStorage or options already fetched");
        }
      } catch (error) {
        console.error("Error fetching service options:", error);
      }
    };

    fetchServiceOptions();
  }, [clicked]);

  const handleFocus = () => {
    if (!clicked) {
      setClicked(true);
    }
  };

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
  // console.log("service",service)
  // const defaultValue = service === null ? "" : service;
  // console.log("first",defaultValue)
  return (
    <React.Fragment>
      <FormControl sx={{ ...newPropsCss }} fullWidth>
        <Select
          fullWidth
          labelId="demo-simple-select-label"
          id="demo-simple-select"
          className='demo-simple-select'
          sx={{ ...newPropsCss, height: "2.4em" }}
          value={service}
          // value={defaultValue}
          onChange={handleServiceChange}
          onFocus={handleFocus}
          displayEmpty
          inputProps={{ 'aria-label': 'Without label' }}
          MenuProps={{ PaperProps: { sx: { maxHeight: 200 } } }}
        >
          {/* <MenuItem value="" defaultValue={true} disabled>Select Service</MenuItem> */}
          <MenuItem value=""
          // disabled
          >
            Select Service
          </MenuItem>
          {serviceOptions && serviceOptions?.map((option, index) => (
            <MenuItem key={index} value={option} sx={{ ...newPropsCss }}>
              {option}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    </React.Fragment>
  );
};

export default ServiceSelector;

