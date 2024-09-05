import React from 'react';

const SignUp = () => {
  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card">
            <div className="card-header text-center">
              <h4 className="my-1 text-gray-700">회원가입</h4>
            </div>
            <div className="card-body">
              <form method='POST'>
                <div className="mb-3">
                  <label><p>아이디</p></label>
                  <input type="email" className="form-control" placeholder="이메일을 입력해 주세요"/>
                </div>
                <div className="mb-3">
                    <label><p>비밀번호</p></label>
                    <input type="password" className="form-control mb-1" placeholder="영문자,숫자,특수문자 포함 8~20자"/>
                    <input type="password" className="form-control" placeholder="비밀번호를 확인해 주세요"/>
                </div>
                <div className="mb-3">
                    <label><p>닉네임</p></label>
                    <input type="text" className="form-control" placeholder="사용할 닉네임을 입력해주세요"/>
                </div>
                <div className="d-grid">
                  <button type="submit" className="btn btn-secondary">가입하기</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SignUp;