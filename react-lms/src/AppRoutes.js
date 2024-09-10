import React, { useMemo } from 'react';
import { Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import SignUp from './pages/SignUp';
import CodingTest from './pages/CodingTest';
import QnABoard from './pages/QnABoard';
import CodingPage from './pages/CodingTestPage';
import MyPage from './pages/MyPage';
import Admin from './pages/Admin/Admin';
import AdminQuiz from './pages/Admin/AdminQuiz';
import QuizForm from './pages/Admin/QuizForm';
import ProtectedRoute from './components/ProtectedRoute';
import { useAuth } from './AuthContext';

const AppRoutes = () => {
  const { username } = useAuth();
  
  const routes = useMemo(() => (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/signup" element={<SignUp />} />
      <Route path="/coding-test" element={<CodingTest />} />
      <Route path="/qna" element={<QnABoard />} />
      <Route path='/coding-page/:quizId' element={<CodingPage username={username} />} />
      <Route path="/my-page" element={<MyPage />} />
      <Route path='/admin' element={<ProtectedRoute><Admin /></ProtectedRoute>} />
      <Route path='/admin/quiz' element={<ProtectedRoute><AdminQuiz /></ProtectedRoute>} />
      <Route path='/admin/quiz/edit' element={<QuizForm />}></Route>
      <Route path='/admin/quiz/edit/:quizId' element={<QuizForm />}></Route>
    </Routes>
  ), [username]);  // username이 변경될 때만 재생성

  return routes;
};

export default AppRoutes;