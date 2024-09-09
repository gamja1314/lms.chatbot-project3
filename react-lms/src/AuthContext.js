// AuthContext.js
import React, { createContext, useState, useContext, useEffect } from 'react';
import api from "./components/Api";

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [username, setUsername] = useState('');
  const [nickname, setNickname] = useState('');

  useEffect(() => {
    checkLoginStatus();
  }, []);

  const checkLoginStatus = async () => {
    try {
      const response = await api.get('/api/member/check');
      if (response.status === 200 && response.data.loggedIn && response.data.username !== 'anonymousUser') {
        setIsLoggedIn(true);
        setUsername(response.data.username);
        setNickname(response.data.nickname);
      } else {
        setIsLoggedIn(false);
        setUsername('');
        setNickname('');
      }
    } catch (error) {
      console.error('로그인 상태 확인 오류 : ', error);
      setIsLoggedIn(false);
      setUsername('');
      setNickname('');
    }
  };

  const login = (userData) => {
    setIsLoggedIn(true);
    setUsername(userData.username);
    setNickname(userData.nickname);
    console.log('로그인 성공:', userData); // 디버깅용
  };

  const logout = async () => {
    try {
      const response = await fetch('/api/member/logout', {
        method: 'POST',
        credentials: 'include'
      });

      if (response.ok) {
        setIsLoggedIn(false);
        setUsername('');
        setNickname('');
      } else {
        console.error('로그아웃 실패');
      }
    } catch (error) {
      console.error('로그아웃 에러', error);
    }
  };

  return (
    <AuthContext.Provider value={{ isLoggedIn, username, nickname, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);