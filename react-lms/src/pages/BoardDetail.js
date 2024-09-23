import React, { useState, useEffect, useCallback } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Button, Card, Container } from 'react-bootstrap';
import api from '../components/Api';
import { useAuth } from '../AuthContext';

const BoardDetail = () => {
    const { boardId } = useParams(); // URL에서 게시글 ID를 가져옴
    const navigate = useNavigate();
    const [board, setBoard] = useState(null);
    const { username } = useAuth();

    // 게시글 세부 정보 가져오기
    const fetchBoardDetail = useCallback(async () => {
        try {
            const response = await api.get(`/api/board/${boardId}`);
            setBoard(response.data);
        } catch (error) {
            console.error('Error fetching board detail:', error);
        }
    }, [boardId]);

    useEffect(() => {
        fetchBoardDetail();
    }, [fetchBoardDetail]);

    useEffect(() => {
        // localStorage에 username 저장
        if (username) {
            localStorage.setItem('username', username);
        }
    }, [username]);

    if (!board) {
        return <div>Loading...</div>;
    }

    console.log("board.member.nickname:", board.member.nickname);
    console.log("localStorage username:", localStorage.getItem('username'));

    // 게시글 수정 페이지로 이동
    const handleEdit = () => {
        navigate(`/board/edit/${boardId}`);
    };

    return (
        <Container className="mt-5">
            <Card className="shadow-sm" style={{ backgroundColor: '#3c3f41', color: '#ffffff' }}>
                <Card.Header style={{ backgroundColor: '#33373b' }}>
                    <h2>{board.title}</h2>
                    <small>작성자: {board.member.nickname} | 작성일: {new Date(board.createDate).toLocaleString()}</small>
                </Card.Header>
                <Card.Body>
                    <Card.Text>{board.content}</Card.Text>
                </Card.Body>
                <Card.Footer>
                    <Button variant="secondary" onClick={() => navigate(-1)} style={{ backgroundColor: '#5a6268', borderColor: '#5a6268' }}>목록으로</Button>
                    {board.username === localStorage.getItem('username') && (
                        <Button variant="primary" onClick={handleEdit} className="ms-2" style={{ backgroundColor: '#007bff', borderColor: '#007bff' }}>
                            수정
                        </Button>
                    )}
                </Card.Footer>
            </Card>
        </Container>
    );
};

export default BoardDetail;
