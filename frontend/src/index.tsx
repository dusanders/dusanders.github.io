import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { RouteContext, RouteContextProvider } from './context/route/RouteContext';
import { LoggerContext, LoggerContextProvider } from './context/logger/Logger';
import { LocaleContext, LocaleContextProvider } from './context/locale/Locale';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <LoggerContextProvider>
    <LoggerContext.Consumer>
      {logger => (
        <LocaleContextProvider>
          <LocaleContext.Consumer>
            {locale => (
              <RouteContextProvider
                logger={logger}>
                <RouteContext.Consumer>
                  {router => (
                    <App
                      locale={locale}
                      logger={logger}
                      router={router} />
                  )}
                </RouteContext.Consumer>
              </RouteContextProvider>
            )}
          </LocaleContext.Consumer>
        </LocaleContextProvider>
      )}
    </LoggerContext.Consumer>
  </LoggerContextProvider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
