import axios, { AxiosInstance, AxiosRequestConfig } from 'axios';

const baseAPI = (url: string, options?: AxiosRequestConfig): AxiosInstance => {
  return axios.create({ baseURL: url, ...options });
}

const authAPI = (url: string, options?: AxiosRequestConfig): AxiosInstance => {
  const token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBQ0NFU1MiLCJhdXRoIjoiUk9MRV9BRE1JTiIsInVzZXJfZW1haWwiOiJzc29sbGlkYTk0QGdtYWlsLmNvbSIsImV4cCI6MTY5NTYwNDc1M30.3GWi5uuI8N_3HBMm1xgWyAYT5rPnYEDu2fyUvMXD0sAmzOIuMp2lSlZ1u7oIKdrHSCFSrVhYQdIKeVVStZ0Uyg";
  return axios.create({
    baseURL: url,
    headers: {
      "Access_Token":token,
      ...(options?.headers), 
    },
    ...options,
  });
}

export const baseInstance: AxiosInstance = baseAPI('http://j9d102.p.ssafy.io:8080/');
export const authInstance: AxiosInstance = authAPI('http://j9d102.p.ssafy.io/8080/');
