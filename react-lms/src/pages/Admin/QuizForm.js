import React, { useState, useEffect, useCallback, useRef } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Form, Button } from 'react-bootstrap';
import axios from 'axios';
import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';

const QuizForm = () => {
    const { quizId } = useParams();
    const navigate = useNavigate();
    const editorRef = useRef();
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
            // 에디터에 기존 내용 설정
            editorRef.current?.getInstance().setHTML(response.data.content);
        } catch (error) {
            console.error('Error fetching quiz:', error);
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

    const handleSubmit = async (e) => {
        e.preventDefault();
        const content = editorRef.current?.getInstance().getHTML();
        try {
            const quizData = { ...quiz, content };
            if (quizId) {
                await axios.put(`/api/quiz/edit/${quizId}`, quizData);
            } else {
                await axios.post('/api/quiz/edit', quizData);
            }
            navigate('/admin/quiz');
        } catch (error) {
            console.error('Error saving quiz:', error);
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
                    <Editor
                        ref={editorRef}
                        initialValue={quiz.content}
                        previewStyle="vertical"
                        height="300px"
                        initialEditType="wysiwyg"
                        useCommandShortcut={true}
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