import { MouseEventHandler, useContext, useEffect, useRef, useState } from 'react';
import { useMutation, useQuery } from 'react-query';
import { useHistory, useParams } from 'react-router-dom';

import UserContext from 'src/contexts/UserContext';
import API from 'src/apis/requests';
import useNoticeToInputPreference from 'src/hooks/useInputPreference';
import useShowMoreContent from 'src/hooks/useShowMoreContent';

import { properties } from './propertyData';

import GoBackButton from 'src/components/@shared/GoBackButton/GoBackButton';
import RangeWithIcons from 'src/components/RangeWithIcons/RangeWithIcons';
import Review from 'src/components/Review/Review';
import Property from 'src/components/Property/Property';
import Skeleton from 'src/components/@shared/Skeleton/Skeleton';
import DrinksDetailDescriptionSkeleton from 'src/components/Skeleton/DrinksDetailDescriptionSkeleton';

import {
  Section,
  PreferenceSection,
  Image,
  DescriptionSection,
  Container,
  Description,
  ShowMoreButton,
  FoldButton,
  ImageWrapper,
} from './styles';
import { COLOR, ERROR_MESSAGE, MESSAGE, PATH, PREFERENCE } from 'src/constants';

const defaultDrinkDetail = {
  name: 'name',
  englishName: 'english name',
  imageUrl: '',
  category: {
    id: 0,
    name: '',
    key: '',
  },
  description: '',
  alcoholByVolume: 0,
  preferenceRate: 0.0,
  preferenceAvg: 0.0,
};

const DrinksDetailPage = () => {
  const { id: drinkId } = useParams<{ id: string }>();

  const history = useHistory();

  const pageContainerRef = useRef<HTMLImageElement>(null);
  const preferenceRef = useRef<HTMLDivElement>(null);
  const descriptionRef = useRef<HTMLParagraphElement>(null);

  const [currentPreferenceRate, setCurrentPreferenceRate] = useState(
    defaultDrinkDetail.preferenceRate
  );
  const [isShowImageFull, setIsShowImageFull] = useState(false);

  const isLoggedIn = useContext(UserContext)?.isLoggedIn;

  useEffect(() => {
    pageContainerRef.current?.scrollIntoView();
  }, []);

  const { data: { data: drink = defaultDrinkDetail } = {}, isLoading } = useQuery(
    'drink-detail',
    () => API.getDrink<string>(drinkId),
    {
      retry: 0,
      onSuccess: ({ data }) => {
        setCurrentPreferenceRate(data.preferenceRate);
      },
    }
  );

  const {
    name,
    englishName,
    imageResponse,
    category: { key: categoryKey },
    alcoholByVolume,
    preferenceAvg,
    description,
  }: Drink.DetailItem = drink;

  const { isShowMore, isContentOpen, onOpenContent, onCloseContent } = useShowMoreContent(
    descriptionRef,
    description
  );

  const { mutate: updatePreference } = useMutation(
    () => {
      if (currentPreferenceRate === 0) {
        return API.deletePreference<string>(drinkId);
      }

      return API.postPreference<string, { preferenceRate: number }>(drinkId, {
        preferenceRate: currentPreferenceRate,
      });
    },
    {
      onError: (error: { code: number; message: string }) => {
        alert(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);
      },
    }
  );

  const { isBlinked, setIsBlinked, observePreferenceSection } = useNoticeToInputPreference({
    targetRef: preferenceRef,
  });

  const setPreferenceRate = (value: number) => {
    setCurrentPreferenceRate(value);
  };

  const moveToLoginPage = () => {
    if (confirm(MESSAGE.LOGIN_REQUIRED_TO_UPDATE_PREFERENCE)) {
      history.push(PATH.LOGIN);
    }
  };

  const onCheckLoggedIn = () => {
    setIsBlinked(false);
    if (!isLoggedIn) {
      moveToLoginPage();
    }
  };

  const onUpdatePreference = () => {
    if (isLoggedIn) {
      updatePreference();
    }
  };

  const onNoticeToInputPreference: MouseEventHandler<HTMLButtonElement> = () => {
    if (!isLoggedIn) {
      moveToLoginPage();
      return;
    }

    observePreferenceSection();
  };

  const showButton = () => {
    return isContentOpen ? (
      <FoldButton type="button" onClick={onCloseContent}>
        접기
      </FoldButton>
    ) : (
      <ShowMoreButton type="button">...더보기</ShowMoreButton>
    );
  };

  const onImageSizeIncrease = () => {
    setIsShowImageFull(true);
  };

  const onImageSizeReduce = () => {
    setIsShowImageFull(false);
  };

  return (
    <Container ref={pageContainerRef}>
      <GoBackButton color={COLOR.BLACK_900} />
      {isLoading ? (
        <Skeleton width="100" height="30rem" />
      ) : (
        <ImageWrapper>
          <Image
            src={imageResponse.medium}
            alt={name}
            loading="lazy"
            onMouseDown={onImageSizeIncrease}
            onMouseUp={onImageSizeReduce}
            onTouchStart={onImageSizeIncrease}
            onTouchEnd={onImageSizeReduce}
          />
        </ImageWrapper>
      )}

      <Section isShowImageFull={isShowImageFull}>
        <PreferenceSection ref={preferenceRef} isBlinked={isBlinked}>
          <h3>
            {currentPreferenceRate
              ? `당신의 선호도는? ${currentPreferenceRate} 점`
              : '선호도를 입력해주세요'}
          </h3>
          <RangeWithIcons
            color={COLOR.YELLOW_300}
            max={PREFERENCE.MAX_VALUE}
            step={PREFERENCE.STEP}
            value={currentPreferenceRate}
            setValue={setPreferenceRate}
            disabled={!isLoggedIn}
            onTouchStart={onCheckLoggedIn}
            onClick={onCheckLoggedIn}
            onTouchEnd={onUpdatePreference}
            onMouseUp={onUpdatePreference}
          />
          <p>다른 사람들은 평균적으로 {preferenceAvg.toFixed(1) ?? '0'}점을 줬어요</p>
        </PreferenceSection>

        <DescriptionSection>
          {isLoading && <DrinksDetailDescriptionSkeleton />}
          <h2>{name}</h2>
          <p>
            {englishName === '' ? `(${alcoholByVolume}%)` : `(${englishName}, ${alcoholByVolume}%)`}
          </p>

          <ul>
            {properties.map((property) => {
              const { Icon, content } = property.getProperty({
                categoryKey,
                alcoholByVolume,
              });

              return (
                <li key={property.id}>
                  <Property Icon={Icon} title={property.name} content={content} />
                </li>
              );
            })}
          </ul>

          <Description
            isShowMore={isShowMore}
            isContentOpen={isContentOpen}
            onClick={onOpenContent}
          >
            <p ref={descriptionRef}>{description}</p>
            {isShowMore && showButton()}
          </Description>
        </DescriptionSection>

        <Review
          drinkId={drinkId}
          drinkName={name}
          preferenceRate={currentPreferenceRate}
          onNoticeToInputPreference={onNoticeToInputPreference}
        />
      </Section>
    </Container>
  );
};

export default DrinksDetailPage;
