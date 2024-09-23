import React, { useState, useEffect, useCallback } from 'react';
import { Form, Table, Button } from 'react-bootstrap';
import { useNavigate, useLocation } from 'react-router-dom';
import CustomPagination from '../components/CustomPagination';
import api from '../components/Api';

const BoardList = () => {
    const navigate = useNavigate();
    const location = useLocation();

    const getInitialPage = () => {
        const searchParams = new URLSearchParams(location.search);
        return parseInt(searchParams.get('page') || '1', 10);
    };

    const [boards, setBoards] = useState([]); // 게시글 리스트
    const [totalPages, setTotalPages] = useState(0); // 전체 페이지 수
    const [searchTerm, setSearchTerm] = useState(''); // 검색어
    const [currentPage, setCurrentPage] = useState(getInitialPage); // 현재 페이지 번호

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        
        return `${year}-${month}-${day} ${hours}:${minutes}`;
    };

    // 게시글 목록을 가져오는 함수
    const fetchBoards = useCallback(async () => {
        try {
            const response = await api.get('/api/board/list', {
                params: {
                    page: currentPage - 1
                }
            });
            setBoards(response.data.content);
            setTotalPages(response.data.totalPages);
        } catch (error) {
            console.error('Error fetching boards:', error);
        }
    }, [currentPage]);

    useEffect(() => {
        fetchBoards();
    }, [fetchBoards]);

    useEffect(() => {
        navigate(`?page=${currentPage}`, { replace: true });
    }, [currentPage, navigate]);

    // 게시글 등록 페이지로 이동
    const handleAddBoard = () => {
        navigate('/board/create');
    };


    const handleBoardClick = (boardId) => {
        navigate(`/board/view/${boardId}`);
    };

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    return (
        <div className='container mt-4'>
            <h1 className="mb-4 text-color">게시글 목록</h1>

            <Button variant="primary" className="mb-3" onClick={handleAddBoard}>
                게시글 등록
            </Button>

            <Form className="mb-4">
                <Form.Group className="mb-3">
                    <Form.Control
                        type="text"
                        placeholder="게시글 제목 검색"
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                    />
                </Form.Group>
            </Form>

            <div className="mb-3 text-color">
                <span>{boards.length} 게시글</span>
            </div>

            <Table striped bordered hover>
                <thead>
                    <th>공지사항</th>
                    <th>작성자</th>
                </thead>
                <tbody>
                    <tr>
                        <td>코딩테스트몬스터에 오신 것을 환영합니다.</td>
                        <td>관리자</td>
                    </tr>
                    <tr>
                        <td>이용방법</td>
                        <td>관리자</td>
                    </tr>
                </tbody>
            </Table>

            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>작성일</th>
                    </tr>
                </thead>
                <tbody>
                    {boards.map((board) => (
                        <tr key={board.boardId}>
                            <td onClick={() => handleBoardClick(board.boardId)} style={{ cursor: 'pointer' }}>
                                {board.title}
                            </td>
                            <td>{board.member.nickname}</td> {/* 작성자의 닉네임 */}
                            <td>{formatDate(board.createDate)}</td> {/* 작성일 표시 */}
                        </tr>
                    ))}
                </tbody>
            </Table>

            <CustomPagination
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={handlePageChange}
            />
        </div>
    );
};

export default BoardList;