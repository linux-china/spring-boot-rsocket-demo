import React from 'react';
import ReactDOM from 'react-dom';
import RSocketComponent from './App';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<RSocketComponent />, div);
  ReactDOM.unmountComponentAtNode(div);
});
