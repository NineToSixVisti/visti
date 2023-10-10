import React, { useEffect, useState } from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Provider } from 'react-redux';
import {store} from './store';
import routes from './router';
import { createGlobalStyle } from 'styled-components';
// import Loading from './components/Common/Loading';
// import { authInstance } from './apis/utils/instance';

const GlobalStyle = createGlobalStyle`
  body, html {
    /* overflow: hidden; */
  }
`;

function App() {
  const [loading, setLoading] = useState(false);

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
      {/* <Loading isLoading={loading}/> */}
    </Provider>
  </>
  );
}

export default App;
