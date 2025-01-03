import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';
import { mockLocale, MockLogger, MockRouter } from './types/testMocks';


test('renders learn react link', () => {
  render(
    <App
      locale={mockLocale}
      logger={new MockLogger()}
      router={new MockRouter()} />
  );
  const linkElement = screen.getByText(/learn react/i);
  expect(linkElement).toBeInTheDocument();
});
