import React, { useEffect, useState } from 'react';
import axios from 'axios';

const MyPage = () => {
  const [memberInfo, setMemberInfo] = useState({
    username: '',
    nickname: '',
    email: '',
    rank: '',
    expPoints: 0,
  });

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

  return (
    <div>
      <h1>My Page</h1>
      <p><strong>Username:</strong> {memberInfo.username}</p>
      <p><strong>Nickname:</strong> {memberInfo.nickname}</p>
      <p><strong>Email:</strong> {memberInfo.email}</p>
      <p><strong>Rank:</strong> {memberInfo.rank}</p>
      <p><strong>Experience Points:</strong> {memberInfo.expPoints}</p>
    </div>
  );
};

export default MyPage;
