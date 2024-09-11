import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../css/Home.css'; // 별도의 CSS 파일을 만들어 스타일을 관리합니다.
import axios from 'axios';
import cmtImage from '../images/ctmW2.png';

const RankingSection = ({ rankings }) => (
  <div className="card ranking-card">
    <h2>랭킹</h2>
    <table>
      <thead>
        <tr>
          <th>순위</th>
          <th>사용자</th>
          <th>점수</th>
        </tr>
      </thead>
      <tbody>
        {rankings.map((rank, index) => (
          <tr key={index}>
            <td>{index+1}</td>
            <td>{rank.nickname}</td>
            <td>{rank.expPoints}</td>
          </tr>
        ))}
      </tbody>
    </table>
  </div>
);

const ChallengesSection = ({ challenges }) => (
  <div className="card challenge-card">
    <h2>도전과제 및 대회</h2>
    <ul>
      {challenges.map((challenge, index) => (
        <li key={index}>
          <Link to={`/challenges/${challenge.id}`} className="challenge-title text-decoration-none text-dark">{challenge.title}</Link>
        </li>
      ))}
    </ul>
  </div>
);

const NoticeSection = ({ notices }) => (
  <div className="card notice-card">
    <h2>공지사항</h2>
    <ul>
      {notices.map((notice, index) => (
        <li className='d-flex justify-content-between' key={index}>
          <Link className='text-decoration-none text-black' to={`/notice/${notice.noticeId}`}>{notice.title}</Link><span>{formatDate(notice.createDate)}</span>
        </li>
      ))}
    </ul>
  </div>
);

const PopularProblemsSection = ({ problems }) => (
  <div className="card problem-card">
    <h2>인기문제(TOP5)</h2>
    {Array.isArray(problems) && problems.length > 0 ? (
      <ul>
        {problems.map((problem, index) => (
          <li key={index}>
            <Link to={`/coding-page/${problem.quizId}`}>{problem.title}</Link>
            <span className="problem-difficulty">난이도: {problem.quizRank}</span>
          </li>
        ))}
      </ul>
    ) : (
      <p>인기 문제가 없습니다.</p>
    )}
  </div>
);

const formatDate = (dateString) => {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  
  return `${year}-${month}-${day} ${hours}:${minutes}`;
};

const Home = () => {
  const [rankings, setRankings] = useState([]);
  const [challenges, setChallenges] = useState([]);
  const [notices, setNotices] = useState([]);
  const [problems, setProblems] = useState([]);



  useEffect(() => {
    const fetchPopularProblems = async () => {
      try {
        const response = await axios.get('/api/quiz/counts');
        setProblems(response.data);
        const expResponse = await axios.get('/api/member/exp-top');
        setRankings(expResponse.data);
        const challengeResponse = await axios.get('/api/challenges/top5');
        setChallenges(challengeResponse.data);
        const noticeResponse = await axios.get('/api/notice');
        setNotices(noticeResponse.data);
      } catch (error) {
        console.error('데이터를 불러오는데 실패하였습니다. :', error);
      }
    };

    fetchPopularProblems();
    
  }, []);

  return (
    <div>
      <div className="ctm-section">
        <img src={cmtImage} alt="ctm" className="ctm-image"/>
      </div>
      <div className="sub-title text-center my-4">
        <h1>코딩테스트몬스터</h1>
        <p>당신의 코딩 실력을 극대화하세요!</p>
      </div>
      <main>
        <div className="grid-container">
          <RankingSection rankings={rankings} />
          <ChallengesSection challenges={challenges} />
          <NoticeSection notices={notices} />
          <PopularProblemsSection problems={problems} />
        </div>
      </main>
    </div>
  );
};

export default Home;