import React, { createContext, useReducer } from "react";
import { Strings_enUS } from "./langs/enUs";
import { StateHandler } from "../../types/types";
import { Strings_KnightsOfNi } from "./langs/knightsOfNi";

export enum SupportedLocales {
  en_US,
  knights_of_ni
}

export interface Strings {
  myFirstName: string;
  myLastName: string;
  myJobTitle: string;
  about: string;
  work: string;
  passion: string;
  education: string;
  chooser: {
    enUs: string;
    knightsOfNi: string;
  };
  aboutSection: {
    title: string;
    desc: string;
  }
}

export interface ILocaleContext {
  locale: SupportedLocales;
  strings: Strings;
  changeLocale(locale: SupportedLocales): void;
}

export interface LocaleContextProps {
  children?: any;
}

export const LocaleContext = createContext({} as ILocaleContext);

interface LocaleState {
  currentLocale: SupportedLocales;
  strings: Strings;
}

const initialState: LocaleState = {
  currentLocale: SupportedLocales.en_US,
  strings: new Strings_enUS()
}

function localeToStrings(locale: SupportedLocales) {
  switch (locale) {
    case SupportedLocales.en_US:
      return new Strings_enUS();
    case SupportedLocales.knights_of_ni:
      return new Strings_KnightsOfNi();
    default:
      return new Strings_enUS();
  }
}

export function LocaleContextProvider(props: LocaleContextProps) {
  const [state, setState] = useReducer<StateHandler<LocaleState>>((prev, next) => {
    if (next.currentLocale !=  undefined
      && next.currentLocale != prev.currentLocale) {
      next.strings = localeToStrings(next.currentLocale);
    }
    return {
      ...prev,
      ...next
    }
  }, initialState);

  return (
    <LocaleContext.Provider value={{
      locale: state.currentLocale,
      strings: state.strings,
      changeLocale: (locale) => {
        setState({
          currentLocale: locale
        });
      }
    }}>
      {props.children}
    </LocaleContext.Provider>
  )
}