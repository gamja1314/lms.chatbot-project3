import React, { useState, useRef, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Controlled as CodeMirror } from 'react-codemirror2';
import axios from 'axios';

import 'codemirror/lib/codemirror.css';
import 'codemirror/theme/material.css';
import 'codemirror/mode/javascript/javascript';

const CodingTestPage = ({ username }) => {
  const [code, setCode] = useState("// Write your code here\nconsole.log('Hello, World!');");
  const [output, setOutput] = useState("");
  const [problem, setProblem] = useState(null);
  const [isPublic, setIsPublic] = useState(false);
  const iframeRef = useRef(null);
  const { quizId } = useParams();

  const submitQuiz = async () => {
    try {
      const response = await axios.post('/api/quiz/submit', null, {
        params: {
          quizId,
          answer: code,
          isPublic,
          username
        }
      });
      
      alert(response.data); // 서버로부터 받은 결과 메시지를 알림으로 표시
    } catch (error) {
      console.error('퀴즈 제출 중 오류 발생:', error);
      alert('퀴즈 제출에 실패했습니다. 다시 시도해주세요.');
    }
  };
  
  useEffect(() => {
    const fetchProblem = async () => {
      try {
        const response = await axios.get(`http://localhost:8282/api/quiz/detail/${quizId}`)
        setProblem(response.data);
      } catch (error) {
        console.error('Error fetching problem:', error);
      }
    };

    fetchProblem();
  }, [quizId]);

  useEffect(() => {
    const iframe = iframeRef.current;
    if (iframe) {
      iframe.srcdoc = `
        <html>
          <head>
            <script>
              function runCode(code) {
                try {
                  console.log = function(msg) { 
                    window.parent.postMessage(msg, '*'); 
                  };
                  eval(code);
                } catch (error) {
                  window.parent.postMessage('Error: ' + error.message, '*');
                }
              }
            </script>
          </head>
          <body></body>
        </html>
      `;
    }
  }, []);

  useEffect(() => {
    const handleMessage = (event) => {
      setOutput(prevOutput => prevOutput + event.data + '\n');
    };
    window.addEventListener('message', handleMessage);
    return () => window.removeEventListener('message', handleMessage);
  }, []);

  const runCode = () => {
    setOutput("");
    const iframe = iframeRef.current;
    if (iframe && iframe.contentWindow) {
      iframe.contentWindow.runCode(code);
    }
  };

  return (
    <div className="container-fluid vh-100 d-flex flex-column" style={{ backgroundColor: '#263238', color: '#B0BEC5' }}>
      <div className='container'>
        <div className="row flex-grow-1">
          {/* Problem Display */}
          <div className="col-md-6 p-4" style={{ borderRight: '1px solid #37474F' }}>
            <h2 className="mb-4">Problem</h2>
            {problem ? (
              <>
                <h3>{problem.title}</h3>
                <p>{problem.content}</p>
              </>
            ) : (
              <p>Loading problem...</p>
            )}
          </div>

          {/* Code Editor and Output */}
          <div className="col-md-6 p-4 d-flex flex-column">
            <h2 className="mb-4">Code Editor</h2>
            <div>
              <CodeMirror
                value={code}
                options={{
                  mode: 'javascript',
                  theme: 'material',
                  lineNumbers: true
                }}
                onBeforeChange={(editor, data, value) => setCode(value)}
              />
            </div>
            <div className="form-check mb-3">
              <input
                type="checkbox"
                checked={isPublic}
                onChange={(e) => setIsPublic(e.target.checked)}
                className="form-check-input"
                id="publicCheck"
              />
              <label className="form-check-label" htmlFor="publicCheck">
                정답 공개
              </label>
            </div>
            <div>
              <button onClick={runCode} className="btn btn-primary mx-3 mb-3">
                Run Code
              </button>
              <button onClick={submitQuiz} className='btn btn-secondary mb-3'>
                제출
              </button>
            </div>
            <div>
              <h3>Output:</h3>
              <pre className="p-2 rounded" style={{ backgroundColor: '#1E272C', color: '#B0BEC5' }}>{output}</pre>
            </div>
          </div>
        </div>
        <iframe 
          ref={iframeRef} 
          style={{display: 'none'}} 
          title="Code Execution Environment"
        ></iframe>
      </div>
    </div>
  );
};

export default CodingTestPage;