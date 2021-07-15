import { Title, ItemList, ItemImage, ItemInfo } from './styles';
import Card from 'src/components/Card/Card';
import { useQuery } from 'react-query';
import API from 'src/apis/requests';
import { PATH } from 'src/constants';
import { Link } from 'react-router-dom';

interface Drinks {
  id: number;
  name: string;
  imageUrl: string;
  alcoholByVolume: number;
}

const HomePage = () => {
  const { data = { data: [], count: 0 } } = useQuery('drinks', API.getDrinks, {
    retry: 1,
  });
  const { data: drinks, count } = data;

  return (
    <div>
      <ul>
        <li>
          <section>
            <Title textAlign="center">
              <h2>오늘 이런 술 어때요?</h2>
              <p>회원님을 위해 준비했어요</p>
            </Title>

            <ItemList count={count}>
              {drinks.map(({ id, name, imageUrl, alcoholByVolume }: Drinks) => (
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
