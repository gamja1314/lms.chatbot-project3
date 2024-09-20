import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../css/Home.css';
import axios from 'axios';
import cmtImage from '../images/ctmW2.png';

const RankingSection = ({ rankings }) => (
  <div className="card ranking-card">
    <div className='d-flex align-content-center justify-content-between title'>
      <h2 className='mb-0'>랭킹</h2>
      <Link to="/member-ranking" className="text-decoration-none small text-color mb-0">전체보기</Link>
    </div>
    <table>
      <thead>
        <tr>
          <th>순위</th>
          <th>랭크</th>
          <th>사용자</th>
          <th>점수</th>
        </tr>
      </thead>
      <tbody>
        {rankings.map((rank, index) => (
          <tr key={index} className='text-white'>
            <td>{index+1}</td>
            <td>{rank.userRank}</td>
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
    <div className='d-flex align-content-center justify-content-between title'>
      <h2 className='mb-0'>도전과제 및 대회</h2>
      <Link to="/challenges" className="text-decoration-none small text-color mb-0">전체보기</Link>
    </div>
    <ul>
      {challenges.map((challenge, index) => (
        <li key={index}>
          <Link to={`/challenges/${challenge.id}`} className="challenge-title text-decoration-none text-white">{challenge.title}</Link>
        </li>
      ))}
    </ul>
  </div>
);

const PopularProblemsSection = ({ problems }) => (
  <div className="card problem-card">
    <div className='d-flex align-content-center justify-content-between title'>
      <h2 className='mb-0'>인기문제(TOP5)</h2>
    </div>
    {Array.isArray(problems) && problems.length > 0 ? (
      <ul>
        {problems.map((problem, index) => (
          <li key={index} className='d-flex justify-content-between'>
            <Link to={`/coding-page/${problem.quizId}`} className='text-decoration-none text-white'>{problem.title}</Link>
            <span className="problem-difficulty">난이도: {problem.quizRank}</span>
          </li>
        ))}
      </ul>
    ) : (
      <p className='text-color'>인기 문제가 없습니다.</p>
    )}
  </div>
);

const Home = () => {
  const [rankings, setRankings] = useState([]);
  const [challenges, setChallenges] = useState([]);
  const [problems, setProblems] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [problemsRes, rankingsRes, challengesRes] = await Promise.all([
          axios.get('/api/quiz/counts'),
          axios.get('/api/member/exp-top'),
          axios.get('/api/challenges/top5')
        ]);
        setProblems(problemsRes.data);
        setRankings(rankingsRes.data);
        setChallenges(challengesRes.data);
      } catch (error) {
        console.error('데이터를 불러오는데 실패하였습니다. :', error);
      }
    };

    fetchData();
  }, []);

  return (
    <div className='container-fluid home-container'>
      <div className='container'>
        <div className="ctm-section">
          <img src={cmtImage} alt="ctm" className="ctm-image"/>
        </div>
        <div className="sub-title text-center my-4">
          <h1>코딩테스트몬스터</h1>
          <p>당신의 코딩 실력을 극대화하세요!</p>
        </div>
        <main className="home-main">
          <div className="ranking-column">
            <RankingSection rankings={rankings} />
          </div>
          <div className="content-column">
            <ChallengesSection challenges={challenges} />
            <PopularProblemsSection problems={problems} />
          </div>
        </main>
      </div>
    </div>
  );
};

export default Home;