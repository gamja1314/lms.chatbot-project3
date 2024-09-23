import React, { useState, useEffect, useCallback } from 'react';
import { Table } from 'react-bootstrap';
import axios from 'axios';
import '../css/memberRanking.css';

const MemberRanking = () => {
  const [rankings, setRankings] = useState([]);  // 전체 랭킹 리스트
  const [userRanking, setUserRanking] = useState(null);  // 로그인한 사용자의 랭킹
  const [searchTerm, setSearchTerm] = useState('');  // 검색어 (등수 또는 닉네임)
  const [searchType, setSearchType] = useState('rank');  // 검색 기준 ('rank' 또는 'nickname')
  const [isLoading, setIsLoading] = useState(true);  // 로딩 상태

  // 서버에서 데이터를 가져오는 함수
  const fetchRankings = useCallback(async () => {
    try {
      const response = await axios.get('/api/member-ranking');  // API 호출
      setRankings(response.data.ranking);  // 전체 멤버 랭킹
      setUserRanking(response.data.userRanking);  // 로그인한 사용자 랭킹
      setIsLoading(false);  // 로딩 완료
    } catch (error) {
      console.error('Error fetching member rankings:', error);
      setIsLoading(false);
    }
  }, []);

  // 컴포넌트 마운트 시 데이터 로드
  useEffect(() => {
    fetchRankings();
  }, [fetchRankings]);

  // 검색 및 포커스 기능
  const focusRanking = () => {
    if (searchType === 'rank') {
      // 등수로 검색
      const rankNumber = parseInt(searchTerm, 10);
      if (rankNumber > 0 && rankNumber <= rankings.length) {
        const element = document.getElementById(`rank-${rankNumber}`);
        if (element) {
          element.scrollIntoView({ behavior: 'smooth', block: 'center' });
          document.querySelectorAll('.focused').forEach(el => el.classList.remove('focused'));
          element.classList.add('focused');
        }
      } else {
        alert('해당 등수를 찾을 수 없습니다.');
      }
    } else if (searchType === 'nickname') {
      // 닉네임으로 검색
      const foundMemberIndex = rankings.findIndex(member => 
        member.nickname.toLowerCase() === searchTerm.toLowerCase()
      );

      if (foundMemberIndex !== -1) {
        const element = document.getElementById(`rank-${foundMemberIndex + 1}`);
        if (element) {
          element.scrollIntoView({ behavior: 'smooth', block: 'center' });
          document.querySelectorAll('.focused').forEach(el => el.classList.remove('focused'));
          element.classList.add('focused');
        }
      } else {
        alert('해당 닉네임을 찾을 수 없습니다.');
      }
    }
  };

  // 로딩 중일 때 표시
  if (isLoading) {
    return <div>Loading...</div>;
  }

  return (
    <div className="container mt-4">
      <h1 className="mb-4 text-color">Member Ranking</h1>

      {/* 검색 기준 선택 옵션 */}
      <div className="mb-4 text-white">
        <label>
          <input 
            type="radio" 
            value="rank" 
            checked={searchType === 'rank'} 
            onChange={() => setSearchType('rank')} 
          /> 
          순위(숫자만 입력)
        </label>
        <label className="ms-3">
          <input 
            type="radio" 
            value="nickname" 
            checked={searchType === 'nickname'} 
            onChange={() => setSearchType('nickname')} 
          /> 
          닉네임
        </label>
      </div>

      {/* 검색 필드 */}
      <div className="mb-4">
        <input
          type="text"
          placeholder={searchType === 'rank' ? '등수를 입력하세요' : '닉네임을 입력하세요'}
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <button onClick={focusRanking}>검색</button>
      </div>

      {/* 로그인한 사용자의 랭킹 표시 */}
      {userRanking && (
        <div className="mb-4">
          <h2 className='text-white text-center' style={{ fontSize: '72px' }}>내 순위</h2>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>순위</th>
                <th>닉네임</th>
                <th>경험치</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{userRanking}</td>
                <td>{rankings[userRanking - 1].nickname}</td>
                <td>{rankings[userRanking - 1].expPoints}</td>
              </tr>
            </tbody>
          </Table>
        </div>
      )}

      {/* 전체 랭킹 표시 */}
      <h2 className='text-white text-center' style={{ fontSize: '40px' }}>전체 랭킹</h2>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>순위</th>
            <th>닉네임</th>
            <th>경험치</th>
          </tr>
        </thead>
        <tbody>
          {rankings.map((member, index) => (
            <tr key={index} id={`rank-${index + 1}`}>
              <td>{index + 1}</td>  {/* 순위 */}
              <td>{member.nickname}</td>
              <td>{member.expPoints}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
};

export default MemberRanking;
