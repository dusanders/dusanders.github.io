import React, { Ref, RefObject, useEffect } from "react";

/**
 * Namespace containing general urls
 */
export namespace LinkConstants {
  /**
   * GitHub repo for the portfolio site
   */
  export const Github_Pages_Portfolio_Repo = "https://github.com/dusanders/dusanders.github.io"
  /**
   * My Kotlin Forza project
   */
  export const ForzaUtils_Android: string = "https://github.com/dusanders/forzautils_android"
  /**
   * Forza utility in React Native
   */
  export const ForzaUtils_ReactNative: string = "https://github.com/dusanders/forzautils_reactnative"
  /**
   * Forza Telemetry parser in TypeScript
   */
  export const ForzaTelemetryApi_TypeScript: string = "https://github.com/dusanders/ForzaTelemetryAPI_typescript"
  /**
   * My base GitHub page
   */
  export const MyGithubUrl: string = 'https://github.com/dusanders';
  /**
   * My portfolio site url
   */
  export const PortfolioUrl: string = 'https://dusanders.github.io/';
  /**
   * My LinkedIn profile url
   */
  export const LinkedInUrl: string = 'https://linkedin.com/in/dus-anders';
}

/**
 * Constant value to declare the url path selector
 * of page sections. This is used when the Router
 * context looks at the page url during a load operation
 */
export const UrlPathIdentifier = '#';
/**
 * Define the available section routes for
 * the web app
 */
export enum Sections {
  Intro = '#intro',
  About = '#about',
  Work = '#work',
  Passion = '#passion',
  Education = '#education'
}

/**
 * Define type for state update handler when using the
 * react 'useReducer()' as state management
 */
export type StateHandler<T> = (prev: T, next: Partial<T>) => T;

/**
 * Utility class to store various useful methods
 */
export class Utils {
  /**
   * Convert url segment to {@see Sections}
   * @param segment url string
   * @returns section, defaults to {@see Sections.Intro}
   */
  static urlToSection(segment: string) {
    switch (segment) {
      case Sections.Intro:
        return Sections.Intro
    }
    return Sections.Intro
  }

  /**
   * Generate a randomized strings
   * @returns Random string
   */
  static generateGuid() {
    let
      d = new Date().getTime(),
      d2 = (performance && performance.now && (performance.now() * 1000)) || 0;
    return 'xxxxx-xxxx-4xxx'.replace(/[xy]/g, c => {
      let r = Math.random() * 16;
      if (d > 0) {
        r = (d + r) % 16 | 0;
        d = Math.floor(d / 16);
      } else {
        r = (d2 + r) % 16 | 0;
        d2 = Math.floor(d2 / 16);
      }
      return (c == 'x' ? r : (r & 0x7 | 0x8)).toString(16);
    });
  }
}

/**
 * Hook event to get scroll Y position on a scroll event
 * @param cb callback function to receive the scroll position
 */
export function useScrollListener(cb: (scrollY: number) => void) {
  useEffect(() => {
    const evHandler = () => {
      cb(window.scrollY)
    }
    document.addEventListener('scroll', evHandler);
    return () => {
      document.removeEventListener('scroll', evHandler);
    }
  }, []);
}

/**
 * Hook that alerts clicks outside of the passed ref
 */
export function useOutsideOnClick(
  ref: RefObject<HTMLDivElement>,
  callback: () => void) {
  useEffect(() => {
    const cb = callback;
    /**
     * Alert if clicked on outside of element
     */
    function handleClickOutside(event: any) {
      if (ref.current && !ref.current.contains(event.target)) {
        cb();
      }
    }
    // Bind the event listener
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      // Unbind the event listener on clean up
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [ref]);
}