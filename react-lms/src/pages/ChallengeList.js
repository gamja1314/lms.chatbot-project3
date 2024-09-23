import React, { useState, useEffect, useCallback } from "react";
import { Form, Table, Spinner, Alert } from "react-bootstrap";
import { useNavigate, useLocation } from "react-router-dom";
import axios from "axios";
import CustomPagination from '../components/CustomPagination';

const ChallengeList = () => {
    const navigate = useNavigate();
    const location = useLocation();

    const getInitialPage = () => {
        const searchParams = new URLSearchParams(location.search);
        return parseInt(searchParams.get('page') || '1', 10);
    }

    const [challenges, setChallenges] = useState([]);
    const [totalPages, setTotalPages] = useState(0);
    const [searchTerm, setSearchTerm] = useState('');
    const [difficulty, setDifficulty] = useState('');
    const [currentPage, setCurrentPage] = useState(getInitialPage);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchChallenges = useCallback(async () => {
        setLoading(true);
        setError(null);
        try {
            const response = await axios.get('/api/challenges', {
                params: {
                    page: currentPage - 1,
                    search: searchTerm,
                    difficulty: difficulty
                }
            });
            setChallenges(response.data.content);
            setTotalPages(response.data.totalPages);
        } catch (error) {
            console.error('데이터를 불러오는데 실패했습니다.', error);
            setError('챌린지 목록을 불러오는데 실패했습니다. 다시 시도해 주세요.');
        } finally {
            setLoading(false);
        }
    }, [currentPage, searchTerm, difficulty]);

    useEffect(() => {
        fetchChallenges();
    }, [fetchChallenges]);

    useEffect(() => {
        navigate(`?page=${currentPage}${searchTerm ? `&search=${searchTerm}` : ''}${difficulty ? `&difficulty=${difficulty}` : ''}`, { replace: true });
    }, [currentPage, searchTerm, difficulty, navigate]);
        
    const handleChallengeClick = (challengeId) => {
        navigate(`/challenges/${challengeId}`);
    };
        
    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    const handleSearchChange = (e) => {
        setSearchTerm(e.target.value);
        setCurrentPage(1);
    };

    const handleDifficultyChange = (e) => {
        setDifficulty(e.target.value);
        setCurrentPage(1);
    };
    
    if (loading) {
        return (
            <div className="d-flex justify-content-center align-items-center" style={{height: '100vh'}}>
                <Spinner animation="border" role="status">
                    <span className="visually-hidden">로딩 중...</span>
                </Spinner>
            </div>
        );
    }

    if (error) {
        return <Alert variant="danger">{error}</Alert>;
    }

    return (
        <div className='container mt-4'>
            <h1 className="mb-4 text-color">챌린지</h1>
            
            <Form className="mb-4">
                <Form.Group className="mb-3">
                    <Form.Control 
                        type="text" 
                        placeholder="챌린지 검색" 
                        value={searchTerm}
                        onChange={handleSearchChange}
                    />
                </Form.Group>
            
            </Form>
    
            <div className="mb-3">
                <span className="text-color">{challenges.length} 챌린지</span>
            </div>
    
            {challenges.length > 0 ? (
                <Table striped bordered hover>
                    <thead>
                        <tr>
                            <th>제목</th>
                        </tr>
                    </thead>
                    <tbody>
                        {challenges.map((challenge) => (
                            <tr key={challenge.id} onClick={() => handleChallengeClick(challenge.id)} style={{cursor: 'pointer'}}>
                                <td>{challenge.title}</td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            ) : (
                <Alert variant="info">검색 결과가 없습니다.</Alert>
            )}
    
            <CustomPagination
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={handlePageChange}
            />
        </div>
    );
}

export default ChallengeList;