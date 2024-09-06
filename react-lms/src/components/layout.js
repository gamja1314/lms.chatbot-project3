import React from "react";
import { Menu } from 'lucide-react';
import { Link } from 'react-router-dom';
import '../css/header.css'

export function Header() {
  return (
    <header className="navbar navbar-light topbar">
      <div className="container">

          <h1><Link to="/">Cotemon</Link></h1>
          <div className="header-flex">
            <div>
              <Link to="/coding-test">Coding Test</Link>
              <Link to="/qna">Q&A</Link>
            </div>
            <div>
              <Link to="/login">로그인</Link>
              <Link to="/signup">회원가입</Link>
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