import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../css/Home.css'; // 별도의 CSS 파일을 만들어 스타일을 관리합니다.
import axios from 'axios';

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
    <h2>도전 과제 및 대회</h2>
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
    <h2>공지사항 및 업데이트</h2>
    <ul>
      {notices.map((notice, index) => (
        <li key={index}>
          <span className="notice-title">{notice.title}</span>
          <span className="notice-date">{notice.date}</span>
        </li>
      ))}
    </ul>
  </div>
);

const PopularProblemsSection = ({ problems }) => (
  <div className="card problem-card">
    <h2>인기 문제 - TOP 5</h2>
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
      } catch (error) {
        console.error('데이터를 불러오는데 실패하였습니다. :', error);
      }
    };

    fetchPopularProblems();
    
  }, []);

  return (
    <div className="home-container">
      <div className='text-center mb-5'>
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

      <section className="cta-section">
        <h2>새로운 도전을 시작하세요!</h2>
        <div className="cta-buttons">
          <Link to="/login" className="btn btn-primary">로그인</Link>
          <Link to="/signup" className="btn btn-secondary">회원가입</Link>
          <Link to="/tutorial" className="btn btn-tertiary">튜토리얼 시작하기</Link>
        </div>
      </section>
    </div>
  );
};

export default Home;