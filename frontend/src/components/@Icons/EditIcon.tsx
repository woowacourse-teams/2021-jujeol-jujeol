import { COLOR } from 'src/constants';

const EditIcon = ({ width = '32px', height = '32px', color = COLOR.WHITE }: IconProps) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 32 32"
      fill={color}
      width={width}
      height={height}
      aria-labelledby="edit-icon-title edit-icon-desc"
    >
      <title id="edit-icon-title">수정</title>
      <desc id="edit-icon-desc">종이 위에 펜이 올라가 있는 아이콘</desc>
      <rect
        x="12.3"
        y="7.7"
        width="18"
        height="6"
        rx="0.79"
        transform="translate(-1.32 18.2) rotate(-45)"
      />
      <path d="M9.85,21.52l1.54-4.64a.51.51,0,0,1,.84-.2l3.09,3.09a.51.51,0,0,1-.2.84l-4.64,1.54A.5.5,0,0,1,9.85,21.52Z" />
      <path d="M24.59,29.46h-20a2,2,0,0,1-2-2v-20a2,2,0,0,1,2-2h10a2,2,0,0,1,0,4h-8V25.46H22.59v-8a2,2,0,1,1,4,0v10A2,2,0,0,1,24.59,29.46Z" />
    </svg>
  );
};

export default EditIcon;
