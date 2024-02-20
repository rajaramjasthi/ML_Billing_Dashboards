import * as React from 'react';
import { useState } from 'react';
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import {
  FormControl,
  Select,
  MenuItem,
  Modal,
  Button,
  Box,
  Typography,
  TextField
} from '@mui/material';
import InputAdornment from '@mui/material/InputAdornment';
import ClearIcon from '@mui/icons-material/Clear';
import dayjs from 'dayjs';


const DurationSelector = ({ months, handleMonthChange, setDateRange, setCalling, calling }) => {
  const [customDate, setCustomDate] = useState(false);
  const [fromDate, setFromDate] = useState(null);
  const [toDate, setToDate] = useState(null);
  const [openModal, setOpenModal] = useState(false);
  const [onClearBtn, setOnClearBtn] = useState(false);
  const [dateErrors, setDateErrors] = useState({
    fromDateError: '',
    toDateError: ''
  })

  const monthsData = [
    {
      id: 1,
      month: 1,
      name: 'This Month',
    },
    {
      id: 2,
      month: 3,
      name: 'Last 3 Months',
    },
    {
      id: 3,
      month: 6,
      name: 'Last 6 Months',
    },
    {
      id: 4,
      month: 12,
      name: 'Last 1 Year',
    },
    {
      id: 5,
      month: 0,
      name: 'Custom date range >',
    },
  ];

  const handleMonthSelection = (selectedMonth) => {
    if (selectedMonth === 0) {
      setOpenModal(true);
      handleMonthChange(selectedMonth);
      setCustomDate(true);
    } else {
      handleMonthChange(selectedMonth);
      setCustomDate(false);
      setFromDate(null);
      setToDate(null);
      setDateRange({ startDate: null, endDate: null });
      setOpenModal(false); // Close modal for predefined date ranges
    }
  };

  const handleFromDateChange = (date) => {
    // const newDate = date?.format('YYYY-MM-DD');
    setFromDate(date);
    setDateErrors({ ...dateErrors, fromDateError: '', })
  };

  const handleToDateChange = (date) => {
    // const newDate = date?.format('YYYY-MM-DD');
    setToDate(date);
    setDateErrors({ ...dateErrors, toDateError: '', })

  };

  const handleCustomDateSelection = () => {
    if (fromDate && toDate) {
      setDateRange({ startDate: fromDate?.format('YYYY-MM-DD'), endDate: toDate?.format('YYYY-MM-DD') });
      setCustomDate(false);
      setOpenModal(false);
      setCalling(!calling);
      setDateErrors({ fromDateError: '', toDateError: '' })
    } else {
      if (!fromDate && !toDate) {
        setDateErrors({ fromDateError: 'Please select from date', toDateError: 'Please select to date' })
      } else {
        if (!fromDate && toDate) {
          setDateErrors({ fromDateError: 'Please select from date', toDateError: '' })
        } else {
          setDateErrors({ toDateError: 'Please select to date', fromDateError: '' })
        }
      }

    };
  }
  const handleClearMonthValue = () => {
    handleMonthChange(3);
    setCustomDate(false);
    setFromDate(null);
    setToDate(null);
    setDateRange({ startDate: null, endDate: null });
    setOpenModal(false); // Close modal for predefined date ranges

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
 const date = dayjs().startOf('day').format('YYYY-MM-DD')

  return (
    <React.Fragment>
      {/* <div className="marginx" style={{ width: '100%' }}> */}
      <FormControl sx={{ ...newPropsCss }} fullWidth>
        <Select
          labelId="demo-simple-select-label"
          id="demo-simple-select"
          className='demo-simple-select'
          value={months}
          fullWidth
          onChange={(event) => handleMonthSelection(event.target.value)}
          sx={{
            ...newPropsCss,
            height: '2.4em',
            backgroundColor: '#FFFF',
            // width: 200,
            textAlign: 'center',
          }}
          endAdornment={
            <InputAdornment position="end" sx={{
              '&:hover': {
                cursor: 'pointer',
              }
            }}
              onClick={() => handleClearMonthValue()}
            >
              {(onClearBtn && months !== 3) && <ClearIcon sx={{
                marginRight: '12px', fontSize: '19px', '&:hover': {
                  cursor: 'pointer',
                  backgroundColor: '#F0F0F0',
                  borderRadius: '100%',
                  padding: '4px',
                  fontSize: '24px',

                }
              }} />}
            </InputAdornment>
          }
          onMouseOver={() => setOnClearBtn(true)}
          onMouseLeave={() => setOnClearBtn(false)}
          onMouseOut={() => setOnClearBtn(false)}
        >
          {monthsData.map((item) => (
            <MenuItem
              key={item.id}
              value={item.month}
              sx={{
                backgroundColor: '#FFFF',
                ':hover': {
                  backgroundColor: '#FFFF',
                  color: 'black',
                },
                '&.Mui-selected': {
                  backgroundColor: '#FFFF !important',
                  color: 'black',
                },
              }}
            >
              {item.name}
            </MenuItem>
          ))}

        </Select>
      </FormControl>
      <Modal open={openModal && customDate} onClose={() => {
        setOpenModal(false)
        handleMonthChange(3);
        setCustomDate(false);
        setFromDate(null);
        setToDate(null);
        setDateRange({ startDate: null, endDate: null });
        setOpenModal(false);
      }} >
        <Box
          sx={{
            position: 'absolute',
            top: '50%',
            left: '50%',
            transform: 'translate(-50%, -50%)',
            bgcolor: 'white',
            p: 4,
            zIndex: 9999,
            borderRadius: '10px'
          }}
        >
          <Typography variant="h6" gutterBottom>
            Select Custom Dates
          </Typography>
          <LocalizationProvider dateAdapter={AdapterDayjs} >
            <DemoContainer components={['DatePicker']} >
              <div className='d-flex align-items-center py-3'>
                <div className='p-3 position-relative'>
                  <DatePicker
                    label="From Date"
                    value={fromDate}
                    onChange={handleFromDateChange}
                    format="YYYY-MM-DD"
                    maxDate={dayjs(date)}
                    renderInput={(params) => (
                      <>
                        <TextField {...params} />
                      </>
                    )}
                  />
                  {dateErrors.fromDateError && (
                    <Typography color="error" sx={{ fontSize: '12px' }} className='m-1 position-absolute'>
                      {dateErrors.fromDateError}
                    </Typography>
                  )}
                </div>
                <div className='p-3 position-relative'>
                  <DatePicker
                    label="To Date"
                    value={toDate}
                    format="YYYY-MM-DD"
                    onChange={handleToDateChange}
                    renderInput={(params) => (
                      <>
                        <TextField {...params} />
                      </>
                    )}
                    minDate={fromDate !== null ? dayjs(fromDate) : ""}
                    // maxDate={dayjs().isBefore(dayjs(fromDate).add(1, 'year')) ? dayjs().add(1, 'year') : dayjs(fromDate).add(1, 'year')}

                    maxDate={dayjs(fromDate).add(1, 'year')}
                    // maxDate={dayjs().isSameOrBefore(dayjs(fromDate).add(1, 'year')) ? dayjs().add(1, 'year') : dayjs(fromDate).add(1, 'year')}
                    // minDate={ dayjs(fromDate)}
                    disabled={fromDate === null}
                  />
                  {dateErrors.toDateError && (
                    <Typography color="error" sx={{ fontSize: '12px' }} className='m-1 position-absolute'>
                      {dateErrors.toDateError}
                    </Typography>
                  )}
                </div>
                <Button onClick={handleCustomDateSelection} sx={{ maxHeight: '40px' }}>Submit</Button>
              </div>
            </DemoContainer>
          </LocalizationProvider>
        </Box>
      </Modal>
      {/* </div> */}
    </React.Fragment>
  );
};

export default DurationSelector;




// import * as React from 'react';
// import { useState } from 'react';
// import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
// import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
// import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
// import { DatePicker } from '@mui/x-date-pickers/DatePicker';
// import {
//   FormControl,
//   Select,
//   MenuItem,
//   Modal,
//   Button,
//   Box,
//   Typography,
//   TextField
// } from '@mui/material';
// import InputAdornment from '@mui/material/InputAdornment';
// import ClearIcon from '@mui/icons-material/Clear';
// import dayjs from 'dayjs';

// const DurationSelector = ({ months, handleMonthChange, setDateRange, setCalling, calling }) => {
//   const [customDate, setCustomDate] = useState(false);
//   const [fromDate, setFromDate] = useState(null);
//   const [toDate, setToDate] = useState(null);
//   const [openModal, setOpenModal] = useState(false);
//   const [onClearBtn, setOnClearBtn] = useState(false);
//   const [dateErrors, setDateErrors] = useState({
//     fromDateError: '',
//     toDateError: ''
//   })

//   const monthsData = [
//     {
//       id: 1,
//       month: 1,
//       name: 'This Month',
//     },
//     {
//       id: 2,
//       month: 3,
//       name: 'Last 3 Months',
//     },
//     {
//       id: 3,
//       month: 6,
//       name: 'Last 6 Months',
//     },
//     {
//       id: 4,
//       month: 12,
//       name: 'Last 1 Year',
//     },
//     {
//       id: 5,
//       month: 0,
//       name: 'Custom date range >',
//     },
//   ];

//   const handleMonthSelection = (selectedMonth) => {
//     if (selectedMonth === 0) {
//       setOpenModal(true);
//       handleMonthChange(selectedMonth);
//       setCustomDate(true);
//     } else {
//       handleMonthChange(selectedMonth);
//       setCustomDate(false);
//       setFromDate(null);
//       setToDate(null);
//       setDateRange({ startDate: null, endDate: null });
//       setOpenModal(false); // Close modal for predefined date ranges
//     }
//   };

//   const handleFromDateChange = (date) => {
//     setFromDate(date);
//     setDateErrors({ ...dateErrors, fromDateError: '', })
//   };

//   const handleToDateChange = (date) => {
//     setToDate(date);
//     setDateErrors({ ...dateErrors, toDateError: '', })
//   };

//   const handleCustomDateSelection = () => {
//     if (fromDate && toDate) {
//       const durationInDays = toDate.diff(fromDate, 'day');
      
//       if (durationInDays > 364) {
//         console.log(durationInDays)
//         setDateErrors({
//           toDateError: 'Please select the duration within a year',
//           // toDateError: 'Please select a duration within 365 days',
//         });
//       } else {
//         setDateRange({
//           startDate: fromDate?.format('YYYY-MM-DD'),
//           endDate: toDate?.format('YYYY-MM-DD'),
//         });
//         setCustomDate(false);
//         setOpenModal(false);
//         setCalling(!calling);
//         setDateErrors({ fromDateError: '', toDateError: '' });
//       }
//     } else {
//       if (!fromDate && !toDate) {
//         setDateErrors({ fromDateError: 'Please select from date', toDateError: 'Please select to date' });
//       } else {
//         if (!fromDate && toDate) {
//           setDateErrors({ fromDateError: 'Please select from date', toDateError: '' });
//         } else {
//           setDateErrors({ toDateError: 'Please select to date', fromDateError: '' });
//         }
//       }
//     }
//   };

//   const handleClearMonthValue = () => {
//     handleMonthChange(3);
//     setCustomDate(false);
//     setFromDate(null);
//     setToDate(null);
//     setDateRange({ startDate: null, endDate: null });
//     setOpenModal(false); // Close modal for predefined date ranges
//   };

//   const newPropsCss = {
//     backgroundColor: "#FFFF",
//     width: "90%",
//     textAlign: "center",
//     ":hover": {
//       backgroundColor: "#FFFF",
//       color: "black",
//     },
//     "&.Mui-selected": {
//       backgroundColor: "#FFFF !important",
//       color: "black",
//     },
//   };

//   return (
//     <React.Fragment>
//       <FormControl sx={{ ...newPropsCss }} fullWidth>
//         <Select
//           labelId="demo-simple-select-label"
//           id="demo-simple-select"
//           className='demo-simple-select'
//           value={months}
//           fullWidth
//           onChange={(event) => handleMonthSelection(event.target.value)}
//           sx={{
//             ...newPropsCss,
//             height: '2.4em',
//             backgroundColor: '#FFFF',
//             textAlign: 'center',
//           }}
//           endAdornment={
//             <InputAdornment position="end" sx={{
//               '&:hover': {
//                 cursor: 'pointer',
//               }
//             }}
//               onClick={() => handleClearMonthValue()}
//             >
//               {(onClearBtn && months !== 3) && <ClearIcon sx={{
//                 marginRight: '12px', fontSize: '19px', '&:hover': {
//                   cursor: 'pointer',
//                   backgroundColor: '#F0F0F0',
//                   borderRadius: '100%',
//                   padding: '4px',
//                   fontSize: '24px',

//                 }
//               }} />}
//             </InputAdornment>
//           }
//           onMouseOver={() => setOnClearBtn(true)}
//           onMouseLeave={() => setOnClearBtn(false)}
//           onMouseOut={() => setOnClearBtn(false)}
//         >
//           {monthsData.map((item) => (
//             <MenuItem
//               key={item.id}
//               value={item.month}
//               sx={{
//                 backgroundColor: '#FFFF',
//                 ':hover': {
//                   backgroundColor: '#FFFF',
//                   color: 'black',
//                 },
//                 '&.Mui-selected': {
//                   backgroundColor: '#FFFF !important',
//                   color: 'black',
//                 },
//               }}
//             >
//               {item.name}
//             </MenuItem>
//           ))}
//         </Select>
//       </FormControl>
//       <Modal open={openModal && customDate} onClose={() => {
//         setOpenModal(false);
//         handleMonthChange(3);
//         setCustomDate(false);
//         setFromDate(null);
//         setToDate(null);
//         setDateRange({ startDate: null, endDate: null });
//         setOpenModal(false);
//       }} >
//         <Box
//           sx={{
//             position: 'absolute',
//             top: '50%',
//             left: '50%',
//             transform: 'translate(-50%, -50%)',
//             bgcolor: 'white',
//             p: 4,
//             zIndex: 9999,
//             borderRadius: '10px'
//           }}
//         >
//           <Typography variant="h6" gutterBottom>
//             Select Custom Dates
//           </Typography>
//           <LocalizationProvider dateAdapter={AdapterDayjs} >
//             <DemoContainer components={['DatePicker']} >
//               <div className='d-flex align-items-center py-3'>
//                 <div className='p-3 position-relative'>
//                   <DatePicker
//                     label="From Date"
//                     value={fromDate}
//                     onChange={handleFromDateChange}
//                     format="YYYY-MM-DD"
//                     renderInput={(params) => (
//                       <>
//                         <TextField {...params} />
//                       </>
//                     )}
//                   />
//                   {dateErrors.fromDateError && (
//                     <Typography color="error" sx={{ fontSize: '12px' }} className='m-1 position-absolute'>
//                       {dateErrors.fromDateError}
//                     </Typography>
//                   )}
//                 </div>
//                 <div className='p-3 position-relative'>
//                   <DatePicker
//                     label="To Date"
//                     value={toDate}
//                     format="YYYY-MM-DD"
//                     onChange={handleToDateChange}
//                     renderInput={(params) => (
//                       <>
//                         <TextField {...params} />
//                       </>
//                     )}
//                     minDate={fromDate !== null ? dayjs(fromDate) : ""}
//                     disabled={fromDate === null}
//                   />
//                   {dateErrors.toDateError && (
//                     <Typography color="error" sx={{ fontSize: '12px' }} className='m-1 position-absolute'>
//                       {dateErrors.toDateError}
//                     </Typography>
//                   )}
//                 </div>
//                 <Button onClick={handleCustomDateSelection} sx={{ maxHeight: '40px' }}>Submit</Button>
//               </div>
//             </DemoContainer>
//           </LocalizationProvider>
//         </Box>
//       </Modal>
//     </React.Fragment>
//   );
// };

// export default DurationSelector;
