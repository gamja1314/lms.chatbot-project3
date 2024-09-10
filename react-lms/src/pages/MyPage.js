import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const MyPage = () => {
  const [memberInfo, setMemberInfo] = useState({
    username: '',
    nickname: '',
    email: '',
    rank: '',
    expPoints: 0,
  });

  const navigate = useNavigate();

  useEffect(() => {
    // API 호출하여 사용자 정보 가져오기
    axios
      .get('/api/member/mypage')
      .then((response) => {
        setMemberInfo(response.data);
      })
      .catch((error) => {
        console.error("Error fetching member data:", error);
      });
  }, []);

  const handleChangePasswordClick = () => {
    navigate('/change-password'); // 비밀번호 변경 페이지로 이동
  };

  return (
    <div className='container-fluid'>
      <div className='container'>
        <h1>My Page</h1>
        <p><strong>아이디:</strong> {memberInfo.username}</p>
        <p><strong>닉네임:</strong> {memberInfo.nickname}</p>
        <p><strong>Email:</strong> {memberInfo.email}</p>
        <p><strong>등급:</strong> {memberInfo.rank}</p>
        <p><strong>경험치:</strong> {memberInfo.expPoints}</p>
        <button onClick={handleChangePasswordClick} className="btn btn-primary">비밀번호 변경</button> {/* 비밀번호 변경 버튼 */}
      </div>
    </div>
  );
};

export default MyPage;
