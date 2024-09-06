import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import { Header, Footer } from './components/layout';
import AppRoutes from './AppRoutes';  // AppRoutes.js가 src 폴더 직접 아래에 있다고 가정

import 'bootstrap/dist/css/bootstrap.min.css'

function App() {
  return (
    <Router>
        <Header />
        <div className="App min-vh-100">
          <main>
            <AppRoutes />
          </main>
        </div>
        <Footer />
    </Router>
  );
}

export default App;