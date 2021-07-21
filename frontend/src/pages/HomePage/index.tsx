import { Title, ItemList, ItemImage, ItemInfo } from './styles';
import Card from 'src/components/Card/Card';
import { useQuery } from 'react-query';
import API from 'src/apis/requests';
import { PATH } from 'src/constants';
import { Link } from 'react-router-dom';
import { useContext } from 'react';
import UserContext from 'src/contexts/UserContext';
import MainHeader from 'src/components/Header/MainHeader';
import promotionImage from 'src/assets/jujeol-promotion-cheers.png';

interface Drinks {
  id: number;
  name: string;
  imageUrl: string;
  alcoholByVolume: number;
}

const HomePage = () => {
  const { data: { data: drinks } = [] } = useQuery('drinks', API.getDrinks, {
    retry: 1,
  });
  const { isLoggedIn, userData } = useContext(UserContext);

  return (
    <div>
      <MainHeader />
      <ul
        style={{ display: 'grid', gridTemplateColumns: 'minmax(320px, 420px)', gridRowGap: '1rem' }}
      >
        <li>
          <section>
            <Title textAlign="center">
              <h2>오늘 이런 술 어때요?</h2>
              <p>
                {isLoggedIn ? `${userData?.name} 님을 위해 준비했어요` : '주절주절이 엄선한 TOP7'}
              </p>
            </Title>

            <ItemList count={drinks?.length ?? 0}>
              {drinks?.map(({ id, name, imageUrl, alcoholByVolume }: Drinks) => (
                <li key={id}>
                  <Link to={`${PATH.DRINKS}/${id}`}>
                    <Card width="13rem" height="17rem">
                      <ItemImage src={imageUrl} alt={name} />
                      <ItemInfo>
                        <h3>{name}</h3>
                        <p>{`${alcoholByVolume}%`}</p>
                      </ItemInfo>
                    </Card>
                  </Link>
                </li>
              ))}
            </ItemList>
          </section>
        </li>
        <li>
          <section style={{ padding: '0 2rem' }}>
            <div style={{ display: 'flex', marginBottom: '1rem', justifyContent: 'space-between' }}>
              <h2 style={{ fontSize: '1.5rem', fontWeight: 'bold' }}>전체보기</h2>
              <Link to="#">{`더보기 >`}</Link>
            </div>
            <ul
              style={{
                display: 'grid',
                gridTemplateColumns: 'repeat(1, 1fr)',
                gridRowGap: '1rem',
              }}
            >
              {drinks?.slice(0, 3).map(({ id, name, imageUrl, alcoholByVolume }: Drinks) => (
                <li style={{ display: 'flex' }} key={id}>
                  <img
                    src={imageUrl}
                    alt={name}
                    width="88px"
                    style={{ backgroundColor: 'white' }}
                  />
                  <div style={{ marginLeft: '1rem' }}>
                    <h3>{name}</h3>
                    <p>{`도수 : ${alcoholByVolume}%`}</p>
                  </div>
                </li>
              ))}
            </ul>
          </section>
        </li>
        <li>
          <section>
            <h2 style={{ opacity: 0 }}>프로모션 이미지</h2>
            <img src={promotionImage} alt="이 건배사 어때요? 이멤버리멤버!" width="100%" />
          </section>
        </li>
        <li>
          <section>
            <Title textAlign="left" style={{ padding: '0 2rem' }}>
              <h2>막걸리 TOP7</h2>
              <p>비가 추적추적 내리는 날에는 막걸리!</p>
            </Title>

            <ItemList count={drinks?.length ?? 0}>
              {drinks?.map(({ id, name, imageUrl, alcoholByVolume }: Drinks) => (
                <li key={id}>
                  <Link to={`${PATH.DRINKS}/${id}`}>
                    <Card width="13rem" height="17rem">
                      <ItemImage src={imageUrl} alt={name} />
                      <ItemInfo>
                        <h3>{name}</h3>
                        <p>{`${alcoholByVolume}%`}</p>
                      </ItemInfo>
                    </Card>
                  </Link>
                </li>
              ))}
            </ItemList>
          </section>
        </li>
      </ul>
    </div>
  );
};

export default HomePage;
