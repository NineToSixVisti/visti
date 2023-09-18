import React from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Provider } from 'react-redux';
import {store} from './store';
import routes from './router';
import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
  body, html {
    overflow: hidden;
  }
`;

function App() {
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
    </Provider>
  </>
  );
}

export default App;
