interface Props {
  ariaLabel: string;
  onClick: () => void;
}

const EditButton = ({ ariaLabel, onClick }: Props) => {
  return (
    <button type="button" aria-label={ariaLabel} onClick={onClick}>
      <svg width="32px" height="32px" viewBox="0 0 100 100" fill="grey">
        <circle cx="30" cy="50" r="6" />
        <circle cx="50" cy="50" r="6" />
        <circle cx="70" cy="50" r="6" />
      </svg>
    </button>
  );
};

export default EditButton;
