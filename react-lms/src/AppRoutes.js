import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import SignUp from './pages/SignUp';
import CodingTest from './pages/CodingTest';
import QnABoard from './pages/QnABoard';
import CodingPage from './pages/CodingTestPage';
import MyPage from './pages/MyPage';

const AppRoutes = ({username}) => (
  <Routes>
    <Route path="/" element={<Home />} />
    <Route path="/login" element={<Login />} />
    <Route path="/signup" element={<SignUp />} />
    <Route path="/coding-test" element={<CodingTest />} />
    <Route path="/qna" element={<QnABoard />} />
    <Route path='/coding-page/:quizId' element={<CodingPage username={username} />} />
    <Route path="/my-page" element={<MyPage />} />
  </Routes>
);


export default AppRoutes;