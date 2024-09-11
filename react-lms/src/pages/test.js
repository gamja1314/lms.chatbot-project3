import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { Modal, Button, Form } from 'react-bootstrap';

const MyPage = () => {
  const [memberInfo, setMemberInfo] = useState({
    username: '',
    nickname: '',
    email: '',
    rank: '',
    expPoints: 0,
  });
  const [showModal, setShowModal] = useState(false);
  const [password, setPassword] = useState('');
  const [confirmText, setConfirmText] = useState('');
  const [error, setError] = useState('');

  const navigate = useNavigate();

  useEffect(() => {
    // API 호출하여 사용자 정보 가져오기
    axios
      .get('/api/member/mypage')
      .then((response) => {
        setMemberInfo(response.data);
      })
      .catch((error) => {
        console.error("Error fetching member data:", error);
      });
  }, []);

  const handleChangePasswordClick = () => {
    navigate('/change-password'); // 비밀번호 변경 페이지로 이동
  };

  const handleWithdrawalClick = () => {
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setPassword('');
    setConfirmText('');
    setError('');
  };

  const handleWithdrawal = async () => {
    if (confirmText !== '회원탈퇴') {
      setError('올바른 확인 텍스트를 입력해주세요.');
      return;
    }

    try {
      await axios.post('/api/member/withdraw', { password });
      alert('회원 탈퇴가 완료되었습니다.');
      navigate('/'); // 홈페이지로 이동
    } catch (error) {
      setError('비밀번호가 올바르지 않거나 탈퇴 처리 중 오류가 발생했습니다.');
    }
  };

  return (
    <div className='container-fluid'>
      <div className='container'>
        <h1>My Page</h1>
        <p><strong>아이디:</strong> {memberInfo.username}</p>
        <p><strong>닉네임:</strong> {memberInfo.nickname}</p>
        <p><strong>Email:</strong> {memberInfo.email}</p>
        <p><strong>등급:</strong> {memberInfo.rank}</p>
        <p><strong>경험치:</strong> {memberInfo.expPoints}</p>
        <button onClick={handleChangePasswordClick} className="btn btn-primary me-2">비밀번호 변경</button>
        <button onClick={handleWithdrawalClick} className="btn btn-danger">회원 탈퇴</button>

        <Modal show={showModal} onHide={handleCloseModal}>
          <Modal.Header closeButton>
            <Modal.Title>회원 탈퇴</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form>
              <Form.Group className="mb-3">
                <Form.Label>비밀번호</Form.Label>
                <Form.Control
                  type="password"
                  placeholder="비밀번호를 입력하세요"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </Form.Group>
              <Form.Group className="mb-3">
                <Form.Label>확인을 위해 "회원탈퇴"를 입력하세요</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="회원탈퇴"
                  value={confirmText}
                  onChange={(e) => setConfirmText(e.target.value)}
                />
              </Form.Group>
              {error && <p className="text-danger">{error}</p>}
            </Form>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseModal}>
              취소
            </Button>
            <Button variant="danger" onClick={handleWithdrawal}>
              탈퇴 확인
            </Button>
          </Modal.Footer>
        </Modal>
      </div>
    </div>
  );
};

export default MyPage;