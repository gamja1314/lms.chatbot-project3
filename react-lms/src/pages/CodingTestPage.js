import React from 'react';
import { Layout, Sidebar, Main, Header, Footer } from '@/components/layout';

const CodingTestPage = () => {
  return (
    <Layout>
      <Header>
        <h1>코딩테스트 문제풀이</h1>
      </Header>
      <div className="flex">
        <Sidebar>
          <ProblemList />
        </Sidebar>
        <Main>
          <ProblemDescription />
          <CodeEditor />
          <TestCases />
          <SubmitButton />
        </Main>
      </div>
      <Footer>
        <p>© 2024 코딩테스트 플랫폼</p>
      </Footer>
    </Layout>
  );
};

const ProblemList = () => {
  return (
    <div>
      <h2>문제 목록</h2>
      {/* 문제 목록 렌더링 로직 */}
    </div>
  );
};

const ProblemDescription = () => {
  return (
    <div>
      <h2>문제 설명</h2>
      {/* 선택된 문제 설명 렌더링 */}
    </div>
  );
};

const CodeEditor = () => {
  return (
    <div>
      <h2>코드 에디터</h2>
      {/* 코드 에디터 컴포넌트 */}
    </div>
  );
};

const TestCases = () => {
  return (
    <div>
      <h2>테스트 케이스</h2>
      {/* 테스트 케이스 목록 및 실행 결과 */}
    </div>
  );
};

const SubmitButton = () => {
  return (
    <button className="bg-blue-500 text-white p-2 rounded">
      제출하기
    </button>
  );
};

export default CodingTestPage;