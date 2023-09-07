import React from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import routes from './router'

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          { routes.map((e) => {
            return (
              <Route
                key={e.path}
                path={e.path}
                element={<e.Component></e.Component>}/>
            )
          })}
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
