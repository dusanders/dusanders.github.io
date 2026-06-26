import React, { createContext, useEffect, useReducer } from "react";

export enum SupportedThemes {
  light = "light",
  dark = "dark",
  terminal = "terminal",
  hardware = "hardware"
}

export interface IThemeContext {
  theme: SupportedThemes;
  changeTheme(theme: SupportedThemes): void;
}

export interface ThemedProps {
  children?: any;
}

interface ThemeState {
  theme: SupportedThemes;
}

export const ThemeContext = createContext({} as IThemeContext);

const defaultTheme = SupportedThemes.dark;

function isSupportedTheme(theme: string | undefined): theme is SupportedThemes {
  return Object.values(SupportedThemes).includes(theme as SupportedThemes);
}

function getInitialState(): ThemeState {
  const htmlTheme = document.documentElement.dataset.theme;
  return {
    theme: isSupportedTheme(htmlTheme) ? htmlTheme : defaultTheme
  };
}

export function ThemeContextProvider(props: ThemedProps) {
  const [state, setState] = useReducer((prev: ThemeState, next: Partial<ThemeState>) => {
    return {
      ...prev,
      ...next
    };
  }, getInitialState());

  useEffect(() => {
    document.documentElement.dataset.theme = state.theme;
  }, [state.theme]);

  return (
    <ThemeContext.Provider value={{
      theme: state.theme,
      changeTheme: (theme) => setState({
        theme
      })
    }}>
      {props.children}
    </ThemeContext.Provider>
  );
}
