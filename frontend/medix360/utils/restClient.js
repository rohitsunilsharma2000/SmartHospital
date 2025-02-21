// utils/restClient.js
import axios from 'axios';
import qs from 'qs'; // Import qs for query string serialization

const restClient = axios.create({
  baseURL: 'http://localhost:8080', // Change to your API base URL
  headers: {
    'Content-Type': 'application/json',
  },
});

export const registerDoctor = async (doctorData, queryParams) => {
  try {
    // Manually serialize query parameters using qs with the "repeat" format.
    const queryString = qs.stringify(queryParams, { arrayFormat: 'repeat' });
    // Construct the URL and trim to remove any extra spaces if present
    const url = `/api/doctors/register?${queryString}`.trim();

    const response = await restClient.post(url, doctorData, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return response.data;
  } catch (error) {
    console.error('Error registering doctor:', error);
    throw error;
  }
};


export const registerPatient = async (payload) => {
  try {
    const response = await restClient.post('/api/patients', payload);
    return response.data; // Return the response data if the request is successful
  } catch (error) {
    console.error('Error creating patients:', error);
    throw error; // Re-throw the error to be handled by the caller
  }
};



export default restClient;
