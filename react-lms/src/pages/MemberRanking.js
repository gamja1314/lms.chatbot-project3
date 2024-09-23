import React, { useState, useEffect, useCallback } from 'react';
import { Table } from 'react-bootstrap';
import axios from 'axios';

const MemberRanking = () => {
  const [rankings, setRankings] = useState([]);  // 전체 랭킹 리스트
  const [userRanking, setUserRanking] = useState(null);  // 로그인한 사용자의 랭킹
  const [isLoading, setIsLoading] = useState(true);  // 로딩 상태

  // 랭킹 데이터 가져오기
  const fetchRankings = useCallback(async () => {
    try {
      const response = await axios.get('/api/member-ranking');
      setRankings(response.data.ranking);  // 전체 멤버 랭킹
      setUserRanking(response.data.userRanking);  // 로그인한 사용자 랭킹
      setIsLoading(false);  // 로딩 완료
    } catch (error) {
      console.error('Error fetching member rankings:', error);
      setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchRankings();
  }, [fetchRankings]);

  // 로딩 중일 때 표시
  if (isLoading) {
    return <div>Loading...</div>;
  }

  return (
    <div className="container mt-4">
      <h1 className="mb-4 text-color">Member Ranking</h1>

      {userRanking && (
        <div className="mb-4">
          <h2 className='text-white'>나의 랭킹</h2>
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

      <h2 className='text-white'>전체 랭킹</h2>
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
            <tr key={index}>
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
