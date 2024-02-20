import * as React from "react";
import { DataGrid, GridToolbar } from "@mui/x-data-grid";
import { Box } from "@mui/material";

export default function GcpTable({ data, months, serviceDescription, fromDate, toDate }) {
  let rows = [];

  if (
    Array.isArray(data) &&
    months !== 0 &&
    serviceDescription !== 0 &&
    fromDate !== 0 &&
    toDate !== 0
  ) {
    rows = data.map((detail) => ({
      id: detail.id,
      date: detail.date,
      serviceId: detail.serviceId,
      serviceDescription: detail.serviceDescription,
      cost: detail.cost,

    }));
  }

  const columns = [
    {
      field: "id",
      headerName: "Id",
      // width: 300,
      minWidth: 250,
      flex: 1

    },
    {
      field: "date",
      headerName: "Date",
      // width: 200,
      minWidth: 180,
      flex: 1,
      valueGetter: (params) => {
        const date = new Date(params.row.date);
        return date.toISOString().split('T')[0];
      },
    },
    {
      field: "serviceId",
      headerName: "Service ID",
      // width: 250,
      minWidth: 200,
      flex: 1

    },
    {
      field: "serviceDescription",
      headerName: "Service Description",
      // width: 400,
      minWidth: 250,
      flex: 1

    },
    {
      field: "cost",
      headerName: "Cost",
      // width: 110,
      minWidth: 100,
      flex: 1
    }
  ];

  return (
    <Box sx={{ display: "flex", flexDirection: "column" }}>
      <div style={{ marginBottom: "20px" }}></div>
      <div style={{ flex: 1, height: "100%", width: "100% !important" }}>
        {rows.length > 0 ? (
          <DataGrid
            rows={rows}
            columns={columns}
            initialState={{
              pagination: {
                paginationModel: { page: 0, pageSize: 10 },
              },
            }}
            pageSizeOptions={[10, 25, 50]}
            disableSelectionOnClick
            slots={{ toolbar: GridToolbar }}
            experimentalFeatures={{ ariaV7: true }}
          // sx={{
          //   "&.MuiDataGrid-withBorderColor":{
          //     backgroundColor:"gray"
          //   }
          // }}
          />
        ) : (
          <div style={{ textAlign: "center", padding: "20px" }}>
            No data available
          </div>
        )}
      </div>
    </Box>
  );
}