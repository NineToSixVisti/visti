import axios, { AxiosInstance, AxiosRequestConfig } from 'axios';

// window 객체 타입 확장
interface MyWindow extends Window {
  Android?: {
    getToken: () => string;
  };
}

declare var window: MyWindow;

const baseAPI = (url: string, options?: AxiosRequestConfig): AxiosInstance => {
  return axios.create({ baseURL: url, ...options });
}

const authAPI = (url: string, options?: AxiosRequestConfig): AxiosInstance => {
  let token: string | undefined;

  // Android에서 제공하는 getToken 함수를 사용하여 토큰을 가져옵니다.
  if (window.Android && typeof window.Android.getToken === 'function') {
    token = window.Android.getToken();
  }

  // 토큰이 없으면 기본 값을 사용합니다. (옵셔널)
  if (!token) {
    token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBQ0NFU1MiLCJhdXRoIjoiUk9MRV9BRE1JTiIsInVzZXJfZW1haWwiOiJzc29sbGlkYTk0QGdtYWlsLmNvbSIsImV4cCI6MTY5NTgwMzQ5N30.upKLXMrDp68j-yHkUloL3kSew498aO33Zs2X2ED92EAlV2bKpakDHCLBqy9SA8R1xryH6z1obLmwH6aGkej7bA";;
  }
  
  return axios.create({
    baseURL: url,
    headers: {
      "Access_Token": token,
      ...(options?.headers), 
    },
    ...options,
  });
}

export const baseInstance: AxiosInstance = baseAPI('http://j9d102.p.ssafy.io:8080/api/');
export const authInstance: AxiosInstance = authAPI('http://j9d102.p.ssafy.io:8080/api/');
