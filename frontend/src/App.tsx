import React from 'react';
import logo from './logo.svg';
import './App.scss';
import { IntroSection } from './sections/intro/Intro';
import { ILoggerContext } from './context/logger/Logger';
import { IRouteContext } from './context/route/RouteContext';
import { Navbar } from './components/navbar/Navbar';
import { AboutSection } from './sections/about/About';
import { ILocaleContext } from './context/locale/Locale';
import { WorkSection } from './sections/work/Work';
import { PassionSection } from './sections/passion/Passion';
import { EducationSection } from './sections/education/Education';
import { FooterSection } from './sections/footer/Footer';
import { Socials } from './sections/socials/Socials';
import { Location } from './sections/location/Location';
import { Experience } from './sections/work/Experience';

export interface AppProps {
  logger: ILoggerContext;
  router: IRouteContext;
  locale: ILocaleContext;
}

function App(props: AppProps) {
  return (
    <div className='App'>
    </div>
  );
}

export default App;
