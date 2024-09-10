import React, { useState, useEffect, useCallback } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Form, Button } from 'react-bootstrap';
import axios from 'axios';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';

const QuizForm = () => {
    const { quizId } = useParams();
    const navigate = useNavigate();
    const [quiz, setQuiz] = useState({
        title: '',
        content: '',
        quizRank: '',
        correct: '',
    });

    const fetchQuiz = useCallback(async () => {
        try {
            const response = await axios.get(`/api/quiz/detail/${quizId}`);
            setQuiz(response.data);
        } catch (error) {
            console.error('퀴즈 불러오기 중 오류 발생:', error);
        }
    }, [quizId]);

    useEffect(() => {
        if (quizId) {
            fetchQuiz();
        }
    }, [quizId, fetchQuiz]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setQuiz(prevQuiz => ({
            ...prevQuiz,
            [name]: value
        }));
    };

    const handleContentChange = (content) => {
        setQuiz(prevQuiz => ({
            ...prevQuiz,
            content: content
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const encodedQuiz = {
                title: encodeURIComponent(quiz.title),
                content: encodeURIComponent(quiz.content),
                correct: encodeURIComponent(quiz.correct),
                quizRank: quiz.quizRank
            };

            const queryString = new URLSearchParams(encodedQuiz).toString();

            if (quizId) {
                // 퀴즈 수정 로직
                await axios.post(`/api/quiz/edit/${quizId}?${queryString}`);
            } else {
                // 퀴즈 등록 로직
                await axios.put(`/api/quiz/edit?${queryString}`);
            }
            navigate('/admin/quiz'); // 퀴즈 등록/수정 후 이동할 페이지
        } catch (error) {
            console.error('퀴즈 저장 중 오류 발생:', error);
        }
    };

    return (
        <div className="container mt-4">
            <h1>{quizId ? '퀴즈 수정' : '퀴즈 등록'}</h1>
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                    <Form.Label>제목</Form.Label>
                    <Form.Control
                        type="text"
                        name="title"
                        value={quiz.title}
                        onChange={handleChange}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>내용</Form.Label>
                    <ReactQuill
                        theme="snow"
                        value={quiz.content}
                        onChange={handleContentChange}
                        style={{ height: '300px', marginBottom: '50px' }}
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>난이도</Form.Label>
                    <Form.Select
                        name="quizRank"
                        value={quiz.quizRank}
                        onChange={handleChange}
                        required
                    >
                        <option value="">선택하세요</option>
                        <option value="A">A</option>
                        <option value="B">B</option>
                        <option value="C">C</option>
                        <option value="D">D</option>
                        <option value="E">E</option>
                    </Form.Select>
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>정답</Form.Label>
                    <Form.Control
                        type="text"
                        name="correct"
                        value={quiz.correct}
                        onChange={handleChange}
                        required
                    />
                </Form.Group>
                <Button variant="primary" type="submit">
                    {quizId ? '수정' : '등록'}
                </Button>
            </Form>
        </div>
    );
};

export default QuizForm;
