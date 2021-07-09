import drinks from 'src/mock/drinks';
import { Title, ItemList, ItemImage, ItemInfo } from './styles';
import Card from 'src/components/Card/Card';

const HomePage = () => {
  return (
    <div>
      <ul>
        {Array.from({ length: 3 }).map((value, index) => (
          <li key={index} style={{ marginBottom: '1rem' }}>
            <section>
              <Title textAlign="center">
                <h2>오늘 이런 술 어때요?</h2>
                <p>회원님을 위해 준비했어요</p>
              </Title>

              <ItemList count={drinks.length}>
                {drinks.map(({ id, name, imageUrl, alcoholByVolume }) => (
                  <li key={id}>
                    <Card>
                      <ItemImage src={imageUrl} alt={name} />
                      <ItemInfo>
                        <h3>{name}</h3>
                        <p>{`${alcoholByVolume}%`}</p>
                      </ItemInfo>
                    </Card>
                  </li>
                ))}
              </ItemList>
            </section>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default HomePage;
