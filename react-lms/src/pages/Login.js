import React from 'react';
import { useNavigate } from 'react-router-dom';

const Login = () => {
  const navigate = useNavigate();

  const handleSignup = () => {
    navigate('/signup');
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card">
            <div className="card-header text-center">
              <h2 className="mb-0">로그인</h2>
            </div>
            <div className="card-body">
              <form action='localhost:8282/member/login' method='POST'>
                <div className="mb-3">
                  <input type="email" className="form-control" placeholder="이메일을 입력해 주세요"
                  />
                </div>
                <div className="mb-3">
                  <div className="input-group">
                    <input type="password" className="form-control" placeholder="비밀번호를 입력해 주세요"/>
                  </div>
                </div>
                <div className="d-grid">
                  <button type="submit" className="btn btn-secondary">로그인하기</button>
                </div>
              </form>
              <div className="mt-3 text-center">
                <button onClick={handleSignup} className="btn btn-link">이메일 회원가입</button>
              </div>
              <div className="mt-2 text-end">
                <button className="btn btn-link text-muted small p-0">비밀번호 재설정</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;