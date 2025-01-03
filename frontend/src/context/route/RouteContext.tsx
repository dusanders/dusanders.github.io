import React, { createContext, useContext, useReducer } from "react";
import { Sections, StateHandler, Utils } from "../../types/types";
import { ILoggerContext } from "../logger/Logger";

export interface IRouteContext {
  currentSection: Sections;
  goToSection(section: Sections): void;
}

export interface RouteContextProps {
  children?: any;
  logger: ILoggerContext;
}

export const RouteContext = createContext({} as IRouteContext);

interface RouteState {
  section: Sections
}

function getInitialState(): RouteState {
  const initialState: RouteState = {
    section: Sections.Intro
  }
  const urlSection = document.URL.substring(document.URL.indexOf('#'));
  if (urlSection) {
    initialState.section = Utils.urlToSection(urlSection);
  }
  return initialState;
}

export function RouteContextProvider(props: RouteContextProps) {
  const TAG = 'RouteContextProvider';
  let initial = getInitialState();

  const [state, setState] = useReducer<StateHandler<RouteState>>((prev, next) => {
    return {
      ...prev,
      ...next
    }
  }, initial);

  return (
    <RouteContext.Provider value={{
      currentSection: state.section,
      goToSection: (section) => setState({
        section: section
      })
    }}>
      {props.children}
    </RouteContext.Provider>
  )
}