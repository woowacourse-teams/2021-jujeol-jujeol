const EditButton = () => {
  return (
    <button type="button" aria-label="내 리뷰 글 수정하기 버튼">
      <svg width="32px" height="32px" viewBox="0 0 100 100" fill="grey">
        <circle cx="30" cy="50" r="6" />
        <circle cx="50" cy="50" r="6" />
        <circle cx="70" cy="50" r="6" />
      </svg>
    </button>
  );
};

export default EditButton;
