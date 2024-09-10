import React from 'react';
import { Link } from 'react-router-dom';

const Admin = () => {
  
  return (
    <div className='container-fluid'>
      <div className='container'>

        <div className='d-flex flex-column align-items-center'>

          <h2>어드민 페이지</h2>
          <Link to="/admin/quiz">퀴즈 관리</Link>
          <Link to="/admin/challenge">챌린지 관리</Link>
          <Link to="/admin/notice">공지사항 관리</Link>
        </div>

      </div>
    </div>
  );
};

export default Admin;
