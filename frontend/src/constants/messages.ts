const MESSAGE = {
  LOGIN_REQUIRED_TO_CREATE_REVIEW: '리뷰를 등록하기 위해서는 로그인이 필요합니다.',
  LOGIN_REQUIRED_TO_CREATE_REVIEW_SUB_MESSAGE: '로그인 화면으로 이동하시겠습니까?',
  LOGIN_REQUIRED_TO_UPDATE_PREFERENCE: '선호도를 등록하기 위해서는 로그인이 필요합니다.',
  LOGIN_REQUIRED_TO_UPDATE_PREFERENCE_SUB_MESSAGE: '로그인 화면으로 이동하시겠습니까?',
  LOGIN_REQUIRED_FOR_MYPAGE: '마이페이지를 이용하시려면 로그인 해주세요',

  LOGIN_SUCCESS: '로그인 되었습니다.',
  LOGOUT_SUCCESS: '로그아웃 되었습니다.',

  PAGE_ACCESS_NOT_ALLOWED: '접근 할 수 없는 페이지 입니다.',
  CONFIRM_DELETE_REVIEW: '리뷰를 삭제하시겠습니까?',
  CANNOT_REDO: '이 결정은 되돌릴 수 없습니다.',

  CREATE_REVIEW_SUCCESS: '리뷰가 등록되었습니다.',
  EDIT_REVIEW_SUCCESS: '리뷰를 수정하였습니다.',
  DELETE_REVIEW_SUCCESS: '리뷰를 삭제하였습니다.',

  EDIT_PROFILE_SUCCESS: '프로필을 수정하였습니다.',

  COPY_LINK_SUCCESS: '클립보드에 복사 되었습니다.',
  COPY_LINK_FAILED: '링크를 복사하지 못했습니다.',
  KAKAO_SHARE_FAILED: '카카오톡 공유하기를 이용할 수 없습니다. 링크 공유를 이용해 주세요.',

  SEARCH_INPUT_CANNOT_EMPTY: '공백이 아닌 검색어를 입력해주세요.',
};

// API error
const ERROR_MESSAGE: { [key: string]: string } = {
  DEFAULT: '에러가 발생했습니다. 다시 한 번 시도해주세요.',

  '1002': '카카오 로그인 서버에 문제가 발생했습니다. 잠시후 다시 시도해주세요.',
  '1005': '권한이 없는 유저입니다',
  '1007': '닉네임은 한글,영어,숫자,-,_ 만 입력할 수 있습니다.',
  '1010': '다른 사람이 사용중인 닉네임입니다. 다른 닉네임을 사용해주세요.',

  '2005': '존재하지 않는 리뷰입니다.',
  '2007': '글은 하루에 한 번만 작성할 수 있습니다.',
  '2008': '리뷰는 1글자 이상 300글자 이하로 작성해주어야 합니다.',
};

const MESSAGE_TYPE_EMOJI = {
  ERROR: '🚨',
  CONFIRM: '✅',
};

export { ERROR_MESSAGE, MESSAGE, MESSAGE_TYPE_EMOJI };
