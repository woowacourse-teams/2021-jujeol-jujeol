const MESSAGE = {
  LOGIN_REQUIRED_TO_CREATE_REVIEW:
    '리뷰를 등록하기 위해서는 로그인이 필요합니다.\n로그인 화면으로 이동하시겠습니까?',
  LOGIN_REQUIRED_TO_UPDATE_PREFERENCE:
    '선호도를 등록하기 위해서는 로그인이 필요합니다.\n로그인 화면으로 이동하시겠습니까?',
  PAGE_ACCESS_NOT_ALLOWED: '접근 할 수 없는 페이지 입니다.',
  CONFIRM_DELETE_REVIEW: '리뷰를 삭제하시겠습니까?',
  CANNOT_REDO: '이 결정은 되돌릴 수 없습니다.',
};

const ERROR_MESSAGE: { [key: string]: string } = {
  DEFAULT: '에러가 발생했습니다. 다시 한 번 시도해주세요.',

  '1005': MESSAGE.LOGIN_REQUIRED_TO_UPDATE_PREFERENCE,

  '2007': '글은 하루에 한 번만 작성할 수 있습니다.',
};

export { ERROR_MESSAGE, MESSAGE };
