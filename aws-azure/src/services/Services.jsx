import axios from "axios";

const BASE_URL = "http://localhost:8080";

const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  return {
    "Content-Type": "application/json",
    Authorization: `Bearer ${token}`,
  };
};

export const UserSignUpService = async (data) => {
  try {
    console.log(data, 'register');
    const response = await axios.post(`${BASE_URL}/auth/register`, data);
    return response.data;
    //  console.log(data, 'register');
  } catch (error) {
    console.error("User Sign Up Error:", error);
    throw error;
  }
};

export const UserForgotPasswordService = async (data) => {
  try {
    const response = await axios.post(`${BASE_URL}/auth/forgot-password`, data);
    // return response.data;
    console.log(data, 'Forgot Password', response);
  } catch (error) {
    console.error("Forgot Password Error:", error);
    throw error;
  }
};



export const UserLoginService = async (data) => {
  try {
    const response = await axios.post(`${BASE_URL}/auth/token`, data);
    return response.data;
  } catch (error) {
    console.error("User Login Error:", error);
    throw error;
  }
};


export const awsService = async (service, startDate, endDate, months) => {
  try {
    let endpoint = '/aws/billing-details';
    const queryParams = new URLSearchParams();
    queryParams.append('service', service || '');
    queryParams.append('startDate', startDate || '');
    queryParams.append('endDate', endDate || '');
    queryParams.append('months', months || '');

    const queryString = queryParams.toString();

    if (queryString) {
      endpoint += `?${queryString}`;
    }

    const response = await axios.get(`${BASE_URL}${endpoint}`, {
      headers: getAuthHeaders(),
    });

    return response.data;
  } catch (error) {
    console.error("AWS Service Error:", error);
    throw error;
  }
};




export const gcpService = async (serviceDescription, startDate, endDate, months) => {
  try {
    const endpoint = '/gcp/details';
    const queryParams = new URLSearchParams();

    queryParams.append('serviceDescription', serviceDescription || '');
    queryParams.append('startDate', startDate || '');
    queryParams.append('endDate', endDate || '');
    queryParams.append('months', months || '');

    const queryString = queryParams.toString();

    const response = await axios.get(`${BASE_URL}${endpoint}${queryString ? `?${queryString}` : ''}`, {
      headers: getAuthHeaders(),
    });

    return response.data;
  } catch (error) {
    console.error('GCP Service Error:', error);
    throw error;
  }
};


export const azureService = async (ResourseType, startDate, endDate, months, azureSubscriptionValue = '') => {
  try {
    const endpoint = `/azure/details`;
    const queryParams = new URLSearchParams();

    queryParams.append('ResourseType', ResourseType || '');
    queryParams.append('subscriptionName', azureSubscriptionValue || "");
    queryParams.append('startDate', startDate || '');
    queryParams.append('endDate', endDate || '');
    queryParams.append('months', months || '');

    const queryString = queryParams.toString();
    console.log("azureSubscriptiojkvjnjffjfjjfjfjnValue",azureSubscriptionValue)
    console.log(queryString , "queryString")

    const response = await axios.get(`${BASE_URL}${endpoint}${queryString ? `?${queryString}` : ''}`, {
      headers: getAuthHeaders(),
    });

    return response.data;
  } catch (error) {
    console.error('Azure Service Error:', error);
    throw error;
  }
};


