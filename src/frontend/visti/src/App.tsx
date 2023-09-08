import React from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { SelectedImageProvider } from './components/Story/SelectImageContext';
import routes from './router';

function App() {
  return (
    <div className="App">
      <SelectedImageProvider>
        <BrowserRouter>
          <Routes>
            {routes.map((e) => (
              <Route key={e.path} path={e.path} element={<e.Component />} />
            ))}
          </Routes>
        </BrowserRouter>
      </SelectedImageProvider>
    </div>
  );
}

export default App;
