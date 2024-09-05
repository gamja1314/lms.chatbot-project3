import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const [message, setMessage] = useState('Loading...');

  useEffect(() => {
    axios.get('http://localhost:8080/api/message')
      .then(response => {
        setMessage(response.data.content);
      })
      .catch(error => {
        console.error('Error fetching data: ', error);
        setMessage('Error loading message');
      });
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <h1>Spring Boot + React 연동 예제</h1>
        <p>
          Spring Boot에서 받은 메시지: <br/>
          <strong>{message}</strong>
        </p>
      </header>
    </div>
  );
}

export default App;