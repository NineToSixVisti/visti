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
    token = "";
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

const SERVER_URL = process.env.REACT_APP_SERVER


export const baseInstance: AxiosInstance = baseAPI(`${SERVER_URL}/api/`);
export const authInstance: AxiosInstance = authAPI(`${SERVER_URL}/api/`);

