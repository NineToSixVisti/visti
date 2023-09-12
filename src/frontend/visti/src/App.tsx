import React from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Provider } from 'react-redux';
import {store} from './store/store';
import routes from './router';

function App() {
  return (
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
  );
}

export default App;
