import React, { useEffect, useState } from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Provider } from 'react-redux';
import {store} from './store';
import routes from './router';
import { createGlobalStyle } from 'styled-components';
import Loading from './components/Common/Loading';
import { authInstance } from './apis/utils/instance';


const GlobalStyle = createGlobalStyle`
  body, html {
    /* overflow: hidden; */
  }
`;

function App() {
  const [loading, setLoading] = useState(false);

  // useEffect(() => {
  //   const requestInterceptor = authInstance.interceptors.request.use(function (req) {
  //     // console.log('인터셉터 시작')
  //     setLoading(true);
  //     return req;
  //   }, function (error) {
  //     return Promise.reject(error);
  //   });
  
  //   const responseInterceptor = authInstance.interceptors.response.use(function (res) {      
  //     // console.log('인터셉터 완료')
  //     setLoading(false);
  //     return res;
  //   }, function (error) {
  //     console.log('인터셉터 에러 ')
  //     setLoading(false);
  //     return Promise.reject(error);
  //   });
  
  //   // 컴포넌트 언마운트 시 인터셉터 제거
  //   return () => {
  //     authInstance.interceptors.request.eject(requestInterceptor);
  //     authInstance.interceptors.response.eject(responseInterceptor);
  //   };
  // }, []);
  

  useEffect(()=>{
    // console.log('loading :', loading);
  },[loading])

  return (    
    <>
    <GlobalStyle />
    <Provider store={store}>
      <div className="App">
        <BrowserRouter>
          <Routes>
            {routes.map((e) => (
              <Route key={e.path} path={e.path} element={<e.Component />} />
              ))}
          </Routes>
        </BrowserRouter>
      </div>
      <Loading isLoading={loading}/>
    </Provider>
  </>
  );
}

export default App;
