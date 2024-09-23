import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import ReactMarkdown from 'react-markdown';
import { Card, Badge, Spinner, Alert, Button } from 'react-bootstrap';

const ChallengeDetail = () => {
    const { id } = useParams();
    const [challenge, setChallenge] = useState(null);
    const [challengeStatus, setChallengeStatus] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchChallengeStatus = React.useCallback(async () => {
        try {
            const [challengeResponse, statusResponse] = await Promise.all([
                axios.get(`/api/challenges/${id}`),
                axios.get(`/api/challenges-user/${id}/status`)
            ]);
            setChallenge(challengeResponse.data);
            setChallengeStatus(statusResponse.data);
            setLoading(false);
        } catch (err) {
            setError('데이터를 불러오는 데 실패했습니다.');
            setLoading(false);
        }
    }, [id]);

    useEffect(() => {
        fetchChallengeStatus();
    }, [fetchChallengeStatus]);

    const handleParticipate = async () => {
        const isConfirmed = window.confirm("한 번 도전하면 다른 챌린지를 동시에 도전할 수 없습니다. 도전하시겠습니까?");
        
        if (isConfirmed) {
            try {
                const response = await axios.post(`/api/challenges-user/${id}`);
                if (response.status === 200) {
                    alert('챌린지에 성공적으로 참여하셨습니다!');
                    fetchChallengeStatus(); // 상태 업데이트를 위해 다시 데이터를 가져옵니다.
                }
            } catch (error) {
                console.error('챌린지 참여 중 오류 발생:', error);
                alert('챌린지 참여에 실패했습니다. 다시 시도해주세요.');
            }
        } else {
            console.log('사용자가 챌린지 참여를 취소했습니다.');
        }
    };
    
    if (loading) return (
        <div className="d-flex justify-content-center align-items-center" style={{height: '100vh'}}>
            <Spinner animation="border" role="status">
                <span className="visually-hidden">로딩 중...</span>
            </Spinner>
        </div>
    );

    if (error) return <Alert variant="danger">{error}</Alert>;
    if (!challenge) return <Alert variant="warning">챌린지를 찾을 수 없습니다.</Alert>;

    return (
        <div className='container mt-5'>
            <Card className="shadow-sm">
                <Card.Body>
                    <div className="d-flex justify-content-between align-items-center mb-4">
                        <Card.Title as="h1" className="mb-0 text-color">{challenge.title}</Card.Title>
                        {challengeStatus && (
                            challengeStatus.isParticipating ? (
                                <Badge bg="success">도전 중</Badge>
                            ) : challengeStatus.canParticipate ? (
                                <Button 
                                    variant="primary" 
                                    disabled={challenge.close}
                                    onClick={handleParticipate}
                                >
                                    도전하기
                                </Button>
                            ) : (
                                <Badge bg="warning">다른 챌린지 참여 중</Badge>
                            )
                        )}
                    </div>
                    <Card.Subtitle className="mb-3 text-white">
                        경험치: <Badge bg="primary">{challenge.expPoints} XP</Badge>
                    </Card.Subtitle>
                    <Card.Text as="div">
                        <ReactMarkdown className='markdown-body text-color'>{challenge.content}</ReactMarkdown>
                    </Card.Text>
                    {challenge.close && (
                        <Alert variant="info" className="mt-3">
                            이 챌린지는 마감되었습니다.
                        </Alert>
                    )}
                </Card.Body>
            </Card>
        </div>
    );
};

export default ChallengeDetail;