import React from "react";
import { Menu } from 'lucide-react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../AuthContext';
import '../css/header.css'

export function Header() {
  const location = useLocation();
  const navigate = useNavigate();
  const { isLoggedIn, nickname, logout } = useAuth();

  const handleLogout = async (e) => {
    e.preventDefault();
    await logout();
    navigate('/login');
  };
  
  return (
    <header className="navbar navbar-light topbar">
      <div className="container">
        <h1><Link to="/" className="text-dark text-decoration-none">Cotemon</Link></h1>
        <div className="header-flex">
          <div>
            <Link to="/coding-test" className="text-dark text-decoration-none m-4">Coding Test</Link>
            <Link to="/qna" className="text-dark text-decoration-none">Q&A</Link>
          </div>
          <div>
            {location.pathname !== '/login' && (
              isLoggedIn ? (
                <div>
                  <Link to="/logout" className="text-dark text-decoration-none m-4" onClick={handleLogout}>로그아웃</Link>
                  <Link to="/my-page" className="text-dark text-decoration-none m-4">{nickname} 님</Link>
                </div>
                
              ) : (
                <div>
                  <Link to="/login" className="text-dark text-decoration-none m-4">로그인</Link>
                  <Link to="/signup" className="text-dark text-decoration-none">회원가입</Link>
                </div>
              )
            )}
          </div>
        </div>
      </div>
    </header>
  );
}

export function Footer() {
  return (
    <footer className="sticky-footer navbar-gray">
    <div className="container mx-auto flex justify-between items-center">
      <span>Copyright @ programmers</span>
    </div>
  </footer>
  );
}

export function CodingHeader() {
  return (
    <header className="navbar navbar-light topbar">
      <div className="container mx-auto flex justify-between items-center">
        <h1>My Website</h1>
        <button className="md:hidden">
          <Menu size={24} />
        </button>
      </div>
    </header>
  );
}