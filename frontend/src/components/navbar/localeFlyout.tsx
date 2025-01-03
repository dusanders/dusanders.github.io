import React from "react";
import { LocaleItem } from "./localeItem";
import { ILocaleContext, SupportedLocales } from "../../context/locale/Locale";

export interface LocaleFlyoutProps {
  locale: ILocaleContext;
}

export function LocaleFlyout(props: LocaleFlyoutProps) {
  return (
    <div className="locale-flyoutRoot">
      <LocaleItem
        onClick={() => {
          props.locale.changeLocale(SupportedLocales.en_US)
        }}
        isSelected={props.locale.locale === SupportedLocales.en_US}>
        {props.locale.strings.chooser.enUs}
      </LocaleItem>
      <LocaleItem
        onClick={() => {
          props.locale.changeLocale(SupportedLocales.knights_of_ni)
        }}
        isSelected={props.locale.locale === SupportedLocales.knights_of_ni}>
        {props.locale.strings.chooser.knightsOfNi}
      </LocaleItem>
    </div>
  )
}