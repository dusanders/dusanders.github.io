import { Strings_enUS } from "../context/locale/langs/enUs";
import { ILocaleContext, SupportedLocales } from "../context/locale/Locale";
import { ILoggerContext } from "../context/logger/Logger";
import { IRouteContext } from "../context/route/RouteContext";
import { Sections } from "./types";

export class MockLogger implements ILoggerContext {
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
export class MockRouter implements IRouteContext {
  currentSection: Sections = Sections.Intro;
  mock_setSection(section: Sections) {
    this.currentSection = section;
  };
  goToSection(section: Sections): void {
    this.currentSection = section
  }
}
export const mockLocale: ILocaleContext = {
  locale: SupportedLocales.en_US,
  strings: new Strings_enUS(),
  changeLocale: () => { }
}