import React from 'react';

const HomePage = () => {
  return (
    <main>
      <ul>
        {Array.from({ length: 10 }).map((value, index) => (
          <section key={index}>
            <h2>오늘 이런 술 어때요?</h2>
            <p>회원님을 위해 준비했어요</p>
            <div>
              <div>
                <img src="" alt="사진" />
                <p>코젤다크</p>
                <p>5%</p>
              </div>
              <div>...</div>
            </div>
          </section>
        ))}
      </ul>
    </main>
  );
};

export default HomePage;
