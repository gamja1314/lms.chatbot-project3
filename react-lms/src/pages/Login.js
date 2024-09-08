import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Login = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleSignup = () => {
    navigate('/signup');
  };

  const handleLogin = async (e) => {
    e.preventDefault();


    const response = await fetch('/member/login', {

    const formData = new URLSearchParams();
    formData.append('username', username);
    formData.append('password', password);

    try {
      const response = await fetch('http://localhost:8282/api/member/login', {

        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: formData,
        credentials: 'include', // 세션 관리용 쿠키를 포함
      });
      
      if (response.ok) {
        const data = await response.json();
        alert('Login 성공!');
        console.log('로그인한 사용자:', data.username);
        navigate('/');

        console.log("로그인 한 유저"+username);
    } else{
        setErrorMessage('Login 실패!');

      } else {
        const errorData = await response.json();
        setErrorMessage(errorData.error || '아이디 또는 비밀번호가 일치하지 않습니다.');
      }
    } catch (error) {
      console.error('Login error:', error);
      setErrorMessage('로그인 중 오류가 발생했습니다. 나중에 다시 시도해주세요.');

    }
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
              <form onSubmit={handleLogin}>
                <div className="mb-3">
                  <input
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    className="form-control"
                    placeholder="아이디를 입력해 주세요"
                    required
                  />
                </div>
                <div className="mb-3">
                  <div className="input-group">
                    <input
                      type="password"
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                      className="form-control"
                      placeholder="비밀번호를 입력해 주세요"
                      required
                    />
                  </div>
                </div>
                <div className="d-grid">
                  <button type="submit" className="btn btn-secondary">로그인하기</button>
                </div>
              </form>
              {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
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