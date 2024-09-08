import React, { useState, useRef, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import Editor from "@monaco-editor/react";
import axios from 'axios';

const CodingTestPage = () => {
  const [code, setCode] = useState("// Write your code here\nconsole.log('Hello, World!');");
  const [output, setOutput] = useState("");
  const [problem, setProblem] = useState(null);
  const iframeRef = useRef(null);
  const { quizId } = useParams();

  useEffect(() => {
    const fetchProblem = async () => {
      try {
        const response = await axios.get(`http://localhost:8282/api/quiz/${quizId}`)
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

  const handleEditorChange = (value) => {
    setCode(value);
  };

  const runCode = () => {
    setOutput("");
    const iframe = iframeRef.current;
    if (iframe && iframe.contentWindow) {
      iframe.contentWindow.runCode(code);
    }
  };

  return (
    <div className="container-fluid">
      <div className="row vh-100">
        {/* Problem Display */}
        <div className="col-md-6 p-4 border-end">
          <h2 className="mb-4">Problem</h2>
          {problem ? (
            <>
              <h3>{problem.title}</h3>
              <p>{problem.description}</p>
              <h4>Example Input:</h4>
              <pre className="bg-light p-2 rounded">{problem.exampleInput}</pre>
              <h4>Example Output:</h4>
              <pre className="bg-light p-2 rounded">{problem.exampleOutput}</pre>
            </>
          ) : (
            <p>Loading problem...</p>
          )}
        </div>

        {/* Code Editor and Output */}
        <div className="col-md-6 p-4 d-flex flex-column">
          <h2 className="mb-4">Code Editor</h2>
          <div className="flex-grow-1 mb-3">
            <Editor
              height="100%"
              defaultLanguage="javascript"
              value={code}
              onChange={handleEditorChange}
            />
          </div>
          <button onClick={runCode} className="btn btn-primary mb-3">
            Run Code
          </button>
          <div>
            <h3>Output:</h3>
            <pre className="bg-light p-2 rounded">{output}</pre>
          </div>
        </div>
      </div>
      <iframe 
        ref={iframeRef} 
        style={{display: 'none'}} 
        title="Code Execution Environment"
      ></iframe>
    </div>
  );
};

export default CodingTestPage;