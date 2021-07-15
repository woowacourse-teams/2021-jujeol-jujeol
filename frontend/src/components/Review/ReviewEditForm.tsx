import { Form, Content, EditButton, DeleteButton } from './ReviewEditForm.styles';

const ReviewEditForm = () => {
  return (
    <Form>
      <h2>리뷰 수정하기</h2>
      <Content>
        <div>
          <span>21.07.12</span>
          <span>0/300</span>
        </div>
        <textarea value="내용이 들어 갑니다" />
      </Content>

      <EditButton>수정하기</EditButton>
      <DeleteButton type="button">삭제하기</DeleteButton>
    </Form>
  );
};

export default ReviewEditForm;
