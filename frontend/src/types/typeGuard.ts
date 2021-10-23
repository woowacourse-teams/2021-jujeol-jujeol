export const isMouseEvent = (event?: Event): event is MouseEvent => {
  return event?.type === 'mousedown' || event?.type === 'mouseup';
};

export const isKeyboardEvent = (event?: Event): event is KeyboardEvent => {
  return event?.type === 'keydown' || event?.type === 'keyup';
};
