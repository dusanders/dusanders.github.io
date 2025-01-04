import React from 'react';
import logo from './logo.svg';
import './App.scss';
import { IntroSection } from './sections/intro/Intro';
import { ILoggerContext } from './context/logger/Logger';
import { IRouteContext } from './context/route/RouteContext';
import { Navbar } from './components/navbar/Navbar';
import { createTheme, CssBaseline, ThemeProvider } from '@mui/material';
import { AboutSection } from './sections/about/About';
import { ILocaleContext } from './context/locale/Locale';
import { WorkSection } from './sections/work/Work';
import { PassionSection } from './sections/passion/Passion';
import { EducationSection } from './sections/education/Education';
import { FooterSection } from './sections/footer/Footer';

export interface AppProps {
  logger: ILoggerContext;
  router: IRouteContext;
  locale: ILocaleContext;
}

const defaultTheme = createTheme({
  palette: {
    mode: 'dark'
  },
})

function App(props: AppProps) {
  return (
    <div className='App'>
      <ThemeProvider theme={defaultTheme}>
        <CssBaseline />
        <Navbar
          locale={props.locale} />
        <IntroSection
          locale={props.locale} />
        <WorkSection
          locale={props.locale} />
        <AboutSection
          locale={props.locale} />
        <EducationSection />
        <PassionSection />
        <FooterSection />
      </ThemeProvider>
    </div>
  );
}

export default App;
