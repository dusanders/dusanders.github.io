import React, { createContext } from "react";

export interface ILoggerContext {
  log(tag: string, message: string): void;
  debug(tag: string, message: string): void;
  warn(tag: string, message: string): void;
  error(tag: string, message: string): void;
}

export interface LoggerContextProps {
  children?: any;
}

/**
 * React Context instance declaration
 */
export const LoggerContext = createContext({} as ILoggerContext);

/**
 * React functional component implementing a Context Provider to register
 * the logger with React's context.
 * @param props {@see LoggerContextProps}
 * @returns Functional React Context component
 */
export function LoggerContextProvider(props: LoggerContextProps) {
  const logger = new Logger()
  return (
    <LoggerContext.Provider value={{
      log: (tag, msg) => logger.log(tag, msg),
      warn: (tag, msg) => logger.warn(tag, msg),
      debug: (tag, msg) => logger.debug(tag, msg),
      error: (tag, msg) => logger.error(tag, msg)
    }}>
      {props.children}
    </LoggerContext.Provider>
  )
}

/**
 * Convience method to return an instance of the logger context
 * @returns {@see ILoggerContext} implementation
 */
export function withLogger(): ILoggerContext {
  return new Logger();
}

/**
 * Class to implement logger context functionality
 */
export class Logger implements ILoggerContext {
  log(tag: string, message: string): void {
    console.log(`${tag} :: ${message}`);
  }
  debug(tag: string, message: string): void {
    console.debug(`${tag} :: ${message}`);
  }
  warn(tag: string, message: string): void {
    console.warn(`${tag} :: ${message}`);
  }
  error(tag: string, message: string): void {
    console.error(`${tag} :: ${message}`);
  }
}