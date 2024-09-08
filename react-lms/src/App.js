import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import { Header, Footer } from './components/layout';
import AppRoutes from './AppRoutes';
import api from "./components/Api";

import 'bootstrap/dist/css/bootstrap.min.css'

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [username, setUsername] = useState('');

  useEffect(() => {
    const checkLoginStatus = async () => {
      try {
        const response = await api.get('/api/member/check');
        if (response.status === 200 && response.data.loggedIn) {
          setIsLoggedIn(true);
          setUsername(response.data.username);
        } else {
          setIsLoggedIn(false);
        }
      } catch (error) {
        console.error('로그인 상태 확인 오류 : ', error);
        setIsLoggedIn(false);
      }
    };

    checkLoginStatus();
  }, []);
  
  return (
    <Router>
      <Header isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} username={username} />
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