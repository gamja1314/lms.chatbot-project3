import React, { useState, useEffect, useCallback } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Form, Button } from 'react-bootstrap';
import MdEditor from 'react-markdown-editor-lite';
import MarkdownIt from 'markdown-it';
import 'react-markdown-editor-lite/lib/index.css';
import { useAuth } from '../AuthContext';
import api from '../components/Api';

const mdParser = new MarkdownIt();

const BoardForm = () => {
    const { boardId } = useParams(); // 게시글 ID를 URL에서 받아옴
    const navigate = useNavigate();
    const { user } = useAuth();
    const [board, setBoard] = useState({
        title: '',
        content: '',
        nickname: '' 
    });

    // 게시글 정보 불러오기 (수정 시)
    const fetchBoard = useCallback(async () => {
        if (!boardId) return; // 게시글 ID가 없으면 무시 (새로운 게시글 작성)
        try {
            const response = await api.get(`/api/board/${boardId}`);
            setBoard(response.data);
        } catch (error) {
            console.error('게시글 불러오기 중 오류 발생:', error);
        }
    }, [boardId]);

    useEffect(() => {
        fetchBoard();
    }, [fetchBoard]);

    // 폼 필드 값 변경 시 호출
    const handleChange = (e) => {
        const { name, value } = e.target;
        setBoard(prevBoard => ({
            ...prevBoard,
            [name]: value
        }));
    };

    // Markdown Editor의 내용 변경 시 호출
    const handleEditorChange = ({ text }) => {
        setBoard(prevBoard => ({
            ...prevBoard,
            content: text
        }));
    };

    // 폼 제출 시 호출 (게시글 등록/수정 처리)
    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            let response;
            
            if (boardId) {
                // 게시글 수정
                response = await api.put(`/api/board/update/${boardId}`, board);
            } else {
                // user가 없는 경우 (관리자 모드 또는 예외 처리)
                if (!user || !user.memberNum) {
                    alert("사용자 정보가 없습니다. 로그인 후 다시 시도해주세요.");
                    return;
                }

                // 새 게시글 작성 (user 정보와 함께 보냄)
                const requestBody = {
                    ...board,
                    memberNum: user.memberNum  // 사용자 ID를 게시글 정보와 함께 전송
                };

                response = await api.post(`/api/board/create`, requestBody);
            }
            console.log('서버 응답:', response.data);
            navigate('/board'); // 게시글 목록 페이지로 리디렉션
        } catch (error) {
            console.error('게시글 저장 중 오류 발생:', error);
            alert('게시글 저장에 실패했습니다. 다시 시도해주세요.');
        }
    };

    return (
        <div className="container mt-4">
            <h1>{boardId ? '게시글 수정' : '게시글 작성'}</h1>
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                    <Form.Label>제목</Form.Label>
                    <Form.Control
                        type="text"
                        name="title"
                        value={board.title}
                        onChange={handleChange}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>내용</Form.Label>
                    <MdEditor
                        style={{ height: '300px' }}
                        renderHTML={(text) => mdParser.render(text)}
                        onChange={handleEditorChange}
                        value={board.content}
                    />
                </Form.Group>
                <Button variant="primary" type="submit">
                    {boardId ? '수정' : '등록'}
                </Button>
            </Form>
        </div>
    );
};

export default BoardForm;
