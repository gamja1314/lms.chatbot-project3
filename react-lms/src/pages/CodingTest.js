import React, { useState, useEffect } from 'react';
import { Form, Table, Pagination } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const CodingTest = () => {
  const [problems, setProblems] = useState([]);
  const [filteredProblems, setFilteredProblems] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [difficulty, setDifficulty] = useState('');
  const [language, setLanguage] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const problemsPerPage = 10;
  const navigate = useNavigate();

  useEffect(() => {
    // 실제 구현에서는 이 부분을 백엔드 API 호출로 대체합니다
    const mockProblems = [
      { id: 1, title: '멀종위기의 대장균 찾기', difficulty: 'Lv. 5', solvedBy: 1148, correctRate: '13%' },
      { id: 2, title: '특정 세대의 대장균 찾기', difficulty: 'Lv. 4', solvedBy: 1826, correctRate: '53%' },
    ];
    setProblems(mockProblems);
    setFilteredProblems(mockProblems);
  }, []);

  useEffect(() => {
    const results = problems.filter(problem =>
      problem.title.toLowerCase().includes(searchTerm.toLowerCase()) &&
      (difficulty === '' || problem.difficulty === difficulty) &&
      (language === '' || problem.language === language)
    );
    setFilteredProblems(results);
    setCurrentPage(1);
  }, [searchTerm, difficulty, language, problems]);

  const indexOfLastProblem = currentPage * problemsPerPage;
  const indexOfFirstProblem = indexOfLastProblem - problemsPerPage;
  const currentProblems = filteredProblems.slice(indexOfFirstProblem, indexOfLastProblem);

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  const handleProblemClick = (problemId) => {
    navigate(`/coding-page/${problemId}`);
  };

  return (
    <div className='container mt-4'>
      <h1 className="mb-4">Coding Test</h1>
      
      <Form className="mb-4">
        <Form.Group className="mb-3">
          <Form.Control 
            type="text" 
            placeholder="풀고 싶은 문제 제목, 기출문제 검색" 
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </Form.Group>
        
        <div className="d-flex">
          <Form.Select 
            className="me-2" 
            value={difficulty}
            onChange={(e) => setDifficulty(e.target.value)}
          >
            <option value="">난이도</option>
            <option value="Lv. 1">Lv. 1</option>
            <option value="Lv. 2">Lv. 2</option>
            <option value="Lv. 3">Lv. 3</option>
            <option value="Lv. 4">Lv. 4</option>
            <option value="Lv. 5">Lv. 5</option>
          </Form.Select>
          
          <Form.Select 
            className="me-2"
            value={language}
            onChange={(e) => setLanguage(e.target.value)}
          >
            <option value="">언어</option>
            <option value="Python">Python</option>
            <option value="Java">Java</option>
            <option value="JavaScript">JavaScript</option>
          </Form.Select>
          
          <Form.Select>
            <option>기출문제 모음</option>
          </Form.Select>
        </div>
      </Form>

      <div className="mb-3">
        <span>{filteredProblems.length} 문제</span>
        <span className="float-end">최신순 ▼</span>
      </div>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>상태</th>
            <th>제목</th>
            <th>난이도</th>
            <th>완료한 사람</th>
            <th>정답률</th>
          </tr>
        </thead>
        <tbody>
          {currentProblems.map((problem) => (
            <tr key={problem.id} onClick={() => handleProblemClick(problem.id)} style={{cursor: 'pointer'}}>
              <td></td>
              <td>{problem.title}</td>
              <td>{problem.difficulty}</td>
              <td>{problem.solvedBy}명</td>
              <td>{problem.correctRate}</td>
            </tr>
          ))}
        </tbody>
      </Table>

      <Pagination>
        {[...Array(Math.ceil(filteredProblems.length / problemsPerPage)).keys()].map(number => (
          <Pagination.Item key={number + 1} active={number + 1 === currentPage} onClick={() => paginate(number + 1)}>
            {number + 1}
          </Pagination.Item>
        ))}
      </Pagination>
    </div>
  );
};

export default CodingTest;