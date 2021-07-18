import { Title, ItemList, ItemImage, ItemInfo } from './styles';
import Card from 'src/components/Card/Card';
import { useQuery } from 'react-query';
import API from 'src/apis/requests';
import { PATH } from 'src/constants';
import { Link } from 'react-router-dom';
import notFoundImage from 'src/assets/default.png';
import { onImageError } from 'src/utils/error';
import { useContext } from 'react';
import UserContext from 'src/contexts/UserContext';

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
      <ul>
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
                      <ItemImage
                        src={imageUrl ?? notFoundImage}
                        alt={name}
                        onError={onImageError}
                      />
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
